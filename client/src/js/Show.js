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
      watching: this.props.search ? false : this.props.show.category === 'current'
    }
  }

  componentDidMount = () => {
    this.fetchShowInfo(this.state.show)
  }

  componentWillReceiveProps = (nextProps) => {
    if(this.props.show !== nextProps.show) {
      const show = {...this.state.show}
      show.score = nextProps.show.score
      show.category = nextProps.show.category
      show.start_date = nextProps.show.start_date
      show.end_date = nextProps.show.end_date
      show.episodes_watched = nextProps.show.episodes_watched
      show.seasons_watched = nextProps.seasons_watched
    
      this.setState({show})
    }
  }

  fetchShowInfo = (show, callback) => {
    fetch(`https://api.themoviedb.org/3/tv/${show.id}?api_key=${TMDBKey}&append_to_response=credits`)
    .then(response => response.json())
    .then(detailedShow => {
      if(!this.props.search) {
        detailedShow.score = show.score
        detailedShow.start_date = show.start_date
        detailedShow.end_date = show.end_date
        detailedShow.episode_runtime = show.episode_runtime
        detailedShow.episodes_watched = show.episodes_watched
        detailedShow.seasons_watched = show.seasons_watched
        detailedShow.category = show.category
      }
      this.setState({ 
        show: detailedShow,  
        fetched: true, 
      }, () => {
        if(callback) callback(detailedShow)
      })
    })
  }

  quickAdd = (show) => {
    if(!this.state.fetched) {
      this.fetchShowInfo(show, this.quickAdd)
    } else {
      const info = {
        episodes_watched: show.number_of_episodes,
        seasons_watched: show.number_of_seasons,
        start_date: '',
        end_date: '',
        score: 0,
        mean_episode_run_time: show.episode_run_time ? Math.min(...show.episode_run_time) : 0,
        number_of_episodes: show.number_of_episodes,
        number_of_seasons: show.number_of_seasons,
      }
      const message = this.props.addShow(show, 'completed', false, info)
      this.setState({onForm: false})
      this.props.displayMessage(message, true)
    }
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

  getCurrEpisodeAndSeason = (show) => {
    let episodes = show.episodes_watched
    let currSeason = 1;
    show.seasons.forEach((season) => {
      if(season.season_number !== 0 && episodes > season.episode_count) {
        currSeason++;
        episodes -= season.episode_count;
      }
    })
    return {currSeason, episode: episodes}
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
            const message = this.props.addShow(show, ev.target.category.value, this.props.search ? false : true, info)
            if(this.props.search) this.props.displayMessage(message, true)
            this.setState({onForm: false}, () => {this.props.history.push(this.state.listPath)})
        }}>
            <div className="fields">
                <div className="category">
                    <input type="radio" name="category" className="watching" value="current" defaultChecked={this.props.search ? false : show.category === 'current'} onChange={() => {this.setState({watching: true})}}/>Watching<br/>
                    <input type="radio" name="category" value="completed" defaultChecked={this.props.search ? true : show.category === 'completed'} onChange={() => {this.setState({watching: false})}}/>Completed<br/>
                    <input type="radio" name="category" value="planning" defaultChecked={this.props.search ? false : show.category === 'planning'} onChange={() => {this.setState({watching: false})}}/>Plan to Watch<br/>
                </div>
                <div className="dates">
                  <div className="start-date">
                  Start date: 
                  &nbsp;<a onClick={() => {
                      document.querySelector('.fields .start').value = this.state.today
                      }}>Insert Today
                  </a>
                  <input type="date" className="start form-control" name="start_date" max={this.state.today} defaultValue={!this.props.search ? show.start_date : null}/>
                  </div>
                  <div className="end-date">
                  End date: 
                  &nbsp;<a onClick={() => {
                      document.querySelector('.fields .end').value = this.state.today
                      }}>Insert Today
                  </a>
                  <input type="date" className="end form-control" name="end_date" max={this.state.today} defaultValue={!this.props.search ? show.end_date : null}/>
                  </div>
                  <select className="form-control" defaultValue={!this.props.search ? show.score ? show.score : "" : ""} name="score">
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
                <div className="extra">
                    {
                    this.state.watching
                    ? (<div className="episodes-watched">
                        Current season:
                        <input type="number" className="form-control" name="curr_season" defaultValue={!this.props.search ? this.getCurrEpisodeAndSeason(show).currSeason : null} min="1" max={show.number_of_seasons ? show.number_of_seasons : 100}/>
                        Episode in current season:
                        <input type="number" className="form-control" name="curr_episode" defaultValue={!this.props.search ? this.getCurrEpisodeAndSeason(show).episode : null} min="1" max={show.seasons && show.seasons.length > 0 
                                                                                ? show.seasons.reduce((a, b) => Math.max(a, b.episode_count), 0)
                                                                                : 100 }/>
                      </div>)
                    : <div className="episodes-watched"></div>
                    }
                  </div>
                </div>
            <button className="btn btn-primary" type="submit">{this.props.search ? 'Add' : 'Confirm'}</button>
        </form>
    )
  }

  renderShowInfo = (show) => {
    const current = this.getCurrEpisodeAndSeason(show)
    const first_air_date = new Date(show.first_air_date)
    const start_date = new Date(show.start_date)
    const end_date = new Date(show.end_date)
    first_air_date.setDate(first_air_date.getDate()+1)
    start_date.setDate(start_date.getDate()+1)
    end_date.setDate(end_date.getDate()+1)
    const options = {
        month: "long",
        year: "numeric",
        day: "numeric",
    }
    return (
      <div className="show-info">
        {
        !this.props.search 
        ? (<div>
          {
          show.start_date
          ? <div className="start-date">Start Date: {start_date.toLocaleDateString("en-US", options)}</div>
          : <div className="start-date">Start Date: -</div>
          }
          {
          show.end_date
          ? <div className="end-date">End Date: {end_date.toLocaleDateString("en-US", options)}</div>
          : <div className="end-date">End Date: -</div>
          }
          {
          current.episode
          ? <div className="current-episode">Current Episode: {current.episode}</div>
          : <div className="current-episode">Current Episode: -</div>
          }
          {
          current.currSeason
          ? <div className="current-season">Current Season: {current.currSeason}</div>
          : <div className="current-season">Current Season: -</div>
          }
          <br />
        </div>)
        : null
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
          <div className="main-content">
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
          </div>

          <div className="btns btn-group mr-2">
          <button className="btn btn-primary" 
            onClick={() => {
              if(this.state.onForm) {
                this.props.history.push(this.state.infoPath)
              } else {
                this.props.history.push(this.state.formPath)
              }
              this.setState({onForm: !this.state.onForm}, () => {
               if(this.props.search) this.props.displayMessage('', false)
              })
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
              if(this.props.search) this.props.displayMessage('', false)
              this.props.history.push(this.state.listPath)
            }}
          >Close</button>
          </div>

        </div>

        <div className="black-overlay" onClick={() => {
          if(this.props.search) this.props.displayMessage('', false)
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

            {
              !this.props.search 
              ? (<div>
                  <div className="percent">Percent Completed: {show.episodes_watched && show.number_of_episodes ? ((show.episodes_watched/show.number_of_episodes)*100).toFixed(2) : '-'}%</div>
                  <div className="score">Score: {show.score ? show.score : '-'}</div>
                </div>)
              : <div></div>
            }
          </div>
        </Link>
        {this.props.search && <button className="quick-add btn btn-primary" type="button" onClick={() => {
              this.quickAdd(show)
            }
        }>Quick add</button>}
      </li>
    )
  }
}

export default Show;