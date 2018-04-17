package src.net.bittreasury.fts.web.action.home;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import src.net.bittreasury.fts.domain.FtsResource;
import src.net.bittreasury.fts.domain.FtsUsers;
import src.net.bittreasury.fts.pojo.HomeVO;
import src.net.bittreasury.fts.pojo.UsersVO;
import src.net.bittreasury.fts.service.ResourceService;
import src.net.bittreasury.fts.service.SearchService;
import src.net.bittreasury.fts.service.UsersService;

//指定模型对象类型
@Controller("homeAction")
@Scope("prototype")
public class HomeAction extends ActionSupport implements ModelDriven<HomeVO> {
	// 定义一个成员变量，作为模型对象
	private HomeVO homeVO = new HomeVO();
	// 注入service
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private SearchService searchService;
	
	// 由ModelDriven拦截器调用，此方法在aciton被实例化后调用，获取一个模型对象
	@Override
	public HomeVO getModel() {
		return homeVO;
	}

	/**
	 * 搜索返回纯静态
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String home() throws Exception {

		// 将资源数量返回前端
		homeVO.setSourceCount(searchService.sourceCount(homeVO.getFtsResource(), null, null));

		return "home";
	}

	/**
	 * 搜索列表 只返回纯静态页面 数据通过json异步加载s
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String list() throws Exception {
		// 通过查询条件
		// 第几页，每页几条
		// 加上高级搜索条件（未作）
		// 返回给前端一个list，放在homeVO里
		// 叫ftsResources
		homeVO.setSourceCount(searchService.sourceCount(homeVO.getFtsResource(), null, null));
		System.out.println("一共:"+homeVO.getSourceCount()+"个资源");
		// List<FtsResource> list =
		// resourceService.findResourcePage(homeVO.getFtsResource(),
		// homeVO.getPageIndex(),
		// homeVO.getPageSize());
		// homeVO.setFtsResources(list);
		return "list";
	}

	/**
	 * 资源详情页
	 * 
	 * @return
	 */
	public String details() {
		// 通过具体资源ID
		// 具体查询某个资源
		// 存到homeVO.ftsResource里
		FtsResource ftsResource = resourceService.getResourceById(homeVO.getFtsResource().getId());
		homeVO.setFtsResource(ftsResource);
		return "details";
	}

}
