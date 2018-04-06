package src.net.bittreasury.fts.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import src.net.bittreasury.fts.domain.FtsUsers;
import src.net.bittreasury.fts.service.UsersService;

public class UsersServiceImplTest {

	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-*.xml"});

	private UsersService usersService;
	
	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindById() {
		//FtsUsers ftsUsers = usersService.findById(31);
		usersService = (UsersService) applicationContext.getBean("usersService");
		FtsUsers ftsUsers = new FtsUsers("不能", "pass", "email", 0, 0);
		usersService.insertUsers(ftsUsers);
		ftsUsers = usersService.findById(31);
		System.out.println(ftsUsers);
	}

}
