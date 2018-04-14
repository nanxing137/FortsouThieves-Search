package src.net.bittreasury.fts.service;

import src.net.bittreasury.fts.domain.FtsUsers;

public interface UsersService {
	public void insertUsers(FtsUsers ftsUsers);

	public FtsUsers findById(Integer userId);

	public FtsUsers getByUserIdAndPasswoed(String userName, String password);

	public FtsUsers getUserByUserName(String userName);
}
