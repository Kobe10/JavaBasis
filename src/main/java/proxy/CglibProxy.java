package proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
	/**
	 * 生成代理类
	 * @param cls
	 * @return
	 */
	public Object getProxy(Class<?> cls) {
		//增强类
		Enhancer enhancer=new Enhancer();
		//设置增强的对象
		enhancer.setSuperclass(cls);
		//设置代理逻辑
		enhancer.setCallback(this);
		return enhancer.create();
	}

	
	@Override
	/**
	 * @param proxy 代理对象,当前对象
	 * @param method 方法
	 * @param args 方法参数
	 * @param methodProxy代理方法,用于执行原来的代理方法
	 * @return 代理逻辑返回值
	 */
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		logging();
		System.out.println(proxy.getClass().getSuperclass());
		//这里只能拿到代理对象proxy,所以只能通过找代理对象的父方法执行原来的方法
		//如果能够拿到原始的被代理对象(比如通过构造函数传入)，则可以通过method来进行执行
		Object result=methodProxy.invokeSuper(proxy, args);
		return result;
	}
	
	private void logging() {
		System.out.println("start logging now!");
	}


}
