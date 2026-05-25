# Selenium TestNG Automation Framework

A scalable UI/API automation framework built using **Selenium WebDriver + TestNG**, with support for **parallel execution, Selenium Grid, retry mechanisms, reporting, data-driven testing, API automation, and BDD (Cucumber)**.

This framework demonstrates a layered automation design that separates test logic, page locators, action classes, utilities, reporting, and configuration to improve maintainability and scalability.

---

# Architecture Overview

## High-Level Design

```text
Test Layer
│
├── UI Tests (TestNG)
├── API Tests (RestAssured)
├── BDD Tests (Cucumber + TestNG Runner)
│
▼
Action Layer
│
├── HomePageActions
├── CartPageActions
├── OrdersPageActions
├── FinalCheckoutPageActions
│
▼
Page Object / Locator Layer
│
└── ShoppingProcessPage
│
▼
Framework Core
│
├── BaseActions
├── Driver Management
├── ThreadLocal WebDriver
├── ConfigManager
├── Wait Utilities
├── Retry Logic
├── Listeners
├── Screenshot Utility
│
▼
Infrastructure
│
├── Selenium Grid
├── Local Browsers
├── Headless Execution
├── Gradle Build
└── Reporting
