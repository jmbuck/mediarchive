import React, { Component } from 'react'
import { Route } from 'react-router-dom'

import '../css/Movie.css'
import { TMDBKey } from '../keys'

class Movie extends Component {

  constructor(props) {
    super(props)
    this.state = {
      movie: this.props.movie,
      fetched: false,
    }
  }

  fetchMovieInfo = (movie) => {
    fetch(`https://api.themoviedb.org/3/movie/${movie.id}?api_key=${TMDBKey}&append_to_response=credits`)
      .then(response => response.json())
      .then(detailedMovie => {
        this.setState({ 
          movie: detailedMovie,  
          fetched: true, 
        })
      })
  }

  showMovieInfo = () => {

  }

  renderMovieInfo = (navProps, movie) => {

  }

  render() {
    const media = this.props.match.params.media
    const query = this.props.match.params.query
    const page = this.props.match.params.page
    const movie = {...this.state.movie}
    const path = `https://image.tmdb.org/t/p/w185${movie.poster_path}`

    return (
      <li className="Movie">
        <div className="preview" onClick={this.showMovieInfo}>
          <div className="title" title={movie.title}>{movie.title}</div>
          {/*Displays movie poster. If poster does not exist, show "poster does not exist" image*/
            movie.poster_path 
            ? <img src={path} alt="movie poster" />
            : <img src="http://static01.mediaite.com/med/wp-content/uploads/gallery/possilbe-movie-pitches-culled-from-the-mediaite-comments-section/poster-not-available1.jpg" alt="movie poster" />
          }
        </div>
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