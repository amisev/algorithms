import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;

        first = new Node();
        first.item = item;
        first.next = oldFirst;

        size++;
    }
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } 

        int i = StdRandom.uniform(size()); 
        int j = 0;

        Item item;
        Node current = first;

        if (i == 0) {
            item = current.item;
            first = first.next;
            current = null;
        } else if (i == size() - 1) {
            while (current.next.next != null) {
                current = current.next;
            }
            item = current.next.item;
            current.next = null;
        } else {
            while (j < i - 1) {
                j++;
                current = current.next;
            }
            item = current.next.item;
            current.next = current.next.next;
        }

        size--;
        return item;
    }

    private Item sample(int i) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } 

        int j = 0;

        Node current = first;
        while (j < i) {
            j++;
            current = current.next;
        }
        Item item = current.item;
        return item;
    }

    public Item sample() {
        int i = StdRandom.uniform(size()); 
        return sample(i);
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        // private Node current = first;
        private final int[] order;
        private int current_ = 0;

        public RandomizedQueueIterator() {
             order = new int[size()];
             for (int i = 0; i< size(); i++) {
                 order[i] = i;
             }
             StdRandom.shuffle(order);
        }

        public boolean hasNext() { return current_ < size(); }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = sample(order[current_++]);
            return item;
        }
    }
    public static void main(String[] args) {

        for (int i = 0; i<100; i++) {
            RandomizedQueue<String> rq = new RandomizedQueue<>();
            rq.enqueue("item1");
            rq.enqueue("item2");
            // rq.dequeue();
            rq.enqueue("item3");
            // rq.dequeue();
            rq.enqueue("item4");

            // rq.enqueue("item4");

            for (String n: rq) 
                System.out.println(n);
        }

        /*
        for (String n: rq) 
            System.out.println(n);
        */
    }
}
