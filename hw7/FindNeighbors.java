import java.util.*;
import edu.princeton.cs.algs4.*;

public class FindNeighbors{
    //depth % 2 = 0, compare x axis, depth % 2 = 1, compare y axis
    private final int dim = 2;
    private NDTreeNode root = null;
    private PriorityQueue<QueryPair> queryPriority = new PriorityQueue<QueryPair>();

    public void init(Point2D[] points){
        //initial of building 2-dimensional tree
        this.root = buildTree(this.root, points);
    }
    public NDTreeNode buildTree(NDTreeNode root, Point2D[] points) {
        //resursive function of bulding 2-dimensional tree
        for(int i = 0; i < points.length; i++) {
            root = insertNode(root, points[i], 0);
        }

        return root;
    }
    public NDTreeNode insertNode(NDTreeNode root, Point2D input, int depth) {
        if (root == null) {
            //if the node is empty
            return new NDTreeNode(input);
        }

        int compare = depth % this.dim;
        if (compare == 0) {
            if (root.getData().x() > input.x()) {
                //need to go to left subtree
                root.setLeft(insertNode(root.getLeft(), input, depth + 1));
            } else {
                root.setRight(insertNode(root.getRight(), input, depth + 1));
            }
        } else if (compare == 1) {
            if (root.getData().y() > input.y()) {
                root.setLeft(insertNode(root.getLeft(), input, depth + 1));
            } else {
                root.setRight(insertNode(root.getRight(), input, depth + 1));
            }
        }

        return root;
    }

    public class NDTreeNode{
        private Point2D data;
        private NDTreeNode left = null;
        private NDTreeNode right = null;

        public NDTreeNode(Point2D point) {
            this.data = point;
        }

        public Point2D getData() {
            return this.data;
        }
        public NDTreeNode getLeft() {
            return this.left;
        }
        public NDTreeNode getRight() {
            return this.right;
        }
        public void setLeft(NDTreeNode node) {
            this.left = node;
        }
        public void setRight(NDTreeNode node) {
            this.right = node;
        }
    }

    
    public Point2D[] query(Point2D point, int k){
        //return the query of the n th minimum distance points
        Point2D[] result = new Point2D[k];

        accessNode(this.root, point, 0);

        for(int i = 0; i < k; i++) {
            result[i] = this.queryPriority.remove().getPoint();
        }

        return result;
    }
    public void accessNode(NDTreeNode root, Point2D query, int depth){
        if (root == null) {
            return;
        }

        this.queryPriority.add(new QueryPair(root.getData(), root.getData().distanceTo(query)));

        int compare = depth % this.dim;
        if (compare == 0) {
            if (root.getData().x() > query.x()) {
                if (root.getRight() != null) {
                    this.queryPriority.add(new QueryPair(root.getRight().getData(), root.getRight().getData().distanceTo(query)));
                }
                accessNode(root.getLeft(), query, depth + 1);
            } else {
                if (root.getLeft() != null) {
                    this.queryPriority.add(new QueryPair(root.getLeft().getData(), root.getLeft().getData().distanceTo(query)));
                }
                accessNode(root.getRight(), query, depth + 1);
            }
        } else {
            if (root.getData().y() > query.y()) {
                if(root.getRight() != null) {
                    this.queryPriority.add(new QueryPair(root.getRight().getData(), root.getRight().getData().distanceTo(query)));
                }
                accessNode(root.getLeft(), query, depth + 1);
            } else {
                if (root.getLeft() != null) {
                    this.queryPriority.add(new QueryPair(root.getLeft().getData(), root.getLeft().getData().distanceTo(query)));
                }
                accessNode(root.getRight(), query, depth + 1);
            }
        }
    }

    public class QueryPair implements Comparable<QueryPair> {
        private final Point2D point;
        private final double distance;

        public QueryPair(Point2D point, double distance) {
            this.point = point;
            this.distance = distance;
        }

        public Point2D getPoint() {
            return this.point;
        }
        public double getDistance() {
            return this.distance;
        }

        public int compareTo(QueryPair outMemory) {
            QueryPair inMemory = QueryPair.this;

            if (outMemory.distance > inMemory.distance) { return -1; } 
            else if (outMemory.distance == inMemory.distance) {return 0; }
            else {return 1; }
        }

        public Comparator<QueryPair> queryPairOrder() {
            return new queryComparator();
        }
        private class queryComparator implements Comparator<QueryPair> {
            public int compare(QueryPair inMemory, QueryPair outMemory) {
                return inMemory.compareTo(outMemory);
            }
        }
    }
    

    public static void main(String[] args) {
        Point2D[] points = new Point2D[5];
        points[0] = new Point2D(3, 2);
        points[1] = new Point2D(5, 8);
        points[2] = new Point2D(7, 9);
        points[3] = new Point2D(1, 1);
        points[4] = new Point2D(0, 0);

        FindNeighbors fn = new FindNeighbors();
        fn.init(points);

        Point2D[] test = fn.query(new Point2D(0.1, 0.1), 2);

        for (int i = 0; i < 2; i++) {
            System.out.println(test[i].toString());
        }
    }
}
