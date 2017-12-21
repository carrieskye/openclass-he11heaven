package domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenClassSession {
	private int id;
	private String title;
	private String description;
	private LocalTime start;
	private LocalTime end;
	private String header;
	private int maxEntries;
	private int currentEntries;
	private boolean volzet;
	private ArrayList<Student> students;
	private String classroom;
	private int opleidingsid;
	private int openlesdagid;


	public OpenClassSession(int id, String title, String description, LocalTime start, LocalTime end,
			int maxEntries, String classroom, int currentEntries) {

		this.id = id;
		setTitle(title);
		setDescription(description);
		setMaxEntries(maxEntries);
		setStart(start);
		setEnd(end);
		setHeader(title, start, end);
		setClassroom(classroom);
		setCurrentEntries(currentEntries);
		students = new ArrayList<>();
	}
	
	public OpenClassSession(int id, String title, String description, LocalTime start, LocalTime end,
			int maxEntries, String classroom) {
		this(id, title, description, start, end, maxEntries, classroom, 0);
	}

	public OpenClassSession() {

	}

	public int getCurrentEntries() {
		return currentEntries;
	}

	public void setCurrentEntries(int currentEntries) {
		this.currentEntries = currentEntries;
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

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public void setEnd(LocalTime end) {
		if(end.isBefore(start)) {
			throw new DomainException("End date must be after start date");
		}
		this.end = end;
	}

	public void setHeader(String title, LocalTime start, LocalTime end) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		this.header = title + " (" + start.format(formatter) + " - " + end.format(formatter) + ")";
	}

	public void setTitle(String title) {
		if(title == null || title.trim().isEmpty())
			throw new DomainException("Title can't be empty");
		if(match(title, "^(\\d).*$"))
			throw new DomainException("Title cannot consist of only numbers.");
		this.title = title;
	}

	public void setClassroom(String a) {
		if (a == null || a.trim().isEmpty()) {
			throw new DomainException("classroom must not be empty");
		}
		this.classroom = a;
	}
	
	public void setOpleidingid(int a){
		this.opleidingsid = a;
	}
	
	public int getOpleidingsid(){
		return this.opleidingsid;
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

	public LocalTime getStart() {
		return this.start;
	}

	public LocalTime getEnd() {
		return this.end;
	}

	public String getClassroom() {
		return this.classroom;
	}
	
	public String toStringHour() {
		return this.getStart().getHour() + ":" + this.getStart().getMinute();
	}

	public int getOpenlesdagid() {
		return openlesdagid;
	}

	public void setOpenlesdagid(int openlesdagid) {
		this.openlesdagid = openlesdagid;
	}
	
	private boolean match(String text, String pattern) {
		Pattern p =  Pattern.compile(pattern);
		Matcher matcher = p.matcher(text);
		if(matcher.find()) {
			return true;
		}
		else return false;

	}
	
}
