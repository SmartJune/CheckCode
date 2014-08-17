
import java.awt.image.BufferedImage;
public class CheckCode {
    
    public static void main(String[] args) {
            BufferedImage image = Tools.getImage("/home/june/checked2.bmp");
            compare(image);    
            System.out.println("");
        
    }
    
    public static void compare(BufferedImage image){
        BufferedImage checkCode[] = Tools.getCheckCodes(image);
 
        for (int t = 0; t < 6; t++) {
            boolean ckFlg = false;
            boolean flag = false;
            int num=-1;
            for (int i = 0; i < 10; i++) {
                num=-1;
                ckFlg = true;
                flag = false;
                BufferedImage testImage = Tools.getImage("/home/june/CheckCode/" + i +".bmp");
                if(testImage==null){
                    continue;
                }
                System.out.println(i);
                for (int y = 0; y < checkCode[t].getHeight() && !flag; y++) {
                    for (int x = 0; x < checkCode[t].getWidth(); x++) {
                    	
                        int expRGB = Tools.pixelConvert(checkCode[t].getRGB(x, y));
                        
                        int cmpRGB = Tools.pixelConvert(testImage.getRGB(x, y));
                        
                        if (expRGB != cmpRGB) {
                        	System.out.println(x+" "+y);
                        	flag = true;
                            ckFlg = false;
                            break;
                        }
                    }
                }

                if (ckFlg) {
                    num=i;
                    break;
                }
            }
            if (ckFlg) {
                System.out.println("第"+(t+1)+"个数字是："+num);
            } else {
                System.out.println("x");
                Tools.writeImageToFile("/home/june/CheckLose/"+t+".bmp", checkCode[t]);
            }
        }
    }
}
