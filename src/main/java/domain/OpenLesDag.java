package domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OpenLesDag {
	private int id;
	private Date datum;
	private LocalDateTime begin;
	private LocalDateTime einde;
	private String titel;
	private String locatie;
	private List<OpenClassSession> sessies;

	public OpenLesDag(Date datum,LocalDateTime begin, LocalDateTime einde) {
		sessies = new ArrayList<>();
		setBegin(begin);
		setEinde(einde);
		setTitel(titel);
		setLocatie(locatie);
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		if (titel == null || titel.trim().isEmpty()) {
			throw new DomainException("Titel mag niet leeg zijn");
		}
		this.titel = titel;
	}
	public String getLocatie() {
		return locatie;
	}

	public void setLocatie(String locatie) {
		if (locatie == null || locatie.trim().isEmpty()) {
			throw new DomainException("Locatie mag niet leeg zijn");
		}
		this.locatie = locatie;
	}

	public LocalDateTime getBegin() {
		return begin;
	}

	public void setBegin(LocalDateTime begin) {
		if (begin == null) {
			throw new DomainException("Begindatum/uur mag niet leeg zijn");
		}
		this.begin = begin;
	}

	public LocalDateTime getEinde() {
		return einde;
	}

	public void setEinde(LocalDateTime einde) {
		if (einde == null) {
			throw new DomainException("Einddatum/uur mag niet leeg zijn");
		}
		this.einde = einde;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id == 0) {
			throw new DomainException("ID mag niet leeg zijn");
		}
		this.id = id;
	}

	public String getDatumString() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE dd/MM/uuuu");
		return begin.format(dateFormatter);
	}

	public String getTijdstipString() {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		return begin.format(timeFormatter) + " - " + einde.format(timeFormatter);
		}
	
	
	public void setSessies(List<OpenClassSession> sessies) {
		if(sessies == null) {
			throw new DomainException();
		}
		
		this.sessies = sessies;
	}
	
	public List<OpenClassSession> getSessies() {
		return this.sessies;
	}
	public OpenClassSession getSessies(int id) {
		return sessies.get(id);
	}
	
	public void addSessie(OpenClassSession o) {
		if(o == null) {
			throw new DomainException();
		}
		sessies.add(o);
	}
}
