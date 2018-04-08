package src.net.bittreasury.fts.web.action.users;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import src.net.bittreasury.fts.domain.FtsUsers;
import src.net.bittreasury.fts.pojo.UsersVO;
import src.net.bittreasury.fts.service.UsersService;

//指定模型对象类型
@Controller("usersAction")
@Scope("prototype")
public class UsersAction extends ActionSupport implements ModelDriven<UsersVO> {
	// 定义一个成员变量，作为模型对象
	private UsersVO usersVo = new UsersVO();
	// 注入service
	@Autowired
	private UsersService usersService;

	public UsersVO getUsersVo() {
		return usersVo;
	}

	public void setUsersVo(UsersVO usersVo) {
		this.usersVo = usersVo;
	}

	// 由ModelDriven拦截器调用，此方法在aciton被实例化后调用，获取一个模型对象
	@Override
	public UsersVO getModel() {
		// TODO Auto-generated method stub
		return usersVo;
	}

	public String logIn() {

		FtsUsers ftsUsers = new FtsUsers();
		ftsUsers = usersService.getByUserIdAndPasswoed(usersVo.getFtsUsers().getUserName(),
				usersVo.getFtsUsers().getPassword());
		if (null != ftsUsers) {
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("ftsUsers", ftsUsers);
			
			
			return "logIn";
		}

		return "error";
	}

	// 新增用户页面
	public String registeredUsers() {

		// 调用service获取页面上需要的数据
		// ...

		System.out.println("action-----------registeredUsers");
		return "registeredUsers";
	}

	// 新增用户提交
	public String submitUsers() {
		// 调用service新增客户
		System.out.println("action-----------submitUsers");
		usersService.insertUsers(usersVo.getFtsUsers());

		// 返回成功
		return "submitUsers";
	}
	
	

}
