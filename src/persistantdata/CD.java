package persistantdata;

import mediatek2021.Document;

public class CD implements Document {
	private int id;
	private String titre;
	private String artiste;

	public CD(int id, String titre, String artiste) {
		this.id = id;
		this.titre = titre;
		this.artiste = artiste;
	}

	@Override
	public Object[] data() {
		return new String[] { this.titre, this.artiste };
	}

	@Override
	public String toString() {
		return id + " : " + titre + " par " + artiste;
	}
}
