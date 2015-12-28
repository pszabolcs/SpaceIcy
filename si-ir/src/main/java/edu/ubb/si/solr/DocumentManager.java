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

	private static final String SERVER = "http://localhost:8983/solr/";
	private static final String CORE_EN = "collections_en";
	private static final String CORE_HU = "collections_hu";
	private static final String CORE_RO = "collections_ro";
	
	private SolrClient clientEn;
	private SolrClient clientHu;
	private SolrClient clientRo;
	
	public DocumentManager() {
		clientEn = new HttpSolrClient(SERVER + CORE_EN);
		clientHu = new HttpSolrClient(SERVER + CORE_HU);
		clientRo = new HttpSolrClient(SERVER + CORE_RO);
	}
	
	public void addDocument(Document document) throws SolrServerException, IOException {
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("uid", document.getUid().toString());
		doc.addField("url", document.getUrl());
		doc.addField("caption", document.getCaption());
		doc.addField("html_context", document.getContext());	
		
		// save in all 3 shards 
		//TODO: save according to HTML tag
		clientEn.add(doc);
		clientEn.commit();
		clientHu.add(doc);
		clientHu.commit();
		clientRo.add(doc);
		clientRo.commit();
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
		// this parameter ensures the search is done in all 3 shards
		q.setParam("shards", "shard_en_hu_ro");
		q.setRows(100);
		QueryResponse res = clientEn.query(q);
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
