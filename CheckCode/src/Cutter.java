
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cutter {
    public static void main(String[] args) {
        BufferedImage image = null;
        BufferedImage checkCode[] = new BufferedImage[4];
        try {
            image = ImageIO.read(new File("/home/june/checked2.bmp"));
        } catch (IOException e) {
            System.out.println("can't open checkCode");
        }
        
      //  BufferedImage cuttedImage = Tools.getCuttedImages(image);
        checkCode=Tools.getCheckCodes(image);

        for (int i = 0; i < checkCode.length; i++) {
            try {
                ImageIO.write(checkCode[i], "BMP", new File("/home/june/" + (i+1)+ ".bmp"));
            } catch (IOException e) {
                System.out.println("can't open checkCode");
            }
        }
    }
}
