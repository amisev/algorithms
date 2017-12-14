import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            String line = StdIn.readString();
            rq.enqueue(line);
        }
        for (String s: rq)
            System.out.println(s);
    }
}
