package myenum;

public class SimpleTest {
	public static void main(String[] args) {
		//name和ordinal
		MyEnum test1=MyEnum.HOT;
		System.out.println(test1);//HOT
		System.out.println(test1.name());//HOT
		System.out.println(test1.ordinal());//3
		//遍历所有的值
		for(MyEnum item:MyEnum.values()) {
			System.out.printf("name:%s,ordinal:%d\n",item.name(),item.ordinal() );
		}
		//通过名字取出Enum对象
		MyEnum getEnumFromName=MyEnum.valueOf("HOT");
		System.out.println(getEnumFromName);
		//通过Ordinal取出Enum对象
		MyEnum[] myEnumArray=MyEnum.class.getEnumConstants();
		MyEnum getEnumFromOrdinal=myEnumArray[3];
		System.out.println(getEnumFromOrdinal);
	}

}
