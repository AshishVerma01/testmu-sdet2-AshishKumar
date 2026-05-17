@tag
  Feature: Purchase the order from Ecommerce website

    Background: I landed on Ecommerce website

    @smoke
    Scenario Outline: Positive test of submitting the order
      Given Logged In with username <username> and password <password>
      When I added the product <product> to cart
      And I proceed for checkout
      Then "THANKYOU FOR THE ORDER." message is displayed on the confirmation page

      Examples:
      |          username             | password |   product   |
      | testframeworkdesign@gmail.com | Test@123 | Zara Coat 3 |