package src.net.bittreasury.fts.solr;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.jupiter.api.Test;

import src.net.bittreasury.fts.domain.FtsCategories;
import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.domain.*;
import src.net.bittreasury.fts.pojo.ResultVO;

public class IndexSearcher {

	private SolrClient client = new HttpSolrClient.Builder("http://localhost:8080/solr/core1").build();
	private SolrClient client2 = new HttpSolrClient.Builder("http://localhost:8080/solr/fts_core").build();

	@Test
	public void search01() throws SolrServerException, IOException {
		// 创建solrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		// solrQuery.set("fts_name:英语");
		// 执行查询并返回结果
		QueryResponse response = client.query(solrQuery);
		// 获取匹配的索引结果
		SolrDocumentList results = response.getResults();
		// 结果总数
		long numFound = results.getNumFound();
		System.out.println("匹配结果总数" + numFound);
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("fts_name"));
			System.out.println(solrDocument.get("fts_resource_total"));
			System.out.println(solrDocument.get("fts_id"));
			System.out.println(solrDocument.get("fts_resource_details"));
			System.out.println("============================");
		}
	}

	@Test
	public void search03() throws SolrServerException, IOException {
		// 创建solrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		// solrQuery.set("fts_name:英语");
		// 执行查询并返回结果
		QueryResponse response = client2.query(solrQuery);
		// 获取匹配的索引结果
		SolrDocumentList results = response.getResults();
		// 结果总数
		long numFound = results.getNumFound();
		System.out.println("匹配结果总数" + numFound);
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("fts_name"));
			System.out.println(solrDocument.get("fts_resource_url"));
			System.out.println(solrDocument.get("fts_category_name"));
			System.out.println(solrDocument.get("fts_id"));
			System.out.println("============================");
		}
	}

	@Test
	public void search04() throws SolrServerException, IOException {
		// 创建solrQuery
		SolrQuery solrQuery = new SolrQuery();
		// 输入关键字
		Integer page = 1;
		FtsResource ftsResource = new FtsResource();
		Integer sort = null;
		if (null != ftsResource && StringUtils.isNotEmpty(ftsResource.getName())) {
			solrQuery.setQuery("fts_name:" + ftsResource.getName() + "OR fts_description:" + ftsResource.getName());
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

		QueryResponse response = client2.query(solrQuery);
		// 查询出的结果
		SolrDocumentList results = response.getResults();
		Long count = results.getNumFound();
		List<FtsResource> resources = new ArrayList<>();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		FtsResource res;
		for (SolrDocument doc : results) {
			res = new FtsResource();

			List<String> list = highlighting.get(doc.get("id")).get("fts_description");
			if (null != list) {
				res.setDescription(list.get(0));
			} else {
				res.setDescription(doc.get("fts_description").toString());
			}

			list = highlighting.get(doc.get("fts_id")).get("fts_name");
			if (null != list) {
				res.setName(list.get(0));
			} else {
				res.setName(doc.get("fts_name").toString());
			}

			res.setId((Integer) doc.get("fts_id"));
			res.setImageUrl(doc.get("fts_imageUrl").toString());
			res.setFtsCategories(new FtsCategories() {
				@Override
				public void setName(String name) {
					super.setId((Integer) doc.get("fts_id"));
				}
			});
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
		System.out.println();
	}

	@Test
	public void search02() throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery("fts_name:我");
		// 设置过滤条件，可以设置多个
		// 如果设置多个，需要使用addFilterQuery方法
		query.setFilterQueries("fts_id:[100 TO 200]");
		// query.addFilterQuery(fq)
		// 设置排序
		query.setSort("fts_id", ORDER.asc);
		// 设置分页信息
		// start：起始记录数
		// rows：每页记录数
		query.setStart(0);
		query.setRows(10);
		// 设置显式的field集合
		// query.setFields(arg0);
		// 设置默认域
		// query.set("df","fts_resource_total");
		// 设置高亮信息********
		query.setHighlight(true);
		// 可多个
		query.addHighlightField("fts_name");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");

		QueryResponse response = client.query(query);
		SolrDocumentList results = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		for (SolrDocument solrDocument : results) {
			List<String> list = highlighting.get(solrDocument.get("id")).get("fts_name");
			if (list != null) {
				System.out.println("高亮显示的信息:");
				System.out.println(list.get(0));
			} else {
				System.out.println("列表为空");
			}

		}
	}
}
