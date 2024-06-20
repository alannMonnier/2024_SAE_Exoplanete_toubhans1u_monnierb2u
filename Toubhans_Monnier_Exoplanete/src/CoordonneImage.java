import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CoordonneImage {

    public double[][] getCoordonne(String chemin) throws IOException {
        File f = new File(chemin);
        BufferedImage img = ImageIO.read(f);
        int hauteur = img.getHeight();
        int largeur = img.getWidth();
        double[][] res = new double[hauteur * largeur][2]; // <-- Tableau de dimensions [hauteur*largeur][2]
        int compteur = 0;
        for(int y  = 0; y < hauteur; y++){
            for(int x = 0; x < largeur; x++){
                res[compteur][0] = y;
                res[compteur][1] = x;
                compteur++;
            }
        }
        return res;
    }
}