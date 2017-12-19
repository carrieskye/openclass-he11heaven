package domain;

import java.sql.Time;
import java.time.LocalDate;

public class OpenLesDag {
	private LocalDate datum;
	private Time beginUur;
	private Time eindUur;
	private String campus;
	

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		if(campus == null) {
			throw new DomainException();
		}
		this.campus = campus;
	}

	public Time getEindUur() {
		return eindUur;
	}

	public void setEindUur(Time eindUur) {
		if(eindUur == null) {
			throw new DomainException();
		}
		this.eindUur = eindUur;
	}

	public Time getBeginUur() {
		return beginUur;
	}

	public void setBeginUur(Time beginUur) {
		if(beginUur == null) {
			throw new DomainException();
		}
		this.beginUur = beginUur;
	}

	public OpenLesDag(LocalDate datum, Time beginUur, Time eindUur, String campus) {
		setDatum(datum);
		setBeginUur(beginUur);
		setEindUur(eindUur);
		setCampus(campus);
		
	}
	
	public LocalDate getDatum() {
		return datum;
	}
	
	public void setDatum(LocalDate datum) {
		if(datum == null) {
			throw new DomainException();
		}
		this.datum = datum;
	}
	
	public String timeToString() {
		return this.beginUur.toString() + " - " + this.eindUur.toString();
	}
}
