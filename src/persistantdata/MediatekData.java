package persistantdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mediatek2021.*;

// classe mono-instance : l'unique instance est connue de la bibliotheque
// via une injection de dependance dans son bloc static

public class MediatekData implements PersistentMediatek {
	private static String url = "jdbc:mysql://localhost:3306/jee?serverTimezone=UTC";
	private static String user = "root";
	private static String mdp = "root";

	private Connection connect;

// Jean-Franeois Brette 01/01/2018
	static {
		// injection dynamique de la dependance dans le package stable mediatek2021
		Mediatek.getInstance().setData(new MediatekData());
	}

	private MediatekData() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, mdp);
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Impossible de se connecter a la BD : " + e.getMessage());
		}
	}

	// renvoie la liste de tous les documents de la bibliotheque
	@Override
	public List<Document> catalogue(int type) {
		List<Document> documents = new ArrayList<>();
		String getDocQuery = "SELECT * FROM document D, $table T WHERE D.id_document = T.id_document";

		// TODO : CPO-AV friendly
		try {
			switch (type) {
			case 1: {
				getDocQuery = getDocQuery.replace("$table", "livre");
				ResultSet res = connect.createStatement().executeQuery(getDocQuery);
				while (res.next())
					documents.add(new Livre(res.getInt("id"), res.getString("titre"), res.getString("auteur"), res.getBoolean("emprunte")));
				break;
			}
			case 2: {
				getDocQuery = getDocQuery.replace("$table", "cd");
				ResultSet res = connect.createStatement().executeQuery(getDocQuery);
				while (res.next())
					documents.add(new CD(res.getInt("id"), res.getString("titre"), res.getString("artiste"), res.getBoolean("emprunte")));
				break;
			}
			case 3: {
				getDocQuery = getDocQuery.replace("$table", "dvd");
				ResultSet res = connect.createStatement().executeQuery(getDocQuery);
				while (res.next())
					documents.add(new DVD(res.getInt("id"), res.getString("titre"), res.getString("realisateur"), res.getBoolean("emprunte")));
				break;
			}
			default:
				return null;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}

		return documents;
	}

	// va recuperer le User dans la BD et le renvoie
	// si pas trouve, renvoie null
	@Override
	public Utilisateur getUser(String login, String password) {
		try {
			PreparedStatement sql = connect.prepareStatement("SELECT * FROM utilisateur WHERE login = ? AND password = ?");
			sql.setString(1, login);
			sql.setString(2, password);
			ResultSet res = sql.executeQuery();
			if (res.next()) {
				return new UtilisateurMediatek(res.getString("login"), res.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// va recuperer le document de numero numDocument dans la BD
	// et le renvoie
	// si pas trouve, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		// TODO : CPO-AV friendly
		String getDocQuery = "SELECT * FROM document D, $table T WHERE D.id_document = $idDoc AND D.id_document = T.id_document";
		getDocQuery = getDocQuery.replace("$idDoc", Integer.toString(numDocument));
		try {
			ResultSet res1 = connect.createStatement().executeQuery(getDocQuery.replace("$table", "livre"));
			if (res1.next())
				return new Livre(res1.getInt("id"), res1.getString("titre"), res1.getString("auteur"), res1.getBoolean("emprunte"));

			ResultSet res2 = connect.createStatement().executeQuery(getDocQuery.replace("$table", "cd"));
			if (res2.next())
				return new CD(res2.getInt("id"), res2.getString("titre"), res2.getString("artiste"), res2.getBoolean("emprunte"));

			ResultSet res3 = connect.createStatement().executeQuery(getDocQuery.replace("$table", "dvd"));
			if (res3.next())
				return new DVD(res3.getInt("id"), res3.getString("titre"), res3.getString("realisateur"), res3.getBoolean("emprunte"));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	// ajoute un nouveau document - exception a definir
	@Override
	public void newDocument(int type, Object... args) throws NewDocException {
		// args[0] -> le titre
		// args [1] --> l'auteur
		// etc en fonction du type et des infos optionnelles

		// TODO : CPO-AV friendly
		try (PreparedStatement docStat = connect.prepareStatement("INSERT INTO document (titre) VALUES (?)",
				Statement.RETURN_GENERATED_KEYS)) {
			if (args[0] == null)
				throw new NewDocException("Aucun titre");
			docStat.setString(1, (String) args[0]);
			docStat.execute();
			ResultSet res = docStat.getGeneratedKeys();
			if (!res.next())
				throw new SQLException("Pas de clé générée");

			PreparedStatement typeStatement = null;
			switch (type) {
			case 1:
				typeStatement = connect
						.prepareStatement("INSERT INTO livre (id, id_document, auteur) VALUES (?, ?, ?)");
				typeStatement.setLong(1, res.getLong(1));
				typeStatement.setLong(2, res.getLong(1));
				if (args[1] == null)
					throw new NewDocException("Pas d'auteur");
				typeStatement.setString(3, (String) args[1]);
				break;
			case 2:
				typeStatement = connect.prepareStatement("INSERT INTO cd (id, id_document, artiste) VALUES (?, ?, ?)");
				typeStatement.setLong(1, res.getLong(1));
				typeStatement.setLong(2, res.getLong(1));
				if (args[1] == null)
					throw new NewDocException("Pas d'artiste");
				typeStatement.setString(3, (String) args[1]);
				break;
			case 3:
				typeStatement = connect
						.prepareStatement("INSERT INTO dvd (id, id_document, realisateur) VALUES (?, ?, ?)");
				typeStatement.setLong(1, res.getLong(1));
				typeStatement.setLong(2, res.getLong(1));
				if (args[1] == null)
					throw new NewDocException("Pas de realisateur");
				typeStatement.setString(3, (String) args[1]);
				break;
			default:
				throw new NewDocException("Type invalide");
			}

			typeStatement.execute();
		} catch (SQLException e) {
			throw new NewDocException(e.getMessage());
		}

	}

	// supprime un document - exception a definir
	@Override
	public void suppressDoc(int numDoc) throws SuppressException {
		try {
			PreparedStatement sql = connect.prepareStatement("DELETE FROM document WHERE id_document = ?");
			sql.setInt(1, numDoc);
			if (sql.executeUpdate() == 0)
				throw new SuppressException("Ce numero de document n'existe pas");
		} catch (SQLException e) {
			throw new SuppressException(e.getMessage());
		}
	}

}
