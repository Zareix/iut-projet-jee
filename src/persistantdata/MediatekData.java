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
// via une injection de d�pendance dans son bloc static

public class MediatekData implements PersistentMediatek {
	private static String url = "jdbc:mysql://localhost:8889/jee";
	private static String user = "root";
	private static String mdp = "root";

	private Connection connect;

// Jean-Fran�ois Brette 01/01/2018
	static {
		// injection dynamique de la d�pendance dans le package stable mediatek2021
		Mediatek.getInstance().setData(new MediatekData());
	}

	private MediatekData() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url,user,mdp);
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Impossible de se connecter � la BD : " + e.getMessage());
		}
	}

	// renvoie la liste de tous les documents de la biblioth�que
	@Override
	public List<Document> catalogue(int type) {
		return null;
	}

	// va r�cup�rer le User dans la BD et le renvoie
	// si pas trouv�, renvoie null
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

	// va r�cup�rer le document de num�ro numDocument dans la BD
	// et le renvoie
	// si pas trouv�, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		return null;
	}

	// ajoute un nouveau document - exception � d�finir
	@Override
	public void newDocument(int type, Object... args) throws NewDocException {
		// args[0] -> le titre
		// args [1] --> l'auteur
		// etc en fonction du type et des infos optionnelles
	}

	// supprime un document - exception � d�finir
	@Override
	public void suppressDoc(int numDoc) throws SuppressException {

	}

}
