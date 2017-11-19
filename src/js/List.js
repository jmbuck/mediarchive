import React, { Component } from 'react'

import '../css/List.css'

class List extends Component {
  render() {
    const list = this.props.match.params.list;
    const media = this.props.match.params.media;

    return (
      <div className="List">
          This is the {media} list!
      </div>
    );
  }
}

export default List;