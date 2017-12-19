package domain;

import java.sql.Time;
import java.util.Date;

public class OpenLesDag {
	private int id;
	private Date datum;
	private Time beginUur;
	private Time eindUur;
	private String campus;
	private String time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id == 0) {
			throw new DomainException();
		}
		this.id = id;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime() {
		this.time = timeToString();
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		if (campus == null) {
			throw new DomainException();
		}
		this.campus = campus;
	}

	public Time getEindUur() {
		return eindUur;
	}

	public void setEindUur(Time eindUur) {
		if (eindUur == null) {
			throw new DomainException();
		}
		this.eindUur = eindUur;
	}

	public Time getBeginUur() {
		return beginUur;
	}

	public void setBeginUur(Time beginUur) {
		if (beginUur == null) {
			throw new DomainException();
		}
		this.beginUur = beginUur;
	}

	public OpenLesDag(Date datum, Time beginUur, Time eindUur, String campus) {
		setDatum(datum);
		setBeginUur(beginUur);
		setEindUur(eindUur);
		setCampus(campus);
		setTime();

	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		if (datum == null) {
			throw new DomainException();
		}
		this.datum = datum;
	}

	public String timeToString() {
		return this.beginUur.toString() + " - " + this.eindUur.toString();
	}
}
