import java.io.IOException;

public class MainDbscan {
    public static void main(String[] args) {
        try {
            // Charger l'image et convertir les couleurs en points
            ImageToColors imageToColors = new ImageToColors("Toubhans_Monnier_Exoplanete/images_flou/Planete 1_copie.jpg");
            double[][] colors = imageToColors.getColors();

            // Initialiser DBSCAN
            Dbscan dbscan = new Dbscan(10.0, 5);

            // Effectuer le clustering
            int[] clusters = dbscan.cluster(colors);

            // Afficher les clusters
            int height = imageToColors.getHeight();
            int width = imageToColors.getWidth();
            System.out.println("[");
            for (int i = 0; i < clusters.length; i++) {
                if (i % width == 0 && i != 0) {
                    System.out.println();
                }
                System.out.print(clusters[i] + ", ");
            }
            System.out.println("]");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

