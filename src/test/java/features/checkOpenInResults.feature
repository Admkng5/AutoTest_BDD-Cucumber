Feature: Check 'www.open.ru' in search results
  Check that search results contains 'www.open.ru'

  @open
  Scenario: Search results contains 'www.open.ru'
    Given go to 'https://www.google.com/'
    When search 'Открытие'
    Then search results contains 'www.open.ru'