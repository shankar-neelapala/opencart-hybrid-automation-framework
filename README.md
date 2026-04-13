# OpenCart Demo - Selenium Test Automation Framework

A Java-based Selenium WebDriver test automation framework for the [OpenCart demo application](https://tutorialsninja.com/demo/index.php), built using TestNG, Maven, and the Page Object Model (POM) design pattern.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Configuration](#configuration)
- [Test Cases](#test-cases)
- [Running Tests](#running-tests)
  - [Local Execution](#local-execution)
  - [Remote Execution (Selenium Grid)](#remote-execution-selenium-grid)
  - [Cross-Browser Testing](#cross-browser-testing)
- [Test Suites](#test-suites)
- [Reporting](#reporting)
- [Screenshots](#screenshots)
- [Logs](#logs)
- [Framework Architecture](#framework-architecture)

---

## Project Overview

This framework automates end-to-end functional test scenarios for the OpenCart e-commerce platform. It covers key user journeys including account registration, login (both single and data-driven), product search, shopping cart management, and cart item removal.

The framework supports:
- **Local** and **Remote (Selenium Grid)** execution
- **Cross-browser** testing (Chrome, Edge, Firefox)
- **Data-driven** testing via Excel files
- **Parallel** test execution
- **HTML test reporting** via ExtentReports
- **Screenshot capture** on test failure
- **Structured logging** via Log4j2

---

## Tech Stack

| Tool / Library         | Version   | Purpose                              |
|------------------------|-----------|--------------------------------------|
| Java                   | 8+        | Programming language                 |
| Selenium WebDriver     | 4.40.0    | Browser automation                   |
| TestNG                 | 7.12.0    | Test framework and runner            |
| Maven                  | 3.x       | Build and dependency management      |
| Apache POI             | 5.5.1     | Excel data reading (data-driven tests)|
| ExtentReports          | 5.1.2     | HTML test reporting                  |
| Log4j2                 | 2.25.3    | Logging                              |
| Apache Commons IO      | 2.21.0    | File utilities                       |
| Apache Commons Lang3   | 3.20.0    | String/random data utilities         |
| Docker                 | -         | Selenium Grid containerized setup    |

---

## Project Structure

```
com.opencart.demo/
├── src/
│   └── test/
│       ├── java/
│       │   ├── pageobjects/                  # Page Object classes
│       │   │   ├── BasePage.java             # PageFactory initialization
│       │   │   ├── HomePage.java             # Home page actions
│       │   │   ├── LoginPage.java            # Login page actions
│       │   │   ├── AccountRegistrationPage.java
│       │   │   ├── MyAccountPage.java
│       │   │   ├── SearchProductPage.java
│       │   │   └── ShoppingCartPage.java
│       │   ├── testbase/
│       │   │   └── BaseClass.java            # WebDriver setup/teardown, utilities
│       │   ├── testcases/                    # Test classes
│       │   │   ├── TC1AccountRegistration.java
│       │   │   ├── TC2LoginWithValidCredentials.java
│       │   │   ├── TC3LoginDataDriven.java
│       │   │   ├── TC4SearchProduct.java
│       │   │   ├── TC5ShoppingCart.java
│       │   │   └── TC6RemoveProductFromCart.java
│       │   └── utilities/
│       │       ├── DataProviders.java        # TestNG DataProvider for Excel data
│       │       ├── ExcelUtilities.java       # Apache POI Excel helper
│       │       └── ExtentReportsManager.java # ExtentReports listener
│       └── resources/
│           ├── config.properties             # App URL, credentials, execution env
│           └── log4j2.xml                    # Log4j2 configuration
├── testdata/
│   └── logindata.xlsx                        # Test data for data-driven login tests
├── reports/                                  # Auto-generated HTML test reports
├── screenshots/                              # Screenshots captured on failure
├── logs/                                     # Log files
├── master.xml                                # Main TestNG suite (all tests)
├── cross-browser.xml                         # Cross-browser TestNG suite
├── selenium-grid.xml                         # Selenium Grid TestNG suite
├── docker-compose.yaml                       # Docker Selenium Grid setup
├── run.bat                                   # Windows batch script to run tests
└── pom.xml                                   # Maven project configuration
```

---

## Prerequisites

- **Java JDK 8+** installed and `JAVA_HOME` configured
- **Maven 3.x** installed and available on `PATH`
- **Google Chrome** and/or **Microsoft Edge** browser installed (for local execution)
- **Docker Desktop** (optional, for Selenium Grid remote execution)

---

## Setup & Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd com.opencart.demo
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Prepare test data:**  
   Create the Excel file at `testdata/logindata.xlsx` with a sheet named `Sheet1`. Each row should contain three columns:

   | Email               | Password   | Result  |
   |---------------------|------------|---------|
   | valid@example.com   | pass@123   | valid   |
   | invalid@example.com | wrongpass  | invalid |

---

## Configuration

Edit `src/test/resources/config.properties` to set your target environment:

```properties
appurl=https://tutorialsninja.com/demo/index.php
email=your-email@gmail.com
password=your-password
execution-env=local    # Use 'local' or 'remote'
```

- `execution-env=local` — Runs tests directly on the local machine using Chrome or Edge.
- `execution-env=remote` — Connects to a Selenium Grid Hub at `http://localhost:4444/wd/hub`.

---

## Test Cases

| Test Class                       | Group       | Description                                                        |
|----------------------------------|-------------|--------------------------------------------------------------------|
| `TC1AccountRegistration`         | regression  | Registers a new user account with random valid data                |
| `TC2LoginWithValidCredentials`   | smoke       | Logs in using credentials from `config.properties`               |
| `TC3LoginDataDriven`             | smoke       | Logs in with multiple credential sets read from Excel              |
| `TC4SearchProduct`               | regression  | Searches for a product by name and verifies the product page       |
| `TC5ShoppingCart`                | regression  | Searches for a product, adds it to the cart, and verifies it       |
| `TC6RemoveProductFromCart`       | regression  | Logs in, opens the cart, and removes a product by product ID       |

---

## Running Tests

### Local Execution

Set `execution-env=local` in `config.properties`, then run:

```bash
mvn test
```

This uses `master.xml` as the suite file (configured in `pom.xml`). The OS and browser are specified via TestNG parameters in the XML suite files.

**Using the batch script (Windows):**
```bat
run.bat
```

### Remote Execution (Selenium Grid)

**Step 1 — Start Selenium Grid with Docker:**
```bash
docker-compose up -d
```
This starts a Selenium Hub and Chrome/Firefox nodes accessible at `http://localhost:4444`.

**Step 2 — Set remote execution in config:**
```properties
execution-env=remote
```

**Step 3 — Run with the grid suite:**
```bash
mvn test -Dsurefire.suiteXmlFiles=selenium-grid.xml
```

**To stop the Grid:**
```bash
docker-compose down
```

### Cross-Browser Testing

Run the same test(s) in parallel across Chrome and Edge:

```bash
mvn test -Dsurefire.suiteXmlFiles=cross-browser.xml
```

This runs `TC2LoginWithValidCredentials` simultaneously on Chrome and Edge (requires remote/grid execution).

---

## Test Suites

| Suite File           | Description                                           |
|----------------------|-------------------------------------------------------|
| `master.xml`         | Full regression suite — all 6 test cases on Edge      |
| `cross-browser.xml`  | Parallel Chrome + Edge execution for TC2              |
| `selenium-grid.xml`  | Selenium Grid suite targeting Linux + Chrome          |

TestNG parameters available in suite files:

| Parameter        | Description                              | Example value |
|------------------|------------------------------------------|---------------|
| `os`             | Target OS platform for remote execution  | `Windows`     |
| `browser`        | Browser to use                           | `chrome`, `edge`, `firefox` |
| `search-product` | Search keyword for product tests         | `mac`         |
| `product-name`   | Exact product name to select             | `macbook`     |
| `product-id`     | Product code/ID for cart operations      | `product 16`  |

---

## Reporting

Test reports are automatically generated in the `reports/` directory after each run.

- **Format:** HTML (ExtentReports Spark theme)
- **Naming:** `Test-Report-yyyy.MM.dd.HH.mm.ss.html`
- **Contents:** Pass/Fail/Skip status, test category (smoke/regression), system info (OS, browser, environment), and screenshots on failure

The report opens automatically in your default browser after the test run completes (on systems that support `java.awt.Desktop`).

---

## Screenshots

Failure screenshots are saved to the `screenshots/` directory.

- **Naming:** `<testMethodName>-yyyy.MM.dd.HH.mm.ss.png`
- Screenshots are also embedded directly into the ExtentReport HTML file for easy review.

---

## Logs

Log files are written to the `logs/` directory via Log4j2.

- **File:** `logs/automation.log` (rolling log)
- Log level and appender configuration is defined in `src/test/resources/log4j2.xml`
- Each test class uses `LogManager.getLogger(this.getClass())` for scoped logging

---

## Framework Architecture

```
┌─────────────────────────────────────────────┐
│                  Test Cases                  │
│  TC1 │ TC2 │ TC3 │ TC4 │ TC5 │ TC6          │
└──────────────────┬──────────────────────────┘
                   │ extends
┌──────────────────▼──────────────────────────┐
│                BaseClass                     │
│  • WebDriver setup/teardown (ThreadLocal)    │
│  • config.properties loading                 │
│  • Local vs Remote execution switch          │
│  • Screenshot utility                        │
│  • Random data generators                    │
└──────────────────┬──────────────────────────┘
                   │ uses
┌──────────────────▼──────────────────────────┐
│              Page Objects                    │
│  • BasePage (PageFactory init)               │
│  • HomePage, LoginPage, MyAccountPage        │
│  • SearchProductPage, ShoppingCartPage       │
│  • AccountRegistrationPage                  │
└──────────────────┬──────────────────────────┘
                   │ reports via
┌──────────────────▼──────────────────────────┐
│           Utilities & Reporting              │
│  • ExtentReportsManager (ITestListener)      │
│  • DataProviders (Excel-driven login data)   │
│  • ExcelUtilities (Apache POI wrapper)       │
└─────────────────────────────────────────────┘
```

**Key design decisions:**

- **ThreadLocal WebDriver** in `BaseClass` ensures thread safety for parallel test execution.
- **Page Object Model** separates locators and actions from test logic, improving maintainability.
- **`@Parameters` annotation** in TestNG makes browser and OS configurable per suite without code changes.
- **`ITestListener`** in `ExtentReportsManager` hooks into TestNG's lifecycle to generate reports automatically.
