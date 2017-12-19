package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OpenClassSession {
	private String title;
	private String description;
	private LocalDateTime start;
	private LocalDateTime end;
	private String header;
	private int maxEntries;
	private ArrayList<Student> students;

	public OpenClassSession(String title, String description, LocalDateTime start, LocalDateTime end, int maxEntries) {
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
		setHeader(title, start, end);
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

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String title, LocalDateTime start, LocalDateTime end) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		this.header = title + " (" + start.format(formatter) + " - " + end.format(formatter) + ")";
	}
}
