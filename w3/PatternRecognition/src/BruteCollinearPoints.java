import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments;
    /**
     *
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (Point point: points) if (point == null) throw new NullPointerException();

        Point[] aux = Arrays.copyOf(points, points.length);
        // check for duplicate points
        for (int i = 1; i < aux.length; i++) {
            if (aux[i].compareTo(aux[i-1]) == 0) throw new IllegalArgumentException();
        }
        Arrays.sort(aux);

        this.segments = new ArrayList<> ();
        // n^4 complexity
        for (int p = 0; p < aux.length; p++) {
            for (int q = p + 1; q < aux.length; q++) {
                for (int r = q + 1; r < aux.length; r++) {
                    for (int s = r + 1; s < aux.length; s++) {
                        if (aux[p].slopeTo(aux[q]) == aux[p].slopeTo(aux[r]) &&  aux[p].slopeTo(aux[r]) == aux[p].slopeTo(aux[s])) {
                            LineSegment segment = new LineSegment(aux[p], aux[s]);
                            segments.add(segment);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public int numberOfSegments(){
        return segments.size();
    }

    /**
     *
     * @return
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
