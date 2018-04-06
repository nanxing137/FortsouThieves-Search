package src.net.bittreasury.fts.pojo;

import src.net.bittreasury.fts.domain.FtsUsers;

/**
 * 包装对象，作为模型对象
 * 
 * @author Thornhill
 *
 */
public class UsersVO {

	// 这里定义页面提交草书
	private FtsUsers ftsUsers;

	public FtsUsers getFtsUsers() {
		return ftsUsers;
	}

	public void setFtsUsers(FtsUsers ftsUsers) {
		this.ftsUsers = ftsUsers;
	}
}
