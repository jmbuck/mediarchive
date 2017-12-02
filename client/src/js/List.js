import React, { Component } from 'react'

import '../css/List.css'
import Show from './Show'
import Movie from './Movie'
import Book from './Book'

class List extends Component {
  constructor() {
    super()
    this.state = {
      movies: {},
      shows: {},
      books: {},
    }
  }

  componentWillMount = () => {
    this.setState({movies: this.props.movies, shows: this.props.shows, books: this.props.books})
  }

  componentWillReceiveProps = (nextProps) => {
    this.setState({movies: nextProps.movies, shows: nextProps.shows, books: nextProps.books})
  }

  getListName = (list, media) => {
    //Convert programmatic list name to readable list name
    if(list === 'completed') return 'completed'

    if(media === 'movies') {
      return 'plan to watch'
    } else if(media === 'books') {
      if(list === 'planning') return 'plan to read'
      if(list === 'current') return 'reading' 
    } else if(media === 'tv') {
      if(list === 'planning') return 'plan to watch'
      if(list === 'current') return 'watching'
    }
  }

  renderList = (list, media) => {
    if(media === 'movies') {
      return this.state.movies[list] && Object.keys(this.state.movies[list]).length > 0
      ? <ul className="media-list">{Object.keys(this.state.movies[list]).map((id, i) => 
          <Movie 
            key={i}
            index={i}
            movie={this.state.movies[list][id]}
            search={false}
            {...this.props}
          />)}
        </ul>
      : this.props.fetchedMovies ? <div>Your {this.getListName(list, media)} list is empty</div> : <div>Fetching list...</div>
    } else if(media === 'tv') {
      return this.state.shows[list] && Object.keys(this.state.shows[list]).length > 0
      ? <ul className="media-list">{Object.keys(this.state.shows[list]).map((id, i) => 
          <Show 
            key={i}
            index={i}
            show={this.state.shows[list][id]}
            search={false}
            {...this.props}
          />)}
        </ul>
      : this.props.fetchedShows ? <div>Your {this.getListName(list, media)} list is empty</div> : <div>Fetching list...</div>
    } else if(media === 'books') {
      return this.state.books[list] && Object.keys(this.state.books[list]).length > 0
      ? <ul className="media-list">{Object.keys(this.state.books[list]).map((id, i) => 
          <Book 
            key={i}
            index={i}
            book={this.state.books[list][id]}
            search={false}
            {...this.props}
          />)}
        </ul>
      : this.props.fetchedBooks ? <div>Your {this.getListName(list, media)} list is empty</div> : <div>Fetching list...</div>
    }
  }

  render() {
    const list = this.props.match.params.list;
    const media = this.props.media;

    return (
      <div className="List">
          {list === 'all' ? (
            <div>
              {media !== 'movies' ? this.renderList('current', media) : <div></div>}
              {this.renderList('completed', media)}
              {this.renderList('planning', media)}
            </div>
          )
          : this.renderList(list, media)}
      </div>
    );
  }
}

export default List;