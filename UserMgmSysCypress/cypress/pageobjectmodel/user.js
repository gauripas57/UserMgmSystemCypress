import testdata from '../testdata/testdata.json';

export class User {


    navigateToRegisterPage() {
      cy.visit("http://localhost:8080/registration_form",{failOnStatusCode: false})
          
    }

    navigateToLoginPage() {
      cy.visit("http://localhost:8080/login_form",{failOnStatusCode: false})
       
    }


    loginUser(testDataKey) {
      const data = testdata[testDataKey];
      cy.get('#email').type(data.email);
      cy.get('#password').type(data.password);
     
  }


    addUser(testDataKey) {
        const data = testdata[testDataKey];
        cy.get('#name').type(data.name);
        cy.get('#email').type(data.email);
        cy.get('#password').type(data.password);
       
    }

    clickSubmit() {
        cy.get('.btn').click();
    }
    
    checkRegisteredUser(testDataKey){
      const data = testdata[testDataKey];
      return cy.request('POST', 'http://localhost:8080/checkRegisteredUser', {name : data.name,email: data.email})
          
    }
    checkIncorrectUser(testDataKey){
      const data = testdata[testDataKey];
      return  cy.request({
        method: 'POST',
        url: 'http://localhost:8080/checkLogin',
        body: { email: data.email, password: data.password },
        failOnStatusCode: false
      })   
    }

    checkProtectedResourcesAPI(response,token,userid){
      return  cy.request({
        method: 'GET',
        url: `http://localhost:8080/private/user/${response.body.userid}`,
        headers: {
          Authorization: `Bearer ${token}`
        },
        failOnStatusCode: false
      })   
    }

    checkLoggedInUser(response){
      return cy.request('GET', `http://localhost:8080/user/${response.body.userid}`)
          
    }
    
    validateIncorrectUserUI(){
      cy.get('p').should('have.text', 'Invalid username or password')
    }

    validateProtectedResourcesUI(){
      cy.get('h2').should('have.text', 'Welcome User You are Logged in')
      cy.get('a').should('exist')
    }

    validateIncorrectUserAPI(response){
      expect(response.body).to.have.property('message', 'Invalid username or password');
    }

    validateTokenUserIdAPI(response){
      expect(response.body).to.have.property('token');
      expect(response.body).to.have.property('userid');
    }

    validateLoggedInUser(response,testDataKey){
      const data = testdata[testDataKey];
        expect(response.body).to.have.property('name', data.name)
        expect(response.body).to.have.property('email', data.email)
        expect(response.body).to.have.property('userid') 
        expect(response.body).to.have.property('token').and.not.be.empty;
        
    }

    validateRegisteredUser(response,testDataKey){
      const data = testdata[testDataKey];
        expect(response.body).to.have.property('name', data.name)
        expect(response.body).to.have.property('email', data.email)
        expect(response.body).to.have.property('userid') 
    
    }

    


  


  
    
  
  }