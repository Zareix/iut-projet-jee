package persistantdata;

import mediatek2021.Document;

public class DVD implements Document {
	private int id;
	private String titre;
	private String realisateur;
	private boolean emprunte;

	public DVD(int id, String titre, String realisateur) {
		this.id = id;
		this.titre = titre;
		this.realisateur = realisateur;
		this.emprunte = false;
	}

	public DVD(int id, String titre, String realisateur, boolean emprunte) {
		this.id = id;
		this.titre = titre;
		this.realisateur = realisateur;
		this.emprunte = emprunte;
	}

	@Override
	public Object[] data() {
		return new Object[] { this.id, this.titre, this.realisateur };
	}

	@Override
	public String toString() {
		return id + " : " + titre + " par " + realisateur + (emprunte ? " - Emprunté" : "");
	}

}
