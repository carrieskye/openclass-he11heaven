package domain;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenLesDag {
	private int id;
	private Date datum;
	private List<OpenClassSession> sessies;
=======

import java.time.LocalDateTime;

public class OpenLesDag {
	private int id;
	private LocalDateTime begin;
	private LocalDateTime einde;
	private String titel;
	private String locatie;
>>>>>>> cf1b194c2114ac8e4d18d0961cdba5137241821f

	public OpenLesDag(LocalDateTime begin, LocalDateTime einde) {
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

<<<<<<< HEAD
	public OpenLesDag(Date datum) {
		setDatum(datum);
		sessies = new ArrayList<>();
=======
	public String getLocatie() {
		return locatie;
	}
>>>>>>> cf1b194c2114ac8e4d18d0961cdba5137241821f

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
