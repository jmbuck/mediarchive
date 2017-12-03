# Mediarchive

## About
Mediarchive is an app created by Jordan Buckmaster and Shawn Montgomery for the Fall 2017 CS 252 class at Purdue University. Mediarchive provides users with an easy and intuitive way to keep track of all the media they've seen or read; books, movies, and TV shows can all be tracked. 

Mediarchive utilizes React for the client-side and Spring for the server-side. We use the [Google Books](https://developers.google.com/books/) and [TMDB](https://www.themoviedb.org/documentation/api/) APIs to retrieve the data and 
[AWS Elastic Beanstalk](https://aws.amazon.com/elasticbeanstalk) to host and store the server-side data.

The client-side code and some of the CSS was done by Jordan Buckmaster and the server-side code and most of the CSS was done by Shawn Montgomery.

## Setup and Installation
Start by cloning this repository onto your computer, then follow the instructions below to set up the client and server.

### Client
1. Run `npm install` in the client directory
2. Register the app with the TMDB and Google Books APIs
3. Add the pertinent API information to `keys.example.js` and rename the file to `keys.js`
4. Start the local server with `npm start` or `yarn start`
### Server
1. Update `application.properties` API key field
2. Install Apache Maven i.e. `sudo apt-get install maven`
3. Run `$ ./mvn clean package install` in the server directory
4. Locate the generated `.jar` file to execute locally  
    _Only for running on AWS_  
    1. Create environment on AWS Elastic Beanstalk [(step-by-step)](https://aws.amazon.com/blogs/devops/deploying-a-spring-boot-application-on-aws-using-aws-elastic-beanstalk/)
    2. Update [`application.properties`](../blob/mediarchive/server/src/main/resources/application.properties) with AWS credentials  
    3. Deploy `.jar` file in the AWS environment

Good luck!
