Feature: Comparing Meerkats

##################################################################################################################
# Compare the Market Energy Journey Test
##################################################################################################################

@Runme
Scenario: Running a test against Compare the Market - energy journey
  Given I am on the Energy Journey homepage
  When I populate the Energy Journey start page with standard information
  And I populate the Electricity Tariff page with standard information using 2000 kwh as my monthly electricity usage
  And I populate the Your Details page with standard information using "fake@fake.com" as my email address
  Then I see that the section titled "Cheapest switchable tariff" is present

##################################################################################################################
# Compare the Market Energy Journey Test - identical to the test above but using verbose Gherkin
##################################################################################################################

Scenario: Running a test against Compare the Market - energy journey - suboptimal gherkin
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

##################################################################################################################
#Basic internet facing 'smoke test' - run this test if there is an issue with the CTM test to confirm all is well
##################################################################################################################

Scenario: Running a Google search
  Given I am on the google homepage
  When I search for webdriver
  Then the page title is as expected