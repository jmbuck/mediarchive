import React, { Component } from 'react'

import '../css/SignIn.css'
import logo from '../img/logo.png'
class SignIn extends Component {

  handleSubmit = (ev) => {
      ev.preventDefault()
      this.props.signIn(ev.target.username.value, ev.target.password.value)
  }

  render() {
    return (
        <div className="SignIn">
            <div className="main-content">
                <img src={logo} alt="Mediarchive" />
                <form className="sign-in" onSubmit={this.handleSubmit}>
                    <input type="text" placeholder="Username" className="form-control" name="username" />
                    <input type="password" placeholder="Password" className="form-control" name="password" />
                    <div className="buttons btn-group mr-2" role="group">
                        <button className="btn btn-primary sign-in-button" type="submit">Sign In</button>
                        <button className="btn btn-primary sign-up-button" type="button" onClick={() => {this.props.history.push('/sign-up')}}>Sign Up</button>
                    </div>
                </form>
            </div>    
        </div>               
    );
  }
}

export default SignIn;