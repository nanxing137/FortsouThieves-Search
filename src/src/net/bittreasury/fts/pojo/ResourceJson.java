package src.net.bittreasury.fts.pojo;

import src.net.bittreasury.fts.domain.FtsResource;

public class ResourceJson {
	private Integer id;
	private String categoriesName;
	private String name;
	private String description;
	private String imageUrl;
//	private String resourceUrl;
//	private int insertTime;
//	private int downloads;
//	private int pageViews;
//	private int locked;

	public ResourceJson(FtsResource ftsResource) {
		this.id = ftsResource.getId();
//		this.categoriesName = ftsResource.getFtsCategories().getName();
		this.categoriesName = "栏目现在有问题!!!";
		this.name = ftsResource.getName();
		this.description = ftsResource.getDescription();
		this.imageUrl = ftsResource.getImageUrl();
//		this.resourceUrl = ftsResource.getResourceUrl();
//		this.insertTime = ftsResource.getInsertTime();
//		this.downloads = ftsResource.getDownloads();
//		this.pageViews = ftsResource.getPageViews();
//		this.locked = ftsResource.getLocked();

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

//	public String getResourceUrl() {
//		return resourceUrl;
//	}
//
//	public void setResourceUrl(String resourceUrl) {
//		this.resourceUrl = resourceUrl;
//	}
//
//	public int getInsertTime() {
//		return insertTime;
//	}
//
//	public void setInsertTime(int insertTime) {
//		this.insertTime = insertTime;
//	}
//
//	public int getDownloads() {
//		return downloads;
//	}
//
//	public void setDownloads(int downloads) {
//		this.downloads = downloads;
//	}
//
//	public int getPageViews() {
//		return pageViews;
//	}
//
//	public void setPageViews(int pageViews) {
//		this.pageViews = pageViews;
//	}
//
//	public int getLocked() {
//		return locked;
//	}
//
//	public void setLocked(int locked) {
//		this.locked = locked;
//	}

}