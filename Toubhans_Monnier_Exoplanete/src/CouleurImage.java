import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CouleurImage {

    public double[][] getCouleur(String chemin) throws IOException {
        File fichier = new File(chemin);
        BufferedImage image = ImageIO.read(fichier);
        int largeur = image.getWidth();
        int hauteur = image.getHeight();
        double[][] resultats = new double[hauteur * largeur][3]; // Tableau pour les valeurs de couleur RGB

        int compteur = 0;
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                int pixel = image.getRGB(x, y); // Récupérer la couleur du pixel
                Color couleur = new Color(pixel);

                // Stocker les valeurs R, G, B normalisées dans le tableau de résultats
                resultats[compteur][0] = couleur.getRed() / 255.0;
                resultats[compteur][1] = couleur.getGreen() / 255.0;
                resultats[compteur][2] = couleur.getBlue() / 255.0;

                compteur++;
            }
        }
        return resultats;
    }
}
