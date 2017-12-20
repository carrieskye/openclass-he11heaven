Feature: overview registered students

As an organiser
I want to be able see an overview of all registered students for a particular session
so that I can inform the teachers of who’s coming to their open lessons.


Scenario: I can see the overview of registered students per session

Given an administrator that is logged in
And he is on the "Toegepaste Informatica" sessions overview page for "2018-04-20"
When he clicks on "Overzicht Inschrijvingen" of "Bomen en Grafen"
Then an overview of registered students is shown


