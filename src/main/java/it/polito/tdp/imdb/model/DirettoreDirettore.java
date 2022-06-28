package it.polito.tdp.imdb.model;

public class DirettoreDirettore {
	private Director direttore1;
	private Director direttore2;
	public DirettoreDirettore(Director direttore1, Director direttore2) {
		super();
		this.direttore1 = direttore1;
		this.direttore2 = direttore2;
	}
	public Director getDirettore1() {
		return direttore1;
	}
	public void setDirettore1(Director direttore1) {
		this.direttore1 = direttore1;
	}
	public Director getDirettore2() {
		return direttore2;
	}
	public void setDirettore2(Director direttore2) {
		this.direttore2 = direttore2;
	}
	
}
