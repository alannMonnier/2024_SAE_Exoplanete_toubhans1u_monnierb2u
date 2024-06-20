package TP_Prepa_SAE;

import TP_Prepa_SAE.OutilCouleur;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageBlanc {
    public void rendreBlanc(String chemin) throws IOException {
        File f = new File(chemin);
        BufferedImage img = ImageIO.read(f);

        int hauteur = img.getHeight();
        int largeur = img.getWidth();

        BufferedImage img_copie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_3BYTE_BGR);
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                int couleur = img.getRGB(x, y);
                int[] val = OutilCouleur.getTabColor(couleur);

                int[] colWhite = new int[3];
                for (int i = 0; i < colWhite.length; i++) {
                    colWhite[i] = val[i] + (75 * (255 - val[i]) / 100); // Correctly calculate the color towards white
                }

                int white = (colWhite[0] << 16) | (colWhite[1] << 8) | colWhite[2];
                img_copie.setRGB(x, y, white);
            }
        }

        String extension = this.getPartFile(chemin)[1];
        String nomFichier = this.getPartFile(chemin)[0];
        boolean ecrit = ImageIO.write(img_copie, extension, new File("Toubhans_Monnier_Exoplanete/image_blanc/" + nomFichier + "_copie_blanche." + extension));
        System.out.println("Image ecrite ? : " + ecrit);
    }

    public String[] getPartFile(String file) {
        String tmp[] = file.split("/");
        String tab[] = tmp[tmp.length - 1].split("\\.");
        return tab;
    }

    public static void main(String[] args) throws IOException {
        ImageBlanc imageBlanc = new ImageBlanc();
        imageBlanc.rendreBlanc("Toubhans_Monnier_Exoplanete/images_exoplanet/Planete 1.jpg");
    }
}