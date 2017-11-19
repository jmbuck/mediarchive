import React, { Component } from 'react'
import { Route, Switch } from 'react-router-dom'

import '../css/Main.css'
import Header from './Header'
import Footer from './Footer'
import List from './List'

class Main extends Component {
  render() {
    return (
      <div className="Main">
        <Header />
        {/* List statistics and links go here on the main page */}
        <Switch>
            <Route path="/:media/:list" render={(navProps) => <List {...navProps} />}/>
            <Route path="/search/:query" render={(navProps) => <Search {...navProps}/>}/>
            <Route path="/" render={(navProps) => <div>List statistics and user information will go here</div>}/>
        </Switch>
        <Footer />
      </div>
    );
  }
}

export default Main;