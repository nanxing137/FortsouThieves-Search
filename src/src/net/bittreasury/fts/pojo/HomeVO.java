package src.net.bittreasury.fts.pojo;

import java.util.List;

import src.net.bittreasury.fts.domain.FtsResource;

public class HomeVO {
	private List<FtsResource> ftsResources;

	// 这里保存搜索条件
	private FtsResource ftsResource;

	public FtsResource getFtsResource() {
		return ftsResource;
	}

	public void setFtsResource(FtsResource ftsResource) {
		this.ftsResource = ftsResource;
	}

	// 现在第几页
	private Integer pageIndex;
	// 一页几个数据
	private Integer pageSize;
	// 一共多少数据
	private Integer sourceCount;

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

	public List<FtsResource> getFtsResources() {
		return ftsResources;
	}

	public void setFtsResources(List<FtsResource> ftsResources) {
		this.ftsResources = ftsResources;
	}
}
