package src.net.bittreasury.fts.service;

import java.util.List;

import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.domain.FtsUsers;

public interface ResourceService {

	public FtsResource getResourceById(Integer id);

	public List<String> fingResourceList();
}
