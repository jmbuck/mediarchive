import React, { Component } from 'react'
import { Route, Link } from 'react-router-dom'

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

  renderMovieInfo = (navProps, movie, query, page) => {
    const path = `https://image.tmdb.org/t/p/w185${movie.poster_path}`
    const release_date = new Date(movie.release_date)
    release_date.setDate(release_date.getDate()+1)
    const options = {
        month: "long",
        year: "numeric",
        day: "numeric",
    }
    return (
      <div>
        <div id="light" className="white-content">
          <button className="btn btn-primary" 
            onClick={() => this.props.history.push(this.props.search ? `/search/movies/${query}/${page}` : `/movies/:list`)}
          >Close</button>
          {/*Displays movie poster. If poster does not exist, show "poster does not exist" image*/
            movie.poster_path 
            ? <img src={path} alt="movie poster" />
            : <img src="http://static01.mediaite.com/med/wp-content/uploads/gallery/possilbe-movie-pitches-culled-from-the-mediaite-comments-section/poster-not-available1.jpg" alt="movie poster" />
          }
          {
          movie.overview 
          ? <div className="synopsis">Synopsis: {movie.overview}</div>
          : <div className="synopsis">No synopsis available.</div>
          }

          {
          movie.tagline
          ? <div className="tagline">Tagline: {movie.tagline}</div>
          : <div className="tagline"></div>
          }
          {
          movie.release_date 
          ? <div className="date">Released: {release_date.toLocaleDateString("en-US", options)}</div>
          : <div className="date">Unknown release date</div>
          }
          
          {movie.runtime ? <div className="duration">Duration: {this.props.formatDuration(movie.runtime)}</div> : <div className="duration"></div>}
          {movie.budget ? <div className="budget">Budget: ${movie.budget.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')}</div> : <div className="budget"></div>}
          {movie.revenue ? <div className="revenue">Revenue: ${movie.revenue.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')}</div> : <div className="revenue"></div>}

          {
          movie.genres && movie.genres.length > 0
          ? (
              <div className="genres">Genres:&nbsp;
              {
                  movie.genres.map((genre, i) => i !== movie.genres.length-1 ? <span key={i}>{genre.name}, </span> : <span key={i}>{genre.name}</span>)
              }
              </div>)
          : <div className="genres"></div>
          }

          {
          movie.imdb_id 
          ? <a href={`http://www.imdb.com/title/${movie.imdb_id}/`} target="_blank" rel="noopener noreferrer">IMDB Page</a>
          : <div></div>
          } 
        </div>
        <div id="fade" className="black-overlay" onClick={(ev) => {
          this.props.history.push(this.props.search ? `/search/movies/${query}/${page}` : `/movies/:list`)
        }}></div>
      </div>
      )
  }

  render() {
    const media = this.props.match.params.media
    const query = this.props.match.params.query
    const page = this.props.match.params.page
    const movie = {...this.state.movie}
    const path = `https://image.tmdb.org/t/p/w185${movie.poster_path}`

    return (
      <li className="Movie">
        { this.props.search 
          ? <Route exact path={`/search/movies/${query}/${page}/${movie.id}`} render={(navProps) => {
              this.fetchMovieInfo(movie)
              return this.renderMovieInfo(navProps, movie, query, page) 
             }}/>
          : <Route path={`/movies/:list/${movie.id}`} render={(navProps) => {
              this.fetchMovieInfo(movie) 
              return this.renderMovieInfo(navProps, movie, query, page)
        }}/>
        }
        <Link to={this.props.search ? `/search/movies/${query}/${page}/${movie.id}` : `/movies/:list/${movie.id}`}>
          <div className="preview" onClick={this.showMovieInfo}>
            <div className="title" title={movie.title}>{movie.title}</div>
            {/*Displays movie poster. If poster does not exist, show "poster does not exist" image*/
              movie.poster_path 
              ? <img src={path} alt="movie poster" />
              : <img src="http://static01.mediaite.com/med/wp-content/uploads/gallery/possilbe-movie-pitches-culled-from-the-mediaite-comments-section/poster-not-available1.jpg" alt="movie poster" />
            }
          </div>
        </Link>
      </li>
    );
  }
}

export default Movie;