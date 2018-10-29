import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y)
                return Double.NEGATIVE_INFINITY;
            else
                return Double.POSITIVE_INFINITY;
        } else {
            return (double)(that.y - this.y) / (that.x - this.x);
        }
    }

    public int compareTo(Point that) {
        if (that.y > this.y)
            return -1;
        else if (that.y == this.y) {
            if (that.x > this.x)
                return -1;
            else if (that.x == this.x)
                return 0;
            else
                return 1;
        } else
            return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new slopeComparator();
    }
    private class slopeComparator implements Comparator<Point> {
        public int compare(Point pointOne, Point pointTwo) {
            //Point.this references the current point object
            Point inMemory = Point.this;

            double dif = inMemory.slopeTo(pointOne) - inMemory.slopeTo(pointTwo);
            if (dif > 0)      return 1;
            else if (dif < 0) return -1;
            else              return 0;
        }
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
