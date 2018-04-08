package src.net.bittreasury.fts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import src.net.bittreasury.fts.dao.ResourceDao;
import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.service.ResourceService;


@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public List<FtsResource> findResourcePage(FtsResource ftsResource,Integer pageIndex, Integer pageSize) {
		
		return resourceDao.findResourcePage(ftsResource, pageIndex, pageSize);
	}

	@Override
	public Integer sourceCount(FtsResource ftsResource) {
		
		return resourceDao.sourceCount(ftsResource);
	}

}
