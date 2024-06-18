import java.awt.Color;

public class Norme implements NormeCouleurs{

    @Override
    public double distanceCouleur(Color c1, Color c2) {
        int[] lab1 = Convertion.rgb2lab(c1.getRed(), c1.getGreen(), c1.getBlue());
        int[] lab2 = Convertion.rgb2lab(c2.getRed(), c2.getGreen(), c2.getBlue());

        double deltaL = (lab1[0] - lab2[0]);
        double C1 = Math.sqrt(Math.pow(lab1[1],2) + Math.pow(lab2[1],2));
        double C2 = Math.sqrt(Math.pow(lab1[1],2) + Math.pow(lab2[1],2));
        double deltaC = (C1 - C2);
        double partGH = Math.pow(lab1[1] - lab2[1],2);
        double partCH = Math.pow(lab1[2] - lab2[2],2);
        double deltaH = Math.sqrt(partCH + partGH - Math.pow(deltaC,2));
        double SC = 1 + 0.045 * C1;
        double SH = 1 + 0.015 * C1;
        return Math.sqrt(Math.pow((deltaL/1),2) + Math.pow((deltaC/SC),2) + Math.pow((deltaH/SH),2));
    }


    public static void main(String[] args) {
        Color c1 = new Color(128, 128, 128);
        Color c2 = new Color(150, 150, 150);
        Norme norme = new Norme();
        System.out.println(norme.distanceCouleur(c1, c2));
    }
    
}
