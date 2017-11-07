package reflection;

public class ReflectServiceImpl implements ReflectService {
	@Override
	public void sayHello(String name) {
		System.out.println("Hello "+name);
	}

}
