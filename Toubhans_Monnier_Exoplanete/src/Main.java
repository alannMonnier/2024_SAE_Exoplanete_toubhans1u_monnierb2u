import TP_Prepa_SAE.ImageBlanc;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String imgChemin = "Toubhans_Monnier_Exoplanete/images_exoplanet/Planete 3.jpg";

        try {
            // Lire l'image originale
            BufferedImage originalImage = ImageIO.read(new File(imgChemin));

            // Réduire la résolution de l'image pour accélérer le traitement
            double scale = 0.5; // Utiliser seulement un dixième de l'image
            BufferedImage reducedImage = reduceImageResolution(originalImage, scale);
            ImageIO.write(reducedImage, "jpg", new File("reducedImage.jpg"));

            // Appliquer un flou gaussien à l'image réduite
            FlouImage flou = new FlouImage();
            BufferedImage imgFloutee = flou.appliquerFlouGaussien("reducedImage.jpg", 7);
            ImageIO.write(imgFloutee, "jpg", new File("imageFloutee.jpg"));
            System.out.println("Image floue créée.");

            // Extraire la palette de couleurs de l'image floutée
            ColorPaletteExtractor colorPaletteExtractor = new ColorPaletteExtractor("imageFloutee.jpg");
            ArrayList<Color> palette = new ArrayList<>();
            palette.add(new Color(0, 0, 255));
            palette.add(new Color(0, 255, 0));
            palette.add(new Color(255, 0, 0));
            palette.add(new Color(255, 192, 203));
            palette.add(new Color(0, 0, 139));
            palette.add(new Color(255, 165, 0));
            System.out.println("Palette de couleurs extraites : " + 6 + " clusters.");

            // Obtenir les couleurs des pixels de l'image floutée
            CouleurImage couleurImage = new CouleurImage();
            double[][] data = couleurImage.getCouleur("imageFloutee.jpg");
            System.out.println("Couleurs des pixels obtenues.");

            // Effectuer le clustering avec K-means basé sur les couleurs
            System.out.println("Démarrage de KMeans.");
            KMeans kmeans = new KMeans(6, data);
            int[] clusters = kmeans.cluster(data);
            System.out.println("Clustering terminé.");

            // Blanchir l'image de base et réduire la taille
            ImageBlanc imageBlanc = new ImageBlanc();
            BufferedImage imageBlanchie = imageBlanc.rendreBlanc(imgChemin);
            BufferedImage reducedBlanchie = reduceImageResolution(imageBlanchie, scale);
            ImageIO.write(reducedBlanchie, "jpg", new File("imageBlanchie.jpg"));
            System.out.println("Image blanchie et réduite créée.");

            // Colorier l'image blanchie réduite en fonction des clusters
            int width = reducedBlanchie.getWidth();
            int height = reducedBlanchie.getHeight();
            int index = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (index < clusters.length) {
                        int clusterIndex = clusters[index];
                        int color = palette.get(clusterIndex).getRGB();
                        reducedBlanchie.setRGB(x, y, color); // Coloriage
                        index++;
                    } else {
                        System.out.println(index + " dépasse la taille des clusters.");
                    }
                }
            }
            System.out.println("Coloration terminée.");

            // Restaurer la taille originale de l'image finale
            BufferedImage finalImage = restoreOriginalResolution(reducedBlanchie, originalImage.getWidth(), originalImage.getHeight());
            ImageIO.write(finalImage, "jpg", new File("imageFinale.jpg"));
            System.out.println("Image finale créée.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage reduceImageResolution(BufferedImage originalImage, double scale) {
        int newWidth = (int) (originalImage.getWidth() * scale);
        int newHeight = (int) (originalImage.getHeight() * scale);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int pixel = originalImage.getRGB((int) (x / scale), (int) (y / scale));
                resizedImage.setRGB(x, y, pixel);
            }
        }
        return resizedImage;
    }

    private static BufferedImage restoreOriginalResolution(BufferedImage reducedImage, int originalWidth, int originalHeight) {
        BufferedImage restoredImage = new BufferedImage(originalWidth, originalHeight, reducedImage.getType());

        double scale = (double) originalWidth / reducedImage.getWidth();
        for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                int pixel = reducedImage.getRGB((int) (x / scale), (int) (y / scale));
                restoredImage.setRGB(x, y, pixel);
            }
        }
        return restoredImage;
    }
}
