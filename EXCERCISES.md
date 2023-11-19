# Exercises

## E1: Driver pattern

Most of the step definition methods use "drivers" to perform automation tasks instead of directly working with the REST API requests (managed through the `WebApiContext` class).

1. Review the `AuthStepDefinitions` class that performs login-related steps and the related `AuthApiDriver` class that encapsulates the automation logic. _Why is this separation is useful?_ 
2. Compare the driver pattern usage in the `AuthStepDefinitions` class with the `RegistrationStepDefinitions` class that performs the REST API requests directly. _Explain how the driver pattern reduces code duplication._
3. **Bonus:** Try to extract the registration automation logic to a `UserApiDriver` class (the class structure has been already created for you).

## E2: Current Object

In some cases the scenario describes what happens with a particular entity (object) through multiple steps. The first step typically identifies the object (e.g. `Given there is an apple`) and then the subsequent steps just work with that (e.g. `When the apple falls down` or `When it falls down`). The "Current Object" pattern is used as a "memory" that can remember what the current object is.

1. Review the "Customer changed the size of the pizza" scenario in the `my_order.feature` file. It talks about a pizza that has been added to the basket and later modified. _Which phrase do we use in the step to refer to the current pizza?_ 
2. Check the related step definitions and learn how these steps know what the current pizza (order item) was. _What is the benefit of using an auto-wired class instance to store the current pizza instead of just having an `int` field in the step definition class?_ 
3. **Bonus:** Check the "Client uses member-only services after login" scenario in `authentication.feature`. Change the 'when' step to `When the client logs in with the registered user credentials` and update the automation code to make the scenario pass again. Hint: you need to remember the "current user". The `CurrentUserContext` class structure has  already been created for you. 

## E3: Ensure

In order to perform a step we might need some prerequisites. These can be explicitly expressed using 'given' steps, but this might make the scenario too verbose. The "ensure" pattern can help.

1. The "Customer has a few different pizzas in the basket" scenario in the `my_order.feature` has two versions. The first has an explicit login step, but the second does not. Review the differences between the two "Given the client has the following items in the basket" steps in the two scenarios. _How does the second one knows that it needs to perform a login first?_
2. Review the bases classes we created to make tracking dependencies easier: `PrerequisiteBase` and `TrackedPrerequisiteBase`. _What are the differences between these?_
3. Uncomment the 'given' step in the "Customer has a few different pizzas in the basket - ensure version" scenario and see the test execution log. _What happened? How many times was the login performed?_
4. **Bonus 1:** The scenario "The logged-in username is shown on home page" in `home.feature` also uses the 'ensure' pattern in order to make sure that there is a user registered, but here we are depending on a registered user with a particular name! _How was that solved?_
5. **Bonus 2:** Implement an 'ensure' pattern to ensure that you have something in your basket before placing an order. The scenario "Customer places an order" in `my_order.feature` guides you. If you did it right, you can delete the 'given' step and the scenario will still pass.

## E4: Attempt Action

In many cases, if there is an "action" in the application (e.g. login) you might need to automate it in two ways:
* Sometimes you just need to have the action _performed_, and it should always succeed. (E.g. when you say "Given the client has logged in")
* In other cases, you might be also be interested in the negative outcome as well. For example when you want to test login errors. In these cases, the 'when' step, where you call the action from should pass even if the action failed and you verify the pass/fail status of the action in the 'then' steps.

Managing these two ways all the time is cumbersome, but we can use the "attempt action" pattern to help!

1. Review the `AuthApiDriver` class and the related `AuthStepDefinitions` step definition class. These use an attempt action pattern for the login! Compare them with the `UserApiDriver`/`RegistrationStepDefinitions` that implements the registration action without the pattern. _What are the differences? List the potential benefits of the attempt action solution?_
2. Review the related infrastructure classes: `ActionAttempt`, `CommandActionAttempt`, `WebApiActionAttempt`. _What is the purpose of these? What is the difference between the `ActionAttempt` and `CommandActionAttempt`?_
3. **Bonus:** Implement the attempt action pattern for the registration action.

## E5: Reset data

This application simulates using a shared, external database, for the sake of the demonstration. (In our case it would be better to use an in-memory database.) This means that we need to "reset" any changes that we make in the shared database after each test - otherwise the subsequent tests might fail.

1. In this codebase, the `resetDatabaseToBaseline` hook in the `DatabaseHooks` class does the data reset. Comment out the content of the hook and run all tests. _Why have the tests failed?_
2. Restore the original hook code, but change the `@Before` annotation to `@After`. Run all tests. _What is the difference? Which one is better?_
3. **Bonus:** Using the `@After` hook, can you make any of the tests fail without changing the code or the scenarios? Hint: run the app and make changes to the database before running a specific scenario. The app and the tests use the same database in this demo!
