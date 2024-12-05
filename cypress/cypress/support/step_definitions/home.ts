import { When, Then, Given } from "@badeball/cypress-cucumber-preprocessor";

let storedUserName = "";

const visitHomePage = () => cy.visit("http://localhost:8019/");

When("the client checks the home page", visitHomePage);

Then("the home page main message should be: {string}", (message) => {
  cy.get("h2.message").should("contain.text", message);
});

Given(
  "there is a user registered with user name {string} and password {string}",
  (userName, password) => {
    storedUserName = userName;
    visitHomePage();
    cy.get("a").contains("Login").click();
    cy.get("input#Name").type(userName);
    cy.get("input#Password").type(password).as("password");
    cy.get("input#LoginButton").click();
  }
);

Then("the user name of the client should be on the home page", () => {
  cy.get("#LoggedInUser").should("contain.text", storedUserName);
});
