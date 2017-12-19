Feature: show education

Scenario Outline: i can see the overview where all the departments and there educations are shown
Given a link to the overview page
When i click the link
Then all the <departments> with there <educations> are displayed

	Examples:
	|departments				|educations						|
	|"Lerarenopleiding"	|"Kleuteronderwijs"		|
	|"Lerarenopleiding"	|"Lager onderwijs"		|
	|"Lerarenopleiding"	|"secundair onderwijs"|
	
	|"Gezondheid"				|"Biomedische laboratoriumtechnologie"	  |
	|"Gezondheid"				|"Mondzorg"																|
	|"Gezondheid"				|"Verpleegkunde"													|
	|"Gezondheid"				|"Voedings- en Dieetkunde"								|
	|"Gezondheid"				|"Vroedkunde"															|
	
	|"Welzijn"					|"Orthopedagogie"											|									
	|"Welzijn"					|"Sociaal werk"												|
	|"Welzijn"					|"Sociale readaptatiewetenschappen"		|
	
	|"Management"				|"Bedrijfsmanagement"	|
	|"Management"				|"Office Management"	|
	
	|"Technologie"			|"Chemie"									|
	|"Technologie"			|"Elektromechanica"				|
	|"Technologie"			|"Elektronica-ICT"				|
	|"Technologie"			|"Energietechnologie"			|
	|"Technologie"			|"Toegepaste informatica"	|
	

	