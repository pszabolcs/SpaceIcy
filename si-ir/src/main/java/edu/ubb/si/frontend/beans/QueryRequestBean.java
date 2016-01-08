package edu.ubb.si.frontend.beans;

import edu.ubb.si.model.Document;
import edu.ubb.si.solr.DocumentManager;
import org.apache.solr.client.solrj.SolrServerException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author bordie
 *
 */

@ViewScoped
@ManagedBean(name = "queryRequest")
public class QueryRequestBean {
	
	private static final String EMPTY_STRING = "";
	
	private DocumentManager manager;
	private String queryStr;
	private boolean visible = false;
	private List<Document> results;
	
	public QueryRequestBean() {
		manager = new DocumentManager();
	}
	
	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	public List<Document> getResults() {
		return results;
	}

	public void setResults(List<Document> result) {
		this.results = result;
	}
	
	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public String getResultsForQuery() {
		try {
			setResults(manager.processQuery(queryStr));
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		setVisible(true);
		return EMPTY_STRING;
	}

}
