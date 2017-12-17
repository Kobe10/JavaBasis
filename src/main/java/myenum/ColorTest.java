package myenum;

public class ColorTest {

	public static void main(String[] args) {
		for(Color color:Color.values()) {
			System.out.printf("ordinal:%d,index:%d,name:%s,chineseName:%s\n"
					,color.ordinal(),color.getIndex(),color.name(),color.getChineseName());
		}

	}

}
