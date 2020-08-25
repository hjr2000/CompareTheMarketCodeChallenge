Feature: Comparing Meerkats

#Basic internet facing 'smoke test' - run this test first to make sure everything is functioning correctly

Scenario: Running a Google search
  Given I am on the google homepage
  When I search for webdriver
  Then the page title is as expected

@Runme
Scenario: Running a test against Compare the Market
  Given I am on the Energy Journey homepage
  When I submit my postcode
  And I select electricity only for the purposes of price comparison
  And I select the first supplier shown
  And I move onto the electricity tariff page
  And I enter 2000 kwh as my monthly electricity usage
  And I enter the date of my most recent bill
  And I move onto the Your Details page
  And I select Fixed Tariff
  And I select Monthly Direct Debit
  And I enter "fake@fake.com" as my email address
  And I select Do Not Contact as my contact preference
  And I select the Confirm check box if required
  And I click the Go To Prices button
  Then I see that the section titled "Cheapest switchable tariff" is present

# Enhancement - random journey generator
# Table driven journey
# Condensed GWT for readability - yes it's an antipattern. Sort of. Add link to Cuke page.
# Standard set of test data for fully deterministic tariff results - but full tariff engine should be checked at API level. Obtaining data from the results looks difficult!
# Guarantee of being able to always select the same supplier each time will require more code but would need to be done for the real thing.
# Note: biz rules for confirm checkbox unknown so have dealt with both cases
# Date used for most recent bill could be automatically populated with the first of the current month and year so that the test remains realistic
# Consider whether there would be any benefit to using dependency injection
# Assertions?
# Commonly used standard data e.g. postcode hived off into a properties file.
# Reliability - this test is 100% reliable it seems, not an easy feat to achieve with Selenium. Why Se? x-browser. Can use with Bstack (inc devices etc), Applitools
# Plug in Selenide - makes Selenium actually usable.
# Explanation of the utilities etc, deal with common pain in the backside Selenium problems.
