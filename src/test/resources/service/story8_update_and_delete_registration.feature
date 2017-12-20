Feature: update and delete registration

As an organiser
I want to be able to manage (edit or delete) registrations
so the list stays updated.

Scenario: I can update a registration

Given an administrator that is logged in
And He is on the registration overview page of "Bomen en Grafen" on "2018-04-20"
When he clicks on "bewerken" next to a student
Then he can edit the details for that student

Scenario: I can delete a registration

Given an administrator that is logged in
And He is on the registration overview page of "Bomen en Grafen" on "2018-04-20"
When he clicks on "verwijderen" next to a student
Then the student is removed from the registrations

