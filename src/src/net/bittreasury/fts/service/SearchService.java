package src.net.bittreasury.fts.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.pojo.ResultVO;

public interface SearchService {

	/**
	 * ftsresource里可以封装各种条件
	 * 
	 * @param ftsResource
	 * @param sort
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public ResultVO getResource(FtsResource ftsResource, Integer sort, Integer page) throws Exception;
	
	public List<FtsResource> searchEasy(String key) throws SolrServerException, IOException;
}
