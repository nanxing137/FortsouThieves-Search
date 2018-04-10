package src.net.bittreasury.fts.pojo;

import src.net.bittreasury.fts.domain.FtsResource;

public class JsonVO {
	private String json;
	private FtsResource ftsResource;
	// 现在第几页
	private Integer pageIndex;
	// 一页几个数据
	private Integer pageSize;
	// 一共多少数据
	private Integer sourceCount;

	public FtsResource getFtsResource() {
		return ftsResource;
	}

	public void setFtsResource(FtsResource ftsResource) {
		this.ftsResource = ftsResource;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getSourceCount() {
		return sourceCount;
	}

	public void setSourceCount(Integer sourceCount) {
		this.sourceCount = sourceCount;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
