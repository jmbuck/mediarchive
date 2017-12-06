import React, { Component } from 'react'

import '../css/SignUp.css'
import logo from '../img/logo.png'

class SignUp extends Component {

  handleSubmit = (ev) => {
      ev.preventDefault()
      if(ev.target.password.value === ev.target.confirm.value) {
        this.props.signUp(ev.target.username.value, ev.target.password.value)
      } else {
        alert('Passwords do not match!') 
        ev.target.password.value = '';
        ev.target.confirm.value = '';
        ev.target.password.focus();
      }
  }

  render() {
    return (
        <div className="SignUp">
            <div className="main-content">
            <img src={logo} alt="Mediarchive" />
                <form className="sign-up" onSubmit={this.handleSubmit}>
                    <input placeholder="Username" className="form-control" type="text" name="username" />
                    <input placeholder="Password" className="form-control" type="password" name="password" />
                    <input placeholder="Confirm Password" className="form-control" type="password" name="confirm" />
                    <div className="buttons btn-group mr-2" role="group">
                        <button className="btn btn-primary sign-up-button" type="submit">Sign Up</button>
                        <button className="btn btn-primary sign-in-button" type="button" onClick={() => {this.props.history.push('/sign-in')}}>Sign In</button>
                    </div>
                </form>
            </div>    
        </div>               
    );
  }
}

export default SignUp;