import React, { Component } from 'react'
import { NavLink } from 'react-router-dom'

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

  update = () => {
    const list = this.props.match.params.list;
    const media = this.props.media;
    this.props.history.push(`/${media}/${list}`)
  }

  getListName = (list, media, proper = false) => {
    //Convert programmatic list name to readable list name
    if(list === 'completed') return proper ? 'Completed' : 'completed'

    if(media === 'movies') {
      return proper ? 'Plan to Watch' : 'plan to watch'
    } else if(media === 'books') {
      if(list === 'planning') return proper ? 'Plan to Read' : 'plan to read'
      if(list === 'current') return proper ? 'Reading' : 'reading' 
    } else if(media === 'tv') {
      if(list === 'planning') return proper ? 'Plan to Watch' : 'plan to watch'
      if(list === 'current') return proper ? 'Watching' : 'watching'
    }
  }

  getMediaName = (media) => {
    if(media === 'movies') return 'Movies'
    if(media === 'tv') return 'Shows'
    if(media === 'books') return 'Books'
  }

  renderList = (list, media) => {
    if(media === 'movies') {
      return this.state.movies[list] && Object.keys(this.state.movies[list]).length > 0
      ? <ul className="media-list">{Object.keys(this.state.movies[list]).map((id, i) => 
          <Movie 
            key={id}
            index={i}
            movie={this.state.movies[list][id]}
            search={false}
            update={this.update}
            {...this.props}
          />)}
        </ul>
      : this.props.fetchedMovies ? <div>Your {this.getListName(list, media)} list is empty</div> : <div>Fetching list...</div>
    } else if(media === 'tv') {
      return this.state.shows[list] && Object.keys(this.state.shows[list]).length > 0
      ? <ul className="media-list">{Object.keys(this.state.shows[list]).map((id, i) => 
          <Show 
            key={id}
            index={i}
            show={this.state.shows[list][id]}
            search={false}
            update={this.update}
            {...this.props}
          />)}
        </ul>
      : this.props.fetchedShows ? <div>Your {this.getListName(list, media)} list is empty</div> : <div>Fetching list...</div>
    } else if(media === 'books') {
      return this.state.books[list] && Object.keys(this.state.books[list]).length > 0
      ? <ul className="media-list">{Object.keys(this.state.books[list]).map((id, i) => 
          <Book 
            key={id}
            index={i}
            book={this.state.books[list][id]}
            search={false}
            update={this.update}
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
          <div className="list-nav btn-group mr-2" role="group" aria-label="list links">
            <NavLink to={`/${media}/all`} className="btn btn-primary">
              All {this.getMediaName(media)}
            </NavLink>
            {media !== 'movies' &&
            <NavLink to={`/${media}/current`} className="btn btn-primary">
              {this.getListName('current', media, true)}
            </NavLink>}
            <NavLink to={`/${media}/completed`} className="btn btn-primary">
              {this.getListName('completed', media, true)}
            </NavLink>
            <NavLink to={`/${media}/planning`} className="btn btn-primary">
              {this.getListName('planning', media, true)}
            </NavLink>
          </div>
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