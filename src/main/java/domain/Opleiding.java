package domain;

import java.util.ArrayList;

public class Opleiding {
	
	private String naam;
	private ArrayList<OpenLesDag> openLesDagen;
	private int id;
	
	public Opleiding(String naam, int id) {
		this.setNaam(naam);
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		if(id <= 0) {
			throw new DomainException();
		}
		this.id = id;
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
