package com.example.EmailSender.service;



import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SolrService {
	@Autowired
    private SolrTemplate solrTemplate;

    // Method to search Solr using searchText
    public List<?> searchSolr(String searchText) throws IOException, SolrServerException {
        // Create a new SolrQuery
        SolrQuery query = new SolrQuery();
        
        // Add the search text (e.g., search in 'search_text' field)
        query.setQuery("search_text:" + searchText);

        // Execute the query
        QueryResponse response = solrTemplate.getSolrClient().query("maincore", query);  // Replace 'maincore' with your core name
        
        // Return the results (assuming generic list here)
        return response.getResults();
    }
}
//import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrClient;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//public class SolrService {
//
//    private final SolrClient solrClient;
//
//    public SolrService() {
//        this.solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr").build();  // Solr URL
//    }
//
//    public QueryResponse searchSolr(String searchText) throws IOException, SolrServerException {
//        SolrQuery query = new SolrQuery();
//        query.setQuery("search_text:" + searchText);
//        return solrClient.query("maincore", query);  // Replace "maincore" with your Solr core name
//    }
//}


