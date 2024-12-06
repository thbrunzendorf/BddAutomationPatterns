import { DataTable, Given, Then } from "@badeball/cypress-cucumber-preprocessor";

Given("the client has the following items in the basket", (dataTable: DataTable) => {
  cy.visit("http://localhost:8019/Menu");
  const pizzas = dataTable.hashes();
  pizzas.forEach((pizza) => {
    // Exercise: TODO!!!
    cy.log(`pizza.name: ${pizza.name}`);
  });
});

Then("the ordered items should be listed on the my order page", () => {
  // Exercise: TODO!!!
});
