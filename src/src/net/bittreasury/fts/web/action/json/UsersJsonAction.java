package src.net.bittreasury.fts.web.action.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.org.apache.xpath.internal.operations.Bool;

import net.sf.json.JSONObject;
import src.net.bittreasury.fts.domain.FtsUsers;
import src.net.bittreasury.fts.pojo.UsersVO;
import src.net.bittreasury.fts.service.UsersService;

@Controller("usersJsonAction")
@Scope("prototype")
public class UsersJsonAction extends ActionSupport implements ModelDriven<UsersVO> {

	private UsersVO usersVo = new UsersVO();
	private Boolean allow;
	private String json;
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Boolean getAllow() {
		return allow;
	}

	public void setAllow(Boolean allow) {
		this.allow = allow;
	}

	public UsersVO getUsersVo() {
		return usersVo;
	}

	public void setUsersVo(UsersVO usersVo) {
		this.usersVo = usersVo;
	}

	@Autowired
	private UsersService usersService;

	public String judge() {
		
		FtsUsers ftsUsers = usersService.getUserByUserName(usersVo.getFtsUsers().getUserName());
		
		if (ftsUsers != null) {
			allow = true;
		} else {
			allow = false;
		}
		JSONObject jsonObject = JSONObject.fromObject(allow);
		json = jsonObject.toString();
		return "json";
	}

	@Override
	public UsersVO getModel() {
		// TODO Auto-generated method stub
		return usersVo;
	}

}
