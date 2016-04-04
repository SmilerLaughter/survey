package survey.interceptor;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

import survey.sercurity.Permission;
import survey.service.PermissionService;

public class PermissionInterceptor implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 拦截所有action，并插入进数据库
	 */
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		ActionProxy proxy = actionInvocation.getProxy();
		String namespace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		
		if ((namespace != null && namespace.length() > 0) || namespace.equals("/")) {
			namespace = "";
		}
		
		String url = namespace + "/" + actionName;
		
		ServletContext servletContext = ServletActionContext.getServletContext();
		ApplicationContext application = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		PermissionService permissionService = (PermissionService)application.getBean("permissionService");
		Permission permission = new Permission();
		permission.setUrl(url);
		permissionService.addPermission(permission);//会检查是否存在该权限
		return actionInvocation.invoke();
	}

}
