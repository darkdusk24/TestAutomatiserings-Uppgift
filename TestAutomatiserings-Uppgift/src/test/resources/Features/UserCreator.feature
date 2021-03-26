Feature: UserCreator
To have an easy way for people to contact me I want to make an account on Mailchimp

Scenario Outline: Create User
Given I have entered <email> into the e-mail input box
And I have entered <username> into the username input box
And I have entered <password> into the password input box
When I press the sign up button
Then <outcome> should be visible on the screen

Examples:
|email|username|password|outcome|
|