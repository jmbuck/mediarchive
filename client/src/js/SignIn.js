import React, { Component } from 'react'
import '../css/SignIn.css'

class SignIn extends Component {

  handleSubmit = (ev) => {
      ev.preventDefault()
      this.props.signIn(ev.target.username.value, ev.target.password.value)
  }

  render() {
    return (
        <div className="SignIn">
            <div className="main-content">
                <h1 className="title">Mediarchive</h1>
                <form className="sign-in" onSubmit={this.handleSubmit}>
                    Username: <input type="text" name="username" />
                    Password: <input type="password" name="password" />
                    <button className="btn btn-primary" type="submit">Sign In</button>
                    <button className="btn btn-primary" type="button" onClick={() => {this.props.history.push('/sign-up')}}>Sign Up</button>
                </form>
            </div>    
        </div>               
    );
  }
}

export default SignIn;