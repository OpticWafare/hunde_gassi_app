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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userCredential = request.getParameter("userCredential");
		String password = request.getParameter("password");
		System.out.println("=== LOGIN ===");
		System.out.println("UserCredential: "+ userCredential);
		System.out.println("Password: "+password);
		
		DBManager db = new DBManager();
		
		User user = null;
		try {
			try {
				user = db.loginUserEmail(userCredential, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(user == null)
			{
				try {
					user = db.loginUserUsername(userCredential, password);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		finally {
			try {
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(user != null) {

			LoginTransfer loginTransfer = new LoginTransfer(user);
			Gson gson = new Gson();
			String json = gson.toJson(loginTransfer);
			response.getWriter().append(json);
		}
		else {
			
			LoginTransfer loginTransfer = new LoginTransfer("E-Mail bzw. Username oder Passwort sind nicht korrekt");
			Gson gson = new Gson();
			String json = gson.toJson(loginTransfer);
			response.getWriter().append(json);
		}
	}

}
