package myenum;

public class SimpleTest {
	public static void main(String[] args) {
		//name��ordinal
		MyEnum test1=MyEnum.HOT;
		System.out.println(test1);//HOT
		System.out.println(test1.name());//HOT
		System.out.println(test1.ordinal());//3
		//�������е�ֵ
		for(MyEnum item:MyEnum.values()) {
			System.out.printf("name:%s,ordinal:%d\n",item.name(),item.ordinal() );
		}
		//ͨ������ȡ��Enum����
		MyEnum getEnumFromName=MyEnum.valueOf("HOT");
		System.out.println(getEnumFromName);
		//ͨ��Ordinalȡ��Enum����
		MyEnum[] myEnumArray=MyEnum.class.getEnumConstants();
		MyEnum getEnumFromOrdinal=myEnumArray[3];
		System.out.println(getEnumFromOrdinal);
	}

}
