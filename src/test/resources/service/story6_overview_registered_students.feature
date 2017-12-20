Feature: overview registered students

As an organiser
I want to be able see an overview of all registered students for a particular session
so that I can inform the teachers of who’s coming to their open lessons.


Scenario: I can see the overview of registered students per session

Given an administrator that is logged in
And he is on a session overview page
When he clicks on overzicht inschrijvingen 
Then an overview of registered students is shown


