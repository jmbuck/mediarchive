# Mediarchive

## About
Mediarchive is an app created by Jordan Buckmaster and Shawn Montgomery for the Fall 2017 CS 252 class at Purdue University. Mediarchive provides users with an easy and intuitive way to keep track of all the media they've seen or read; books, movies, and TV shows can all be tracked. 

Mediarchive utilizes React for the client-side and Spring for the server-side. We use the [Google Books](https://developers.google.com/books/) and [TMDB](https://www.themoviedb.org/documentation/api/) APIs to retrieve the data and 
[AWS Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk) to host and store the server-side data.

The client-side code and some of the CSS was done by Jordan Buckmaster and the server-side code and most of the CSS was done by Shawn Montgomery.

## Setup and Installation
### Client
1. Clone this repository onto your computer
2. Run `npm install` in the project directory
3. Register the app with the TMDB, Firebase, and Google Books APIs
4. Add the pertinent API information to `keys.example.js` and rename the file to `keys.js`
### Server
1. Create environment on AWS Elastic Beanstalk for Java and MySQL persistence
2. Clone this repository onto your computer
3. Update `application.properties` with new MySQL endpoint and security
4. Install Apache Maven i.e. Ubuntu: `sudo apt-get install maven`
5. Run `mvn clean package install` in the server directory to generate executable `.jar` file
6. Deploy `.jar` file in the AWS environment

Good luck!
