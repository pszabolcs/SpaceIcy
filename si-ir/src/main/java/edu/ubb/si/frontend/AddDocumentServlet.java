package edu.ubb.si.frontend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ubb.si.model.Document;
import edu.ubb.si.solr.DocumentManager;

/**
 * Servlet implementation class AddDocumentServlet
 */
@WebServlet("/addoc")
public class AddDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDocumentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		String caption = request.getParameter("caption");
		String context = request.getParameter("context");
		
		Document document = new Document(url, caption, context);
		DocumentManager manager = new DocumentManager();
		try {
			manager.addDocument(document, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		response.sendRedirect("index.jsp");
	}

}
