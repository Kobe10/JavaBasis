package lambda;

public class Person {
	private String name;
	private int age;
	public Person(int i) {
		name="name"+i;
	}
	public Person(int i, int age) {
		name="name"+i;
		this.age=age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	

	
}
