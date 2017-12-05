import React, { Component } from 'react'
import '../css/SignUp.css'

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
                <h1 className="title">Mediarchive</h1>
                <form className="sign-up" onSubmit={this.handleSubmit}>
                    Username: <input type="text" name="username" />
                    Password: <input type="password" name="password" />
                    Confirm Password: <input type="password" name="confirm" />
                    <button className="btn btn-primary" type="submit">Sign Up</button>
                    <button className="btn btn-primary" type="button" onClick={() => {this.props.history.push('/sign-in')}}>Sign In</button>
                </form>
            </div>    
        </div>               
    );
  }
}

export default SignUp;