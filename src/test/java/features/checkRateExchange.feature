Feature: Check exchange rate on the 'www.open.ru'
  Check that sell rate is bigger than buy rate

  @checkRate
  Scenario: Sell rate is bigger than buy rate
    Given go to 'https://www.open.ru/'
    Then check sell rate is bigger than buy rate