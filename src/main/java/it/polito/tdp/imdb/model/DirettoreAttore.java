package it.polito.tdp.imdb.model;

public class DirettoreAttore {
	private Director direttore;
	private Actor attore;
	public DirettoreAttore(Director direttore, Actor attore) {
		super();
		this.direttore = direttore;
		this.attore = attore;
	}
	public Director getDirettore() {
		return direttore;
	}
	public void setDirettore(Director direttore) {
		this.direttore = direttore;
	}
	public Actor getAttore() {
		return attore;
	}
	public void setAttore(Actor attore) {
		this.attore = attore;
	}
	
}
