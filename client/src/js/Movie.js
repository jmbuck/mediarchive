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
      onForm: this.props.location.pathname !== `/search/movies/${this.props.match.params.query}/${this.props.match.params.page}/${this.props.movie.id}` 
              && this.props.location.pathname !== `/movies/:list/${this.props.movie.id}`
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

  renderMovieInfo = (movie) => {
    const release_date = new Date(movie.release_date)
    release_date.setDate(release_date.getDate()+1)
    const options = {
        month: "long",
        year: "numeric",
        day: "numeric",
    }
    return (
      <div className="movie-info">
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
    )
  }

  //TODO: Default values on edit form
  //Confirmation to user of a successful add
  //Actually implement submit
  renderMovieForm = (movie) => {
    let today = new Date()
    let dd = today.getDate()
    let mm = today.getMonth()+1
    let yyyy = today.getFullYear()
    if(dd < 10) dd = '0' + dd
    if(mm < 10) mm = '0' + mm
    today = `${yyyy}-${mm}-${dd}`

    return (
        <form className="MovieForm" onSubmit={(ev) => {
            ev.preventDefault();
            console.log('submit, add to list/edit')
        }}>
            <div className="fields">
                <div className="category">
                    <input type="radio" name="category" value="completed" defaultChecked={true}/>Completed<br/>
                    <input type="radio" name="category" value="planning" defaultChecked={false}/>Plan to Watch<br/>
                </div>
                <div className="optional">
                    <div className="date">
                    Date watched: 
                    <a onClick={() => {
                        document.querySelector('.optional input').value = today
                        }}>Insert Today
                    </a>
                    <input type="date" name="date" max={today}/>
                    </div>
                    <select name="score">
                        <option value="">-- Score --</option>
                        <option value="10">10</option>
                        <option value="9">9</option>
                        <option value="8">8</option>
                        <option value="7">7</option>
                        <option value="6">6</option>
                        <option value="5">5</option>
                        <option value="4">4</option>
                        <option value="3">3</option>
                        <option value="2">2</option>
                        <option value="1">1</option>
                    </select>
                </div>
            </div>
            <button className="btn btn-primary" type="submit">{this.props.search ? 'Add' : 'Confirm'}</button>
        </form>
    )
  }

  renderMovie = (navProps, movie, query, page) => {
    const path = `https://image.tmdb.org/t/p/w185${movie.poster_path}`
    return (
      <div>
        <div id="light" className="white-content">
          {/*Displays movie poster. If poster does not exist, show "poster does not exist" image*/
            movie.poster_path 
            ? <img src={path} alt="movie poster" />
            : <img src="http://static01.mediaite.com/med/wp-content/uploads/gallery/possilbe-movie-pitches-culled-from-the-mediaite-comments-section/poster-not-available1.jpg" alt="movie poster" />
          }
          <Route exact path={this.props.search ? `/search/movies/${query}/${page}/${movie.id}` : `/movies/:list/${movie.id}`} render={(navProps) => {
            return this.renderMovieInfo(movie);
          }}/>

          <Route path={this.props.search ? `/search/movies/${query}/${page}/${movie.id}/add` : `/movies/:list/${movie.id}/edit`} render={(navProps) => {
            return this.renderMovieForm(movie);
          }}/>

          <button className="btn btn-primary" 
            onClick={() => {
              this.setState({onForm: !this.state.onForm})
              if(this.state.onForm) {
                this.props.history.push(this.props.search ? `/search/movies/${query}/${page}/${movie.id}` : `/movies/:list/${movie.id}`)
              } else {
                this.props.history.push(this.props.search ? `/search/movies/${query}/${page}/${movie.id}/add` : `/movies/:list/${movie.id}/edit`)
              }
            }}
          >{this.state.onForm ? 'Info' : this.props.search ? 'Add' : 'Edit'}</button>

          <button className="btn btn-primary" 
            onClick={() => this.props.history.push(this.props.search ? `/search/movies/${query}/${page}` : `/movies/:list`)}
          >Close</button>

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
          ? <Route path={`/search/movies/${query}/${page}/${movie.id}`} render={(navProps) => {
              this.fetchMovieInfo(movie)
              return this.renderMovie(navProps, movie, query, page) 
             }}/>
          : <Route path={`/movies/:list/${movie.id}`} render={(navProps) => {
              this.fetchMovieInfo(movie) 
              return this.renderMovie(navProps, movie, query, page)
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