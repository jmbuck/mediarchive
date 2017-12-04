import React, { Component } from 'react'

import '../css/Stats.css'
import { serverKey } from '../keys'

class Stats extends Component {
  
    constructor(props) {
        super(props)
        this.state = {
            stats: {},
            fetched: false
        }
    }

    componentDidMount = () => {
      this.fetchStats(this.props.user, this.props.auth)
    }

    componentWillReceiveProps = (nextProps) => {
        if(nextProps.user && nextProps.auth && !this.state.fetched) {
          this.fetchStats(nextProps.user, nextProps.auth)
        }
    }

    fetchStats = (user, auth) => {
      fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/getStats?username=${user}&key=${serverKey}`, {
        headers: {
            'Authorization': auth
        }}
        )
        .then(response => response.json())
        .then(stats => {  
            if(stats !== 'FORBIDDEN') {
                this.setState({stats})
            }
            this.setState({fetched: true})
        })
    }

    renderCompleted = (stats, media) => {
      if(media === 'books') {
        return (
          <div className="completed-stats">
            <div className="list-title">COMPELTED</div>
            <div>Total books: {stats.completed.total_books}</div>
            <div>Mean score: {stats.completed.mean_book_score}</div>
            <div>Total page count: {stats.completed.total_pages}</div>
            <div>Mean page count: {stats.completed.mean_pages}</div>
          </div>
        )
      } else if(media === 'tv') {
        return (
          <div className="completed-stats">
            <div className="list-title">COMPELTED</div>
            <div>Total shows: {stats.completed.total_shows}</div>
            <div>Mean score: {stats.completed.mean_show_score}</div>
            <div>Total episodes: {stats.completed.total_episodes}</div>
            <div>Total seasons: {stats.completed.total_seasons}</div>
            <div>Mean episode runtime: {this.props.formatDuration(stats.completed.mean_episode_runtime)}</div>
            <div>Total runtime: {this.props.formatDuration(stats.completed.total_show_runtime)}</div>
          </div>
        )
      } else if(media === 'movies') {
        return (
          <div className="completed-stats">
            <div className="list-title">COMPELTED</div>
            <div>Total movies: {stats.completed.total_movies}</div>
            <div>Mean score: {stats.completed.mean_movie_score}</div>
            <div>Total runtime: {this.props.formatDuration(stats.completed.total_movie_runtime)}</div>
            <div>Mean runtime: {this.props.formatDuration(stats.completed.mean_movie_runtime)}</div>
          </div>
        )
      }
    }

    renderCurrent = (stats, media) => {
      if(media === 'books') {
        return (
          <div className="current-stats">
            <div className="list-title">READING</div>
            <div>Total books: {stats.current.total_books}</div>
            <div>Mean score: {stats.current.mean_book_score}</div>
            <div>Total page count: {stats.current.total_pages} pages</div>
            <div>Mean page count: {stats.current.mean_pages} pages</div>
          </div>
        )
      } else if(media === 'tv') {
        return (
          <div className="current-stats">
            <div className="list-title">WATCHING</div>
            <div>Total shows: {stats.current.total_shows}</div>
            <div>Mean score: {stats.current.mean_show_score}</div>
            <div>Total episodes: {stats.current.total_episodes}</div>
            <div>Total seasons: {stats.current.total_seasons}</div>
            <div>Mean episode runtime: {this.props.formatDuration(stats.current.mean_episode_runtime)}</div>
            <div>Total runtime: {this.props.formatDuration(stats.current.total_show_runtime)}</div>
          </div>
        )
      } 
    }

    renderPlanning = (stats, media) => {
        if(media === 'books') {
          return (
            <div className="planning-stats">
              <div className="list-title">PLAN TO READ</div>
              <div>Total books: {stats.planning.total_books}</div>
              <div>Total page count: {stats.planning.total_pages}</div>
              <div>Mean page count: {stats.planning.mean_pages}</div>
            </div>
          )
        } else if(media === 'tv') {
          return (
            <div className="planning-stats">
              <div className="list-title">PLAN TO WATCH</div>
              <div>Total shows: {stats.planning.total_shows}</div>
              <div>Total episodes: {stats.planning.total_episodes}</div>
              <div>Total seasons: {stats.planning.total_seasons}</div>
              <div>Mean episode runtime: {this.props.formatDuration(stats.planning.mean_episode_runtime)}</div>
              <div>Total runtime: {this.props.formatDuration(stats.planning.total_show_runtime)}</div>
            </div>
          )
        } else if(media === 'movies') {
          return (
            <div className="planning-stats">
              <div className="list-title">PLAN TO WATCH</div>
              <div>Total movies: {stats.planning.total_movies}</div>
              <div>Total runtime: {this.props.formatDuration(stats.planning.total_movie_runtime)}</div>
              <div>Mean runtime: {this.props.formatDuration(stats.planning.mean_movie_runtime)}</div>
            </div>
          )
        }
    }

    renderStats = (stats) => {
        return (
            <div>
                <div className="book-stats">
                    <div className="media-name">BOOKS</div>
                    { 
                    stats.completed 
                    ? this.renderCompleted(stats, 'books')
                    : <div className="completed-stats"></div>
                    }
                    {
                    stats.current
                    ? this.renderCurrent(stats, 'books')
                    : <div className="current-stats"></div>
                    }
                    {
                    stats.planning
                    ? this.renderPlanning(stats, 'books')
                    : <div className="planning-stats"></div>
                    }
                </div>
                <div className="movie-stats">
                    <div className="media-name">MOVIES</div>
                    { 
                    stats.completed 
                    ? this.renderCompleted(stats, 'movies')
                    : <div className="completed-stats"></div>
                    }
                    {
                    stats.planning
                    ? this.renderPlanning(stats, 'movies')
                    : <div className="planning-stats"></div>
                    }
                </div>
                <div className="show-stats">
                    <div className="media-name">SHOWS</div>
                    { 
                    stats.completed 
                    ? this.renderCompleted(stats, 'tv')
                    : <div className="completed-stats"></div>
                    }
                    {
                    stats.current
                    ? this.renderCurrent(stats, 'tv')
                    : <div className="current-stats"></div>
                    }
                    {
                    stats.planning
                    ? this.renderPlanning(stats, 'tv')
                    : <div className="planning-stats"></div>
                    }
                </div>
            </div>
        )
    }

    render() {
        return (
        <div className="Stats">
            {
                this.state.fetched 
                ? this.renderStats(this.state.stats)
                : <div>Fetching stats for {this.props.user}...</div>
            }
        </div>
        );
    }
}

export default Stats;