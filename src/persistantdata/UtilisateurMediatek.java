package persistantdata;

import mediatek2021.Utilisateur;

public class UtilisateurMediatek implements Utilisateur {
	private String login;
	private String mdp;

	public UtilisateurMediatek(String l, String m) {
		this.login = l;
		this.mdp = m;
	}

	@Override
	public Object[] data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login() {
		return login;
	}

	@Override
	public String password() {
		return mdp;
	}

	@Override
	public String toString() {
		return this.login();
	}
}
