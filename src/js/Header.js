import React, { Component } from 'react'
import '../css/Header.css'

class Header extends Component {
  render() {
    return (
      <div className="Header">
        <div className="btn-toolbar" role="toolbar" aria-label="toolbar with lists">
          <div className="nav btn-group mr-2" role="group" aria-label="list links">
            <button type="button" className="btn btn-primary">Movies</button>
            <button type="button" className="btn btn-primary">TV Shows</button>
            <button type="button" className="btn btn-primary">Books</button>
          </div>
          <div className="search-bar input-group">
            <select className="input-group-addon" id="btnGroupAddon">
              <option value="movies">Movies</option>
              <option value="tv">TV Shows</option>
              <option value="books">Books</option>
            </select>
            <input type="text" className="form-control" placeholder="Find media" aria-describedby="btnGroupAddon" />
          </div>
          <div className="misc-buttons btn-group" role="group" aria-label="sign out">
            <button type="button" className="btn btn-secondary">Statistics</button>
            <button type="button" className="btn btn-secondary">Sign out</button>
          </div>
        </div>
      </div>
    );
  }
}

export default Header;