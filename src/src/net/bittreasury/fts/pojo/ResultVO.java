package src.net.bittreasury.fts.pojo;

import java.util.List;

import org.hibernate.dialect.Ingres10Dialect;

import src.net.bittreasury.fts.domain.FtsResource;
/**
 * 主要功能查询用的VO
 * 保存了结果json，结果列表，结果总数，总页数，当前页
 * @author Thornhill
 *
 */
public class ResultVO {
	private List<FtsResource> list;
	private String json;
	private Long recordCount;
	private Integer pageCount;
	private Integer curPage;

	public List<FtsResource> getList() {
		return list;
	}

	public void setList(List<FtsResource> list) {
		this.list = list;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
}
