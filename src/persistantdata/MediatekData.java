package persistantdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mediatek2021.*;
import user.UtilisateurMediatek;

// classe mono-instance : l'unique instance est connue de la bibliotheque
// via une injection de dépendance dans son bloc static

public class MediatekData implements PersistentMediatek {
	private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String user = "raphgc";
	private static String mdp = "raphael";

	private Connection connect;

// Jean-François Brette 01/01/2018
	static {
		// injection dynamique de la dépendance dans le package stable mediatek2021
		Mediatek.getInstance().setData(new MediatekData());
	}

	private MediatekData() {
		try {
			connect = DriverManager.getConnection(url, user, mdp);
		} catch (SQLException e) {
			System.err.println("Impossible de se connecter à la BD : " + e.getMessage());
		}
	}

	// renvoie la liste de tous les documents de la bibliothèque
	@Override
	public List<Document> catalogue(int type) {
		return null;
	}

	// va récupérer le User dans la BD et le renvoie
	// si pas trouvé, renvoie null
	@Override
	public Utilisateur getUser(String login, String password) {
		try {
			PreparedStatement sql = connect.prepareStatement("SELECT * FROM utilisateur WHERE login = ? AND mdp = ?");
			sql.setString(1, login);
			sql.setString(2, password);
			ResultSet res = sql.executeQuery();
			while (res.next()) {
				return new UtilisateurMediatek(res.getString("login"), res.getString("mdp"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// va récupérer le document de numéro numDocument dans la BD
	// et le renvoie
	// si pas trouvé, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		return null;
	}

	// ajoute un nouveau document - exception à définir
	@Override
	public void newDocument(int type, Object... args) throws NewDocException {
		// args[0] -> le titre
		// args [1] --> l'auteur
		// etc en fonction du type et des infos optionnelles
	}

	// supprime un document - exception à définir
	@Override
	public void suppressDoc(int numDoc) throws SuppressException {

	}

}
