Feature: overview open campus days

As a user
I want to be able to apply for a session
so that I can come to the open school days

Scenario: I can apply for an open session

Given a user with first name "Donald", last name "Trump" and an email address "dtrump@whitehouse.com"
And he is on a sessions overview page
When he applies for a session
Then He is registered for that session

Scenario Outline: Entering invalid details shows an error messge

Given a user with first name <firstName>, last name <lastName> and an email address <email>
And he is on a sessions overview page
When he applies for a session
Then an error message is shown

	Examples:
	
	|firstName | lastName | email |
	|" " | "Trump" | "thePresident@whitehouse.com" |
	|"Donald " | "Trump" | " "|
	|"Donald" | " " | "thepresident@whitehouse.com"|


	


