package src.net.bittreasury.fts.web.action.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xpath.internal.operations.Bool;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import src.net.bittreasury.fts.domain.FtsCategories;
import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.domain.FtsUsers;
import src.net.bittreasury.fts.pojo.*;
import src.net.bittreasury.fts.pojo.UsersVO;
import src.net.bittreasury.fts.service.ResourceService;
import src.net.bittreasury.fts.service.UsersService;

@Controller("usersJsonAction")
@Scope("prototype")
public class UsersJsonAction extends ActionSupport implements ModelDriven<JsonVO> {

	private JsonVO jsonVO = new JsonVO();

	// 使用json判断用户名是否合法
	@Autowired
	private UsersService usersService;
	// 为了使用json提供动态加载资源
	@Autowired
	private ResourceService resourceService;

	public String judge() {

		Map<String, Object> map = new HashMap<>();
		Boolean allow;
		// FtsUsers ftsUsers =
		// usersService.getUserByUserName(usersVo.getFtsUsers().getUserName());
		FtsUsers ftsUsers = new FtsUsers();
		if (ftsUsers != null) {
			allow = true;
		} else {
			allow = false;
		}
		map.put("judge", allow);
		JSONObject jsonObject = JSONObject.fromObject(map);
		String json = jsonObject.toString();
		jsonVO.setJson(json);
		return "json";
	}

	public String list() {
		List<FtsResource> list = resourceService.findResourcePage(jsonVO.getFtsResource(), jsonVO.getPageIndex(),
				jsonVO.getPageSize());
		List<ResourceJson> jsonList = new LinkedList<>();
		for (FtsResource ftsresource : list) {
			jsonList.add(new ResourceJson(ftsresource));
		}
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", jsonList);

		JsonConfig jsonConfig = new JsonConfig();

		// jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//jsonConfig.setExcludes(new String[] { "ftsCategories" });

		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		jsonVO.setJson(jsonObject.toString());
		return "json";
	}

	@Override
	public JsonVO getModel() {
		return jsonVO;
	}

	

}
