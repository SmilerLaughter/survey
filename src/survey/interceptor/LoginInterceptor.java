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
	 * �Զ���������������У���û��Ƿ��Ѿ���¼ ���û��¼�������¼ҳ�� ���������н��� User��ע�룬Ϊ��action����ÿ��ʵ��
	 * sessionAware �ӿ�
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
		if (user != null && action instanceof UserAware) {//���user��Ϊ�� ��actionʵ���� UserAware�ӿڣ������userע��
			((UserAware)action).setUser(user);
		}
		
		if (permission == null || permission.isOpen()) {//���permissionΪ�գ������ǿ��ŵģ���ֱ�ӻص�
			return actionInvocation.invoke();
		} else {

			if (user == null) { //�û�δ��¼
				return "login";
			} else {//�ѵ�¼
				
				if (user.isSuperAdmin()) {//�ǳ�������Ա
					return actionInvocation.invoke();
				} else {//���ǳ�������Ա���ж��Ƿ�ӵ����Ȩ��
					if (user.hasPermission(permission)) { //���ӵ�д�Ȩ��
						return actionInvocation.invoke();
					}else {//û�д�Ȩ��
						return "login";
					}
				}
			}
		}

	/*	BaseAction action = (BaseAction) arg0.getAction();// ��ȡ��ǰaction
		if (action instanceof UserAction) { // ����� useraction ����������
			return arg0.invoke();
		} else {// ���򣬽������ز�������ȡuser����userΪ�գ���δ��¼����ת����¼ҳ��
			User user = (User) arg0.getInvocationContext().getSession().get("user");
			if (user == null) {
				return "login";
			} else if (action instanceof UserAware) {
				((UserAware) action).setUser(user);// ע��user��action
			}
		}

		return arg0.invoke();*/
	}

}
