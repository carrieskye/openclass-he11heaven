package domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenLesDag {
	private int id;
	private Date datum;
	private List<OpenClassSession> sessies;

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
		sessies = new ArrayList<>();

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
