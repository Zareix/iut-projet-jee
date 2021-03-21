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

/**
 * 
 * @see PersistentMediatek
 */
public class MediatekData implements PersistentMediatek {
	private static String url = "jdbc:mysql://localhost:3306/jee?serverTimezone=UTC";
	private static String user = "root";
	private static String mdp = "root";

	private static Object lock = new Object();

	private Connection connect;

	static {
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

	/**
	 * Retourne la liste de tout les documents d'un type donné
	 * 
	 * @param type : type de document
	 * @return la liste des documents du type
	 */
	@Override
	public List<Document> catalogue(int type) {
		List<Document> documents = new ArrayList<>();
		String getDocQuery = "SELECT id FROM $table";
		try {
			switch (type) {
			case 1: {
				getDocQuery = getDocQuery.replace("$table", "livre");
				break;
			}
			case 2: {
				getDocQuery = getDocQuery.replace("$table", "cd");
				break;
			}
			case 3: {
				getDocQuery = getDocQuery.replace("$table", "dvd");
				break;
			}
			default:
				return null;
			}
			ResultSet res = connect.createStatement().executeQuery(getDocQuery);
			while (res.next())
				documents.add(getDocument(res.getInt("id")));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}

		return documents;
	}

	/**
	 * Retourne l'utilisateur à partir d'un login et d'un mot de passe
	 * 
	 * @param login    : identifiant
	 * @param password : mot de passe
	 * 
	 * @return l'utisateur si trouvé, null sinon
	 */
	@Override
	public Utilisateur getUser(String login, String password) {
		try {
			PreparedStatement sql = connect
					.prepareStatement("SELECT * FROM utilisateur WHERE login = ? AND password = ?");
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

	/**
	 * Retourne un document correspond au numéro donné en paramètre
	 * 
	 * @param numDocument : le numéro du doc recherché
	 * 
	 * @return le document si trouvé, null sinon
	 */
	@Override
	public Document getDocument(int numDocument) {
		String getDocQuery = "SELECT * FROM document D, $table T WHERE D.id_document = $idDoc AND D.id_document = T.id_document";
		getDocQuery = getDocQuery.replace("$idDoc", Integer.toString(numDocument));
		try {
			ResultSet res1 = connect.createStatement().executeQuery(getDocQuery.replace("$table", "livre"));
			if (res1.next())
				return new Livre(res1.getInt("id"), res1.getString("titre"), res1.getString("auteur"),
						res1.getBoolean("emprunte"));

			ResultSet res2 = connect.createStatement().executeQuery(getDocQuery.replace("$table", "cd"));
			if (res2.next())
				return new CD(res2.getInt("id"), res2.getString("titre"), res2.getString("artiste"),
						res2.getBoolean("emprunte"));

			ResultSet res3 = connect.createStatement().executeQuery(getDocQuery.replace("$table", "dvd"));
			if (res3.next())
				return new DVD(res3.getInt("id"), res3.getString("titre"), res3.getString("realisateur"),
						res3.getBoolean("emprunte"));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Ajout un document à la BD selon son type
	 * 
	 * @param type : le type de document (1-livre, 2-cd, 3-dvd)
	 * @param args : un ensemble de propriété propre à chaque type
	 */
	@Override
	public void newDocument(int type, Object... args) throws NewDocException {
		try (PreparedStatement docStat = connect.prepareStatement("INSERT INTO document (titre) VALUES (?)",
				Statement.RETURN_GENERATED_KEYS)) {
			docStat.setString(1, (String) args[0]);
			docStat.execute();
			ResultSet res = docStat.getGeneratedKeys();
			if (!res.next())
				throw new SQLException("Erreur : pas de clé générée");

			PreparedStatement typeStatement = null;
			switch (type) {
			case 1:
				typeStatement = connect
						.prepareStatement("INSERT INTO livre (id, id_document, auteur) VALUES (?, ?, ?)");
				typeStatement.setLong(1, res.getLong(1));
				typeStatement.setLong(2, res.getLong(1));
				if (args[1] == null)
					throw new NewDocException("Merci d'entrer un auteur");
				if (args[1].equals(""))
					throw new NewDocException("Merci d'entrer un auteur");
				typeStatement.setString(3, (String) args[1]);
				break;
			case 2:
				typeStatement = connect.prepareStatement("INSERT INTO cd (id, id_document, artiste) VALUES (?, ?, ?)");
				typeStatement.setLong(1, res.getLong(1));
				typeStatement.setLong(2, res.getLong(1));
				if (args[1] == null)
					throw new NewDocException("Merci d'entrer un artiste");
				if (args[1].equals(""))
					throw new NewDocException("Merci d'entrer un artiste");
				typeStatement.setString(3, (String) args[1]);
				break;
			case 3:
				typeStatement = connect
						.prepareStatement("INSERT INTO dvd (id, id_document, realisateur) VALUES (?, ?, ?)");
				typeStatement.setLong(1, res.getLong(1));
				typeStatement.setLong(2, res.getLong(1));
				if (args[1] == null)
					throw new NewDocException("Merci d'entrer un realisateur");
				if (args[1].equals(""))
					throw new NewDocException("Merci d'entrer un realisateur");
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

	/**
	 * Supprimer un document de la BD selon son id
	 * 
	 * @param numDoc le numéro du document à supprimer
	 */
	@Override
	public void suppressDoc(int numDoc) throws SuppressException {
		try {
			synchronized (lock) {
				ResultSet res = connect.createStatement()
						.executeQuery("SELECT emprunte FROM document WHERE id_document =" + numDoc);
				if (res.next()) {
					if (res.getBoolean("emprunte"))
						throw new SuppressException("Ce document ne peut pas être supprimé, il est emprunté");
					PreparedStatement sql = connect.prepareStatement("DELETE FROM document WHERE id_document = ?");
					sql.setInt(1, numDoc);
					if (sql.executeUpdate() == 0)
						throw new SuppressException("Il y a eu une erreur lors de la suppression");
				} else {
					throw new SuppressException("Ce numero de document n'existe pas");
				}
			}
		} catch (SQLException e) {
			throw new SuppressException(e.getMessage());
		}
	}

}
