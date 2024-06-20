import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CopieFlou {

    /**
     * Prend une image et copie celle-ci en flou dans le dossier images_flou
     * @param chemin : chemin de l'image
     */
    public void copieFlouImage(String chemin) throws IOException {
        FlouImage flouImage = new FlouImage();
        BufferedImage image = flouImage.appliquerFlouGaussien(chemin, 8);

        String[] parts = getPartFile(chemin);
        String nomFichier = parts[0];
        String extension = parts[1];
        boolean ecritureReussie = ImageIO.write(image, extension, new File("Toubhans_Monnier_Exoplanete/images_flou/" + nomFichier + "_flou." + extension));
        System.out.println("Ecriture, succès ? : " + ecritureReussie);
    }

    /**
     * Récupère le nom du fichier et son extension
     * @param chemin : chemin complet du fichier
     * @return un tableau avec le nom du fichier à l'index 0 et l'extension à l'index 1
     */
    public String[] getPartFile(String chemin) {
        String tmp[] = chemin.split("/");
        String tab[] = tmp[tmp.length - 1].split("\\.");
        return tab;
    }
}
