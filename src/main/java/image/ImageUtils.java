package image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	public static BufferedImage operate(BufferedImage img1,BufferedImage img2) {
		int width=img1.getWidth();
		int height=img1.getHeight();
		int i,j,result;
		for(i=0;i<width;i++)
			for(j=0;j<height;j++) {
				result=(img1.getRGB(i, j)+img2.getRGB(i, j))/2;
				img1.setRGB(i, j, result);
			}
		return img1;
	}
	
	public static BufferedImage cover(File baseImg, File coverImg,float alpha) {
		BufferedImage buffBaseImg=null;
		try {
			buffBaseImg=ImageIO.read(baseImg);
			BufferedImage buffCoverImg=ImageIO.read(coverImg);
			int width=buffBaseImg.getWidth();
			int height=buffBaseImg.getHeight();
			Graphics2D gBaseImg=buffBaseImg.createGraphics();
			gBaseImg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			
			gBaseImg.drawImage(buffCoverImg,0,0,width,height,null);
			gBaseImg.dispose();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return buffBaseImg;
	}
	
	public static void generateCoveredFile(BufferedImage buffImg,String savePath) {
		int suffixIndex=savePath.lastIndexOf(".")+1;
		try {
			ImageIO.write(buffImg, savePath.substring(suffixIndex), new File(savePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("合成图片成功！");
	}

}
