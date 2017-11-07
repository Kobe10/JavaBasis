package proxy;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		//��̬�������
		UserManager userManager=new UserManagerImpl();
		UserManagerImplProxy staticProxy=new UserManagerImplProxy(userManager);
		staticProxy.addUser("hello", "hello");
		staticProxy.delUser(1);
		staticProxy.modifyUser(1, "hello", "hello");
		System.out.println("-------------------------");
		//��̬�������
		LogHandler logHandler=new LogHandler();
		UserManager dynamicProxy=(UserManager) logHandler.createProxy(userManager);
		dynamicProxy.addUser("hello", "hello");
		dynamicProxy.delUser(1);
		dynamicProxy.modifyUser(1, "hello", "hello");
		//dynamicProxy.addBatch(new ArrayList<String>());
		System.out.println("-------------------------");
		//cglib��̬����
		CglibProxy cglibProxy=new CglibProxy();
		UserManagerImpl cglibUserManager=(UserManagerImpl)cglibProxy.getProxy(UserManagerImpl.class);
		cglibUserManager.addBatch(new ArrayList<>());
		cglibUserManager.delUser(1);

	}

}
