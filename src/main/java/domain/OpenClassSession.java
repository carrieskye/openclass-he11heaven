package domain;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class OpenClassSession {
	private String title;
	private String description;
	private DateTime start;
	private DateTime end;
	private int maxEntries;
	private ArrayList<Student> students;

	public OpenClassSession(String title, String description, DateTime start, DateTime end, int maxEntries) {
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
		this.maxEntries = maxEntries;
		students = new ArrayList<>();
	}

	public void register(Student student) throws DomainException {
		if (student == null) {
			throw new DomainException("Invalid student.");
		} else if (students.size() < maxEntries) {
			students.add(student);
		} else {
			throw new DomainException("This session is full.");
		}
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public DateTime getStart() {
		return start;
	}

	public DateTime getEnd() {
		return end;
	}

}
