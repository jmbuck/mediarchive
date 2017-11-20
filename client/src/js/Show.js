import React, { Component } from 'react'

import '../css/Show.css'

class Show extends Component {
  render() {
    const show = this.props.show
    return (
      <div className="Show">
          {show.name}
      </div>
    );
  }
}

export default Show;