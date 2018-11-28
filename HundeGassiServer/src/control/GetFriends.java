package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.DBManager;
import model.User;

/**
 * Servlet implementation class GetFriends
 */
@WebServlet("/GetFriends")
public class GetFriends extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFriends() {
        super();
        // TODO Auto-generated constructor stub
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

		System.out.println("=== GET FRIENDS ===");
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		DBManager db = new DBManager();
		List<User> users = db.getFriendsFromUser(userId);
		
		Gson gson = new Gson();
		String json = gson.toJson(users);
		
		System.out.println("Sending back: " + json);
		response.getWriter().append(json);
	}

}
