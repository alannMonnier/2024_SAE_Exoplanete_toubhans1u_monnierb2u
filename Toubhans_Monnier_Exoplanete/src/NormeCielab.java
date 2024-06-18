import java.awt.Color;

public class NormeCielab  implements NormeCouleurs{

    public double distanceCouleur(Color c1, Color c2){    
        // Convertion des couleurs
        int[] lab1 = Convertion.rgb2lab(c1.getRed(), c1.getGreen(), c1.getBlue());
        int[] lab2 = Convertion.rgb2lab(c2.getRed(), c2.getGreen(), c2.getBlue());

        double partG = Math.pow((lab2[0] - lab1[0]), 2);
        double partC = Math.pow((lab2[1] - lab1[1]), 2);
        double partD = Math.pow((lab2[2] - lab1[2]), 2);
        return Math.sqrt(partG + partC + partD);
    } 
    
    public static void main(String[] args) {
        Color c1 = new Color(128, 128, 128);
        Color c2 = new Color(150, 150, 150);
        NormeCielab normeCielab = new NormeCielab();
        System.out.println(normeCielab.distanceCouleur(c1, c2));
    }
}
