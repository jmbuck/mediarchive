import React, { Component } from 'react'
import { Route, Redirect, Switch } from 'react-router-dom'
import base64 from 'base-64'

import Main from './Main'
import SignIn from './SignIn'
import SignUp from './SignUp'
import '../css/App.css'
import { serverKey } from '../keys'

class App extends Component {
  constructor() {
    super()
    this.state = {
      user: null,
      auth: null,
      books: {},
      movies: {},
      shows: {},
      fetchedMovies: false,
      fetchedBooks: false,
      fetchedShows: false,
      signedIn: false,
      ready: false,
    }
  }

  componentDidMount = () => {
    this.setState({
      user: localStorage.getItem('user'),
      auth: localStorage.getItem('uid')
    }, () => {
      if(this.state.auth && this.state.user) {
        this.setState({signedIn: true, ready: true})
        this.fetchLists()
      } else {
        this.setState({ready: true})
      }
    })
  }

  fetchMovieList = (user) => {
    fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/getMovies?username=${user}&key=${serverKey}`, {
      headers: {
        'Authorization': this.state.auth
      }}
    )
    .then(response => response.json())
    .then(movieList => {    
      const movies = {...this.state.movies}
      if(movieList !== '"FORBIDDEN"') {
        movieList.completed.forEach((movie) => {
          if(!movies.completed) movies.completed = {}
          movies.completed[`movie-${movie.id}`] = movie
        })
        movieList.planning.forEach((movie) => {
          if(!movies.planning) movies.planning = {}
          movies.planning[`movie-${movie.id}`] = movie
        })
      }
      this.setState({fetchedMovies: true, movies})
    })
  }

  fetchShowList = (user) => {
    fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/getShows?username=${user}&key=${serverKey}`, {
      headers: {
        'Authorization': this.state.auth
      }}
    )
    .then(response => response.json())
    .then(showList => {   
      const shows = {...this.state.shows}
      if(showList !== '"FORBIDDEN"') {
        showList.completed.forEach((show) => {
          if(!shows.completed) shows.completed = {}
          shows.completed[`show-${show.id}`] = show
        })
        showList.current.forEach((show) => {
          if(!shows.current) shows.current = {}
          shows.current[`show-${show.id}`] = show
        })
        showList.planning.forEach((show) => {
          if(!shows.planning) shows.planning = {}
          shows.planning[`show-${show.id}`] = show
        })
      }
      this.setState({fetchedShows: true, shows})
    })
  }

  fetchBookList = (user) => {
    fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/getBooks?username=${user}&key=${serverKey}`, {
      headers: {
        'Authorization': this.state.auth
      }}
    )
    .then(response => response.json())
    .then(bookList => {   
      const books = {...this.state.books}
      if(bookList !== '"FORBIDDEN"') {
        bookList.completed.forEach((book) => {
          if(!books.completed) books.completed = {}
          books.completed[`book-${book.id}`] = book
        })
        bookList.current.forEach((book) => {
          if(!books.current) books.current = {}
          books.current[`book-${book.id}`] = book
        })
        bookList.planning.forEach((book) => {
          if(!books.planning) books.planning = {}
          books.planning[`book-${book.id}`] = book
        })
      }
      this.setState({fetchedBooks: true, books})
    })
  }


  fetchLists = () => {
    this.fetchMovieList(this.state.user)
    this.fetchShowList(this.state.user)
    this.fetchBookList(this.state.user)
  }

  signIn = (user, password) => {
    fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/login?username=${user}&key=${serverKey}`, {
      mode: 'cors',
      headers: {
        'Authorization': `${base64.encode(`${user}:${password}`)}`
      }
    })
    .then(response => response.json())
    .then(data => {
      if(data === 'OK') {
        const auth = base64.encode(`${user}:${password}`)
        localStorage.setItem('uid', auth)
        localStorage.setItem('user', user)
        this.setState({ user, auth, signedIn: true, ready: true }, this.fetchLists)
      } else {
        alert('Invalid username or password')
      }
    })
  }

  signUp = (user, password) => {
    fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/addUser?key=${serverKey}`, {
      method: 'POST',
      body: JSON.stringify({
        username: user,
        password: base64.encode(`${user}:${password}`)
      })
    })
    .then(response => response.json())
    .then(data => {
      if(data !== 'BAD_REQUEST') {
        this.signIn(user, password)
      } else {
        alert(`The username ${user} is taken!`)
      }
    })
  }

  signedIn = () => {
    return this.state.signedIn;
  }

  signOut = () => {
    localStorage.removeItem('uid')
    localStorage.removeItem('user')
    this.setState({user: null, auth: null, signedIn: false, ready: false}, () => {
      this.props.history.push('/sign-in')
    })
  }

  formatDuration = (totalTime) => {
    if(totalTime === 0) {
      return '0 minutes'
    }
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

  addMovie = (category, date, score, edit, movie) => {
    const movies = {...this.state.movies}
    if(!movies[category]) {
      movies[category] = {}
    }
    
    let message = this.isDuplicate(movie, 'movie')
    if(!message || edit) {
      movies[category][`movie-${movie.id}`] = {}
      movies[category][`movie-${movie.id}`].watched_date = date ? date : ""
      movies[category][`movie-${movie.id}`].score = score ? parseInt(score, 10) : 0
      movies[category][`movie-${movie.id}`].runtime = movie.runtime ? movie.runtime : 0
      movies[category][`movie-${movie.id}`].id = movie.id
      movies[category][`movie-${movie.id}`].title = movie.title
      movies[category][`movie-${movie.id}`].poster_path = movie.poster_path
      movies[category][`movie-${movie.id}`].category = category


      this.setState({movies}, () => {
        if(edit) {
          const oldCategory =  movie.category ? movie.category : category
          if(category !== oldCategory) {
            this.deleteMedia('movies', oldCategory, movies[oldCategory][`movie-${movie.id}`])
          }
        } 
      })
      
      fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/add?list=${category}&media=movie&username=${this.state.user}&key=${serverKey}`, {
        method: 'POST',
        headers: {
          'Authorization': this.state.auth
        },
        body: JSON.stringify(movies[category][`movie-${movie.id}`])
      })

      message = `${movie.title} successfully added to list!`
    }
    return message;
  }

  addShow = (show, category, edit, info) => {
    const shows = {...this.state.shows}
    if(!shows[category]) {
      shows[category] = {}
    }
    
    let message = this.isDuplicate(show, 'show')
    if(!message || edit) {
      shows[category][`show-${show.id}`] = {}
      shows[category][`show-${show.id}`].start_date = info.start_date ? info.start_date : ""
      shows[category][`show-${show.id}`].end_date = info.end_date ? info.end_date : ""
      shows[category][`show-${show.id}`].score = info.score ? parseInt(info.score, 10) : 0
      shows[category][`show-${show.id}`].episode_runtime = info.mean_episode_run_time
      shows[category][`show-${show.id}`].episodes_watched = info.episodes_watched
      shows[category][`show-${show.id}`].seasons_watched = info.seasons_watched
      shows[category][`show-${show.id}`].number_of_episodes = info.number_of_episodes ? info.number_of_episodes : 0
      shows[category][`show-${show.id}`].number_of_seasons = info.number_of_seasons ? info.number_of_seasons : 0
      shows[category][`show-${show.id}`].id = show.id
      shows[category][`show-${show.id}`].name = show.name
      shows[category][`show-${show.id}`].poster_path = show.poster_path
      shows[category][`show-${show.id}`].category = category
    
      this.setState({shows}, () => {
        if(edit) {
          const oldCategory =  show.category ? show.category : category
          if(category !== oldCategory) {
            this.deleteMedia('tv', oldCategory, shows[oldCategory][`show-${show.id}`])
          }
        } 
      })
      
      fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/add?list=${category}&media=show&username=${this.state.user}&key=${serverKey}`, {
        method: 'POST',
        headers: {
          'Authorization': this.state.auth
        },
        body: JSON.stringify(shows[category][`show-${show.id}`])
      })
     
      message = `${show.name} successfully added to list!`
    }
    return message;
  }

  addBook = (category, start_date, end_date, score, edit, book) => {
    const books = {...this.state.books}
    if(!books[category]) {
      books[category] = {}
    }
    
    let message = this.isDuplicate(book, 'book')
    if(!message || edit) {
      books[category][`book-${book.id}`] = {}
      books[category][`book-${book.id}`].start_date = start_date ? start_date : ""
      books[category][`book-${book.id}`].end_date = end_date ? end_date : ""
      books[category][`book-${book.id}`].score = score ? parseInt(score, 10) : 0
      books[category][`book-${book.id}`].page_count = book.volumeInfo.printedPageCount ? book.volumeInfo.printedPageCount : 0
      books[category][`book-${book.id}`].id = book.id
      books[category][`book-${book.id}`].title = book.volumeInfo.title
      books[category][`book-${book.id}`].path = !book.volumeInfo.imageLinks ? null : book.volumeInfo.imageLinks.thumbnail ? book.volumeInfo.imageLinks.thumbnail : null
      books[category][`book-${book.id}`].category = category

      this.setState({books}, () => {
        if(edit) {
          const oldCategory =  book.category ? book.category : category
          if(category !== oldCategory) {
            this.deleteMedia('books', oldCategory, books[oldCategory][`book-${book.id}`])
          }
        } 
      })

      fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/add?list=${category}&media=book&username=${this.state.user}&key=${serverKey}`, {
        method: 'POST',
        headers: {
          'Authorization': this.state.auth
        },
        body: JSON.stringify(books[category][`book-${book.id}`])
      })

      message = `${book.volumeInfo.title} successfully added to list!`
    }
    return message;
  }

  deleteMedia = (media, list, item, update) => {
    let apiMedia;
    if(media === 'books') {
      apiMedia = 'book'
      const books = {...this.state.books}
      delete books[list][`book-${item.id}`]
      this.setState({ books }, () => {if(update) update()})
    } else if(media === 'tv') {
      apiMedia = 'show'
      const shows = {...this.state.shows}
      delete shows[list][`show-${item.id}`]
      this.setState({ shows }, () => {if(update) update()})
    } else if(media === 'movies') {
      apiMedia = 'movie'
      const movies = {...this.state.movies}
      delete movies[list][`movie-${item.id}`]
      this.setState({ movies }, () => {if(update) update()})
    }
    
    fetch(`http://mediarchive-env.us-east-1.elasticbeanstalk.com/delete?id=${item.id}&list=${list}&media=${apiMedia}&username=${this.state.user}&key=${serverKey}`, {
      method: 'DELETE',
      headers: {
        'Authorization': this.state.auth
      }
    })
  }

  render() {
    return (
      <div className="App">
        <Switch>
          <Route path="/sign-in" render={(navProps) =>
            !this.signedIn() 
            ? <SignIn signIn={this.signIn} {...navProps} />
            : <Redirect to="/"/>
          }/>
          <Route path="/sign-up" render={(navProps) =>
            !this.signedIn() 
            ? <SignUp signUp={this.signUp} {...navProps} />
            : <Redirect to="/"/>
          }/>
          <Route path="/" render={() =>
              this.signedIn() || !this.state.ready
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
                  signOut={this.signOut} 
                  user={this.state.user}
                  auth={this.state.auth}            
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
