import React, { Component } from 'react'
import { Route, Link } from 'react-router-dom'

import '../css/Show.css'
import { TMDBKey } from '../keys'

class Show extends Component {

  constructor(props) {
    super(props)
    this.state = {
      show: this.props.show,
      fetched: false,
      onForm: this.props.location.pathname !== `/search/tv/${this.props.match.params.query}/${this.props.match.params.page}/${this.props.show.id}` 
              && this.props.location.pathname !== `/tv/:list/${this.props.show.id}`
    }
  }

  fetchShowInfo = (show) => {
    fetch(`https://api.themoviedb.org/3/tv/${show.id}?api_key=${TMDBKey}&append_to_response=credits`)
    .then(response => response.json())
    .then(detailedShow => {
      this.setState({ 
        show: detailedShow,  
        fetched: true, 
      })
    })
  }

  renderShowForm = (show) => {
    let today = new Date()
    let dd = today.getDate()
    let mm = today.getMonth()+1
    let yyyy = today.getFullYear()
    if(dd < 10) dd = '0' + dd
    if(mm < 10) mm = '0' + mm
    today = `${yyyy}-${mm}-${dd}`

    return (
        <form className="ShowForm" onSubmit={(ev) => {
            ev.preventDefault();
            this.props.addShow(ev, show)
        }}>
            <div className="fields">
                <div className="category">
                    <input type="radio" name="category" value="completed" defaultChecked={true}/>Completed<br/>
                    <input type="radio" name="category" value="watching" defaultChecked={false}/>Watching<br/>
                    <input type="radio" name="category" value="planning" defaultChecked={false}/>Plan to Watch<br/>
                </div>
                <div className="optional">
                    <div className="start-date">
                    Start date: 
                    <a onClick={() => {
                        document.querySelector('.optional .start').value = today
                        }}>Insert Today
                    </a>
                    <input type="date" className="start" name="start_date" max={today}/>
                    </div>
                    <div className="end-date">
                    End date: 
                    <a onClick={() => {
                        document.querySelector('.optional .end').value = today
                        }}>Insert Today
                    </a>
                    <input type="date" className="end" name="end_date" max={today}/>
                    </div>
                    Current season:
                    <input type="number" name="curr_season" min="1" max={show.number_of_seasons ? show.number_of_seasons : 100}/>
                    Episode in current season:
                    <input type="number" name="curr_episodes" min="1" max={show.seasons && show.seasons.length > 0 
                                                                           ? show.seasons.reduce((a, b) => Math.max(a, b.episode_count), 0)
                                                                           : 100 }/>
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

  renderShowInfo = (show) => {
    const first_air_date = new Date(show.first_air_date)
    first_air_date.setDate(first_air_date.getDate()+1)
    const options = {
        month: "long",
        year: "numeric",
        day: "numeric",
    }
    return (
      <div className="show-info">
        {
        show.overview 
        ? <div className="synopsis">Synopsis: {show.overview}</div>
        : <div className="synopsis">No synopsis available.</div>
        }

        {
        show.first_air_date 
        ? <div className="date">Released: {first_air_date.toLocaleDateString("en-US", options)}</div>
        : <div className="date">Unknown release date</div>
        }
        
        {
          show.episode_run_time && show.episode_run_time.length > 0 
          ? <div className="runtime">Episode runtime: {this.props.formatDuration(Math.min(...show.episode_run_time))}</div> 
          : <div className="runtime"></div>
        }

        {
          show.number_of_episodes 
          ? <div className="episodes">Number of episodes: {show.number_of_episodes}</div> 
          : <div className="episodes"></div>
        }

        {
          show.number_of_seasons
          ? <div className="seasons">Number of seasons: {show.number_of_seasons}</div> 
          : <div className="seasons"></div>
        }

        {
        show.genres && show.genres.length > 0
        ? (<div className="genres">{show.genres.length === 1 ? 'Genre' : 'Genres'}:&nbsp;
            {
                show.genres.map((genre, i) => i !== show.genres.length-1 ? <span key={i}>{genre.name}, </span> : <span key={i}>{genre.name}</span>)
            }
            </div>)
        : <div className="genres"></div>
        }

        {
        show.homepage
        ? <a href={show.homepage} className="homepage">Homepage</a>
        : <div className="homepage"></div>
        }
      </div>
    )
  }

  renderShow = (navProps, show, query, page) => {
    const path = `https://image.tmdb.org/t/p/w185${show.poster_path}`
    return (
      <div>
        <div className="white-content">
          {/*Displays tv show poster. If poster does not exist, show "poster does not exist" image*/
            show.poster_path 
            ? <img src={path} alt="TV show poster" />
            : <img src="http://static01.mediaite.com/med/wp-content/uploads/gallery/possilbe-movie-pitches-culled-from-the-mediaite-comments-section/poster-not-available1.jpg" alt="TV show poster" />
          }
          <Route exact path={this.props.search ? `/search/tv/${query}/${page}/${show.id}` : `/tv/:list/${show.id}`} render={(navProps) => {
            return this.renderShowInfo(show);
          }}/>

          <Route path={this.props.search ? `/search/tv/${query}/${page}/${show.id}/add` : `/tv/:list/${show.id}/edit`} render={(navProps) => {
            return this.renderShowForm(show);
          }}/>

          <button className="btn btn-primary" 
            onClick={() => {
              this.setState({onForm: !this.state.onForm})
              if(this.state.onForm) {
                this.props.history.push(this.props.search ? `/search/tv/${query}/${page}/${show.id}` : `/tv/:list/${show.id}`)
              } else {
                this.props.history.push(this.props.search ? `/search/tv/${query}/${page}/${show.id}/add` : `/tv/:list/${show.id}/edit`)
              }
            }}
          >{this.state.onForm ? 'Info' : this.props.search ? 'Add' : 'Edit'}</button>

          <button className="btn btn-primary" 
            onClick={() => this.props.history.push(this.props.search ? `/search/tv/${query}/${page}` : `/tv/:list`)}
          >Close</button>

        </div>

        <div className="black-overlay" onClick={(ev) => {
          this.props.history.push(this.props.search ? `/search/tv/${query}/${page}` : `/tv/:list`)
        }}></div>
      </div>
      )
  }

  render() {
    const query = this.props.match.params.query
    const page = this.props.match.params.page
    const show = {...this.state.show}
    const path = `https://image.tmdb.org/t/p/w185${show.poster_path}`

    return (
      <li className="Show">
       <Route path={this.props.search ? `/search/tv/${query}/${page}/${show.id}` : `/tv/:list/${show.id}`} render={(navProps) => {
          if(!this.state.fetched) this.fetchShowInfo(show)
          return this.renderShow(navProps, show, query, page) 
       }}/>
        <Link 
          to={this.props.search ? `/search/tv/${query}/${page}/${show.id}` : `/tv/:list/${show.id}`}
          onClick={() => {this.setState({onForm: false})}}
          className="preview"
        >
          <div className="preview">
            <div className="title" title={show.name}>{show.name}</div>
            {/*Displays movie poster. If poster does not exist, show "poster does not exist" image*/
              show.poster_path 
              ? <img src={path} alt="movie poster" />
              : <img src="http://static01.mediaite.com/med/wp-content/uploads/gallery/possilbe-movie-pitches-culled-from-the-mediaite-comments-section/poster-not-available1.jpg" alt="movie poster" />
            }
          </div>
        </Link>
      </li>
    )
  }
}

export default Show;