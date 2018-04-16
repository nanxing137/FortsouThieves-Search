package src.net.bittreasury.fts.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.net.bittreasury.fts.domain.FtsCategories;
import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.pojo.ResultVO;
import src.net.bittreasury.fts.service.SearchService;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

	// 依赖注入client
	@Autowired
	private HttpSolrClient client;

	public List<FtsResource> searchEasy(String key) throws SolrServerException, IOException {
		// 创建solrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("fts_description:" + key);
		// solrQuery.set("fts_name:英语");
		// 执行查询并返回结果
		QueryResponse response = client.query(solrQuery);
		// 获取匹配的索引结果
		SolrDocumentList results = response.getResults();
		// 结果总数
		long numFound = results.getNumFound();
		List<FtsResource> list = new ArrayList<>();
		System.out.println("匹配结果总数" + numFound);
		FtsResource ftsResource;
		for (SolrDocument solrDocument : results) {
			ftsResource = new FtsResource();
			ftsResource.setDescription(solrDocument.get("fts_description").toString());
			ftsResource.setName(solrDocument.get("fts_name").toString());
			System.out.println("============================");
			list.add(ftsResource);
		}
		return list;
	}

	@Override
	public ResultVO getResource(FtsResource ftsResource, Integer sort, Integer page) throws Exception {
		// 创建solrQuery
		SolrQuery solrQuery = new SolrQuery();
		// 输入关键字

		if (null != ftsResource && StringUtils.isNotEmpty(ftsResource.getName())) {
			solrQuery.setQuery("fts_name:" + ftsResource.getName() + " OR fts_description:" + ftsResource.getName());
		} else {
			solrQuery.setQuery("*:*");
		}
		// 设置栏目过滤
		if (null != ftsResource && null != ftsResource.getFtsCategories()) {
			if (StringUtils.isNotEmpty(ftsResource.getFtsCategories().getName())) {
				solrQuery.addFilterQuery("fts_category:" + ftsResource.getFtsCategories().getName());
			}
		}
		// 设置排序
		if (null != sort) {
			// 如有需求，则按下载量排行
			// 暂时闲置
			solrQuery.setSort("fts_downloads", ORDER.desc);
		}
		// 设置分页信息
		if (page == null) {
			page = 1;
		}
		Integer pageSize = 10;
		solrQuery.setStart((page - 1) * pageSize);
		solrQuery.setRows(pageSize);

		// 设置高亮信息
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("fts_description");
		solrQuery.addHighlightField("fts_name");
		solrQuery.setHighlightSimplePre("<span class=\"text-danger\">");
		solrQuery.setHighlightSimplePost("</span>");

		QueryResponse response = client.query(solrQuery);
		// 查询出的结果
		SolrDocumentList results = response.getResults();
		Long count = results.getNumFound();
		List<FtsResource> resources = new ArrayList<>();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		FtsResource res;
		for (SolrDocument doc : results) {
			res = new FtsResource();

			List<String> list = highlighting.get(doc.get("id")).get("fts_description");
			// List<String> list = null;
			if (null != list) {
				res.setDescription(list.get(0));
			} else {
				res.setDescription(doc.get("fts_description").toString());
			}

			list = highlighting.get(doc.get("id")).get("fts_name");
			if (null != list) {
				res.setName(list.get(0));
			} else {
				res.setName(doc.get("fts_name").toString());
			}

			res.setId(Integer.parseInt(doc.get("fts_id").toString()));
			res.setImageUrl(doc.get("fts_image_url").toString());
			FtsCategories ftsCategories = new FtsCategories();
			ftsCategories.setName(doc.get("fts_category_name").toString());
			res.setFtsCategories(ftsCategories);
			// 获取高亮信息

			resources.add(res);

		}
		ResultVO resultVO = new ResultVO();
		resultVO.setList(resources);
		resultVO.setCurPage(page);
		resultVO.setRecordCount(count);
		// 求出一共几页
		int pageCount = (int) (count / pageSize);
		if (count % pageSize != 0) {
			pageCount++;
		}
		resultVO.setPageCount(pageCount);
		return resultVO;
	}

}
