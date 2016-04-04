package survey.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import survey.sercurity.Permission;
import survey.service.PermissionService;
/**
 * 对权限的监听，当spring容器初始化时，把所有权限放进容器中
 * @author 满儿
 *
 */
@Component
public class PermissionListener implements ApplicationListener<ApplicationEvent>,ServletContextAware {

	@Autowired
	private PermissionService permissionService;
	
	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {

		if (arg0 instanceof ContextRefreshedEvent) {
			List<Permission> permissions = permissionService.getAllPermissions();
			
			Map<String, Permission> map = new HashMap<String, Permission>();
			for (Permission permission : permissions) {
				map.put(permission.getUrl(), permission);
			}
			
			if (servletContext != null) {
				servletContext.setAttribute("allPermissions", map);
			}
		}
	}
	
	
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		servletContext = arg0;
	}

	
}
