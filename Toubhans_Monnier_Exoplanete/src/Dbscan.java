import java.util.ArrayList;
import java.util.List;

public class Dbscan implements ClusteringInterface {
    private double eps;
    private int minPts;

    public Dbscan(double eps, int minPts) {
        this.eps = eps;
        this.minPts = minPts;
    }

    @Override
    public int[] cluster(double[][] data) {
        int N = data.length;
        int[] clusterLabels = new int[N];
        int clusterId = 0;

        for (int i = 0; i < N; i++) {
            if (clusterLabels[i] == 0) { // Not yet visited
                List<Integer> neighbors = regionQuery(data, i);
                if (neighbors.size() >= minPts) {
                    clusterId++;
                    expandCluster(data, clusterLabels, i, neighbors, clusterId);
                } else {
                    clusterLabels[i] = -1; // Noise
                }
            }
        }

        return clusterLabels;
    }

    private void expandCluster(double[][] data, int[] clusterLabels, int pointIdx, List<Integer> neighbors, int clusterId) {
        clusterLabels[pointIdx] = clusterId;

        int index = 0;
        while (index < neighbors.size()) {
            int neighborIdx = neighbors.get(index);

            if (clusterLabels[neighborIdx] == 0) { // Not yet visited
                clusterLabels[neighborIdx] = clusterId;

                List<Integer> newNeighbors = regionQuery(data, neighborIdx);
                if (newNeighbors.size() >= minPts) {
                    neighbors.addAll(newNeighbors);
                }
            }

            if (clusterLabels[neighborIdx] == -1) { // Noise
                clusterLabels[neighborIdx] = clusterId;
            }

            index++;
        }
    }

    private List<Integer> regionQuery(double[][] data, int pointIdx) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (colorDistance(data[pointIdx], data[i]) <= eps) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    private double colorDistance(double[] color1, double[] color2) {
        double sum = 0;
        for (int i = 0; i < color1.length; i++) {
            double diff = color1[i] - color2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
