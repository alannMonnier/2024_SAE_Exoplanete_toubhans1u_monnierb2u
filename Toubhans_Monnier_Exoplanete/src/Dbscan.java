import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Classe qui implémente l'algorithme DBSCAN
 */
public class Dbscan implements ClusteringInterface {

    private BufferedImage image;
    private boolean[][] visited;
    private int[][] clusters;
    private List<int[]> colors;
    private int largeur;
    private int hauteur;

    public Dbscan(String chemin) throws IOException {
        this.image = ImageIO.read(new File(chemin));
        this.largeur = image.getHeight();
        this.hauteur = image.getWidth();
        this.visited = new boolean[largeur][hauteur];
        this.clusters = new int[largeur][hauteur];
        this.colors = new ArrayList<>();
        generateColors();
    }

    public int[] cluster(double[][] data, int eps, int minPts) {
        int c = 0;

        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    List<int[]> neighbors = regionQuery(i, j, eps);
                    if (neighbors.size() >= minPts) {
                        c++;
                        expandCluster(i, j, neighbors, c, eps, minPts);
                    } else {
                        clusters[i][j] = -1; // Marquer comme bruit
                    }
                }
            }
        }

        return clustersToArray();
    }

    private void expandCluster(int x, int y, List<int[]> neighbors, int c, int eps, int minPts) {
        clusters[x][y] = c;
        List<int[]> seeds = new ArrayList<>(neighbors);

        for (int i = 0; i < seeds.size(); i++) {
            int[] point = seeds.get(i);
            int px = point[0];
            int py = point[1];

            if (!visited[px][py]) {
                visited[px][py] = true;
                List<int[]> result = regionQuery(px, py, eps);
                if (result.size() >= minPts) {
                    seeds.addAll(result);
                }
            }
            if (clusters[px][py] == 0) {
                clusters[px][py] = c;
            }
        }
    }

    private List<int[]> regionQuery(int x, int y, int eps) {
        List<int[]> neighbors = new ArrayList<>();
        int[] color = getColor(x, y);

        for (int i = Math.max(0, x - eps); i < Math.min(largeur, x + eps); i++) {
            for (int j = Math.max(0, y - eps); j < Math.min(hauteur, y + eps); j++) {
                if (distance(x, y, i, j) <= eps && similarColor(color, getColor(i, j))) {
                    neighbors.add(new int[]{i, j});
                }
            }
        }
        return neighbors;
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private int[] getColor(int x, int y) {
        int rgb = image.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return new int[]{r, g, b};
    }

    private boolean similarColor(int[] color1, int[] color2) {
        int seuil = 30;
        int diff = Math.abs(color1[0] - color2[0]) + Math.abs(color1[1] - color2[1]) + Math.abs(color1[2] - color2[2]);
        return diff < seuil;
    }

    private void generateColors() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            colors.add(new int[]{rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)});
        }
    }

    private int[] clustersToArray() {
        int[] clusterArray = new int[largeur * hauteur];
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                clusterArray[i * hauteur + j] = clusters[i][j];
            }
        }
        return clusterArray;
    }

    public void colorClusters() throws IOException {
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
                if (clusters[i][j] > 0) {
                    int clusterId = clusters[i][j];
                    int[] color = colors.get(clusterId % colors.size());
                    int rgb = (color[0] << 16) | (color[1] << 8) | color[2];
                    image.setRGB(i, j, rgb);
                } else if (clusters[i][j] == -1) {
                    image.setRGB(i, j, 0x000000);
                }
            }
        }

        ImageIO.write(image, "png", new File("dbscan_clusters.png"));
    }
}
