import React, { Component } from 'react'
import { NavLink, Link } from 'react-router-dom'

import '../css/Header.css'
import logo from '../img/logo.png'
import symbol from '../img/symbol.png'

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
          <Link to="/stats" className="logos">
            <img src={symbol} alt="Mediarchive symbol" />
            &nbsp;&nbsp;
            <img src={logo} alt="Mediarchive"/>
          </Link>
          <div className="nav btn-group mr-2" role="group" aria-label="list links">
            <NavLink to="/movies/all" className="movies-button btn btn-primary">
              Movies
            </NavLink>
            <NavLink to="/tv/all" className="shows-button btn btn-primary">
              Shows
            </NavLink>
            <NavLink to="/books/all" className="books-button btn btn-primary">
              Books
            </NavLink>
          </div>
          <form className="search-bar input-group" onSubmit={this.handleSubmit}>
            <select name="media" className="input-group-addon media-dropdown" id="btnGroupAddon">
              <option value="movies">Movies</option>
              <option value="tv">Shows</option>
              <option value="books">Books</option>
            </select>
            <input type="text" name="query" className="form-control" placeholder="Find media" aria-describedby="btnGroupAddon" />
          </form>
          <div className="misc-buttons btn-group" role="group" aria-label="sign out">
            <NavLink to="/stats" className="stats-btn btn btn-secondary">
              Statistics
            </NavLink>
            <button type="button" className="btn btn-secondary" onClick={this.props.signOut}>Sign out</button>
          </div>
        </div>
      </div>
    );
  }
}

export default Header;