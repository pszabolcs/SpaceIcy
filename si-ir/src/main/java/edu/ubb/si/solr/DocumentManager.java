package edu.ubb.si.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import edu.ubb.si.model.Document;

public class DocumentManager {

	private SolrClient client;
	
	public DocumentManager() {
		String server = "http://localhost:8983/solr/spaceicy";
		client = new HttpSolrClient(server);
	}
	
	public void addDocument(Document document) throws SolrServerException, IOException {
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("uid", document.getUid().toString());
		doc.addField("url", document.getUrl());
		doc.addField("caption", document.getCaption());
		doc.addField("html_context", document.getContext());	
		
		client.add(doc);
		client.commit();
	}
	
	public void addDocuments(List<Document> documents) throws SolrServerException, IOException {
		for (Document d: documents) {
			addDocument(d);
		}
	}
	
	public List<Document> processQuery(String query) throws SolrServerException, IOException {
		SolrQuery q = new SolrQuery();
		q.setQuery(query);
		q.setParam("defType", "edismax");
		q.setParam("qf", "image_name^40 caption^40 html_context^20");
		q.setRows(100);
		QueryResponse res = client.query(q);
		SolrDocumentList list = res.getResults();
		
		LinkedHashSet<Document> result = new LinkedHashSet<Document>();
		
		for (SolrDocument doc : list) {
			Document document = new Document(
						(String) doc.getFieldValue("url"),
						(String) doc.getFieldValue("caption"),
						(String) doc.getFieldValue("html_context")
					);
					
			document.setUid(UUID.fromString((String) doc.getFieldValue("uid")));
			
			result.add(document);
		}
		
		return new ArrayList<Document>(result);
	}
}
