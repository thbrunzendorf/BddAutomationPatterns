import {
  When,
  Then,
  Given,
  Before,
} from "@badeball/cypress-cucumber-preprocessor";

import * as chrono from "chrono-node";

Before(() => {
  visitHomePage();
  cy.get("a").contains("[Reset Data]").click();
});

Before({ tags: "@login" }, () => {
  cy.visit("localhost:8019/Login");
  cy.get("input#Name").type("Marvin");
  cy.get("input#Password").type("1234");
  cy.get("input#LoginButton").click();
});

Given("the client has items in the basket", () => {
  cy.get("a").contains("Menu").click();
  cy.get("input[value='Add to my order!']").first().click();
});

When("the client checks the my order page", () => {
  cy.visit("localhost:8019/MyOrder");
});

When(
  "the client specifies {} at {} as delivery time",
  (date: string, time: string) => {
    const deliveryDate = chrono.parseDate(`${date} ${time}`);

    cy.visit("localhost:8019/MyOrder");
    cy.get("#ChangeDetailsButton").click();
    cy.get("#DeliveryDate")
      .clear()
      .type(
        Intl.DateTimeFormat("en-CA", {
          year: "numeric",
          month: "2-digit",
          day: "2-digit",
        }).format(deliveryDate),
      );
    cy.get("#DeliveryTime")
      .clear()
      .type(`${deliveryDate.getHours()}:${deliveryDate.getMinutes()}`);
    cy.get("#SaveButton").click();
  },
);

Then(
  "the order should indicate that the delivery date is {}",
  (date: string) => {
    const expectedDeliveryDate = chrono.parseDate(`${date}`);

    cy.get("#DeliveryDate").should(
      "contain.text",
      Intl.DateTimeFormat("en-CA", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
      }).format(expectedDeliveryDate),
    );
  },
);

Then("the delivery time should be {}", (time: string) => {
  const expectedDeliveryDate = chrono.parseDate(`${time}`);

  cy.get("#DeliveryDate").should(
    "contain.text",
    `${expectedDeliveryDate.getHours()}:${expectedDeliveryDate.getMinutes()}`,
  );
});

const visitHomePage = () => cy.visit("http://localhost:8019/");
