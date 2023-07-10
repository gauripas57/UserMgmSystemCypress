# MainSpringBootApp

## Project Name
Springboot Web application framework that does
User Registration and Login with API & Authentication.

### Description
This project implements a web application with two main functionalities: registration and login. The application allows users to register by providing their name, email, and password. It also enables registered users to log in using their email and password. The application is built using Java with the Spring Boot framework and utilizes Thymeleaf for templating. It utilizes AWS DynamoDB as the database for storing user information.

### Table of Contents
Installation
Usage
API Endpoints
AWS DynamoDB


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

#### API Endpoints
**Register API** <br>
Method: POST <br>
baseUrl: http://localhost:8080 <br>
Endpoint: /checkRegisteredUser <br>
Description: Verifies that the registration was successful by checking that the user's information is now stored in the DynamoDB table.
Request Body: 
```Json
{
  "name": "Rajan",
  "email": "Rajan.doe@gmail.com"
}

```
Response: 
```
{
  "userid": "096f2c5d-fa60-4d59-8960-9b7147d1f024",
  "name": "Rajan",
  "email": "Rajan.doe@gmail.com",
  "token": null
}

```
**Login API** <br>

Method: GET <br>
baseUrl: http://localhost:8080 <br>
Endpoint: /user/{userid} <br>
Description: Verifies that the login was successful by checking that the API returns a valid token. <br>
Response:
```
{
  "userid": "adba76bc-ce0d-44",
  "name": "Vedant",
  "email": "vedant@gmail.com",
  "token": "eyJhbGc"
}

```
**Unsuccessful API response for invalid credentials** <br>
Method: POST <br>
baseUrl: http://localhost:8080 <br>
Endpoint: /checkLogin <br>
Description: Verify that the login was unsuccessful by checking that the API returns an error message. <br>
Request Body:
```Json
{
  "email": "Invalid.doe@gmail.com",
  "password": "sdsdfs",
}

```
Response:
```
"message": "Invalid username or password"

```
**Show Protected resources after valid login & token.** <br>
Method: GET <br>
baseUrl:http://localhost:8080 <br>
Endpoint: /private/user/{userid} <br>
headers: Authorization Bearer token <br>
Description: Get a protected resource using the API and the token obtained from the login.Verify that the resource is retrieved successfully and has the expected content.  <br>
Response:
```
{
    "userid": "258f2123a",
    "name": "Rajan",
    "email": "Rajan.doe@gmail.com",
    "token": "eyJhdoJhYw"
}
```

#### AWS DynamoDB

![AWS DynamoDB](https://github.com/gauripas57/UserMgmSystemCypress/blob/main/Images/AWS%20Dynamodb.png)











