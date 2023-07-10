/// <reference types="cypress" />
import {User} from '../pageobjectmodel/user'


//TC1 :Step 1 Fill in the registration form with valid user information.
//     Step 2 Submit the form.

describe("Register a User & verify the API",() =>{
    const userobj = new User()
    let response;
      it("Should be able to register",() =>{
         userobj.navigateToRegisterPage()
         userobj.addUser('test1')
         userobj.clickSubmit()
      }) 
// Step3 : Verify that the registration was successful by 
//checking that the user's information is now stored in the DynamoDB table.
      it("Should be able to check details in DynamoDB",() =>{
        userobj.checkRegisteredUser('test1').then((res) => {
          response = res
          userobj.validateRegisteredUser(response,'test1')
    
      }) 
    });

//TC2 : Step 1: Fill in the login form with valid user information.
//      Step 2: Submit the form.
it("Should be able to login",() =>{
  userobj.navigateToLoginPage()
  userobj.loginUser('test1')
  userobj.clickSubmit()
}) 
// Step 3: Verify that the login was successful by
// checking that the API returns a valid token.

it("should verify successful login with API token",() =>{
  cy.wrap(response).then((response) => {
    userobj.checkLoggedInUser(response).then((res) => {
      response = res
      userobj.validateLoggedInUser(response,'test1')
  }) 
}) 
});

//TC3 : Step1: Fill in the login form with invalid user information (i.e., email and password that do not match any registered user).
//      Step2 : Submit the form.
it("Should not be able to login with invalid credentials",() =>{
  userobj.navigateToLoginPage()
  userobj.loginUser('test2')
  userobj.clickSubmit()
  userobj.validateIncorrectUserUI()
}) 

// Step3 : Verify that the login was unsuccessful by 
//        checking that the API returns an error message.
it("Should be able to verify that login was unsuccessful when API returns an error message",() =>{
  userobj.checkIncorrectUser('test2').then((res) => {
    response = res
    userobj.validateIncorrectUserAPI(response)

}) 
});

 // TC4: Step 1: Log in with valid user information.

  it("Should be able to login with valid user credentials",() =>{
    userobj.navigateToLoginPage()
    userobj.loginUser('test1')
    userobj.clickSubmit()
    userobj.validateProtectedResourcesUI()
  }) 

// Step2 : Get a protected resource using the API and the token obtained from the login.
// Step 3: Verify that the resource is retrieved successfully and has the expected content.

  it("Should be able to verify that resource is retrieved successfully and has the expected content",() =>{
    cy.wrap(response).then((response) => {
    userobj.checkIncorrectUser('test1').then((res) => {
      response = res
      userobj.validateTokenUserIdAPI(response)
      const token = response.body.token;
      const userId = response.body.userid;

      userobj.checkProtectedResourcesAPI(response,token,userId).then((res) => {
        response = res
        userobj.validateLoggedInUser(response,'test1')
  
  }) 
  });
})
})
 
  }) 

 
  


  