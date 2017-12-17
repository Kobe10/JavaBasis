package myenum;

public enum Color {
	RED("��ɫ",1),GREEN("��ɫ",2),BLACK("��ɫ",3);

	private String chineseName;
	private int index;
	//ֻ����˽�з���
	private Color(String chineseName, int index) {
		this.chineseName=chineseName;
		this.index=index;
	}
	
	public static String getName(int index) {
		for(Color color:Color.values()) {
			if(color.getIndex()==index)
				return color.name();
		}
		return null;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
