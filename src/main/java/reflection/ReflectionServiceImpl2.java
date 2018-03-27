package reflection;

public class ReflectionServiceImpl2 implements ReflectService {
	String name;
	
	public ReflectionServiceImpl2(String name) {
		this.name=name;
	}

	@Override
	public void sayHello(String name) {
		System.out.println(name+",我是第二个service类");
	}
	
	public void sayHello() {
		System.out.println("默认用户："+this.name+",我是第二个service类");
	}
	
	public void sayToTwo(String p1,String p2) {
		System.out.println(p1+" hello,我是第二个service类");
		System.out.println(p2+" hello,我是第二个service类");
	}

}
