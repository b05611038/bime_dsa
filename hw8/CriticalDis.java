import java.util.*;
import edu.princeton.cs.algs4.*;

public class CriticalDis{
    private static Point2D[] points;
    private static Vertex[] v;
    private static Edge[] edges;
    private static int lefted = -1, righted = -1;

    private static class Edge implements Comparable<Edge> {
        public int i;
        public int j;
        public double dis;

        public Edge(int i, int j) {
            this.i = i;
            this.j = j;
            double dx = (points[i].x() - points[j].x());
            double dy = (points[i].y() - points[j].y());
            this.dis = dx * dx + dy * dy; 
        }

        public int compareTo(Edge other) {
            if (dis < other.dis)       return -1;
            else if (dis == other.dis) return 0;
            else                       return 1;
        }

        public Comparator<Edge> comparator() { return new EdgeComparator(); }

        private class EdgeComparator implements Comparator<Edge> {
            @Override
            public int compare(Edge one, Edge other) {
                if (one.dis < other.dis)       return -1;
                else if (one.dis == other.dis) return 0;
                else                           return 1;
            }
        }
    }

    private static class Vertex {
        public double spend;
        public ArrayList<Edge> edges;

        public Vertex() {
            this.spend = Double.POSITIVE_INFINITY;
            edges = new ArrayList<Edge>();
        }
    }

    public static void build(int index, double d) {
        if (d > 0 && v[index].spend <= d) return;
        if (v[righted].spend <= d) return;

        v[index].spend = d;
        for (int i = 0; i < v[index].edges.size(); i++) {
            build(v[index].edges.get(i).j, Math.max(v[index].edges.get(i).dis, v[index].spend));
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        //store the first integral represent point numbers
        int readCount = in.readInt();

        double minSum = Double.POSITIVE_INFINITY, maxSum = 0;
        points = new Point2D[readCount];
        for(int i = 0; i < readCount; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            points[i] = new Point2D(x, y);

            if (minSum > x + y) {
                minSum = x + y;
                lefted = i;
            }
            if (maxSum < x + y) {
                maxSum = x + y;
                righted = i;
            }
        }

        for (int i = 0; i < readCount; i++) {
            if (i == lefted || i == righted) continue;
            if (points[i].x() <= points[lefted].x() || points[i].y() <= points[lefted].y())
                points[i] = null;
            else if (points[i].x() >= points[righted].x() || points[i].y() >= points[righted].y())
                points[i] = null;
        }

        int edgeNum = 0;
        v = new Vertex[readCount];
        for (int i = 0; i < readCount; i++) {
            if (points[i] == null) continue;
            v[i] = new Vertex();

            for (int j = 0; j < readCount; j++) {
                if (i == j) continue;
                if (points[j] == null) continue;
                if (points[i].x() < points[j].x() && points[i].y() < points[j].y()) {
                    v[i].edges.add(new Edge(i, j));
                    edgeNum++;
                }
            }
        }

        build(lefted, -1);
        System.out.printf("%5.5f\n", Math.sqrt(v[righted].spend));
    }
}
