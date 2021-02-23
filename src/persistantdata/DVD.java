package persistantdata;

import mediatek2021.Document;

public class DVD implements Document {
	private int id;
	private String titre;
	private String realisateur;

	public DVD(int id, String titre, String realisateur) {
		this.id = id;
		this.titre = titre;
		this.realisateur = realisateur;
	}

	@Override
	public Object[] data() {
		return new String[] { this.titre, this.realisateur };
	}

	@Override
	public String toString() {
		return id + " : " + titre + " par " + realisateur;
	}

}
