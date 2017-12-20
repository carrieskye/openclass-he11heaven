Feature: show education

Als een leerling
Kan ik een overzicht van de afdelingen en hun opleidingen zien 
Zodat ik een opleiding kan kiezen om een openlesdag te volgen

Scenario Outline: i can see the overview where all the departments and there educations are shown

Given a link to the overview page
When i click the link
Then all the <departments> with their <educations> are displayed

	Examples:
	|departments				|educations						|
	|"Lerarenopleiding"	|"Kleuteronderwijs"		|
	|"Lerarenopleiding"	|"Lager Onderwijs"		|
	|"Lerarenopleiding"	|"Secundair Onderwijs"|
	
	|"Gezondheid"				|"Biomedische Laboratoriumtechnologie"	  |
	|"Gezondheid"				|"Mondzorg"																|
	|"Gezondheid"				|"Verpleegkunde"													|
	|"Gezondheid"				|"Voedings- en Dieetkunde"								|
	|"Gezondheid"				|"Vroedkunde"															|
	
	|"Welzijn"					|"Orthopedagogie"											|									
	|"Welzijn"					|"Sociaal Werk"												|
	|"Welzijn"					|"Sociale Readaptatiewetenschappen"		|
	
	|"Management"				|"Bedrijfsmanagement"	|
	|"Management"				|"Office Management"	|
	
	|"Technologie"			|"Chemie"									|
	|"Technologie"			|"Elektromechanica"				|
	|"Technologie"			|"Elektronica-ICT"				|
	|"Technologie"			|"Energietechnologie"			|
	|"Technologie"			|"Toegepaste Informatica"	|
	

	