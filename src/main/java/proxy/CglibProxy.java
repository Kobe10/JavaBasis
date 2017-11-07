package proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
	/**
	 * ���ɴ�����
	 * @param cls
	 * @return
	 */
	public Object getProxy(Class<?> cls) {
		//��ǿ��
		Enhancer enhancer=new Enhancer();
		//������ǿ�Ķ���
		enhancer.setSuperclass(cls);
		//���ô����߼�
		enhancer.setCallback(this);
		return enhancer.create();
	}

	
	@Override
	/**
	 * @param proxy �������,��ǰ����
	 * @param method ����
	 * @param args ��������
	 * @param methodProxy������,����ִ��ԭ���Ĵ�����
	 * @return �����߼�����ֵ
	 */
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		logging();
		System.out.println(proxy.getClass().getSuperclass());
		//����ֻ���õ��������proxy,����ֻ��ͨ���Ҵ������ĸ�����ִ��ԭ���ķ���
		//����ܹ��õ�ԭʼ�ı��������(����ͨ�����캯������)�������ͨ��method������ִ��
		Object result=methodProxy.invokeSuper(proxy, args);
		return result;
	}
	
	private void logging() {
		System.out.println("start logging now!");
	}


}
