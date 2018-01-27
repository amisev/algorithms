import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments;
    /**
     *
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (Point point: points) if (point == null) throw new NullPointerException();

        Point[] aux = Arrays.copyOf(points, points.length);
        // check for duplicate points
        for (int i = 1; i < aux.length; i++) {
            if (aux[i].compareTo(aux[i-1]) == 0) throw new IllegalArgumentException();
        }
        Arrays.sort(aux);

        this.segments = new ArrayList<> ();

        for (int p = 0; p < aux.length; p++) {
            Double[] slopes = new Double[aux.length - p - 1];
            for (int q = p + 1; q < aux.length; q++) {
                slopes[q - p - 1] = aux[p].slopeTo(aux[q]);
            }
            Integer[] ind = new Integer[slopes.length];
            for (int i = 0; i < ind.length; i++) {
                ind[i] = i;
            }

            ArrayList<Integer> segmentInd = new ArrayList<>();
            sort(slopes, ind, segmentInd);
            for (Integer i: segmentInd) {
                LineSegment segment = new LineSegment(aux[p], aux[p + 1 + i]);
                segments.add(segment);
            }
        }
    }

    /**
     *
     * @return
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     *
     * @return
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    private static void sort(Comparable[] a, Integer[] ind, ArrayList<Integer> segmentInd) {
        StdRandom.shuffle(ind);
        sort(a, ind, segmentInd, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, Integer[] ind, ArrayList<Integer> segmentInd, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[ind[lo]];
        while (i <= gt) {
            int cmp = a[ind[i]].compareTo(v);
            if (cmp < 0) exch(ind, lt++, i++);
            else if (cmp > 0) exch(ind, i, gt--);
            else i++;
        }
        if ((gt - lt) >= 2) {
            Arrays.sort(ind, lt, gt + 1);
            segmentInd.add(ind[gt]);
        }
        sort(a, ind, segmentInd, lo, lt - 1);
        sort(a, ind, segmentInd, gt + 1, hi);
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    /*
    (8564, 5170) -> (19405, 5170)
    (2117, 7686) -> (19906, 7686)
    (3695, 8555) -> (18899, 8555)
    (5325, 9050) -> (18800, 9050)
    (3895, 13147) -> (19888, 13147)
     */
}
