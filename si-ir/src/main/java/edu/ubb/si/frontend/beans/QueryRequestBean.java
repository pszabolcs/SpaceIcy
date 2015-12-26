package edu.ubb.si.frontend.beans;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.solr.client.solrj.SolrServerException;

import edu.ubb.si.model.Document;
import edu.ubb.si.solr.DocumentManager;

@RequestScoped
@ManagedBean(name = "queryRequest")
public class QueryRequestBean {
	
	private static final String EMPTY_STRING = "";
	
	private DocumentManager manager;
	private String queryStr;
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
	
	public String getResultsForQuery() {
		try {
			setResults(manager.processQuery(queryStr));
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		
		return EMPTY_STRING;
	}

}
