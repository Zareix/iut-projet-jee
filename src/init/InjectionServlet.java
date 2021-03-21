package init;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(value = "/initializeResources", loadOnStartup = 1)
public class InjectionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		System.out.println("%%%% INIT %%%%");
		try {
			Class.forName("persistantdata.MediatekData");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
