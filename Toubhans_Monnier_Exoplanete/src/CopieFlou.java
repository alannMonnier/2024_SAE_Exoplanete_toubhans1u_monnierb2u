import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe permettant de créer générer des images flou et de laes copier dans le dossier images_flou
 */
public class CopieFlou {
    /**
     * Prend une image et copie celle-ci en flou dans le dossier images_flou
     * @param chemin : chemin de l'image
     */
    public void copieFlouImage(String chemin) throws IOException {
        FlouImage flouImage = new FlouImage();
        BufferedImage image = flouImage.appliquerFlouGaussien(chemin, 7);

        String extension = this.getPartFile(chemin)[1];
        String nomFichier = this.getPartFile(chemin)[0];
        boolean ecrit = ImageIO.write(image, extension, new File("Toubhans_Monnier_Exoplanete/images_flou/"+nomFichier+"_copie."+extension));
        System.out.println("Ecriture, succès ? : " + ecrit);
        System.out.println("Type : " + BufferedImage.TYPE_3BYTE_BGR);
    }

    /**
     *
     * @param file : chemin de l'image
     * @return tab[0] nom du fichier, tab[1] l'extension du fichier
     */
    public String[] getPartFile(String file) {
        String tmp[] = file.split("/");
        String tab[] = tmp[tmp.length - 1].split("\\.");
        return tab;
    }
}
