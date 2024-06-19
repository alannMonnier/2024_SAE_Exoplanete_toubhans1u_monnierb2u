import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageComposante {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Indiquez le chemin d'entrée et de sortie pour votre image");
            return;
        }

        String inputImagePath = args[0];
        String outputImagePath = args[1];   
        String composante = args[2];   
        
        try {
            Image image = new Image();

            // On charge l'image
            BufferedImage originalImage = image.loadPicture(inputImagePath);


            // On copie les dimensions de l'image
            BufferedImage copiedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

            // Copier chaque pixel de l'image originale dans la nouvelle image
            for (int x = 0; x < originalImage.getWidth(); x++) {
                for (int y = 0; y < originalImage.getHeight(); y++) {
                    int color = originalImage.getRGB(x, y);
                    int cRB = 0;
                    if(composante.equals("green")){
                        cRB = color & 0xffff00;
                        //cRB = Math.round(color + (75/100) * (255-150));
                    }
                    else if(composante.equals("blue")){
                        cRB = color & 0x00ffff;
                        //cRB = Math.round(color + (75/100) * (255-150));
                    }
                    else{
                        cRB = color & 0xff00ff;
                        //cRB = Math.round(color + (75/100) * (255-150));
                    }                  
                    
                    copiedImage.setRGB(x, y, cRB);
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
