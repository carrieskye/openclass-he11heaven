package domain;

import java.util.ArrayList;
import java.util.List;

public class Opleiding {
	
	private String naam;
	private ArrayList<OpenLesDag> openLesDagen;
	private int id;
	
	public Opleiding(String naam, int id) {
		this.setNaam(naam);
		this.setId(id);
		openLesDagen = new ArrayList<>();
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
	
	public void setOpenLesDagen(ArrayList<OpenLesDag> openLesDagen) {
		if(openLesDagen == null) {
			throw new DomainException();
		}
		
		this.openLesDagen = openLesDagen;
	}
	
	public void addOpenLesDag(OpenLesDag o) {
		if(o == null) {
			throw new DomainException();
		}
		openLesDagen.add(o);
	}
}
