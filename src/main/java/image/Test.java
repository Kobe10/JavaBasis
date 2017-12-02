package image;

import java.awt.image.BufferedImage;
import java.io.File;

public class Test {

	public static void main(String[] args) {
		String baseImgPath="image/051.png";
		String coverImgPath="image/51.png";
		String saveImgPath="";
		for(int i=52;i<=52;i++) {
			saveImgPath="image/0"+i+".png";
			BufferedImage buffImg=ImageUtils.cover(new File(baseImgPath), new File(coverImgPath), 0.5f);
			ImageUtils.generateCoveredFile(buffImg, saveImgPath);
			baseImgPath=saveImgPath;
			coverImgPath="image/"+i+".png";
		}
		
		
		
	/*	File imgDir=new File("image");
		File[] files=imgDir.listFiles();
		for(File file:files) {
			//System.out.println(file.getName());
			String name=file.getName();
			if(name.indexOf(' ')!=-1) {
				String suffix=name.substring(name.indexOf('.'));
				name=name.substring(0, name.indexOf(' '))+suffix;
			}
			file.renameTo(new File("image/"+name));
		}*/

	}

}
