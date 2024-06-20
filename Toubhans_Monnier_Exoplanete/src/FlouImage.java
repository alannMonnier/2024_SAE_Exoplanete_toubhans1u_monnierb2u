import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlouImage {

    // Méthode pour appliquer un flou gaussien
    // Méthode pour appliquer un flou gaussien
    public BufferedImage appliquerFlouGaussien(String cheminImage, int taille) throws IOException {
        BufferedImage img = ImageIO.read(new File(cheminImage));
        int largeur = img.getWidth();
        int hauteur = img.getHeight();
        BufferedImage imageResultat = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);

        float sigma = taille / 6.0f;
        float[] noyauGaussien = creerNoyauGaussien(taille, sigma);

        int demiTaille = taille / 2;
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                float[] somme = {0, 0, 0};
                float poidsTotal = 0;

                for (int ky = -demiTaille; ky <= demiTaille; ky++) {
                    int pixelY = Math.min(Math.max(0, y + ky), hauteur - 1); // Gestion des bords de l'image en Y
                    for (int kx = -demiTaille; kx <= demiTaille; kx++) {
                        int pixelX = Math.min(Math.max(0, x + kx), largeur - 1); // Gestion des bords de l'image en X
                        int pixel = img.getRGB(pixelX, pixelY);
                        float coeff = noyauGaussien[(ky + demiTaille) * taille + (kx + demiTaille)];
                        somme[0] += coeff * ((pixel >> 16) & 0xFF);
                        somme[1] += coeff * ((pixel >> 8) & 0xFF);
                        somme[2] += coeff * (pixel & 0xFF);
                        poidsTotal += coeff;
                    }
                }

                int r = Math.min(255, Math.max(0, (int) (somme[0] / poidsTotal)));
                int g = Math.min(255, Math.max(0, (int) (somme[1] / poidsTotal)));
                int b = Math.min(255, Math.max(0, (int) (somme[2] / poidsTotal)));
                int nouveauPixel = (r << 16) | (g << 8) | b;
                imageResultat.setRGB(x, y, nouveauPixel);
            }
        }
        return imageResultat;
    }




    // Méthode pour créer la matrice de convolution gaussienne
    private static float[] creerNoyauGaussien(int taille, float sigma) {
        float[] matrice = new float[taille * taille];
        float somme = 0;
        int demiTaille = taille / 2;

        for (int y = -demiTaille; y <= demiTaille; y++) {
            for (int x = -demiTaille; x <= demiTaille; x++) {
                int index = (y + demiTaille) * taille + (x + demiTaille);
                if (index < matrice.length) { // Vérification pour éviter l'index out of bounds
                    float valeur = (float) (Math.exp(-(x * x + y * y) / (2 * sigma * sigma)) / (2 * Math.PI * sigma * sigma));
                    matrice[index] = valeur;
                    somme += valeur;
                }
            }
        }

        // Normaliser les coefficients
        for (int i = 0; i < matrice.length; i++) {
            matrice[i] /= somme;
        }

        return matrice;
    }
}
