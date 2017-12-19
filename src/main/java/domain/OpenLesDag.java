package domain;

import java.util.Date;

public class OpenLesDag {
	private int id;
	private Date datum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id == 0) {
			throw new DomainException();
		}
		this.id = id;
	}

	public OpenLesDag(Date datum) {
		setDatum(datum);

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
}
