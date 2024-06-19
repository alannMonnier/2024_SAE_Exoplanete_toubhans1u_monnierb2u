import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class KMeans implements Distance{

    // Nombre de groupes
    private int ng;
    // Liste de données de points
    private double[][] donnees;
    // Liste contenant les centroides
    private double[][] centroids;
    // Liste contenant le tableau de chaque groupe de cluster
    private ArrayList<ArrayList<double[]>> groupes;
    // Liste contenant le tableau contenant les indices des groupes
    ArrayList<ArrayList<Integer>> groupIndices;


    public KMeans(int _ng, double[][] donnees){
        this.ng = _ng;
        this.donnees = donnees;
        this.centroids = new double[_ng][];
    }


    // Récupère le tableau de centroides
    public double[][] getCentroids() {
        return centroids;
    }

    // Méthode qui retourne une liste contenant chacune
    public ArrayList<ArrayList<Integer>> getGroupIndices() {
        return groupIndices;
    }

    // Lance l'algorithme de kMeans
    public void kMeans(){
        Random random = new Random();
        // Initialisation des centroïdes
        for(int i=0; i<ng; i++){
            // Choisis un centroide aléatoires présente dans la liste de données
            this.centroids[i] = this.donnees[(random.nextInt(this.donnees.length))];
        }


        // Initialise le tableau d'indice des groupes
        groupIndices = new ArrayList<>(ng);
        for (int i = 0; i < ng; i++) {
            groupIndices.add(new ArrayList<>());
        }


        // Boucle principale
        boolean fini = false;
        while(!fini){
            fini = true;
            // Initialisation des groupes
            groupes = new ArrayList<>(ng);
            for(int j=0; j<ng; j++){
                // Créer des groupes vides
                groupes.add(new ArrayList<>());
            }

            int indice = 0;
            // Construction des groupes
            for(double[] d : this.donnees){
                  // Récupère l'indice du centroide le plus de la donnée d
                  int k = this.indiceCentroidePlusProche(d);
                  // Ajoute la donnée auw groupes k
                  groupes.get(k).add(d);

                // Supprime l'indice s'il est présent dans tous les groupes d'indices
                for(ArrayList<Integer> liste : groupIndices){
                    System.out.println(liste);
                    for(int val=0; val < liste.size(); val++){
                        if(liste.get(val) == indice){
                            liste.remove(liste.get(val));
                        }
                    }
                }
                // Ajoute l'indice au groupe
                groupIndices.get(k).add(indice);
                indice++;
            }


            // Mise à jour des centroïdes
            for(int l=0; l < ng; l++){
                // Si un groupe n'a pas été déjà créé on ne le mets pas à jour
                if(groupes.get(l).isEmpty()) continue;

                // Calcul le nouveau centroïde
                double[] centroid = barycentre(groupes.get(l));
                // Condition pour savoir si on reboucle pour réutiliser l'algorithme
                boolean isEqualCentroidX = (this.centroids[l][0] == centroid[0]);
                boolean isEqualCentroidY = (this.centroids[l][1] == centroid[1]);
                int long1 = this.centroids[l].length;
                int long2 = centroid.length;

                if(!(isEqualCentroidX || isEqualCentroidY || (long1 != long2)  )){
                    // Change la valeur du centroide avec la nouvelle valeur
                    this.centroids[l] = centroid;
                    fini = false;
                }
            }

        }
    }

    // Récupère l'indice du centroide le plus proche de la donnée courante
    public int indiceCentroidePlusProche(double[] donnee){
        int indice = 0;
        double distanceMin = Double.MAX_VALUE;

        for(int ind=0; ind < this.centroids.length; ind++){
            double[][] tab = {donnee, centroids[ind]};
            double distance = this.calculerDistance(tab)[0];
            // Si valeur trouvé est plus petit que la distance minimale précédente
            if(distance < distanceMin){
                // Modifie la distance minimale
                distanceMin = distance;
                indice = ind;
            }
        }
        return indice;
    }

    // Calcul la distance entre deux points grâce à leur coordonnées x et y
    @Override
    public int[] calculerDistance(double[][] tab) {
        double result = 0;
        for (int i = 0; i < tab[0].length; i++) {
            // Distance entre deux points
            result += Math.pow((tab[0][i] - tab[1][i]), 2);
        }
        return new int[]{(int) Math.sqrt(result)};
    }



    // Recalcule la valeur du  barycentre
    public double[] barycentre(ArrayList<double[]> groupe){
        double[] centroid = new double[groupe.get(groupe.size()-1).length];
        // Fais une moyenne
        for(double[] point : groupe){
            for(int i=0; i<centroid.length; i++){
                centroid[i] += point[i];
            }
        }
        for(int i=0; i<centroid.length; i++){
            centroid[i] /= groupe.size();
        }
        return centroid;
    }


    // Main pour tester l'algorithme KMeans
    public static void main(String[] args) throws IOException {

        //double[][] data = {{1, 2}, {5, 4}, {2, 6}, {6, 6}, {3, 3}, {6, 1}};
        double[][] data = {{2, 10}, {2, 5}, {8, 4}, {5, 8}, {7, 5}, {6, 4}, {1, 2}, {4, 9}};


        KMeans kmeans = new KMeans(3, data);
        kmeans.kMeans();

        double[][] centroids = kmeans.getCentroids();
        double moyX = 0;
        double moyY = 0;
        for (double[] centroid : centroids) {
            moyX += centroid[0];
            moyY += centroid[1];
            System.out.println("Centroïde: " + Arrays.toString(centroid));
        }

        // Affiche l'indice de la données qui appartient à telle groupe
        ArrayList<ArrayList<Integer>> groupIndices = kmeans.getGroupIndices();
        for (int i = 0; i < groupIndices.size(); i++) {
            System.out.println("Groupe " + i + ": " + groupIndices.get(i));
        }
    }
}