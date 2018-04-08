package src.net.bittreasury.fts.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.http.HttpRequest;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserLoginInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		//判断用户请求url是公开地址
		//目前登陆注册时公开地址
		//获取请求url
		HttpServletRequest request = ServletActionContext.getRequest();
		String requestURI = request.getRequestURI();
		if(1==1) {
			//如果时公开的
			return actionInvocation.invoke();
		}
		
		//这里证明没有放行，判断是否认证
		HttpSession session = request.getSession();
		//登陆成功向session加入XXX
		
		String ftsUsers = (String) session.getAttribute("ftsUsers");
		if(ftsUsers!=null) {
			return actionInvocation.invoke();
		}
		
		
		return "registeredUsers";
		
		
		
		
	}

}
