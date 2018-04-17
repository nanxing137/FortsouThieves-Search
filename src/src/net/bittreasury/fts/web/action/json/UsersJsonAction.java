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
import com.sun.org.apache.xpath.internal.compiler.Keywords;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.alibaba.fastjson.JSON;

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
		FtsUsers ftsUsers = usersService.getUserByUserName(jsonVO.getFtsUsers().getUserName());
		if (ftsUsers != null) {
			allow = false;
		} else {
			allow = true;
		}
		map.put("status", allow);
		JSONObject jsonObject = JSONObject.fromObject(map);
		JSONArray jsonArray = JSONArray.fromObject(allow);
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

		if (jsonVO.getFtsResource() == null) {
			FtsResource ftsResource = new FtsResource();
			jsonVO.setFtsResource(ftsResource);
		}

		ResultVO resource = searchService.getResource(jsonVO.getFtsResource(), null, jsonVO.getPageIndex());
		List<FtsResource> list = resource.getList();
		List<ResourceJson> list2 = new ArrayList<>();
		for (FtsResource ftsource : list) {
			list2.add(new ResourceJson(ftsource));
		}
//		String temp = JSON.toJSONString(list2);
//		System.out.println(temp);
		// JSONObject jsonObject = JSONObject.fromObject(list);
		JSONArray jsonArray = JSONArray.fromObject(list2);
//		System.out.println(jsonArray.toString());
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
		// JSONObject jsonObject = JSONObject.fromObject(list);
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

	/**
	 * 返回前100搜索热词，用于搜索的提示功能 返回格式为json 返回数据放到jsonVO中的json中即可
	 * 
	 * @return
	 */
	public String keywords() {
		List<String> list = resourceService.fingResourceList();
		JSONArray jsonArray = JSONArray.fromObject(list);
		jsonVO.setJson(jsonArray.toString());
		return "json";
	}

	@Override
	public JsonVO getModel() {
		return jsonVO;
	}

}
