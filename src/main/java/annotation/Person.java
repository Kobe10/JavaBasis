package annotation;

@MyAnnotation(name="person",say="hello",age=12)
public interface Person {
	@MyAnnotation(name="person")
	public String getName();
	
	@MyAnnotation(say="good")
	public void sayit();
	
	@MyAnnotation(age=10)
	public int age();

}
