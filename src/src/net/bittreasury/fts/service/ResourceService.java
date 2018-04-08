package src.net.bittreasury.fts.service;

import java.util.List;

import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.domain.FtsUsers;

public interface ResourceService {
	public List<FtsResource> findResourcePage(FtsResource ftsResource ,Integer pageIndex,Integer pageSize);
	public Integer sourceCount(FtsResource ftsResource);
}
