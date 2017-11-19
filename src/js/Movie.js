import React, { Component } from 'react'

import '../css/Movie.css'

class Movie extends Component {
  render() {
    const movie = this.props.movie
    return (
      <div className="Movie">
          {movie.title}
      </div>
    );
  }
}

export default Movie;