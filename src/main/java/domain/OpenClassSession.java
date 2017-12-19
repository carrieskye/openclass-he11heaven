package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OpenClassSession {
	private int id;
	private String title;
	private String description;
	private LocalDateTime start;
	private LocalDateTime end;
	private String header;
	private int maxEntries;
	private ArrayList<Student> students;

	public OpenClassSession(int id, String title, String description, LocalDateTime start, LocalDateTime end,
			int maxEntries) {
		this.id = id;
		this.setTitle(title);
		this.description = description;
		setStart(start);
		setEnd(end);
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

	private void setStart(LocalDateTime start) {
		if (start.isBefore(LocalDateTime.now())) {
			throw new DomainException("Start date must be in the future.");
		}
		this.start = start;
	}

	private void setEnd(LocalDateTime end) {
		if (end.isBefore(LocalDateTime.now())) {
			throw new DomainException("End date must be in the future.");
		}
		this.end = end;
	}

	private void setHeader(String title, LocalDateTime start, LocalDateTime end) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		this.header = title + " (" + start.format(formatter) + " - " + end.format(formatter) + ")";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getHeader() {
		return this.header;
	}

	public String getDescription() {
		return this.description;
	}

	public LocalDateTime getStart() {
		return this.start;
	}

	public LocalDateTime getEnd() {
		return this.end;
	}
}
