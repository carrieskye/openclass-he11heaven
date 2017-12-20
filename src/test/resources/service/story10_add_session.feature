Feature: add a session

As a user
I want to be able to apply for a session
so that I can come to the open school days

Scenario: I can add a session

Given an administrator that is logged in
And a session with name "Webontwikkeling" starting at "10:30" and ending at "13:30" on "2018-03-03"
And with a description "Een introductie in het maken van je eigen website." and location "D1.80" and category "Toegepaste Informatica" and max inschrijvingen 20
And he is on the add session page
When he submits this session
Then the session is added

Scenario Outline: An error message is displayed if a field is left empty

Given an administrator that is logged in
And a session with name <name> starting at <startTime> and ending at <endTime> on <date>
And with a description <description> and location <location> and category <category> and max inschrijvingen <max>
When he submits this session on the add a session page
Then an error message is shown

	Examples:
	|name|startTime|endTime|date|description|location|category|max|
	|""|"10:30"|"13:30"|"2018-03-03"|"Een introductie in het maken van je eigen website."|"D1.80"|"Toegepaste Informatica"| 30 |
	|"Webontwikkeling"|""|"13:30"|"2018-03-03"|"Een introductie in het maken van je eigen website."|"D1.80"|"Toegepaste Informatica"|30 |
	|"Webontwikkeling"|"10:30"|""|"2018-03-03"|"Een introductie in het maken van je eigen website."|"D1.80"|"Toegepaste Informatica"|30 |
	|"Webontwikkeling"|"10:30"|"13:30"|""|"Een introductie in het maken van je eigen website."|"D1.80"|"Toegepaste Informatica"|30 |
	|"Webontwikkeling"|"10:30"|"13:30"|"2018-03-03"|""|"D1.80"|"Toegepaste Informatica"|30 |
	|"Webontwikkeling"|"10:30"|"13:30"|"2018-03-03"|"Een introductie in het maken van je eigen website."|""|"Toegepaste Informatica"|30 |
	|"Webontwikkeling"|"10:30"|"13:30"|"2018-03-03"|"Een introductie in het maken van je eigen website."|"D1.80"|""|30 |

	


