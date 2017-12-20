Feature: overview open campus days

As a user
I want to be able to see the sessions of a particular day for that bachelor
so that I can choose which session I want to apply for

Scenario Outline: I can see the overview of available sessions for a department for a day

Given a user that is on the "Toegepaste Informatica" open campus days page
When he chooses the date "2018-04-20" 
Then the overview of every available <session> is shown

	Examples:
	
	|session|
	|"Bomen en Grafen"|
	|"OOP"|
	|"Scripttalen"| 

	


