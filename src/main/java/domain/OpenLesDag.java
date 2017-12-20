package domain;

import java.time.LocalDateTime;

public class OpenLesDag {
	private int id;
	private LocalDateTime begin;
	private LocalDateTime einde;
	private String titel;
	private String locatie;

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
}
