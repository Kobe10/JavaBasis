package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		Class<?> clazz=Class.forName("annotation.Student");
		//类上是否存在注解
		if(clazz.isAnnotationPresent(MyAnnotation.class)) {
			Annotation[] anns=clazz.getAnnotations();
			for(Annotation ann:anns) {
				MyAnnotation myAnn=(MyAnnotation)ann;
				System.out.printf("name:%s,say:%s,age:%d\n", myAnn.name(),myAnn.say(),myAnn.age());
			}
		}
		  //方法上有无注解
		Method[] methods=clazz.getMethods();
		for(Method method:methods) {
			if(method.isAnnotationPresent(MyAnnotation.class)) {
				System.out.println("method:"+method.getName());
				Annotation[] methodAnns=method.getAnnotations();
				for(Annotation methodAnn:methodAnns) {
					MyAnnotation myAnn=(MyAnnotation)methodAnn;
					System.out.printf("name:%s,say:%s,age:%d\n", myAnn.name(),myAnn.say(),myAnn.age());
				}
				System.out.println("-------end");
			}
		}

	}

}
