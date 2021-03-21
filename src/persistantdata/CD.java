package persistantdata;

import mediatek2021.Document;

public class CD implements Document {
	private int id;
	private String titre;
	private String artiste;
	private boolean emprunte;

	public CD(int id, String titre, String artiste) {
		this.id = id;
		this.titre = titre;
		this.artiste = artiste;
		this.emprunte = false;
	}

	public CD(int id, String titre, String artiste, boolean emprunte) {
		this.id = id;
		this.titre = titre;
		this.artiste = artiste;
		this.emprunte = emprunte;
	}

	@Override
	public Object[] data() {
		return new Object[] { this.id, this.titre, this.artiste };
	}

	@Override
	public String toString() {
		return id + " : " + titre + " par " + artiste + (emprunte ? " - Emprunté" : "");
	}
}
