Feature: Saucedemo Login and Checkout Flow

  Background:
    Given User is on the Saucedemo login page

  Scenario: Successful login with valid credentials
    When User enters valid username and password
    And User clicks the login button
    Then User should be redirected to the inventory page

  Scenario: Unsuccessful login with invalid credentials
    When User enters invalid username and password
    And User clicks the login button
    Then A login error message should be displayed

  Scenario: Successful checkout flow
    Given User is logged in with standard credentials
    And User adds a product to the cart
    And User navigates to the cart page
    And User proceeds to the checkout page
    And User fills in checkout information
    And User clicks the finish button
    Then User should see the confirmation message

  Scenario: Successful logout from inventory page
    Given User is logged in with standard credentials
    When User clicks the menu button
    And User clicks the logout link
    Then User should be redirected to the login page

  Scenario: Remove product from the cart
    Given User is logged in with standard credentials
    And User adds a product to the cart
    And User navigates to the cart page
    When User removes the product from the cart
    Then The cart should be empty

  Scenario: Checkout with missing first name
  Given User is logged in with standard credentials
  And User adds a product to the cart
  And User navigates to the cart page
  And User proceeds to the checkout page
  When User fills in checkout info with missing first name
  Then A checkout error message should be displayed

  Scenario: Checkout with missing postal code
    Given User is logged in with standard credentials
    And User adds a product to the cart
    And User navigates to the cart page
    And User proceeds to the checkout page
    When User fills in checkout info with missing postal code
    Then A checkout error message should be displayed


