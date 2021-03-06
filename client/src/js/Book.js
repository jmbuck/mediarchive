import React, { Component } from 'react'
import { Route, Link } from 'react-router-dom'

import '../css/Book.css'
import { booksKey } from '../keys'
import noCover from '../img/noCover.png'

class Book extends Component {

  constructor(props) {
    super(props)
    this.state = {
      book: this.props.book,
      fetched: false,
      onForm: this.props.location.pathname !== `/search/books/${this.props.match.params.query}/${this.props.match.params.page}/${this.props.book.id}` 
              && this.props.location.pathname !== `/books/:list/${this.props.book.id}`,
      today: this.props.getToday(),
      formPath: this.props.search ? `/search/books/${this.props.match.params.query}/${this.props.match.params.page}/${this.props.book.id}/add`
      : `/books/${this.props.match.params.list}/${this.props.book.id}/edit`,
      infoPath: this.props.search ? `/search/books/${this.props.match.params.query}/${this.props.match.params.page}/${this.props.book.id}`
      : `/books/${this.props.match.params.list}/${this.props.book.id}`,
      listPath: this.props.search ? `/search/books/${this.props.match.params.query}/${this.props.match.params.page}`
      : `/books/${this.props.match.params.list}`,
    }
  }

  componentDidMount = () => {
    this.fetchBookInfo(this.state.book)
  }

  componentWillReceiveProps = (nextProps) => {
    if(this.props.book !== nextProps.book) {
      const book = {...this.state.book}
      book.score = nextProps.book.score
      book.category = nextProps.book.category
      book.start_date = nextProps.book.start_date
      book.end_date = nextProps.book.end_date

      this.setState({book})
    }
  }

  fetchBookInfo = (book, callback) => {
    fetch(`https://www.googleapis.com/books/v1/volumes/${book.id}?key=${booksKey}`)
    .then(response => response.json())
    .then(detailedBook => {
      if(!this.props.search) {
        detailedBook.score = book.score
        detailedBook.start_date = book.start_date
        detailedBook.end_date = book.end_date
        detailedBook.page_count = book.page_count
        detailedBook.path = book.path
        detailedBook.category = book.category
      }
      this.setState({ 
        book: detailedBook ? detailedBook : book,  
        fetched: true, 
      }, () => {
        if(callback) callback(detailedBook)
      })
    })
  }

  quickAdd = (book) => {
    if(!this.state.fetched) {
      this.fetchBookInfo(book, this.quickAdd)
    } else {
      const message = this.props.addBook('completed', '', '', 0, false, book)
      this.setState({onForm: false})
      this.props.displayMessage(message, true)
    }
  }

  renderBookForm = (book) => {
   return (
    <form className="book-form" onSubmit={(ev) => {
      ev.preventDefault();
      const message = this.props.addBook(ev.target.category.value, ev.target.start_date.value, ev.target.end_date.value, ev.target.score.value, this.props.search ? false : true, book)
      if(this.props.search) this.props.displayMessage(message, true)
      this.setState({onForm: false}, () => {this.props.history.push(this.state.listPath)})
    }}>
      <div className="fields">
          <div className="category">
              <input type="radio" name="category" value="current" defaultChecked={this.props.search ? false : book.category === 'current'}/>Reading<br/>
              <input type="radio" name="category" value="completed" defaultChecked={this.props.search ? true : book.category === 'completed'}/>Completed<br/>
              <input type="radio" name="category" value="planning" defaultChecked={this.props.search ? false : book.category === 'planning'}/>Plan to Read<br/>
          </div>
          <div className="optional">
              <div className="start-date">
              Start date: 
              &nbsp;<a onClick={() => {
                  document.querySelector('.optional .start').value = this.state.today
                  }}>Insert Today
              </a>
              <input type="date" className="form-control start" name="start_date" defaultValue={!this.props.search ? book.start_date : null} max={this.state.today}/>
              </div>
              <div className="end-date">
              End date: 
              &nbsp;<a onClick={() => {
                  document.querySelector('.optional .end').value = this.state.today
                  }}>Insert Today
              </a>
              <input type="date" className="form-control end" name="end_date" defaultValue={!this.props.search ? book.end_date : null}max={this.state.today}/>
              </div>
                 <select className="form-control" defaultValue={!this.props.search ? book.score ? book.score : "" : ""} name="score">
                  <option value="">-- Score --</option>
                  <option value="10">10</option>
                  <option value="9">9</option>
                  <option value="8">8</option>
                  <option value="7">7</option>
                  <option value="6">6</option>
                  <option value="5">5</option>
                  <option value="4">4</option>
                  <option value="3">3</option>
                  <option value="2">2</option>
                  <option value="1">1</option>
              </select>
          </div>
      </div>
      <button className="btn btn-primary" type="submit">{this.props.search ? 'Add' : 'Confirm'}</button>
      </form>
    )
  }

  renderBookInfo = (book) => {
    const start_date = new Date(book.start_date)
    const end_date = new Date(book.end_date)
    start_date.setDate(start_date.getDate()+1)
    end_date.setDate(end_date.getDate()+1)
    const options = {
        month: "long",
        year: "numeric",
        day: "numeric",
    }
    return (
      <div className="book-info">
        {
        !this.props.search 
        ? (<div>
          {
          book.start_date
          ? <div className="start-date">Start Date: {start_date.toLocaleDateString("en-US", options)}</div>
          : <div className="start-date">Start Date: -</div>
          }
          {
          book.end_date
          ? <div className="end-date">End Date: {end_date.toLocaleDateString("en-US", options)}</div>
          : <div className="end-date">End Date: -</div>
          }
          <br />
        </div>)
        : null
        }
        {
        book.volumeInfo.authors && book.volumeInfo.authors.length > 0
        ? (<div className="authors">{book.volumeInfo.authors.length === 1 ? 'Author' : 'Authors'}:&nbsp;
            {
                book.volumeInfo.authors.map((author, i) => i !== book.volumeInfo.authors.length-1 ? <span key={i}>{author}, </span> : <span key={i}>{author}</span>)
            }
            </div>)
        : <div className="authors"></div>
        }

        {
        book.volumeInfo.description 
        ? <div className="synopsis" dangerouslySetInnerHTML={{__html: 'Synopsis: '+book.volumeInfo.description}}></div>
        : <div className="synopsis">No synopsis available.</div>
        }

        {
        book.volumeInfo.publishedDate
        ? <div className="date">Published: {book.volumeInfo.publishedDate}</div>
        : <div className="date"></div>
        }
        
        {
        book.volumeInfo.printedPageCount
        ? <div className="page-count">Page Count: {book.volumeInfo.printedPageCount} pages</div> 
        : <div className="page-count"></div>
        }

        {
        book.volumeInfo.previewLink
        ? <a href={book.volumeInfo.previewLink} className="preview-link">Preview</a>
        : <div className="homepage"></div>
        }
      </div>
    )
  }

  renderBook = (navProps, book, query, page, list) => {
    const path = book.path ? book.path : !book.volumeInfo || !book.volumeInfo.imageLinks ? null : book.volumeInfo.imageLinks.thumbnail ? book.volumeInfo.imageLinks.thumbnail : null
    return (
      <div>
        <div className="white-content">
          <div className="main-content">
            <div className="image">
            {/*Displays book cover. If cover does not exist, show "poster does not exist" image*/
              path 
              ? <img src={path} alt="book cover" />
              : <img src={noCover} alt="TV show poster" />
            }
            </div>
            <Route exact path={this.state.infoPath} render={(navProps) => {
              return this.renderBookInfo(book);
            }}/>

            <Route path={this.state.formPath} render={(navProps) => {
              return this.renderBookForm(book);
            }}/>
          </div>

          <div className="btns btn-group">
            <button className="btn btn-primary" 
              onClick={() => {
                if(this.state.onForm) {
                  this.props.history.push(this.state.infoPath)
                } else {
                  this.props.history.push(this.state.formPath)
                }
                this.setState({onForm: !this.state.onForm}, () => {
                  if(this.props.search) this.props.displayMessage('', false)
                })
              }}
            >{this.state.onForm ? 'Info' : this.props.search ? 'Add' : 'Edit'}</button>
          
            {!this.props.search && <button className="btn btn-primary" 
              onClick={() => { 
                if(list === 'all') {
                  if(this.props.books['completed'] && this.props.books['completed'][`book-${book.id}`]) list = 'completed'
                  if(this.props.books['current'] && this.props.books['current'][`book-${book.id}`]) list = 'current'
                  if(this.props.books['planning'] && this.props.books['planning'][`book-${book.id}`]) list = 'planning'
                }
                this.props.deleteMedia('books', list, book, this.props.update)
              }}
            >Delete</button>}
            
            <button className="btn btn-primary" 
              onClick={() => {
                if(this.props.search) this.props.displayMessage('', false)
                this.props.history.push(this.state.listPath)}
              }
            >Close</button>
          </div>

        </div>

        <div className="black-overlay" onClick={(ev) => {
          if(this.props.search) this.props.displayMessage('', false)
          this.props.history.push(this.state.listPath)
        }}></div>
      </div>
      )
  }

  render() {
    const query = this.props.match.params.query
    const page = this.props.match.params.page
    const list = this.props.match.params.list
    const book = {...this.state.book}
    const path = book.path ? book.path : !book.volumeInfo || !book.volumeInfo.imageLinks ? null : book.volumeInfo.imageLinks.thumbnail ? book.volumeInfo.imageLinks.thumbnail : null

    return (
      <li className="Book">
       <Route path={this.state.infoPath} render={(navProps) => {
          if(this.state.fetched) {
            return this.renderBook(navProps, book, query, page, list) 
          }
          return <div>Fetching book info...</div>
       }}/>
        <Link 
          to={this.state.infoPath}
          onClick={() => {this.setState({onForm: false})}}
          className="preview"
        >
          <div className="preview">
            <div className="title" title={book.title ? book.title : book.volumeInfo.title}>{book.title ? book.title : book.volumeInfo.title}</div>
            {/*Displays book cover. If cover does not exist, show "poster does not exist" image*/
              path 
              ? <img src={path} alt="book cover" />
              : <img src={noCover} alt="book cover" />
            }
            {
              !this.props.search 
              ? <div className="score">Score: {book.score ? book.score : '-'}</div>
              : <div className="score"></div>
            }
          </div>
        </Link>
        {this.props.search && <button className="quick-add btn btn-primary" type="button" onClick={() => {
              this.quickAdd(book)
            }
        }>Quick add</button>}
      </li>
    )
  }
}

export default Book;