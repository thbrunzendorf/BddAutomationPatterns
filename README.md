# GeekPizza Sample Application for BDD Automation Patterns workshop by Gaspar Nagy & Seb Rose

## Preparation

### Machine Prerequisites

* Windows, Mac or Linux operating system.
* Approximately 500MB free space is necessary in total (including size of dependencies).
* *Java 8 JDK* (or later) with *Maven* installed
* Optional: Java IDE (e.g. *IntelliJ*, *Eclipse* or *Visual Studio Code*)

### Preparation Steps

* Clone the coding exercises from this GitHub repository or [download it as zip](archive/refs/heads/main.zip) and unzip it. **Avoid using shared drives!** We recommend using a folder with a shorter path to avoid reaching the maximum folder path limit of Windows.

* **_Now we are going to test your setup._** For that we will use the E1 exercise as an example. Please perform the following steps carefully. If you find any problems, please inform us immediately, so that we can fix the problems in time.
  * We will refer the _mvn_ command a few times below. If that does not work, you can also use the _mvnw_ command instead (_mvnw_ is an _mvn_ wrapper that chooses the right version of _mvn_). 
* _**Download dependencies:**_ Download dependencies for the E1 exercise (this will ensure that all dependencies that are used by the exercises are downloaded to the machine)
  * On a command line shell, go to the E1 folder in the downloaded repository.
  * Invoke: `mvn compile`
* _**Test compilation, test & app execution:**_ Run tests of E1 exercise: there should be several passing tests (and no failing one).
  * On a command line shell, go to the E1 folder again.
  * Invoke: `mvn test`
  * The command should complete without errors and report `Tests run: 16, Failures: 0, Errors: 0, Skipped: 0`
  * Invoke: `mvn spring-boot:run`
  * The command should complete without errors, and you should be able to navigate to http://localhost:8019 and see a pizza website, with some pizzas on the menu.
* _**Test IDE setup:**_ Open E1 exercise in your IDE and run the demo app and the tests.
  * Open/import `pom.xml` from the E1 exercise in your IDE.
  * Build solution. Make sure it builds successfully.
  * Run tests (e.g. `CucumberApiTest` class) from IDE. You should get the same result as in Maven.
  * Open `home.feature` file. You should see a feature file with syntax coloring. If not: you need a Cucumber plugin.
  * Run the sample application. You should be able to navigate to http://localhost:8019.

CONGRATULATIONS, YOU ARE READY TO GO!
