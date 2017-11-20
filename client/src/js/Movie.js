import React, { Component } from 'react'
import { Route } from 'react-router-dom'

import '../css/Movie.css'

class Movie extends Component {
  
  fetchMovieInfo = (movie) => {
   
  }

  renderMovieInfo = (navProps, movie) => {

  }

  render() {
    const media = this.props.match.params.media
    const query = this.props.match.params.query
    const page = this.props.match.params.page
    const movie = this.props.movie
    return (
      <li className="Movie">
        {movie.title}

        { this.props.search 
          ? <Route path={`/search/movies/${query}/${page}/${movie.id}`} render={(navProps) => {
              this.fetchMovieInfo(movie)
              return this.renderMovieInfo(navProps, movie) 
             }}/>
          : <Route path={`/movies/:list/${movie.id}`} render={(navProps) => {
              this.fetchMovieInfo(movie) 
              return this.renderMovieInfo(navProps, movie)
        }}/>
        }
      </li>
    );
  }
}

export default Movie;