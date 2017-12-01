import React, { Component } from 'react'

import '../css/List.css'

class List extends Component {

  renderList = (list, media) => {
    
  }

  render() {
    const list = this.props.match.params.list;
    const media = this.props.media;

    return (
      <div className="List">
          {this.renderList(list, media)}
      </div>
    );
  }
}

export default List;