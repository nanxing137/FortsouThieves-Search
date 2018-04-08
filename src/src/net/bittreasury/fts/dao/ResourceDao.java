package src.net.bittreasury.fts.dao;

import java.util.List;

import src.net.bittreasury.fts.domain.FtsResource;

public interface ResourceDao {
	public List<FtsResource> findResourcePage(FtsResource ftsResource,Integer pageIndex, Integer pageSize);

	public Integer sourceCount(FtsResource ftsResource);
}
