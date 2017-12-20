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
	private String classroom;

	public OpenClassSession(int id, String title, String description, LocalDateTime start, LocalDateTime end,
			int maxEntries, String classroom) {
		this.id = id;
		setTitle(title);
		setDescription(description);
		setMaxEntries(maxEntries);
		setStart(start);
		setEnd(end);
		setHeader(title, start, end);
		setClassroom(classroom);
		students = new ArrayList<>();

	}

	public OpenClassSession() {

	}

	public int getMaxEntries() {
		return maxEntries;
	}

	public void setMaxEntries(int maxEntries) {
		if(maxEntries < 1){throw new DomainException("Maximum amount of entries is unvalid.");}
		this.maxEntries = maxEntries;
	}

	public void setDescription(String description) {
		if(description == null || description.trim().isEmpty()){
			throw new DomainException("Description can't be empty.");
		}
		this.description = description;
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

	public void setStart(LocalDateTime start) {
		if (start.isBefore(LocalDateTime.now())) {
			throw new DomainException("Start date must be in the future.");
		}
		this.start = start;
	}

	public void setEnd(LocalDateTime end) {
		if (end.isBefore(LocalDateTime.now())) {
			throw new DomainException("End date must be in the future.");
		}
		this.end = end;
	}

	public void setHeader(String title, LocalDateTime start, LocalDateTime end) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		this.header = title + " (" + start.format(formatter) + " - " + end.format(formatter) + ")";
	}

	public void setTitle(String title) {
		if(title == null || title.trim().isEmpty()){
			throw new DomainException("Title can't be empty");
		}
		this.title = title;
	}

	public void setClassroom(String a) {
		if (a == null || a.trim().isEmpty()) {
			throw new DomainException("classroom must not be empty");
		}
		this.classroom = a;
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

	public String getClassroom() {
		return this.classroom;
	}
	
	
	public String toStringDate() {
		
		return this.getStart().getDayOfMonth() + "-" + this.getStart().getMonthValue() + "-" + this.getStart().getYear();
	}
	
	public String toStringHour() {
		return this.getStart().getHour() + ":" + this.getStart().getMinute();
	}
	
}
