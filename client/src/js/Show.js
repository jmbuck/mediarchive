import React, { Component } from 'react'
import { Route, Link } from 'react-router-dom'

import '../css/Show.css'
import { TMDBKey } from '../keys'
import noPoster from '../img/noPoster.png'

class Show extends Component {

  constructor(props) {
    super(props)
    this.state = {
      show: this.props.show,
      fetched: false,
      onForm: this.props.location.pathname !== `/search/tv/${this.props.match.params.query}/${this.props.match.params.page}/${this.props.show.id}` 
              && this.props.location.pathname !== `/tv/:list/${this.props.show.id}`,
      today: this.props.getToday(),
      formPath: this.props.search ? `/search/tv/${this.props.match.params.query}/${this.props.match.params.page}/${this.props.show.id}/add`
      : `/tv/${this.props.match.params.list}/${this.props.show.id}/edit`,
      infoPath: this.props.search ? `/search/tv/${this.props.match.params.query}/${this.props.match.params.page}/${this.props.show.id}`
      : `/tv/${this.props.match.params.list}/${this.props.show.id}`,
      listPath: this.props.search ? `/search/tv/${this.props.match.params.query}/${this.props.match.params.page}`
      : `/tv/${this.props.match.params.list}`,
      displayMessage: false,
      watching: false
    }
  }

  componentDidMount = () => {
    this.fetchShowInfo(this.state.show)
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

  getEpisodeCount = (show, currSeason, episode) => {
    currSeason = currSeason ? parseInt(currSeason.value, 10) : 0
    episode = episode ? parseInt(episode.value, 10) : 0
    let episodes = show.seasons.reduce((episodes, season) => {
      if(season.season_number !== 0 && season.season_number < currSeason) {
        episodes += season.episode_count
      }
      return episodes
    }, 0)
    episodes += currSeason ? episode : 0
    return episodes
  }

  renderShowForm = (show) => {
    return (
        <form className="ShowForm" onSubmit={(ev) => {
            ev.preventDefault();
            const completed = ev.target.category.value === 'completed'
            const info = {
              episodes_watched: completed ? show.number_of_episodes : this.getEpisodeCount(show, ev.target.curr_season, ev.target.curr_episode),
              seasons_watched: completed ? show.number_of_seasons : ev.target.curr_season ? parseInt(ev.target.curr_season.value, 10)-1 : 0,
              start_date: ev.target.start_date.value,
              end_date: ev.target.end_date.value,
              score: ev.target.score.value,
              mean_episode_run_time: show.episode_run_time ? Math.min(...show.episode_run_time) : 0,
              number_of_episodes: show.number_of_episodes,
              number_of_seasons: show.number_of_seasons,
            }
            const message = this.props.addShow(show, ev.target.category.value, info)
            this.setState({onForm: false, message, displayMessage: true}, () => {this.props.history.push(this.state.infoPath)})
        }}>
            <div className="fields">
                <div className="category">
                    <input type="radio" name="category" value="completed" defaultChecked={true} onChange={() => {this.setState({watching: false})}}/>Completed<br/>
                    <input type="radio" name="category" className="watching" value="current" defaultChecked={false} onChange={() => {this.setState({watching: true})}}/>Watching<br/>
                    <input type="radio" name="category" value="planning" defaultChecked={false} onChange={() => {this.setState({watching: false})}}/>Plan to Watch<br/>
                </div>
                <div className="optional">
                    <div className="start-date">
                    Start date: 
                    <a onClick={() => {
                        document.querySelector('.optional .start').value = this.state.today
                        }}>Insert Today
                    </a>
                    <input type="date" className="start" name="start_date" max={this.state.today}/>
                    </div>
                    <div className="end-date">
                    End date: 
                    <a onClick={() => {
                        document.querySelector('.optional .end').value = this.state.today
                        }}>Insert Today
                    </a>
                    <input type="date" className="end" name="end_date" max={this.state.today}/>
                    </div>

                    {
                    this.state.watching
                    ? (<div className="episodes-watched">
                        Current season:
                        <input type="number" name="curr_season" min="1" max={show.number_of_seasons ? show.number_of_seasons : 100}/>
                        Episode in current season:
                        <input type="number" name="curr_episode" min="1" max={show.seasons && show.seasons.length > 0 
                                                                                ? show.seasons.reduce((a, b) => Math.max(a, b.episode_count), 0)
                                                                                : 100 }/>
                      </div>)
                    : <div className="episodes-watched"></div>
                    }
                   
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
        this.state.displayMessage 
        ? <div className="message">{this.state.message}</div>
        : <div className="message"></div>
        }

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

  renderShow = (navProps, show, query, page, list) => {
    const path = `https://image.tmdb.org/t/p/w185${show.poster_path}`
    return (
      <div>
        <div className="white-content">
          {/*Displays tv show poster. If poster does not exist, show "poster does not exist" image*/
            show.poster_path 
            ? <img src={path} alt="TV show poster" />
            : <img src={noPoster} alt="TV show poster" />
          }
          <Route exact path={this.state.infoPath} render={(navProps) => {
            return this.renderShowInfo(show);
          }}/>

          <Route path={this.state.formPath} render={(navProps) => {
            return this.renderShowForm(show);
          }}/>

          <button className="btn btn-primary" 
            onClick={() => {
              this.setState({onForm: !this.state.onForm, displayMessage: false})
              if(this.state.onForm) {
                this.props.history.push(this.state.infoPath)
              } else {
                this.props.history.push(this.state.formPath)
              }
            }}
          >{this.state.onForm ? 'Info' : this.props.search ? 'Add' : 'Edit'}</button>

          {!this.props.search && <button className="btn btn-primary" 
            onClick={() => {
              if(list === 'all') {
                if(this.props.shows['completed'] && this.props.shows['completed'][`show-${show.id}`]) list = 'completed'
                if(this.props.shows['current'] && this.props.shows['current'][`show-${show.id}`]) list = 'current'
                if(this.props.shows['planning'] && this.props.shows['planning'][`show-${show.id}`]) list = 'planning'
              }
              this.props.deleteMedia('tv', list, show, this.props.update)
            }}
          >Delete</button>}
          
          <button className="btn btn-primary" 
            onClick={() => {
              this.setState({displayMessage: false})
              this.props.history.push(this.state.listPath)
            }}
          >Close</button>

        </div>

        <div className="black-overlay" onClick={() => {
          this.setState({displayMessage: false})
          this.props.history.push(this.state.listPath)
        }}></div>
      </div>
      )
  }

  render() {
    const query = this.props.match.params.query
    const page = this.props.match.params.page
    const list = this.props.match.params.list
    const show = {...this.state.show}
    const path = `https://image.tmdb.org/t/p/w185${show.poster_path}`

    return (
      <li className="Show">
       <Route path={this.state.infoPath} render={(navProps) => {
        if(this.state.fetched) {
          return this.renderShow(navProps, show, query, page, list) 
        }
        return <div>Fetching show info...</div>
       }}/>
        <Link 
          to={this.state.infoPath}
          onClick={() => {this.setState({onForm: false})}}
          className="preview"
        >
          <div className="preview">
            <div className="title" title={show.name}>{show.name}</div>
            {/*Displays show poster. If poster does not exist, show "poster does not exist" image*/
              show.poster_path 
              ? <img src={path} alt="TV show poster" />
              : <img src={noPoster} alt="TV show poster" />
            }
          </div>
        </Link>
      </li>
    )
  }
}

export default Show;