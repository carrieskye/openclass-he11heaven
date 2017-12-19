package domain;

public class Opleiding {
	
	private String naam;
	
	public Opleiding(String naam) {
		this.setNaam(naam);
	}

	private void setNaam(String naam) {
		if(naam == null || naam.trim().isEmpty()) {
			throw new DomainException();
		}
		this.naam = naam;
	}
	
	public String getNaam() {
		return this.naam;
	}

}
