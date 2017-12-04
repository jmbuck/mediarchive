import React, { Component } from 'react'
import { NavLink } from 'react-router-dom'

import '../css/Header.css'

class Header extends Component {

  handleSubmit = (ev) => {
    ev.preventDefault()
    this.props.history.push(`/search/${ev.target.media.value}/${encodeURI(ev.target.query.value)}/1`)
    ev.target.query.value = "";
  }

  render() {
    return (
      <div className="Header">
        <div className="btn-toolbar" role="toolbar" aria-label="toolbar with lists">
          <div className="nav btn-group mr-2" role="group" aria-label="list links">
            <NavLink to="/movies/all" className="btn btn-primary">
              Movies
            </NavLink>
            <NavLink to="/tv/all" className="btn btn-primary">
              Shows
            </NavLink>
            <NavLink to="/books/all" className="btn btn-primary">
              Books
            </NavLink>
          </div>
          <form className="search-bar input-group" onSubmit={this.handleSubmit}>
            <select name="media" className="input-group-addon media-dropdown" id="btnGroupAddon">
              <option value="movies">Movies</option>
              <option value="tv">TV Shows</option>
              <option value="books">Books</option>
            </select>
            <input type="text" name="query" className="form-control" placeholder="Find media" aria-describedby="btnGroupAddon" />
          </form>
          <div className="misc-buttons btn-group" role="group" aria-label="sign out">
            <button type="button" className="btn btn-secondary">Statistics</button>
            <button type="button" className="btn btn-secondary" onClick={this.props.signOut}>Sign out</button>
          </div>
        </div>
      </div>
    );
  }
}

export default Header;