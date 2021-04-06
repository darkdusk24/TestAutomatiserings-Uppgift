Feature: UserCreator
Description: To have an easy way for people to contact me I want to make an account on Mailchimp

	Scenario Outline: Create User
		Given I have entered <email> into the e-mail input box
		And I have entered <username> into the username input box
		And I have entered <password> into the password input box
		When I press the sign up button
		Then <outcomeText> should be visible on the screen

Examples:
|						email|		username|			password|						outcomeText|
|"Test@mail.com"|"functional"|"Testing-123"|"Check your email"|
|"Test@mail.com"|"OneHundredOne"|"Testing-123"|"Enter a value less than 100 characters long"|
|"Test@mail.com"|"alreadyInUse"|"Testing-123"|"Another user with this username already exists. Maybe it's your evil twin. Spooky."|
|""|"functional"|"Testing-123"|"Please enter a value"|
