package proxy;

public class UserManagerImplProxy implements UserManager {
	UserManager userManager;
	public UserManagerImplProxy(UserManager userManager) {
		this.userManager=userManager;
	}
	
	private void logging() {
		System.out.println("start logging now!");
	}

	@Override
	public void addUser(String username, String password) {
		logging();
		userManager.addUser(username, password);
		// TODO Auto-generated method stub

	}

	@Override
	public void delUser(int userId) {
		logging();
		userManager.delUser(userId);
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyUser(int userId, String username, String password) {
		logging();
		userManager.modifyUser(userId, username, password);

	}

}
