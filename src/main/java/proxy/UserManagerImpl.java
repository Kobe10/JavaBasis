package proxy;

import java.util.List;

public class UserManagerImpl implements UserManager {

	@Override
	public void addUser(String username, String password) {
		System.out.println("---UserManagerImpl:addUser---");

	}

	@Override
	public void delUser(int userId) {
		System.out.println("---UserManagerImpl:delUser---");

	}

	@Override
	public void modifyUser(int userId, String username, String password) {
		System.out.println("---UserManagerImpl:modifyUser---");

	}
	
	public void addBatch(List<String>users) {
		System.out.println("---UserManagerImpl:addBatch---");
	}

}
