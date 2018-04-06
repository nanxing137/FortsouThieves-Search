package src.net.bittreasury.fts.dao.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import src.net.bittreasury.fts.dao.UsersDao;
import src.net.bittreasury.fts.domain.FtsUsers;


@RunWith(SpringJUnit4ClassRunner.class)
//spring和junit整合，在这里加载spring配置文件，创建spring容器
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-*.xml"})
public class UsersDaoImplTest {

	@Autowired
	private UsersDao usersDao;
	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		FtsUsers ftsUsers = usersDao.findUserById(31);
		System.out.println(ftsUsers.getUserName());
	}

}
