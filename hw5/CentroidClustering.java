import java.util.*;
import java.lang.*;
import java.io.*;

public class CentroidClustering {
    private int size;
    private ArrayList<ClusterPair> clusterPairs = new ArrayList<ClusterPair>();
    private List<List<Double>> distanceTable = new ArrayList<List<Double>>();

    public CentroidClustering(ClusterPair[] inputPair) {
        //structure, assign the variable we want
        int n = inputPair.length;
        for (int i = 0; i < n; i++) {
            this.clusterPairs.add(inputPair[i]);
            this.distanceTable.add(new ArrayList<Double>());
        }
        this.size = this.clusterPairs.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                //calculate all diatances and save it into table
                this.distanceTable.get(i).add(this.clusterPairs.get(i).distanceTo(this.clusterPairs.get(j)));
            }
        }
    }

    //data structure of clustering pair
    public static class ClusterPair implements Comparable<ClusterPair> {
        private final double x;
        private final double y;
        private final int num;

        public ClusterPair(double x, double y, int pointNum) {
            this.x = x;
            this.y = y;
            this.num = pointNum;
        }

        public double getX() {
            return this.x;
        }
        public double getY() {
            return this.y;
        }
        public int getN() {
            return this.num;
        }

        public double distanceTo(ClusterPair that) {
            return (double)(Math.sqrt(Math.pow((this.x - that.x), 2) + Math.pow((this.y - that.y), 2)));
        }

        public int compareTo(ClusterPair outMemory) {
            ClusterPair inMemory = ClusterPair.this;

            int dif = outMemory.num - inMemory.num;

            return dif;
        }

        public Comparator<ClusterPair> clusterPairOrder() {
            return new clusterComparator();
        }
        private class clusterComparator implements Comparator<ClusterPair> {
            public int compare(ClusterPair inMemory, ClusterPair outMemory) {
                //ClusterPair.this references the current ClusterPair object
                return inMemory.compareTo(outMemory);
            }
        }

        public void printCluster() {
            System.out.print(this.num);
            System.out.print(" ");
            System.out.print(String.format("%.2f", this.x));
            System.out.print(" ");
            System.out.print(String.format("%.2f", this.y));
            System.out.println("");
        }
    }

    public ClusterPair[] resultsOfClustering(int stopNum) {
        int stopFlag = stopNum;

        while (this.size > stopFlag) {
            //the processs of reduce cluster pair
            int minDistanceI = -1;
            int minDistanceJ = -1;
            double minDistance = Double.POSITIVE_INFINITY;
            for (int i = 0; i < this.distanceTable.size(); i++) {
                for (int j = 0; j < this.distanceTable.get(i).size(); j++) {
                    //get all the distance of all pair
                    if (distanceTable.get(i).get(j) < minDistance) {
                        minDistanceI = i;
                        minDistanceJ = j + i + 1;
                        minDistance = this.distanceTable.get(i).get(j);
                    }
                }
            }

            int mergeNum = this.clusterPairs.get(minDistanceI).getN() + this.clusterPairs.get(minDistanceJ).getN();
            double mergeX = (this.clusterPairs.get(minDistanceI).getX() * this.clusterPairs.get(minDistanceI).getN() + this.clusterPairs.get(minDistanceJ).getX() * this.clusterPairs.get(minDistanceJ).getN()) / mergeNum;
            double mergeY = (this.clusterPairs.get(minDistanceI).getY() * this.clusterPairs.get(minDistanceI).getN() + this.clusterPairs.get(minDistanceJ).getY() * this.clusterPairs.get(minDistanceJ).getN()) / mergeNum;

            ClusterPair newPair = new ClusterPair(mergeX, mergeY, mergeNum);

            //remove the old pair in formation from memory
            for (int i = 0; i < minDistanceJ; i++) {
                this.distanceTable.get(i).remove(minDistanceJ - i - 1);
            }
            this.distanceTable.remove(minDistanceJ);
            for (int i = 0; i < minDistanceI; i++) {
                this.distanceTable.get(i).remove(minDistanceI - i - 1);
            }
            this.distanceTable.remove(minDistanceI);
            this.clusterPairs.remove(minDistanceJ);
            this.clusterPairs.remove(minDistanceI);

            //update new pair distance
            this.clusterPairs.add(newPair);
            this.distanceTable.add(new ArrayList<Double>());
            for (int i = 0; i < (this.distanceTable.size() - 1); i++) {
                distanceTable.get(i).add(this.clusterPairs.get(i).distanceTo(this.clusterPairs.get(this.clusterPairs.size() - 1)));
            }

            this.size = clusterPairs.size();
        }

        ClusterPair[] returnPair = new ClusterPair[stopFlag];
        for (int i = 0; i < stopFlag; i++) {
            returnPair[i] = this.clusterPairs.get(i);
        }
        Arrays.sort(returnPair);

        return returnPair;
    }

    public static void main(String[] args) throws Exception {
        // read file from args[0] in Java 7 style
        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))){
            String lines = br.readLine();
            // store the first integer in variable readCount (number of reads)

            int readCount = Integer.parseInt(lines);
            String[] readsArray = new String[readCount];
            for (int i = 0; i < readCount; i++) {
                readsArray[i] = (String) br.readLine();
            }

            br.close();

            ClusterPair[] clustering = new ClusterPair[readCount];
            for (int i = 0; i < readCount; i++) {
                String lineContent = readsArray[i];

                String[] parts = lineContent.split(" ");
                double x = Double.valueOf(parts[0]);
                double y = Double.valueOf(parts[1]);

                clustering[i] = new ClusterPair(x, y, 1);
            }

            //process of clustering
            CentroidClustering cc = new CentroidClustering(clustering);
            ClusterPair[] results = cc.resultsOfClustering(3);
            //print the results
            for (int i = 0; i < results.length; i++) {
                results[i].printCluster();
            }
        } 
    }
}
