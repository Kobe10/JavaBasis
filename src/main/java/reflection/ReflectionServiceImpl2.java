package reflection;

public class ReflectionServiceImpl2 implements ReflectService {
	String name;
	
	public ReflectionServiceImpl2(String name) {
		this.name=name;
	}

	@Override
	public void sayHello(String name) {
		System.out.println(name+",���ǵڶ���service��");
	}
	
	public void sayHello() {
		System.out.println("Ĭ���û���"+this.name+",���ǵڶ���service��");
	}
	
	public void sayToTwo(String p1,String p2) {
		System.out.println(p1+" hello,���ǵڶ���service��");
		System.out.println(p2+" hello,���ǵڶ���service��");
	}

}
