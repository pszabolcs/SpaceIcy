package edu.ubb.si.frontend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ubb.si.crawler.Controller;

/**
 * Servlet implementation class FetchPageServlet
 */
@WebServlet("/fetchpage")
public class FetchPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		String storage = request.getParameter("storage");
		String maxpages = request.getParameter("maxpages");
		String maxdepth = request.getParameter("maxdepth");
		
		Controller controller = new Controller();
		if (storage != "") {
			controller.setStorageFolder(storage);
		}
		
		try {
			controller.setMaxPagesToFetch(Integer.parseInt(maxpages));
		} catch (NumberFormatException e) {
			System.out.println("Max pages to fetch not set");
		}
		
		try {
			controller.setMaxDepthOfCrawling(Integer.parseInt(maxdepth));
		} catch (NumberFormatException e) {
			System.out.println("Max depth of crawling not set");
		}
		
		try {
			controller.fetchPage(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("index.jsp");
	}

}
