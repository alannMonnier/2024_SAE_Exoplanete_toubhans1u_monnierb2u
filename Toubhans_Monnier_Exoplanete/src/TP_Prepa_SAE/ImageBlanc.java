package TP_Prepa_SAE;

import TP_Prepa_SAE.OutilCouleur;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageBlanc {

    public BufferedImage rendreBlanc(String chemin) throws IOException {
        File f = new File(chemin);
        BufferedImage img = ImageIO.read(f);

        int hauteur = img.getHeight();
        int largeur = img.getWidth();

        BufferedImage img_copie = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                int couleur = img.getRGB(x, y);
                int[] val = OutilCouleur.getTabColor(couleur);

                int[] colWhite = new int[3];
                for (int i = 0; i < colWhite.length; i++) {
                    colWhite[i] = val[i] + (75 * (255 - val[i]) / 100);
                }

                int white = (colWhite[0] << 16) | (colWhite[1] << 8) | colWhite[2];
                img_copie.setRGB(x, y, white);
            }
        }

        String extension = getPartFile(chemin)[1];
        String nomFichier = getPartFile(chemin)[0];
        String cheminSortie = "Toubhans_Monnier_Exoplanete/image_blanc/" + nomFichier + "_copie_blanche." + extension;
        boolean ecrit = ImageIO.write(img_copie, extension, new File(cheminSortie));
        System.out.println("Image écrite ? : " + ecrit);

        return img_copie;
    }

    public String[] getPartFile(String file) {
        String tmp[] = file.split("/");
        String tab[] = tmp[tmp.length - 1].split("\\.");
        return tab;
    }

    public static void main(String[] args) throws IOException {
        ImageBlanc imageBlanc = new ImageBlanc();
        BufferedImage imageBlanchie = imageBlanc.rendreBlanc("Toubhans_Monnier_Exoplanete/images_flou/Planete_1_floute.jpg");
        // Vous pouvez utiliser imageBlanchie pour d'autres traitements si nécessaire
    }
}
