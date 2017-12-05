import React, { Component } from 'react'
import { Link } from 'react-router-dom'

import '../css/Search.css'
import { TMDBKey, booksKey } from '../keys'
import Show from './Show'
import Movie from './Movie'
import Book from './Book'


class Search extends Component {
  constructor(props) {
    super(props)
    this.state = {
        results: [],
        fetched: false,
        displayMessage: false,
    }
  }

  componentWillMount() {
    const query = encodeURI(this.props.match.params.query)
    const media = this.props.match.params.media
    const page = this.props.match.params.page
    this.fetchMedia(query, media, page)
  }

  componentWillReceiveProps(nextProps) {
    const query = encodeURI(nextProps.match.params.query)
    const media = nextProps.match.params.media
    const page = nextProps.match.params.page

    const oldQuery = encodeURI(this.props.match.params.query)
    const oldMedia = this.props.match.params.media
    const oldPage = this.props.match.params.page

    if(oldQuery !== query || oldPage !== page || oldMedia !== media) { 
      this.setState({results: [], fetched: false}, () => {
        this.fetchMedia(query, media, page)
      })  
    }
  }

  fetchMedia = (query, media, page) => {
    if(isNaN(page)) {
      this.props.history.push(`/search/${media}/${query}/1`)
    } else {
      if(media === 'books') {
        this.fetchBooks(query, page);
      } else if(media === 'tv') {
        this.fetchShows(query, page);
      } else if(media === 'movies') {
        this.fetchMovies(query, page);
      } else {
        this.props.history.push(`/search/movies/${query}/${page}`)
      } 
    }
  }

  displayMessage = (message, display) => {
    this.setState({displayMessage: display, message})
  }

  fetchBooks = (query, page) => {
    page = parseInt(page, 10);
    if(query) {
      fetch(`https://www.googleapis.com/books/v1/volumes?q=${query}&startIndex=${20*(page-1)}&maxResults=20&country=US&key=${booksKey}`)
        .then(response => {
          return response.json()
        })
        .then(books => {
          if(books.error && page !== 1) {
            if(page !== 1) {
              this.props.history.push(`/search/${this.props.match.params.media}/${this.props.match.params.query}/1`)
            }
            return
          } 
    
          this.setState({ results: books.items, page, totalPages: Math.ceil(books.totalItems/20.0), fetched: true})
        })
    }
  }

  fetchShows = (query, page) => {
    if(query) {
      fetch(`https://api.themoviedb.org/3/search/tv?api_key=${TMDBKey}&query=${query}&page=${page}`)
          .then(response => {
              if(response.status === 422) {
                  this.props.history.push(`/search/${this.props.match.params.media}/${this.props.match.params.query}/1`)
                  return
              }
              return response.json()})
          .then(shows => {
              if(!shows) return
              this.setState({ results: shows.results, page, totalPages: shows.total_pages, fetched: true }, () => {
                  if(page > shows.total_pages) {
                      this.props.history.push(`/search/${this.props.match.params.media}/${this.props.match.params.query}/1`)
                  }
              })
          }
      )
    }
  }

  fetchMovies = (query, page) => {
    if(query) {
      fetch(`https://api.themoviedb.org/3/search/movie?api_key=${TMDBKey}&query=${query}&page=${page}`)
          .then(response => {
              if(response.status === 422) {
                  this.props.history.push(`/search/${this.props.match.params.media}/${this.props.match.params.query}/1`)
                  return
              }
              return response.json()})
          .then(movies => {
              if(!movies) return
              this.setState({ results: movies.results, page, totalPages: movies.total_pages, fetched: true }, () => {
                  if(page > movies.total_pages) {
                      this.props.history.push(`/search/${this.props.match.params.media}/${this.props.match.params.query}/1`)
                  }
              })
          }
      )
    }
  }

  renderResults = (media) => {
    if(media === 'movies') {
      return this.state.results && this.state.results.length > 0
      ? <ul className="search-results">{this.state.results.map((result, i) => 
          <Movie 
            key={i}
            index={i}
            movie={result}
            search={true}
            displayMessage={this.displayMessage}
            {...this.props}
          />)}
        </ul>
      : this.state.fetched ? <div>No results found.</div> : <div>Searching...</div>
    } else if(media === 'tv') {
      return this.state.results && this.state.results.length > 0
      ? <ul className="search-results">{this.state.results.map((result, i) => 
          <Show 
            key={i}
            index={i}
            show={result}
            search={true}
            displayMessage={this.displayMessage}
            {...this.props}
          />)}
        </ul>
      : this.state.fetched ? <div>No results found.</div> : <div>Searching...</div>
    } else if(media === 'books') {
      return this.state.results && this.state.results.length > 0
      ? <ul className="search-results">{this.state.results.map((result, i) =>
          <Book 
            key={i}
            index={i}
            book={result}
            search={true}
            displayMessage={this.displayMessage}
            {...this.props}
          />)}
        </ul>
      : this.state.fetched ? <div>No results found.</div> : <div>Searching...</div>
    }
  }

  render() {
    const media = this.props.match.params.media
    const query = this.props.match.params.query
    const page = this.props.match.params.page

    return (
      <div className="Search">
        {
        this.state.displayMessage 
        ? <div className="message">{this.state.message}</div>
        : <div className="message"></div>
        }
        {this.renderResults(media)}
        <div className="page">
          {(() => {
            if(page > 1) {
              return (
                <Link to={`/search/${media}/${query}/${parseInt(page, 10)-1}`}>
                    <button className="btn btn-primary" type="button"><i className="fa fa-arrow-left"></i></button>
                </Link>
              )
          }})()}
          {(() => {  
            if(page < this.state.totalPages) {
              return (
                <Link to={`/search/${media}/${query}/${parseInt(page, 10)+1}`}>
                    <button className="btn btn-primary" type="button"><i className="fa fa-arrow-right"></i></button>
                </Link>
              )
          }})()}
          <span className="page-number">Page {page} of {this.state.totalPages}</span>
        </div>
      </div>
    );
  }
}

export default Search;