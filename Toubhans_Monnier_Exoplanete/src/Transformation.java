import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Transformation {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Indiquez le chemin d'entrée et de sortie pour votre image");
            return;
        }

        String inputImagePath = args[0];
        String outputImagePath = args[1];
               
        Color[] couleurs = new Color[3];
        couleurs[0] = Color.ORANGE;
        couleurs[1] = Color.BLACK;
        couleurs[2] = Color.BLUE;
        

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
                    Color c = new Color(rgb[0], rgb[1], rgb[2]);

                    double min = 1000000000;
                    Color couleurFinale = new Color(0,0,0);

                    for(Color col : couleurs){
                        double redDifference = Math.pow(c.getRed() - col.getRed(), 2);
                        double greenDifference = Math.pow(c.getGreen() - col.getGreen(), 2);
                        double blueDifference = Math.pow(c.getBlue() - col.getBlue(), 2);
                        
                        double distance = redDifference + greenDifference + blueDifference;
                        if(min > distance){
                            min = distance;
                            couleurFinale = col;
                        }
                    }
                    

                    copiedImage.setRGB(x, y, couleurFinale.getRGB());
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