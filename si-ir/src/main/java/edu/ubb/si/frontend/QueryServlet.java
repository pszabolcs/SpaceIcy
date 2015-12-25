package edu.ubb.si.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServerException;

import edu.ubb.si.model.Document;
import edu.ubb.si.solr.DocumentManager;

/**
 * Servlet implementation class QueryServlet
 */
@WebServlet("/query")
public class QueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryStr = request.getParameter("query");
		DocumentManager manager = new DocumentManager();
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		try {
			List<Document> result = manager.processQuery(queryStr);
			
			for (Document doc : result) {
				out.println("<p>" + doc.getCaption() + "</p>");
				out.println("<img src=\"" + doc.getUrl() + "\"/><br/>");
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		out.println("</body>");
		out.println("</html>");
	}

}
