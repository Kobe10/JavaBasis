package proxy;

public interface UserManager {
	public void addUser(String username,String password);
	public void delUser(int userId);
	public void modifyUser(int userId,String username,String password);

}
