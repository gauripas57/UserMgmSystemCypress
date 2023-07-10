# UserMgmSystemCypress

## Project Name
Cypress Automation script framework that tests
User Registration and Login with API & Authentication Testing

### Description
This project focuses on testing the user registration and authentication functionality of an application using Cypress, a tool for UI and API automation testing. The project utilizes the Page Object Model (POM) design pattern for the automation test framework. It also includes the Mochawesome reporter, a custom reporter that generates a standalone HTML/CSS report with screenshots and videos to visualize test runs.

### Table of Contents
Installation
Usage
Test Cases
Test Results Report


#### Installation
To run this project, you need to follow the installation steps below:

1. Install Cypress via npm: This will install Cypress locally as a dev dependency for your project.

```bash
cd /your/project/path
npm install cypress --save-dev
```
2. Install the Mochawesome reporter:

```bash
npm i --save-dev cypress-mochawesome-reporter
```
3. Change Cypress reporter configuration:
Edit the configuration file (cypress.config.js by default) and update it as follows:

```Javascript
const { defineConfig } = require('cypress');

module.exports = defineConfig({
  reporter: 'cypress-mochawesome-reporter',
  e2e: {
    setupNodeEvents(on, config) {
      require('cypress-mochawesome-reporter/plugin')(on);
    },
  },
});

```
4. Add the Mochawesome reporter to cypress/support/e2e.js:
In the cypress/support/e2e.js file, add the following import statement:

```Javascript
import 'cypress-mochawesome-reporter/register';

```
#### Usage
To use this project, follow the steps below:

Step 1: Run Spring Boot Project

Start the Spring Boot project in your IntelliJ IDE to launch the local server at [Localhost](http://localhost:8080/) This is the backend server that the Cypress tests will interact with.

Step 2: Start Cypress Run

Open the test project in your Visual Studio Code (VS Code) IDE.

From the VS Code terminal, run the following command to start the Cypress test runner:
```bash
 npx cypress open
```
This will open the Cypress Test Runner GUI.

Step 3: Prerequisites

Ensure that the AWS DynamoDB is set up with a user table and that the test users are not registered in the system. This is necessary for the test scenarios to run successfully.

You are now ready to execute the Cypress tests by interacting with the UI in the Cypress Test Runner.

#### Test Cases


**Test Case: User Registration**

Test Steps:

Fill in the registration form with valid user information.
Submit the form.
Verify that the registration was successful by checking that the user's information is now stored in the DynamoDB table.

**Test Case: User Login**

Test Steps:

Fill in the login form with valid user information.
Submit the form.
Verify that the login was successful by checking that the API returns a valid token.

**Test Case: Unregistered User Login**

Test Steps:

Fill in the login form with invalid user information (i.e., email and password that do not match any registered user).
Submit the form.
Verify that the login was unsuccessful by checking that the API returns an error message.

**Test Case: Accessing Protected Resources**

Test Steps:

Log in with valid user information.
Get a protected resource using the API and the token obtained from the login.
Verify that the resource is retrieved successfully and has the expected content.

#### Test Results Report









