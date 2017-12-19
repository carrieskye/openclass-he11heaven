package domain;

import java.util.ArrayList;

public class Opleiding {
	
	private String naam;
	private ArrayList<OpenLesDag> openLesDagen;
	
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

	public ArrayList<OpenLesDag> getOpenLesDagen() {
		return this.openLesDagen;
	}
	
	public void addOpenLesDag(OpenLesDag o) {
		if(o == null) {
			throw new DomainException();
		}
		openLesDagen.add(o);
	}
}
