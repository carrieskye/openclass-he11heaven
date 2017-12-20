Feature: confirmation email

As a user 
I want to receive an email when I have registered for a session
so that I can confirm that my registration has succeeded.

Scenario: I receive an email when I successfully register for a session

Given a user with first name "Donald", last name "Trump" and an email address "dtrump@whitehouse.com"
And he is on the "Toegepaste Informatica" sessions overview page for "2018-04-20"
When he applies for a session
Then he receives a confirmation email

