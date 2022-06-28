package it.polito.tdp.imdb.model;

public class DirettorePeso implements Comparable<DirettorePeso>{
	private Director direttore;
	private int peso;
	public DirettorePeso(Director direttore, int peso) {
		super();
		this.direttore = direttore;
		this.peso = peso;
	}
	public Director getDirettore() {
		return direttore;
	}
	public void setDirettore(Director direttore) {
		this.direttore = direttore;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(DirettorePeso o) {
		return o.getPeso() - this.peso;
	}
	
}
