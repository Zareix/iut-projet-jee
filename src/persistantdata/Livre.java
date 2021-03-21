package persistantdata;

import mediatek2021.Document;

public class Livre implements Document {
	private int id;
	private String titre;
	private String auteur;
	private boolean emprunte;

	public Livre(int id, String titre, String auteur) {
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
		this.emprunte = false;
	}

	public Livre(int id, String titre, String auteur, boolean emprunte) {
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
		this.emprunte = emprunte;
	}

	@Override
	public Object[] data() {
		return new Object[] { this.id, this.titre, this.auteur, this.emprunte };
	}

	@Override
	public String toString() {
		return id + " : " + titre + " par " + auteur + (emprunte ? " - Emprunté" : "");
	}
}
