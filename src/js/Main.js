import React, { Component } from 'react'

import '../css/Main.css'
import Header from './Header'
import Footer from './Footer'

class Main extends Component {
  render() {
    return (
      <div className="Main">
        <Header />
        {/* List statistics and links go here on the main page */}
        <Footer />
      </div>
    );
  }
}

export default Main;