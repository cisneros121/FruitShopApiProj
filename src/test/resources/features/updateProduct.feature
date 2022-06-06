Feature: update Product

  @wip
  Scenario: update a product name and price
    Given Find product named "bananas"
    When I update product with name "apple" and price 0.79;
    Then I confirm product update



