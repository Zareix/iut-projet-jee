package persistantdata;

import mediatek2021.Document;

public class Livre implements Document {
	private int id;
	private String titre;
	private String auteur;

	public Livre(int id, String titre, String auteur) {
		this.id = id;
		this.titre = titre;
		this.auteur = auteur;
	}

	@Override
	public Object[] data() {
		return new String[] { this.titre, this.auteur };
	}

	@Override
	public String toString() {
		return id + " : " + titre + " par " + auteur;
	}
}
