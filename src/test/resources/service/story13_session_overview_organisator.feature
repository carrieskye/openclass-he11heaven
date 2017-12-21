
Feature: Session overview organisator
	As an organisor
	I want to be able to view a summary of all the sessions of an education
	and a list of subscribers for specific sessions
	So that i can get an easy overview for organisable purposes.

Scenario: I can see an overview of all sessions of all departments together
Given a session with <title>, <description>, <start>, <end>, <maxEntries>, <classroom>
	And the session is added
	And a student with <voornaam>, <naam>, <email>
	And the student is subscribed for the session
When i request the organisatorial overview
Then an overview is shown for the session with a <title>, <description>, <start>, <end>, <maxEntries>, <classroom>
