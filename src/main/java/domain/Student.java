package domain;

public class Student {
	private int id;
	private String firstName, lastName, email;

	public Student() {
		this.id = -1;
	}

	public Student(int id, String firstName, String lastName, String email) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
	}

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

	public int getId() {
		if (id < 0) {
			throw new DomainException("Id is ongeldig.");
		}
		return id;
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

	public void setId(int studentId) {
		this.id = studentId;
	}
}
