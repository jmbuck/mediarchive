import React, { Component } from 'react'

import '../css/Book.css'

class Book extends Component {
  render() {
    const book = this.props.book
    return (
      <div className="Book">
          {book.volumeInfo.title}
      </div>
    );
  }
}

export default Book;