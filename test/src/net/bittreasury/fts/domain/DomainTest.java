package src.net.bittreasury.fts.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class DomainTest {

	@Test
	public void Test() {
		
//		List<FtsResource> list =new LinkedList<>();
		FtsCategories ftsCategories = new FtsCategories(null, "电影	", 0);
		FtsResource ftsResource = new FtsResource(ftsCategories, "资源", "resourceUrl", 0, 0, 0, 0);
		FtsResource ftsResource2 = new FtsResource(ftsCategories, "资源2", "resourceUrl2", 0, 0, 0, 0);
//		list.add(ftsResource);
//		list.add(ftsResource2);
//		Map<String, Object> map = new HashMap<>();
//		map.put("resourceList", list);
//		
//		
//		JsonConfig jsonConfig = new JsonConfig();
//
//		//jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//		jsonConfig.setExcludes(new String[] {"ftsCategories"});
//		
//		
//		JSONObject jsonObject = JSONObject.fromObject(map,jsonConfig);
//		System.out.println(jsonObject);
		
		List<ResourceJson> resourceJsons = new LinkedList<>();
		resourceJsons.add(new ResourceJson(ftsResource));
		resourceJsons.add(new ResourceJson(ftsResource2));
		Map<String, Object> map = new HashMap<>();
		map.put("list", resourceJsons);
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject.toString());
		
	}
	
	
	class ResourceJson {
		private Integer id;
		private String categoriesName;
		private String name;
		private String description;
		private String imageUrl;
		private String resourceUrl;
		private int insertTime;
		private int downloads;
		private int pageViews;
		private int locked;
		
		public ResourceJson(FtsResource ftsResource) {
			this.id = ftsResource.getId();
			this.categoriesName = ftsResource.getFtsCategories().getName();
			this.name = ftsResource.getName();
			this.description = ftsResource.getDescription();
			this.imageUrl = ftsResource.getImageUrl();
			this.resourceUrl = ftsResource.getResourceUrl();
			this.insertTime = ftsResource.getInsertTime();
			this.downloads = ftsResource.getDownloads();
			this.pageViews = ftsResource.getPageViews();
			this.locked = ftsResource.getLocked();
			 
		}
		
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getCategoriesName() {
			return categoriesName;
		}

		public void setCategoriesName(String categoriesName) {
			this.categoriesName = categoriesName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getResourceUrl() {
			return resourceUrl;
		}

		public void setResourceUrl(String resourceUrl) {
			this.resourceUrl = resourceUrl;
		}

		public int getInsertTime() {
			return insertTime;
		}

		public void setInsertTime(int insertTime) {
			this.insertTime = insertTime;
		}

		public int getDownloads() {
			return downloads;
		}

		public void setDownloads(int downloads) {
			this.downloads = downloads;
		}

		public int getPageViews() {
			return pageViews;
		}

		public void setPageViews(int pageViews) {
			this.pageViews = pageViews;
		}

		public int getLocked() {
			return locked;
		}

		public void setLocked(int locked) {
			this.locked = locked;
		}

	}

}
