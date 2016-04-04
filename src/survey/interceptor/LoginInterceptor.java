package survey.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

import survey.action.BaseAction;
import survey.model.User;
import survey.sercurity.Permission;

public class LoginInterceptor implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	/**
	 * 自定义拦截器：用于校验用户是否已经登录 如果没登录，进入登录页面 在拦截器中进行 User的注入，为了action不用每次实现
	 * sessionAware 接口
	 */
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		BaseAction action = (BaseAction) actionInvocation.getAction();
		ActionProxy proxy = actionInvocation.getProxy();
		String namespace = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if (namespace == null || "/".equals(namespace)) {
			namespace = "";
		}
		String url = namespace + "/" + actionName;
		Permission permission = null;
		
		@SuppressWarnings("unchecked")
		Map<String, Permission> map = (Map<String, Permission>) actionInvocation.getInvocationContext().getApplication()
				.get("allPermissions");
		permission = map.get(url);
		
		User user = (User) actionInvocation.getInvocationContext().getSession().get("user");
		if (user != null && action instanceof UserAware) {//如果user不为空 且action实现了 UserAware接口，则进行user注入
			((UserAware)action).setUser(user);
		}
		
		if (permission == null || permission.isOpen()) {//如果permission为空，并且是开放的，则直接回调
			return actionInvocation.invoke();
		} else {

			if (user == null) { //用户未登录
				return "login";
			} else {//已登录
				
				if (user.isSuperAdmin()) {//是超级管理员
					return actionInvocation.invoke();
				} else {//不是超级管理员，判断是否拥有其权限
					if (user.hasPermission(permission)) { //如果拥有此权限
						return actionInvocation.invoke();
					}else {//没有此权限
						return "login";
					}
				}
			}
		}

	/*	BaseAction action = (BaseAction) arg0.getAction();// 获取当前action
		if (action instanceof UserAction) { // 如果是 useraction ，则不用拦截
			return arg0.invoke();
		} else {// 否则，进行拦截操作，获取user，若user为空，则未登录，调转至登录页面
			User user = (User) arg0.getInvocationContext().getSession().get("user");
			if (user == null) {
				return "login";
			} else if (action instanceof UserAware) {
				((UserAware) action).setUser(user);// 注入user给action
			}
		}

		return arg0.invoke();*/
	}

}
