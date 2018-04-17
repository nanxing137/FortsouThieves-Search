package src.net.bittreasury.fts.dao;

import java.util.List;

import src.net.bittreasury.fts.domain.FtsResource;

public interface ResourceDao {

	public FtsResource getResourceById(Integer id);

	public List<String> fingResourceList();

}
