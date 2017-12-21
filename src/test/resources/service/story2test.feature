Feature: overview open campus days


As a user
I want to be able to see the overview of the open campus days
so that I can see which days I can register for the open campus days of my bachelor

Scenario Outline: i can see the overview of the open campus days

Given a user that is on the education overview page
When he chooses a the education <education>
Then the overview of <dates> with open days for that education is shown

	Examples:
	
|education								|dates						|
|"Toegepaste Informatica"	|"18 apr 2018"		|
|"Chemie"									|"20 mei 2018"		|
|"Vroedkunde"							|"13 feb 2018"		|


	

	

