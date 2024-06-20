import TP_Prepa_SAE.NormeRedmean;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DbscanTiers implements ClusteringInterface{


    private double eps;
    private int minPts;
    private double[][] data;
    private int[] marques;

    public DbscanTiers(double eps, int minPts){
        this.eps = eps;
        this.minPts = minPts;
    }




    @Override
    public int[] cluster(double[][] data) {
        this.data = data;
        int cluster = 0;

        marques = new int[data.length];
        // 0 si pas traité -1 si n'appartient pas au cluster
        // Autre valeur correspond numéro de son cluster
        for(int i=0; i < data.length; i++){
            // Si le point n'a pas été traitée
            if(marques[i] == 0){
                ArrayList<Integer> v = this.regionQuery(i);
                if(v.size() >= minPts){
                    cluster++;
                    this.expandCluster(i, v, cluster);
                }
                else{
                    // -1 si appartient pas
                    marques[i] = -1;
                }
            }
        }
        return marques;
    }

    public ArrayList<Integer> regionQuery(int indice){
        ArrayList<Integer> v = new ArrayList<>();
        for(int i=0; i < data.length; i++){
            if(i != indice && this.calculerDistance(indice, i) <= eps){
                v.add(i);
            }
        }
        return v;
    }


    public double calculerDistance(int a, int b){
        NormeRedmean normeRedmean = new NormeRedmean();
        Color colorA = new Color((int) data[a][0], (int) data[a][1], (int) data[a][2]);
        Color colorB = new Color((int) data[b][0], (int) data[b][1], (int) data[b][2]);
        return normeRedmean.distanceCouleur(colorA, colorB);
    }


    public void expandCluster(int indice, ArrayList<Integer> v, int cluster){
        cluster += indice;
        for(int i=0; i < v.size(); i++){
            // Si le point n'a pas été traitée
            if(marques[i] == 0){
                marques[i]  = indice;
                ArrayList<Integer> vi = this.regionQuery(i);
                if(v.size() > minPts){
                    v.addAll(vi);
                }
            }
            if(marques[i] == -1){
                marques[i] = cluster;
            }
        }
    }

    public static void main(String[] args) {
        double[][] data = {
                {255, 0, 0}, {254, 0, 0}, {253, 0, 0},
                {0, 255, 0}, {0, 254, 0}, {0, 253, 0},
                {0, 0, 255}, {0, 0, 254}, {0, 0, 253},
                {128, 128, 128}, {127, 127, 127}, {126, 126, 126}
        };

        DbscanTiers dbscan = new DbscanTiers(2.0, 2);
        int[] result = dbscan.cluster(data);

        for (int label : result) {
            System.out.println(label);
        }
    }
}
