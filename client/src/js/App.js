import React, { Component } from 'react'
import { Route, Redirect, Switch } from 'react-router-dom'

import Main from './Main'
import '../css/App.css'

class App extends Component {
  
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

  addMovie = (ev, movie) => {
    console.log('add movie')
  }

  addShow = (ev, show) => {
    console.log('add show')
  }

  addBook = (ev, book) => {
    console.log('add book')
  }

  render() {
    return (
      <div className="App">
        <Switch>
          <Route path="/" render={() =>
              this.signedIn() 
              ? <Main 
                  formatDuration={this.formatDuration} 
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
