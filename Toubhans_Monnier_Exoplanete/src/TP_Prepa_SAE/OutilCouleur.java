package TP_Prepa_SAE;

import java.awt.Color;

public class OutilCouleur {
    
    public static int[] getTabColor(int color){
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        return new int[]{red, green, blue};
    }


    public static void main(String[] args) {
        Color color = new Color(255, 32, 12);
        int[] tab_rgb = OutilCouleur.getTabColor(color.getRGB());
        for(int rgb : tab_rgb){
            System.out.println(rgb);
        }
    }
}
