import React, { Component } from 'react'
import { Route, Switch } from 'react-router-dom'

import '../css/Main.css'
import Header from './Header'
import Footer from './Footer'
import List from './List'
import Search from './Search'

class Main extends Component {
  render() {
    return (
      <div className="Main">
        <Header {...this.props}/>
        {/* List statistics and links go here on the main page */}
        <Switch>
            <Route exact path="/search/:media/:query/:page" render={(navProps) => <Search {...navProps}/>}/>
            <Route path="/:media/:list" render={(navProps) => <List {...navProps} />}/>            
            <Route path="/" render={(navProps) => <div>List statistics and user information will go here</div>}/>
        </Switch>
        <Footer />
      </div>
    );
  }
}

export default Main;