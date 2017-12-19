package domain;

public class Student {
	private String firstName, lastName, email;

	public Student(){}

	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isEmpty()) {
			throw new DomainException("Voornaam mag niet leeg zijn.");
		}
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		if (lastName == null || lastName.isEmpty()) {
			throw new DomainException("Achternaam mag niet leeg zijn.");
		}
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new DomainException("Email mag niet leeg zijn.");
		}
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
}
