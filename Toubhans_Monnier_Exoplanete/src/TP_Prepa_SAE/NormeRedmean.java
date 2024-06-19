package TP_Prepa_SAE;

import java.awt.Color;

public class NormeRedmean implements NormeCouleurs{


    
    public double distanceCouleur(Color c1, Color c2) {
        double deltaR = c1.getRed() - c2.getRed();
        double deltaG = c1.getGreen() - c2.getGreen();
        double deltaB = c1.getBlue() - c2.getBlue();
        double r = (c1.getRed() + c2.getRed())/2;        
        return Math.sqrt((2+(r/256)) * Math.pow(deltaR,2) + 4*Math.pow(deltaG,2) +(2+( (255-r)/256 )) * Math.pow(deltaB, 2));
    }


    public static void main(String[] args) {
        Color c1 = new Color(128, 128, 128);
        Color c2 = new Color(150, 150, 150);
        NormeRedmean normeRedmean = new NormeRedmean();
        System.out.println(normeRedmean.distanceCouleur(c1, c2));
    }
    
}
