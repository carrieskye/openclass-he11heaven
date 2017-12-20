Feature: overview open campus days

As a user
I want to be able to apply for a session
so that I can come to the open school days

Scenario: I can apply for an open session

Given a user with first name "Donald", last name "Trump" and an email address "dtrump@whitehouse.com"
And he is on the "Toegepaste Informatica" sessions overview page for "2018-04-20"
When he applies for a session
Then He is registered for that session

	


