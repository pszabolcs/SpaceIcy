package edu.ubb.si.solr;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
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
}
