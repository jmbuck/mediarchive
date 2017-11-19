import React, { Component } from 'react'

import '../css/Search.css'
import { TMDBKey } from '../keys'


class Search extends Component {
  constructor(props) {
    super(props)
    this.state = {
        results: [],
        fetched: false,
    }
  }

  componentWillMount() {
    const query = this.props.match.params.query
    const media = this.props.match.params.media
    const page = this.props.match.params.page
    this.fetchMedia(query, media, page)
  }

  componentWillReceiveProps(nextProps) {
    const query = nextProps.match.params.query
    const media = nextProps.match.params.media
    const page = nextProps.match.params.page

    const oldQuery = this.props.match.params.query
    const oldMedia = this.props.match.params.media
    const oldPage = this.props.match.params.page

    if(oldQuery !== query || oldPage !== page || oldMedia !== media) {
        this.fetchMedia(query, media, page)
    }
  }

  fetchMedia = (query, media, page) => {
    if(media === 'books') {
      this.fetchBooks(query, page);
    } else if(media === 'tv') {
      this.fetchShows(query, page);
    } else if(media === 'movies') {
      this.fetchMovies(query, page);
    } else {
      console.log('Invalid media, searching movies')
      this.props.history.push(`/search/movies/${query}/${page}`)
    }
  }

  fetchBooks = (query, page) => {
    console.log('Fetching books '+query)
  }

  fetchShows = (query, page) => {
    console.log('Fetching shows '+query)
  }

  fetchMovies = (query, page) => {
    console.log('Fetching movies '+query)
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

  render() {
    const query = this.props.match.params.query
    const media = this.props.match.params.media

    return (
      <div className="Search">
        {
          this.state.results && this.state.results.length > 0
            ? <ul>{this.state.results.map((result, i) => <li>{result.title}</li>)}</ul>
            : this.state.fetched ? <div>No results found.</div> : <div>Searching...</div>
        }
      </div>
    );
  }
}

export default Search;