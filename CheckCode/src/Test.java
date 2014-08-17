import java.awt.image.BufferedImage;

public class Test {
    public static void main(String[] args) {
    //    for (int i = 1; i <= 10; i++) {
    		try {
				Tools.downloadImage("http://www.ems.com.cn/ems/rand", "/home/june/check.ashx");
			} catch (Exception e) {
				System.out.println("download failed");
			}
        	BufferedImage image = Tools.getImage("/home/june/check.ashx");
            Filter.blackAndWhiteFilter(image);
            Tools.writeImageToFile("/home/june/checked.bmp", image);
            Filter.dotFilter(image);
            Tools.writeImageToFile("/home/june/checked2.bmp", image);
     //   }
    }
}
