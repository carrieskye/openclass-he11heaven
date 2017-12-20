package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OpenLesDag {
	private int id;
	private LocalDateTime begin;
	private LocalDateTime einde;
	private String titel;
	private String locatie;
	private String datumString, tijdstipString;

	private List<OpenClassSession> sessies;

	public OpenLesDag(int id, String titel, String locatie, LocalDateTime begin, LocalDateTime einde) {
		sessies = new ArrayList<>();
		setId(id);
		setBegin(begin);
		setEinde(einde);
		setTitel(titel);
		setLocatie(locatie);
	}

	public OpenLesDag(int id, String titel, String locatie) {
		this(id, titel, locatie, null, null);
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
		// if (begin == null) {
		// throw new DomainException("Begindatum/uur mag niet leeg zijn");
		// }
		this.begin = begin;
	}

	public LocalDateTime getEinde() {
		return einde;
	}

	public void setEinde(LocalDateTime einde) {
		// if (einde == null) {
		// throw new DomainException("Einddatum/uur mag niet leeg zijn");
		// }
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

	public String generateDatumString() {
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");
		DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("uuuu");

		return begin.format(dayFormatter) + "<br>" + begin.format(monthFormatter) + "<br>"
				+ begin.format(yearFormatter);
	}

	public String generateTijdstipString() {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		return begin.format(timeFormatter) + " - " + einde.format(timeFormatter);
	}

	private void setSessies(List<OpenClassSession> sessies) {
		if (sessies == null) {
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

	public void addSessie(OpenClassSession sessie) {
		if (sessie == null) {
			throw new DomainException();
		}
		// als er nog geen begin en einde is, die van sessie overnemen
		if (begin == null && einde == null) {
			setBegin(sessie.getStart());
			setEinde(sessie.getEnd());
		}
		// bepalen of toegevoegde sessie vroeger begint of later eindigt dan
		// huidige begin/eind
		if (sessie.getStart().isBefore(begin)) {
			setBegin(sessie.getStart());
		}
		if (sessie.getEnd().isAfter(einde)) {
			setEinde(sessie.getEnd());
		}

		sessies.add(sessie);
		
		this.datumString = generateDatumString();
		this.tijdstipString = generateTijdstipString();
	}

	public void addAllSessies(List<OpenClassSession> sessies) {
		for (OpenClassSession sessie : sessies) {
			addSessie(sessie);
		}
	}

	public String getDatumString() {
		return datumString;
	}

	public String getTijdstipString() {
		return tijdstipString;
	}

}
