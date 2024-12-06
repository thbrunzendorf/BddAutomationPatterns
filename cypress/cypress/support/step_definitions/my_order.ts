import { When, Then, Given, Before, DataTable } from "@badeball/cypress-cucumber-preprocessor";

type Pizza = {
  name: string;
  size: string;
};

const orderedPizzas: Pizza[] = [];

Given("the client has the following items in the basket", (dataTable: DataTable) => {
  cy.visit("http://localhost:8019/Menu");
  const pizzas = dataTable.hashes();
  pizzas.forEach((pizza) => {
    cy.get(`input[value=${pizza.name}]`).parent().submit();
    orderedPizzas.push(pizza as Pizza);
    cy.visit("http://localhost:8019/Menu");
  });
});

Then("the ordered items should be listed on the my order page", () => {
  cy.visit("http://localhost:8019/MyOrder");
  // const pizzas = dataTable.hashes();
  cy.get("#OrderItems td.order-item-name").each(($el, index, $list) => {
    cy.wrap($el.text()).should("eq", orderedPizzas[index].name);
  });
});
