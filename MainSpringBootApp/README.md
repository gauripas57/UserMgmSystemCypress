# MainSpringBootApp

## Project Name
Springboot Web application framework that does
User Registration and Login with API & Authentication.

### Description
This project implements a web application with two main functionalities: registration and login. The application allows users to register by providing their name, email, and password. It also enables registered users to log in using their email and password. The application is built using Java with the Spring Boot framework and utilizes Thymeleaf for templating. It utilizes AWS DynamoDB as the database for storing user information.

### Table of Contents
Installation
Usage
Test Cases
Test Results Report


#### Installation
To run this project, you need to follow the installation steps below:

1. Open this project in IntelliJ IDE and load pom.xml for Maven dependencies.

2. Run DynamoDbSpringbootApplication.java:
It will start the springboot application & will be hosted on local server port 8080 

3. Open [Localhost](http://localhost:8080/)


#### Usage
To use this project, follow the steps below:

Step 1: Register the User by clicking on register link

Step 2: Then Login with valid credentials

Step 3: Home page will be displayed . It has Welcome user message & Logout link. [To check protect resources functionality API validates if user has API token & valid credentials and then displays the home page]

Step 4: AWS Dynamo db should have user table with userid as primarykey

Step 5: Error handling : Duplicate registered user is prohitibed & checked by unique email id. User gets a error message if used invalid credentials to login

Step 6: To verify API endpoints Postman/ Cypress automation can be used




**Test Case: User Registration**

Test Steps:

Fill in the registration form with valid user information.
Submit the form.
Verify that the registration was successful by checking that the user's information is now stored in the DynamoDB table.

#### AWS DynamoDB











