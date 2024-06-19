package TP_Prepa_SAE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;


public class Image {
    
    public BufferedImage loadPicture(String path) throws IOException{
        return ImageIO.read(new File(path));        
    }

    public boolean save(BufferedImage bi, String format, File output) throws IOException{
        return ImageIO.write(bi, format, output);
    }


    public static void main(String[] args) {
        Image image = new Image();
        try{
            BufferedImage bi = image.loadPicture("./image_test/img2.jpg");
            image.save(bi, "JPG", new File("./image/img2.jpg"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }
}
