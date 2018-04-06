package src.net.bittreasury.fts.service.impl;

import src.net.bittreasury.fts.dao.UsersDao;
import src.net.bittreasury.fts.domain.FtsUsers;
import src.net.bittreasury.fts.service.UsersService;

public class UsersServiceImpl implements UsersService {

	private UsersDao usersDao;

	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Override
	public void insertUsers(FtsUsers ftsUsers) {

		usersDao.insertUsers(ftsUsers);

	}

	@Override
	public FtsUsers findById(Integer userId) {

		return usersDao.findUserById(userId);
	}

}
