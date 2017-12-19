package domain;

import java.util.ArrayList;

public class Afdeling {
	
	private String naam;
	private ArrayList<Opleiding> opleidingen;
	
	public Afdeling(String naam) {
		setNaam(naam);
		opleidingen = new ArrayList<>();
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
	
	public ArrayList<Opleiding> getOpleidingen() {
		return this.opleidingen;
	}
	public Opleiding getOpleiding(int id) {
		return opleidingen.get(id);
	}
	
	public void addOpleiding(Opleiding o) {
		if(o == null) {
			throw new DomainException();
		}
		opleidingen.add(o);
	}

}
