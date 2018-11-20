package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.DBManager;
import model.LoginTransfer;
import model.User;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("=== REGISTER ===");

		// Parameter holen
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String fcmToken = request.getParameter("fcmToken");

		System.out.println("username: " + username);

		// DATENCHECK

		// Stimmen Passwörter überein?
		if (password.equals(password2) != true) {
			try {
				showError(request, response, "Passwörter stimmen nicht überein");
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		// DBManager erzeugen
		DBManager db = new DBManager();

		// Ist diese E-Mail Adresse bereits registriert?
		try {
			if (db.getUserByEmail(email) != null) {
				try {
					showError(request, response, "Diese Email Addresse ist schon registriert!");
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Ist dieser Username bereits registriert?
		try {
			if (db.getUserByUsername(username) != null) {
				try {
					showError(request, response, "Dieser Username ist schon registriert!");
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// DATENCHECK ERFOLGREICH

		// Daten des Users in Objekt zwischenspeichern
		User user = new User(username, email, password, fcmToken);
		User user2 = null;
		try {
			// User erstellen
			db.createUser(user);
			// Erstellten User wieder aus DB holen
			user2 = db.getUserByEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Wenn das Holen des erstellten Users aus DB erfolgreich war
		if (user2 != null) {

			LoginTransfer loginTransfer = new LoginTransfer(user2);

			// Daten in JSON umwandeln
			Gson gson = new Gson();
			String json = gson.toJson(loginTransfer);
			// Senden
			response.getWriter().append(json);
			return;
		} else {
			try {
				showError(request, response, "User konnte nicht angelegt werden!");
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}

	private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws ServletException, IOException {

		LoginTransfer loginTransfer = new LoginTransfer(errorMessage);
		Gson gson = new Gson();
		String json = gson.toJson(loginTransfer);
		response.getWriter().append(json);
	}
}