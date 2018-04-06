package src.net.bittreasury.fts.dao.impl;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import src.net.bittreasury.fts.dao.*;
import src.net.bittreasury.fts.domain.*;

public class UsersDaoImpl extends HibernateDaoSupport implements UsersDao {

	@Override
	public void insertUsers(FtsUsers ftsUsers) {
		this.getHibernateTemplate().save(ftsUsers);

	}

	@Override
	public FtsUsers findUserById(Integer userId) {
		return this.getHibernateTemplate().get(FtsUsers.class, userId);
	}

}
