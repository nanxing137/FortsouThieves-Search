package src.net.bittreasury.fts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.ng.servlet.ServletHostConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import src.net.bittreasury.fts.dao.*;
import src.net.bittreasury.fts.domain.*;

@Repository("usersDao")
public class UsersDaoImpl extends HibernateDaoSupport implements UsersDao {

	@Autowired
	public void setHT(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}

	@Override
	public void insertUsers(FtsUsers ftsUsers) {
		this.getHibernateTemplate().save(ftsUsers);

	}

	@Override
	public FtsUsers findUserById(Integer userId) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FtsUsers getByUserIdAndPasswoed(String userName, String password) {
		StringBuilder hql = new StringBuilder("from FtsUsers");
		hql.append(" where userName=? and password=? ");


		List<FtsUsers> list = (List<FtsUsers>) this.getHibernateTemplate().find(hql.toString(), userName, password);

		return (FtsUsers) ((list.size()>0) ? list.get(0) : null);
	}

	@Override
	public FtsUsers getUserByUserName(String userName) {
		
		StringBuilder hql = new StringBuilder("from FtsUsers");
		hql.append(" where userName=? ");
		List<FtsUsers> list = (List<FtsUsers>) this.getHibernateTemplate().find(hql.toString(), userName);

		return (FtsUsers) ((list.size()>0) ? list.get(0) : null);
	}

}
