package src.net.bittreasury.fts.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.Test;
import org.apache.solr.client.solrj.impl.HttpSolrClient.*;
import org.apache.solr.client.solrj.io.SolrClientCache;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

public class IndexManager {

	private SolrClient client = new HttpSolrClient.Builder("http://localhost:8080/solr/core1").build();

	@Test
	public void insertAndUpdateIndex() throws SolrServerException, IOException {

		// 创建httpSolrClient

		// QueryResponse resp = client.query("core1", new SolrQuery("*:*"));
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "c001");
		doc.addField("name", "solr test");
		client.add(doc);

		client.commit();

	}

	@Test
	public void deleteIndex() throws SolrServerException, IOException {
		// 根据指定的id删除索引
		// client.deleteById("c001");
		// 根据条件删除
		client.deleteByQuery("id:c001");
		client.commit();
	}
}
