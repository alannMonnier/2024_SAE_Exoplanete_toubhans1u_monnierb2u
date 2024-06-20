import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class KMeans {
    private int ng;
    private double[][] donnees;
    private double[][] centroids;
    private int[] clusterAssignments;
    private double[][] newCentroids;
    private int[] clusterSizes;

    public KMeans(int _ng, double[][] donnees) {
        this.ng = _ng;
        this.donnees = donnees;
        this.centroids = new double[_ng][donnees[0].length];
        this.clusterAssignments = new int[donnees.length];
        this.newCentroids = new double[_ng][donnees[0].length];
        this.clusterSizes = new int[_ng];
    }

    public double[][] getCentroids() {
        return centroids;
    }

    public int[] getClusterAssignments() {
        return clusterAssignments;
    }

    public void kMeans() {
        Random random = new Random();

        // Initialisation des centroides avec des données aléatoires
        for (int i = 0; i < ng; i++) {
            centroids[i] = Arrays.copyOf(donnees[random.nextInt(donnees.length)], donnees[0].length);
        }

        boolean fini = false;
        int iteration = 0;
        while (!fini && iteration < 1000) {
            fini = true;

            // Réinitialiser les nouveaux centroides et les tailles de clusters
            for (int i = 0; i < ng; i++) {
                Arrays.fill(newCentroids[i], 0.0);
                clusterSizes[i] = 0;
            }

            // Attribution des points de données aux centroides les plus proches
            for (int i = 0; i < donnees.length; i++) {
                int nearestCentroid = indiceCentroidePlusProche(donnees[i]);
                clusterAssignments[i] = nearestCentroid;

                // Accumuler les coordonnées pour les nouveaux centroides
                for (int j = 0; j < donnees[i].length; j++) {
                    newCentroids[nearestCentroid][j] += donnees[i][j];
                }
                clusterSizes[nearestCentroid]++;
            }

            // Calculer les nouveaux centroides
            for (int i = 0; i < ng; i++) {
                if (clusterSizes[i] > 0) {
                    for (int j = 0; j < newCentroids[i].length; j++) {
                        newCentroids[i][j] /= clusterSizes[i];
                    }
                }
            }

            // Vérifier si les centroides ont changé
            for (int i = 0; i < ng; i++) {
                if (!estApproxEgal(centroids[i], newCentroids[i])) {
                    centroids[i] = Arrays.copyOf(newCentroids[i], newCentroids[i].length);
                    fini = false;
                }
            }
            iteration++;
        }
    }

    private int indiceCentroidePlusProche(double[] donnee) {
        int indice = 0;
        double distanceMin = Double.MAX_VALUE;

        for (int i = 0; i < centroids.length; i++) {
            double distance = calculerDistance(donnee, centroids[i]);
            if (distance < distanceMin) {
                distanceMin = distance;
                indice = i;
            }
        }
        return indice;
    }

    private double calculerDistance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            double diff = a[i] - b[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    private boolean estApproxEgal(double[] a, double[] b) {
        double tol = 1e-4;
        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i] - b[i]) > tol) {
                return false;
            }
        }
        return true;
    }

    public int[] cluster(double[][] data) {
        this.donnees = data;
        kMeans();
        return clusterAssignments;
    }
}
