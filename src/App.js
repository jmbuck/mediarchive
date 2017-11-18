import React, { Component } from 'react'
import { Route, Redirect, Switch } from 'react-router-dom'

import Main from './Main'
import './App.css'

class App extends Component {
  
  signedIn = () => {
    return true;
  }

  render() {
    return (
      <div className="App">
        <Route path="/" render={() =>
            this.signedIn() 
            ? <Main />
            : <Redirect to="/sign-in" />
          }/>
      </div>
    );
  }
}

export default App;
