package src.net.bittreasury.fts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import src.net.bittreasury.fts.dao.UsersDao;
import src.net.bittreasury.fts.domain.FtsUsers;
import src.net.bittreasury.fts.service.UsersService;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersDao;

	@Override
	@Transactional
	public void insertUsers(FtsUsers ftsUsers) {

		usersDao.insertUsers(ftsUsers);

	}

	@Override
	public FtsUsers findById(Integer userId) {

		return usersDao.findUserById(userId);
	}

	@Override
	public FtsUsers getByUserIdAndPasswoed(String userName, String password) {
		
		return usersDao.getByUserIdAndPasswoed(userName, password);
	}

	@Override
	public FtsUsers getUserByUserName(String userName) {
		return usersDao.getUserByUserName(userName);
	}

}
