package src.net.bittreasury.fts.web.action.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xpath.internal.operations.Bool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import src.net.bittreasury.fts.domain.FtsCategories;
import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.domain.FtsUsers;
import src.net.bittreasury.fts.pojo.*;
import src.net.bittreasury.fts.service.ResourceService;
import src.net.bittreasury.fts.service.SearchService;
import src.net.bittreasury.fts.service.UsersService;

@Controller("usersJsonAction")
@Scope("prototype")
public class UsersJsonAction extends ActionSupport implements ModelDriven<JsonVO> {

	private JsonVO jsonVO = new JsonVO();

	// solr查询
	@Autowired
	private SearchService searchService;

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

	/**
	 * 这时候使用solr的高级搜索
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchJson() throws Exception {
		FtsResource ftsResource = new FtsResource();
		//ftsResource.setName("片");
		
		jsonVO.setFtsResource(ftsResource);
		ResultVO resource = searchService.getResource(jsonVO.getFtsResource(), null, jsonVO.getPageIndex());
		List<FtsResource> list = resource.getList();
		List<ResourceJson> list2 = new ArrayList<>();
		for (FtsResource ftsource : list) {
			list2.add(new ResourceJson(ftsource));
		}
//		JSONObject jsonObject = JSONObject.fromObject(list);
		JSONArray jsonArray = JSONArray.fromObject(list2);
		jsonVO.setJson(jsonArray.toString());
		return "json";

	}
	/**
	 * 测试用的简单搜索
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchJsonE() throws Exception {
		
		List<FtsResource> list = searchService.searchEasy("片");
		JSONArray jsonArray = JSONArray.fromObject(list);
		//JSONObject jsonObject = JSONObject.fromObject(list);
		jsonVO.setJson(jsonArray.toString());
		return "json";
		
	}

	/**
	 * 普通搜索 暂时不用了
	 * 
	 * @return
	 */
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
		// jsonConfig.setExcludes(new String[] { "ftsCategories" });

		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		jsonVO.setJson(jsonObject.toString());
		return "json";
	}

	@Override
	public JsonVO getModel() {
		return jsonVO;
	}

}
