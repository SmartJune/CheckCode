
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class Tools {
    
    public static void writeImageToFile(String imgFile, BufferedImage bi) {
    	//把图片写入文件
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(imgFile
            .substring(imgFile.lastIndexOf('.') + 1));
        ImageWriter writer = (ImageWriter) writers.next();

        File f = new File(imgFile);
        ImageOutputStream ios;

        try {
            ios = ImageIO.createImageOutputStream(f);
            writer.setOutput(ios);
            writer.write(bi);
            ios.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static int pixelConvert(int pixel) {
        int result = 0;
        //获取图像的 RGB 分量
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;

        result = 0xff000000;	//黑色
        //黑色 或 白色
        int tmp = r * r + g * g + b * b;
        if (tmp > 3 * 128 * 128) {
            result += 0x00ffffff;	//白色
        }

        return result;
    }

    public static BufferedImage getImage(String path) {
        //根据路径读取图片
    	BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
        }

        return image;
    }

    public static BufferedImage getCuttedImages(BufferedImage image){
    	//弃用，原用于切割掉图片左右的空间
    	int x1=0,x2=0;
    	boolean foundedFirst = false;
    	boolean foundedLast = false;
    	BufferedImage cuttedImage;
    	System.out.println(image.getWidth()+" "+image.getHeight());
    	for (int i = 0; i < image.getWidth() && !foundedFirst; i++) {
            for (int j = 0; j < image.getHeight(); j++) {
            	if(image.getRGB(i, j) == 0xff000000 ){
            		x1 = i;
            		System.out.println(i+" "+j);
            		foundedFirst = true;
            		break;
            	}
            }       
    	}
    	for (int i = (image.getWidth())-1; i >= 0 && !foundedLast; i--) {
    		for (int j = 0; j < image.getHeight(); j++) {
    			if(image.getRGB(i, j) == 0xff000000 ){
            		x2 = i;
            		System.out.println(i+" "+j);
            		foundedLast = true;
            		break;
            	}
            }
    	}
    	cuttedImage = image.getSubimage(x1, 0, x2-x1, image.getHeight());
    	writeImageToFile("/home/june/cutted.bmp", cuttedImage);
    	return cuttedImage;
    }
    public static int getStart(int widthPosition, BufferedImage image){
    	//找到一个字符的起点
    	for (int i = widthPosition; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
            	if(image.getRGB(i, j) == 0xff000000 ){
            		return i;
            	}
            }       
    	}	
    	return -1;
    }
    public static int getEnd(int widthPosition, BufferedImage image){
    	//找到字符的终点
    	int count = 0;
    	for (int i = widthPosition; i < image.getWidth() ; i++) {
    		count = 0;
            for (int j = 0; j < image.getHeight(); j++) {
            	if(image.getRGB(i, j) == 0xffffffff ){
            		count++;
            		if(count == image.getHeight()){
            			count = 0;
            			return i;
            		}
            	}
            }       
    	}
    	return -1;
    }
    public static void downloadImage(String urlAddress, String path) throws Exception{
    	//下载验证码图片下来
    	URL url = new URL(urlAddress);
		File outFile = new File(path);
		OutputStream os = new FileOutputStream(outFile);
		InputStream is = url.openStream();
		byte[] buff = new byte[1024];
		while(true) {
			int readed = is.read(buff);
			if(readed == -1) {
				break;
			}
			byte[] temp = new byte[readed];
			System.arraycopy(buff, 0, temp, 0, readed);
			os.write(temp);
		}
		is.close(); 
        os.close();
    }
    public static BufferedImage[] getCheckCodes(BufferedImage image) {
        //开始切割
    	BufferedImage checkCode[] = new BufferedImage[6];
        int height = image.getHeight();
        int width = image.getWidth();

        int x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12;
        x1 = Tools.getStart(0, image);
        x2 = Tools.getEnd(x1, image);
    //    	System.out.println(x1+" "+x2);
        checkCode[0] = image.getSubimage(x1, 0, x2-x1, height);
        
        x3 = Tools.getStart(x2, image);
        x4 = Tools.getEnd(x3, image);
   //     	System.out.println(x3+" "+x4);
        checkCode[1] = image.getSubimage(x3, 0, x4-x3, height);
        
        x5 = Tools.getStart(x4, image);
        x6 = Tools.getEnd(x5, image);
    //    	System.out.println(x5+" "+x6);
        checkCode[2] = image.getSubimage(x5, 0, x6-x5, height);
        
        x7 = Tools.getStart(x6, image);
        x8 = Tools.getEnd(x7, image);
   //     	System.out.println(x7+" "+x8);
        checkCode[3] = image.getSubimage(x7, 0, x8-x7, height);
        x9 = Tools.getStart(x8, image);
        x10 = Tools.getEnd(x9, image);
  //      	System.out.println(x9+" "+x10);
        checkCode[4] = image.getSubimage(x9, 0, x10-x9, height);
        x11 = Tools.getStart(x10, image);
        x12 = Tools.getEnd(x11, image);
  //      	System.out.println(x11+" "+x12);
        checkCode[5] = image.getSubimage(x11, 0, x12-x11, height);
        
        return checkCode;
    }
}
