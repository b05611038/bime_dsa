import java.util.*;
import edu.princeton.cs.algs4.*;

public class FastCollinearPoints {
    private Point[] p;
    private int num;
    private int successNum = 0;
    private ArrayList<LineSegment> segment = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        this.p = points;
        this.num = points.length;

        //sort the point first
        boolean exceptionFlag = false;

        Insertion.sort(this.p);

        for (int i = 0; i < this.p.length - 1; i++) {
            if (i < this.p.length - 1) {
                if (this.p[i].compareTo(this.p[i + 1]) == 0)
                    exceptionFlag = true;
            }
        }

        if (exceptionFlag)
            throw new java.lang.IllegalArgumentException();

	ArrayList<Point> successPoint = new ArrayList<Point>();
        ArrayList<Double> successSlope = new ArrayList<Double>();
        ArrayList<Double> slope;
        ArrayList<Integer> slopeCount;
        Point checkPoint;
        double checkSlope;
        boolean okFlag = true;

        //scale each point
        for (int i = 0; i < this.p.length; i++) {
            //initial the record array in new iter
            slope = new ArrayList<Double>();
            slopeCount = new ArrayList<Integer>();
            for (int j = i + 1; j < this.p.length; j++) {
                double slopeTemp = this.p[i].slopeTo(this.p[j]);

                if (slope.isEmpty()) {
                    slope.add(slopeTemp);
                    slopeCount.add(1);
                } else {
                    for (int k = slope.size() - 1; k >= 0; k--) {
                        if (slope.get(k) == slopeTemp) {
                            //the slope appears repeatly
                            int countTemp = slopeCount.get(k);
                            countTemp++;
                            slopeCount.add(k, countTemp);
                            break;
                        }

                        if (k == 0) {
                            //the slope didn't appear
                            slope.add(slopeTemp);
                            slopeCount.add(1);
                        }
                    }
                }
            }

            for (int j = 0; j < slope.size(); j++) {
                //to check the slope count of the slope record
                if (slopeCount.get(j) >= 3) {
                    //in this part we have certained that we have line segment
                    if (this.successNum == 0) {
                        //first time success
                        successSlope.add(slope.get(j));
                        for (int k = this.p.length - 1; k > 0; k--) {
                            if (this.p[i].slopeTo(this.p[k]) == slope.get(j)) {
                                //get the point and segment
                                successPoint.add(this.p[k]);
                                this.segment.add(new LineSegment(this.p[i], this.p[k]));
                                this.successNum++;
                                break;
                            }
                        }
                    } else {
                        //should contain the check part
                        checkSlope = slope.get(j);
                        checkPoint = this.p[this.p.length - 1];
                        for (int k = this.p.length - 1; k >= 0; k--) {
                            //search the last point of line segment
                            if (this.p[i].slopeTo(this.p[k]) == slope.get(j)) {
                                checkPoint = this.p[k];
                                break;
                            }
                        }
                        for (int k = 0; k < this.successNum; k++) {
                                if (successPoint.get(k).equals(checkPoint) && successSlope.get(k) == checkSlope)
                                    okFlag = false;
                                    break;
                        }
                        if(okFlag) {
                            //the segment didn't repeatly record
                            successPoint.add(checkPoint);
                            successSlope.add(checkSlope);
                            this.segment.add(new LineSegment(this.p[i], checkPoint));
                            this.successNum++;
                        }
                        okFlag = true;//reset the flag
                    }
                } //else do nothing
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return this.successNum;
    } 

    public LineSegment[] segments() {
        LineSegment[] segments;

        segments = new LineSegment[this.successNum];
        for (int i = 0; i < this.segment.size(); i++) {
            segments[i] = this.segment.get(i);
        }
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();

            points[i] = new Point(x, y);
        }

        //print the line segments
        FastCollinearPoints collinear;
        try {
            if (n == 0)
                throw new java.lang.IllegalArgumentException();
            collinear = new FastCollinearPoints(points);
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.lang.IllegalArgumentException();
        }

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}
