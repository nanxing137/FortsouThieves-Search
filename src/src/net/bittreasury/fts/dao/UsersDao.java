package src.net.bittreasury.fts.dao;

import src.net.bittreasury.fts.domain.*;

public interface UsersDao {
	public void insertUsers(FtsUsers ftsUsers);
	public FtsUsers findUserById(Integer userId);
	public FtsUsers getByUserIdAndPasswoed(String userName,String password);
	public FtsUsers getUserByUserName(String userName);
}
