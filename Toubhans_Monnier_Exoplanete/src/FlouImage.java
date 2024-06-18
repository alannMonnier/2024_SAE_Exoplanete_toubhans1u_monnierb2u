import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlouImage {

    /**
    public static void main(String[] args) throws IOException {
        // Charger l'image depuis un fichier
        BufferedImage image = ImageIO.read(new File("Planete.jpg"));

        // Tailles de filtres à tester
        int[] taillesFiltres = {10, 20, 50};

        // Appliquer les filtres de différentes tailles et sauvegarder les images
        for (int taille : taillesFiltres) {
            // Appliquer le flou par moyenne
            BufferedImage imageFlouMoyenne = appliquerFlouMoyenne(image, taille);
            ImageIO.write(imageFlouMoyenne, "jpg", new File("flou_moyenne_" + taille + ".jpg"));

            // Appliquer le flou gaussien
            BufferedImage imageFlouGaussien = appliquerFlouGaussien(image, taille);
            ImageIO.write(imageFlouGaussien, "jpg", new File("flou_gaussien_" + taille + ".jpg"));
        }
    }
     **/

    // Méthode pour appliquer un flou par moyenne
    private static BufferedImage appliquerFlouMoyenne(BufferedImage image, int taille) {
        int largeur = image.getWidth();
        int hauteur = image.getHeight();
        BufferedImage imageResultat = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);

        int demiTaille = taille / 2;
        for (int y = demiTaille; y < hauteur - demiTaille; y++) {
            for (int x = demiTaille; x < largeur - demiTaille; x++) {
                float[] somme = {0, 0, 0};
                for (int ky = -demiTaille; ky <= demiTaille; ky++) {
                    for (int kx = -demiTaille; kx <= demiTaille; kx++) {
                        int pixel = image.getRGB(x + kx, y + ky);
                        somme[0] += ((pixel >> 16) & 0xFF);
                        somme[1] += ((pixel >> 8) & 0xFF);
                        somme[2] += (pixel & 0xFF);
                    }
                }
                int nbPixels = taille * taille;
                int r = Math.min(255, Math.max(0, (int)(somme[0] / nbPixels)));
                int g = Math.min(255, Math.max(0, (int)(somme[1] / nbPixels)));
                int b = Math.min(255, Math.max(0, (int)(somme[2] / nbPixels)));
                int nouveauPixel = (r << 16) | (g << 8) | b;
                imageResultat.setRGB(x, y, nouveauPixel);
            }
        }
        return imageResultat;
    }

    // Méthode pour appliquer un flou gaussien
    public BufferedImage appliquerFlouGaussien(String image, int taille) throws IOException {
        File file = new File(image);
        BufferedImage img = ImageIO.read(file);
        int largeur = img.getWidth();
        int hauteur = img.getHeight();
        BufferedImage imageResultat = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);

        float sigma = taille / 6.0f;
        float[] noyauGaussien = creerNoyauGaussien(taille, sigma);

        int demiTaille = taille / 2;
        for (int y = demiTaille; y < hauteur - demiTaille; y++) {
            for (int x = demiTaille; x < largeur - demiTaille; x++) {
                float[] somme = {0, 0, 0};
                for (int ky = -demiTaille; ky <= demiTaille; ky++) {
                    for (int kx = -demiTaille; kx <= demiTaille; kx++) {
                        int pixel = img.getRGB(x + kx, y + ky);
                        float coeff = noyauGaussien[(ky + demiTaille) * taille + (kx + demiTaille)];
                        somme[0] += coeff * ((pixel >> 16) & 0xFF);
                        somme[1] += coeff * ((pixel >> 8) & 0xFF);
                        somme[2] += coeff * (pixel & 0xFF);
                    }
                }
                int r = Math.min(255, Math.max(0, (int)somme[0]));
                int g = Math.min(255, Math.max(0, (int)somme[1]));
                int b = Math.min(255, Math.max(0, (int)somme[2]));
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
                float valeur = (float) (Math.exp(-(x * x + y * y) / (2 * sigma * sigma)) / (2 * Math.PI * sigma * sigma));
                matrice[(y + demiTaille) * taille + (x + demiTaille)] = valeur;
                somme += valeur;
            }
        }
        // Normaliser les coefficients
        for (int i = 0; i < matrice.length; i++) {
            matrice[i] /= somme;
        }
        return matrice;
    }
}