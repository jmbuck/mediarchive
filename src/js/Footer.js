import React, { Component } from 'react'
import '../css/Footer.css'

class Footer extends Component {
  render() {
    return (
      <div className="Footer">
        <span className="date">2017</span>
        <span className="creators">Mediarchive was created by Jordan Buckmaster and Shawn Montgomery</span>
        <span className="apis">Created using the TMDB and Google Books APIs</span>
        <span className="repo">Find the repository <a href="https://github.com/jmbuck/mediarchive">here</a></span>
      </div>
    );
  }
}

export default Footer;