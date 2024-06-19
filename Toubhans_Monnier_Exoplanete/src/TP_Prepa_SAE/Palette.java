package TP_Prepa_SAE;

import java.awt.Color;
import java.util.ArrayList;



public class Palette {
    
    private ArrayList<Color> couleurs;
    private NormeCouleurs distanceCouleur;

    public Palette(ArrayList<Color> couleurs, NormeCouleurs normeCouleurs){
        this.couleurs = couleurs;        
        this.distanceCouleur = normeCouleurs;
    }

   

    public Color getPlusProche(Color col){
        Color color = null;
        double minDist = 1000000;
        for(Color c : this.couleurs){
            double distance = this.distanceCouleur.distanceCouleur(c, col);
            if(distance < minDist){
                minDist = distance;
                color = c;
            }
        }
        return color;
    }

    public double distance(Color col1, Color col2){
        int distR = col1.getRed() - col2.getRed();
        int distG = col1.getGreen() - col2.getGreen();
        int distB = col1.getBlue() - col2.getBlue();
        return Math.sqrt(Math.pow(distR,2) * Math.pow(distG,2) * Math.pow(distB,2));
    }

    public static void main(String[] args) {
        Color c1 = new Color(0,0,0);
        Color c2 = new Color(128,128,128);
        Color c3 = new Color(0,128,0);
        Color c4 = new Color(0,128,128);
        Color c5 = new Color(0,0,128);

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(c1);
        colors.add(c2);
        colors.add(c3);
        colors.add(c4);
        colors.add(c5);

        Palette palette = new Palette(colors, new NormeRedmean());

        Color plusProche = palette.getPlusProche(new Color(150, 150, 150));
        System.out.println(plusProche.getRed());
        System.out.println(plusProche.getGreen());
        System.out.println(plusProche.getBlue());
    }
}
