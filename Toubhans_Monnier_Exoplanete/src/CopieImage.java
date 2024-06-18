import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CopieImage {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Indiquez le chemin d'entrée et de sortie pour votre image");
            return;
        }

        String inputImagePath = args[0];
        String outputImagePath = args[1];

        
        try {
            Image image = new Image();

            // On charge l'image
            BufferedImage originalImage = image.loadPicture(inputImagePath);


            // On copie les dimensions de l'image
            BufferedImage copiedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            // Copier chaque pixel de l'image originale dans la nouvelle image
            for (int x = 0; x < originalImage.getWidth(); x++) {
                for (int y = 0; y < originalImage.getHeight(); y++) {
                    int[] rgb = OutilCouleur.getTabColor(originalImage.getRGB(x, y));
                    int gray = 0;
                    for (int i : rgb){
                        gray += i;
                    }
                    gray = gray / 3;
                    int grayRGB = (gray << 16) | (gray << 8) | gray;

                    copiedImage.setRGB(x, y, grayRGB);
                }
            }

            // Sauver la nouvelle image dans le fichier de sortie
            image.save(copiedImage, "JPG", new File(outputImagePath));

            System.out.println("C'est tout bon");
        

        } catch (IOException e) {
            System.out.println("Error while processing the image: " + e.getMessage());
            e.printStackTrace();
        }
    }
}