import TP_Prepa_SAE.NormeRedmean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe qui implémente l'algorithme dbscan
 */

public class DbscanBis implements ClusteringInterface{
    private BufferedImage image;
    private int hauteur;
    private int largeur;
    private int eps;
    private int min;
    public DbscanBis(String chemin, int eps, int min) throws IOException {
        this.image = ImageIO.read(new File(chemin));
        this.hauteur = image.getHeight();
        this.largeur = image.getWidth();
        this.eps = eps;
        this.min = min;
    }

    /**
     *
     * @param data : tableau Nobjet * Ncarac, obtenu par la méthode pixelToData
     * @return : un tableau de cluster
     */

    public int[] cluster(double[][] data){

        int[] cluster = new int[data.length];
        /**
        for(int i = 0; i < data.length; i++){
             //if(){
                ArrayList<Integer> voisinIndex = regionQuery(data, this.eps);
                if(voisinIndex.size() >= this.min){
                   // expandCluster();
                }
            } else {
                cluster[i] = -1;
            }
        }*/
        return cluster;
    }

    /**
     *
     * @param data : tableau Nobjet * Ncarac, obtenu par la méthode pixelToData
     * @param index : un élement dans data
     * @param voisin :
     */
    /**
    public void expandCluster(double[][] data, ArrayList<Integer> voisins, int index, int[] cluster){
        cluster[]
        for(int voisin :  voisins){
            if(){
                voisin = regionQuery(voisin, this.eps);
            }
        }
    }*/

    /**
     *
     * @param data : tableau Nobjet * Ncarac, obtenu par la méthode pixelToData
     * @param index : un élement dans data
     * @return : la liste de tous les voins de l'élément index dans le tableau à deux dimensions data
     */
    public ArrayList<Integer> regionQuery(double[][] data, int index){
        ArrayList<Integer> voisin = new ArrayList<>();
        int length = data.length;
        for(int i = 0; i < length; i++){
            if(index != i && calculerDistance(i, index, data) <= this.eps){
                voisin.add(i);
            }
        }
        return voisin;
    }

    /**
     * méthode permettant de calculer la distance entre deux couleur dans l'espace RGB
     * @return : distance entre 2 couleurs
     */
    public double calculerDistance(int a, int b, double[][] data){
        NormeRedmean normeRedmean = new NormeRedmean();
        Color colorA = new Color((int) data[a][0], (int) data[a][1], (int) data[a][2]);
        Color colorB = new Color((int) data[b][0], (int) data[b][1], (int) data[b][2]);
        return normeRedmean.distanceCouleur(colorA, colorB);
    }

    /**
     * méthode permettant de convertir chaque pixel en un élément du tableau Nobjet * Ncarac on aura ici [indexPixel][3] ou [3] représente les couleurs RGB
     * @return : le tableau de donnée
     * int blue = color & 0xff;
     * int green = (color & 0xff00) >> 8;
     * int red = (color & 0xff0000) >> 16;
     */
    public double[][] pixelToData(){
        double[][] res = new double[hauteur * largeur][3];
        int index = 0;
        // l'ordre de l'index dans corespond au parcour d'abord de la hauteur puis de la largeur
        for(int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                int rgb = image.getRGB(i,j);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                res[index][0] = red;
                res[index][1] = green;
                res[index][2] = blue;
                index++;
            }
        }
        return res;
    }
}
