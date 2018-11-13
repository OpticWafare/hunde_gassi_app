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
import model.Route;

/**
 * Servlet implementation class GetRoutes
 */
@WebServlet("/GetRoutes")
public class GetRoutes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRoutes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int userid = Integer.parseInt(request.getParameter("userid"));
		System.out.println("get routes from user: " + userid);
		DBManager dbmanager = new DBManager();
		List<Route> routes = dbmanager.selectRoute(userid);
		
		Gson gson = new Gson();
		System.out.println("Sending routes: " + gson.toJson(routes));
		response.getWriter().append(gson.toJson(routes));
	}

}
