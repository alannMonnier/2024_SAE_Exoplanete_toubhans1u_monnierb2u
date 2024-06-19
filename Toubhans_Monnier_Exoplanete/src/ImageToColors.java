import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageToColors {
    private BufferedImage image;
    private int height;
    private int width;
    private double[][] colors;

    public ImageToColors(String imagePath) throws IOException {
        this.image = ImageIO.read(new File(imagePath));
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.colors = new double[height * width][3];

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                colors[index][0] = red;
                colors[index][1] = green;
                colors[index][2] = blue;
                index++;
            }
        }
    }

    public double[][] getColors() {
        return colors;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
