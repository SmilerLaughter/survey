package survey.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Scope("prototype")
@Controller("baseAction")
public  class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model;
	
	/**
	 * 通过反射初始化model
	 */
	public BaseAction() {

		Type genericSuperclass = this.getClass().getGenericSuperclass();
		if(genericSuperclass instanceof ParameterizedType){
			Type[] types = ((ParameterizedType)genericSuperclass).getActualTypeArguments();
			if(types != null && types.length > 0){
				Class clazz = (Class)types[0];
				try {
					model = (T)clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 返回model，压入值栈
	 * 
	 */
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	
	
}
