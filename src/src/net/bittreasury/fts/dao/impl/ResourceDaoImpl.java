package src.net.bittreasury.fts.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import src.net.bittreasury.fts.dao.ResourceDao;
import src.net.bittreasury.fts.domain.FtsResource;

@Repository("resourceDao")
public class ResourceDaoImpl extends HibernateDaoSupport implements ResourceDao {

	@Autowired
	public void setHT(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}

	@Override
	public FtsResource getResourceById(Integer id) {
		FtsResource ftsResource = this.getHibernateTemplate().get(FtsResource.class, id);
		return ftsResource;
	}

	@Override
	public List<String> fingResourceList() {
		Query query = this.getSessionFactory().getCurrentSession().createQuery("from FtsResource");
		query.setFirstResult(1);
		query.setMaxResults(200);
		List<FtsResource> list = query.list();
		
		
		//List<FtsResource> list = (List<FtsResource>) this.getHibernateTemplate().find("from FtsResource");
		List<String> result = new ArrayList<>();
		for (FtsResource ftsResource : list) {
			result.add(ftsResource.getName());
		}
		
		
		return result;
	}

}
