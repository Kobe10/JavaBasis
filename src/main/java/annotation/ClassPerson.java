package annotation;
@MyAnnotation(name="person",say="hello",age=12)
public class ClassPerson {
	@MyAnnotation(name="person")
	public String getName() {return null;};
	
	@MyAnnotation(say="good")
	public void sayit() {};
	
	@MyAnnotation(age=10)
	public int age() {return 0;};

}
