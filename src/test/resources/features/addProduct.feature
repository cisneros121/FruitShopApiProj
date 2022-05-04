Feature: Add product

  Scenario: Add a new product to existing vendor
    Given I find get vendor id from vendor "Fun Fresh Fruits Ltd."
    When I add a product with the name "apple sauce" price 4 and category "acc" to selected vendor
    Then i verify produce is added
    Then I delete the product






