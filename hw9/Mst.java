import java.util.*;
import java.lang.*;
import java.io.*;
import edu.princeton.cs.algs4.*;

public class Mst{
    private double length = 0;

    public Mst(Point2D[] points) {
        int[] groups = new int[points.length];
        for(int i = 0; i < points.length; i++) {
            groups[i] = -1;
        }

        Edge[] edges = new Edge[points.length * (points.length - 1) / 2];

        int edgeNum = 0;
        for(int i = 0; i < points.length; i++) {
            for(int j = i + 1; j < points.length; j++) {
                edges[edgeNum] = new Edge(points[i], points[j], i, j);
                edgeNum++;
            }
        }
        Arrays.sort(edges, edges[0].comparator());

        int count = 0;
        int group = 0;
        for (int i = 0; i < edges.length; i++) {
            Edge shortest = edges[i];
            int indexOne = shortest.I();
            int indexTwo = shortest.J();
            if (groups[indexOne] == -1) {
                if (groups[indexTwo] == -1) {
                    groups[indexOne] = group;
                    groups[indexTwo] = group;
                    group++;
                } else {
                    groups[indexOne] = groups[indexTwo];
                }
                this.length += shortest.W();
                count++;
            } else if (groups[indexTwo] == -1) {
                groups[indexTwo] = groups[indexOne];
                this.length += shortest.W();
                count++;
            } else if (groups[indexOne] != groups[indexTwo]) {
                int preserve = Math.min(groups[indexOne], groups[indexTwo]);
                int delete = Math.max(groups[indexOne], groups[indexTwo]);

                for (int j = 0; j < groups.length; j++) {
                    if (groups[j] == delete) groups[j] = preserve;
                }
                this.length += shortest.W();
                count++;
            }
        }
    }

    public double getAnswer() {return this.length; }

    public class Edge implements Comparable<Edge> {
        private int i, j;
        private double weight;

        public Edge(Point2D pI, Point2D pJ, int i, int j) {
            this.i = i;
            this.j = j;

            this.weight = pI.distanceTo(pJ);
        }

        public int compareTo(Edge other) {
            if (this.weight < other.W())       return -1;
            else if (this.weight == other.W()) return 0;
            else                               return 1;
        }

        public int I() { return this.i; }
        public int J() { return this.j; }
        public double W() { return this.weight; }

        public Comparator<Edge> comparator() { return new EdgeComparator(); }
        private class EdgeComparator implements Comparator<Edge> {
            public int compare(Edge one, Edge other) {
                return one.compareTo(other);
            }
        }        
    }

    public static void main(String[] args) throws Exception{
        // read file from args[0] in Java 7 style
        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))){
            String lines = br.readLine();

            int num = Integer.parseInt(lines);
            String[] readsArray = new String[num];
            for(int i = 0; i < num; i++) {
                readsArray[i] = (String) br.readLine();
            }

            br.close();

            Point2D[] points = new Point2D[num];
            for(int i = 0; i < num; i++) {
                String lineContent = readsArray[i];
                String[] parts = lineContent.split(" ");
                double x = Double.valueOf(parts[0]);
                double y = Double.valueOf(parts[1]);

                points[i] = new Point2D(x, y);
            }

            Mst mst = new Mst(points);
            double path = mst.getAnswer();

            System.out.printf("%5.5f\n", path);
        }
    }
}
