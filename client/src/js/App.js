import React, { Component } from 'react'
import { Route, Redirect, Switch } from 'react-router-dom'

import Main from './Main'
import '../css/App.css'

class App extends Component {
  constructor() {
    super()
    this.state = {
      user: null,
      books: {},
      movies: {},
      shows: {},
      fetchedMovies: false,
      fetchedBooks: false,
      fetchedShows: false,
    }
  }

  componentDidMount = () => {
    if(this.signedIn()) {
      this.fetchMovieList(this.state.user)
      this.fetchShowList(this.state.user)
      this.fetchBookList(this.state.user)
    }
  }

  signedIn = () => {
    return true;
  }

  formatDuration = (totalTime) => {
    let minutes = totalTime
    let hours = Math.floor(minutes / 60) 
    minutes = minutes % 60
    let days = Math.floor(hours / 24) 
    hours = hours % 24
    let output = ""
    if(days) output += `${days} ${days === 1 ? 'day' : 'days'}, `
    if(hours) output += `${hours} ${hours === 1 ? 'hour' : 'hours'}, `
    if(minutes) {
      output += `${minutes} ${minutes === 1 ? 'minute' : 'minutes'}`
    } else {
      //Cut out ending comma and space
      output = output.substr(0, output.length-2)
    }
    return output
  }

  getToday = () => {
    let today = new Date()
    let dd = today.getDate()
    let mm = today.getMonth()+1
    let yyyy = today.getFullYear()
    if(dd < 10) dd = '0' + dd
    if(mm < 10) mm = '0' + mm
    return `${yyyy}-${mm}-${dd}`
  }

  isDuplicate = (item, media) => {
    if(media === 'movie') {
      const movies = {...this.state.movies}
      if(movies['completed'] && movies['completed'][`movie-${item.id}`]) {
        return `${item.title} already exists in your completed list!`
      } else if(movies['planning'] && movies['planning'][`movie-${item.id}`]) {
        return `${item.title} already exists in your plan to watch list!`
      }
    } else if(media === 'book') {
      const books = {...this.state.books}
      if(books['completed'] && books['completed'][`book-${item.id}`]) {
        return `${item.volumeInfo.title} already exists in your completed list!`
      } else if(books['planning'] && books['planning'][`book-${item.id}`]) {
        return `${item.volumeInfo.title} already exists in your plan to read list!`
      } else if(books['reading'] && books['reading'][`book-${item.id}`]) {
        return `${item.volumeInfo.title} already exists in your reading list!`
      }
    } else if(media === 'show') {
      const shows = {...this.state.shows}
      if(shows['completed'] && shows['completed'][`show-${item.id}`]) {
        return `${item.name} already exists in your completed list!`
      } else if(shows['planning'] && shows['planning'][`show-${item.id}`]) {
        return `${item.name} already exists in your plan to watch list!`
      } else if(shows['watching'] && shows['watching'][`show-${item.id}`]) {
        return `${item.name} already exists in your watching list!`
      }
    } 
    return false;
  }

  addMovie = (category, date, score, movie) => {
    const movies = {...this.state.movies}
    if(!movies[category]) {
      movies[category] = {}
    }
    
    let message = this.isDuplicate(movie, 'movie')
    if(!message) {
      movies[category][`movie-${movie.id}`] = {}
      movies[category][`movie-${movie.id}`].watched_date = date ? date : ""
      movies[category][`movie-${movie.id}`].score = score ? parseInt(score, 10) : 0
      movies[category][`movie-${movie.id}`].runtime = movie.runtime ? movie.runtime : 0
      movies[category][`movie-${movie.id}`].id = movie.id
      movies[category][`movie-${movie.id}`].title = movie.title
      movies[category][`movie-${movie.id}`].poster_path = movie.poster_path
      this.setState({movies})

      message = `${movie.title} successfully added to list!`
    }
    return message;
  }

  addShow = (show, category, info) => {
    const shows = {...this.state.shows}
    if(!shows[category]) {
      shows[category] = {}
    }
    
    let message = this.isDuplicate(show, 'show')
    if(!message) {
      shows[category][`show-${show.id}`] = {}
      shows[category][`show-${show.id}`].start_date = info.start_date ? info.start_date : ""
      shows[category][`show-${show.id}`].end_date = info.end_date ? info.end_date : ""
      shows[category][`show-${show.id}`].score = info.score ? parseInt(info.score, 10) : 0
      shows[category][`show-${show.id}`].mean_episode_run_time = info.mean_episode_run_time
      shows[category][`show-${show.id}`].episodes_watched = info.episodes_watched
      shows[category][`show-${show.id}`].seasons_watched = info.seasons_watched
      shows[category][`show-${show.id}`].number_of_episodes = info.number_of_episodes ? info.number_of_episodes : 0
      shows[category][`show-${show.id}`].number_of_seasons = info.number_of_seasons ? info.number_of_seasons : 0
      shows[category][`show-${show.id}`].id = show.id
      shows[category][`show-${show.id}`].name = show.name
      shows[category][`show-${show.id}`].poster_path = show.poster_path
      this.setState({shows})
      
      message = `${show.name} successfully added to list!`
    }
    return message;
  }

  addBook = (category, start_date, end_date, score, book) => {
    const books = {...this.state.books}
    if(!books[category]) {
      books[category] = {}
    }
    
    let message = this.isDuplicate(book, 'book')
    if(!message) {
      books[category][`book-${book.id}`] = {}
      books[category][`book-${book.id}`].start_date = start_date ? start_date : ""
      books[category][`book-${book.id}`].end_date = end_date ? end_date : ""
      books[category][`book-${book.id}`].score = score ? parseInt(score, 10) : 0
      books[category][`book-${book.id}`].pageCount = book.volumeInfo.printedPageCount ? book.volumeInfo.printedPageCount : 0
      books[category][`book-${book.id}`].id = book.id
      books[category][`book-${book.id}`].title = book.volumeInfo.title
      books[category][`book-${book.id}`].path = !book.volumeInfo.imageLinks ? null : book.volumeInfo.imageLinks.thumbnail ? book.volumeInfo.imageLinks.thumbnail : null
      this.setState({books})
      
      message = `${book.volumeInfo.title} successfully added to list!`
    }
    return message;
  }

  deleteMedia = (media, list, item, update) => {
    if(media === 'books') {
      const books = {...this.state.books}
      delete books[list][`book-${item.id}`]
      this.setState({ books }, () => {update()})
    } else if(media === 'tv') {
      const shows = {...this.state.shows}
      delete shows[list][`show-${item.id}`]
      this.setState({ shows }, () => {update()})
    } else if(media === 'movies') {
      const movies = {...this.state.movies}
      delete movies[list][`movie-${item.id}`]
      this.setState({ movies }, () => {update()})
    }
    
    
    //DELETE FETCH REQUEST HERE
  }

  fetchMovieList = (user) => {
    this.setState({fetchedMovies: true})
  }

  fetchShowList = (user) => {
    this.setState({fetchedShows: true})
  }

  fetchBookList = (user) => {
    this.setState({fetchedBooks: true})
  }

  render() {
    return (
      <div className="App">
        <Switch>
          <Route path="/" render={() =>
              this.signedIn() 
              ? <Main 
                  movies={this.state.movies}
                  shows={this.state.shows}
                  books={this.state.books}
                  deleteMedia={this.deleteMedia}
                  formatDuration={this.formatDuration}
                  fetchedMovies={this.state.fetchedMovies}
                  fetchedShows={this.state.fetchedShows}
                  fetchedBooks={this.state.fetchedBooks}
                  getToday={this.getToday}
                  addMovie={this.addMovie}
                  addShow={this.addShow}
                  addBook={this.addBook}               
                  {...this.props} 
                />
              : <Redirect to="/sign-in" />
            }/>
        </Switch>
      </div>
    );
  }
}

export default App;
