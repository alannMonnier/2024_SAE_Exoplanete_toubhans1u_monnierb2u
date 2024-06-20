import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ColorPaletteExtractor {

    private BufferedImage image;
    private BufferedImage whiteImage; // Image blanche pour dessiner les régions colorées
    private static final int MAX_COLORS = 8; // Nombre maximal de couleurs à extraire
    private static final int COLOR_THRESHOLD = 5; // Seuil de similarité des couleurs ajusté

    // Couleurs spécifiées pour la palette
    private static final Color[] specifiedColors = {
            Color.RED, Color.PINK, Color.ORANGE, Color.GREEN,
            new Color(128, 0, 128), new Color(139, 69, 19),
            Color.YELLOW, Color.BLUE
    };

    public ColorPaletteExtractor(String chemin) throws IOException {
        this.image = ImageIO.read(new File(chemin));
        this.whiteImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public ArrayList<Color> extractColors() {
        ArrayList<Color> extractedColors = new ArrayList<>();
        HashMap<Color, Integer> colorCounts = new HashMap<>();

        int width = image.getWidth();
        int height = image.getHeight();

        // Parcourir chaque pixel de l'image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);

                // Vérifier si une couleur similaire est déjà dans la palette extraite
                boolean foundSimilar = false;
                for (Color extractedColor : extractedColors) {
                    if (calculateColorDistance(color, extractedColor) < COLOR_THRESHOLD) {
                        colorCounts.put(extractedColor, colorCounts.getOrDefault(extractedColor, 0) + 1);
                        foundSimilar = true;
                        break;
                    }
                }

                if (!foundSimilar) {
                    // Ajouter la couleur à la palette extraite
                    extractedColors.add(color);
                    colorCounts.put(color, colorCounts.getOrDefault(color, 0) + 1);
                }
            }
        }

        // Sélectionner les MAX_COLORS couleurs les plus présentes
        extractedColors.sort((c1, c2) -> colorCounts.get(c2).compareTo(colorCounts.get(c1)));
        extractedColors = new ArrayList<>(extractedColors.subList(0, Math.min(MAX_COLORS, extractedColors.size())));

        // Colorier les régions correspondant aux couleurs extraites sur l'image blanche
        Graphics g = whiteImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, whiteImage.getWidth(), whiteImage.getHeight());

        int rectangleWidth = whiteImage.getWidth() / MAX_COLORS;
        for (int i = 0; i < Math.min(MAX_COLORS, extractedColors.size()); i++) {
            Color currentColor = extractedColors.get(i);
            g.setColor(currentColor);
            g.fillRect(i * rectangleWidth, 0, rectangleWidth, whiteImage.getHeight());
        }
        g.dispose();

        return extractedColors;
    }

    private int calculateColorDistance(Color c1, Color c2) {
        int redDiff = Math.abs(c1.getRed() - c2.getRed());
        int greenDiff = Math.abs(c1.getGreen() - c2.getGreen());
        int blueDiff = Math.abs(c1.getBlue() - c2.getBlue());
        return redDiff + greenDiff + blueDiff;
    }

    public static void main(String[] args) {
        try {
            ColorPaletteExtractor extractor = new ColorPaletteExtractor("Toubhans_Monnier_Exoplanete/images_flou/Planete_1_floute.jpg");
            ArrayList<Color> palette = extractor.extractColors();

            // Enregistrer l'image blanche avec les régions colorées
            File output = new File("output_image.png");
            ImageIO.write(extractor.whiteImage, "png", output);
            System.out.println("Image avec les régions colorées sauvegardée : " + output.getAbsolutePath());

            System.out.println("Palette de couleurs extraites :");
            for (Color color : palette) {
                System.out.println("Color: " + color);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
