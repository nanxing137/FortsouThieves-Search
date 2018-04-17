package src.net.bittreasury.fts.dao.impl;

import java.util.ArrayList;
import java.util.List;

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
	public Integer sourceCount(FtsResource ftsResource) {

		String name = "";
		// 到时候可能加入条件查询
		if (null != ftsResource && ftsResource.getName() != null) {
			name = ftsResource.getName();
		}
		Integer count = (Integer) this.getHibernateTemplate()
				.find("select count(*) from FtsResource as ftsResource where name like ?", "%" + name + "%").iterator()
				.next();

		return count;
	}

	@Override
	public List<FtsResource> findResourcePage(FtsResource ftsResource, Integer pageIndex, Integer pageSize) {
		// 条件查询
		// 主要功能
		// 还没做
		// 这里可以用高级搜索框架
		Integer firstSource = 0;
		Integer lastSource = firstSource + 10;
		StringBuilder hql = new StringBuilder("from FtsResource");
		hql.append(" ");
		if (pageIndex != null) {
			firstSource = (pageIndex - 1) * pageSize;
		}
		if (pageSize != null) {
			lastSource = firstSource + pageSize;
		} else {

			// 如无数据告知一页展示几条
			// 那么默认十条
			lastSource = firstSource + 10;
		}
		// hql.append("limit(?,?) ");

		// return (List<FtsResource>) this.getHibernateTemplate().find(hql.toString(),
		// firstSource,pageSize);
		return (List<FtsResource>) this.getHibernateTemplate().find(hql.toString());
	}

	@Override
	public FtsResource getResourceById(Integer id) {
		FtsResource ftsResource = this.getHibernateTemplate().get(FtsResource.class, id);
		return ftsResource;
	}

	@Override
	public List<String> fingResourceList() {
		List<FtsResource> list = (List<FtsResource>) this.getHibernateTemplate().find("from FtsResource");
		List<String> result = new ArrayList<>();
		for (FtsResource ftsResource : list) {
			result.add(ftsResource.getName());
		}

		return result;
	}

}
