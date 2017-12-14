import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    public Deque() {
        first = last = null;
    }

    public boolean isEmpty() {
        return size == 0;
     }

    public int size(){
        return size;
    }
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

        if (isEmpty()) {
            last = first;
        } 

        size++;
    }
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }

        size++;
    }
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } 

        Item item = first.item;

        if (size() == 1) {
            last = first = null;
        } else {
            first = first.next;
        }

        size--;
        return item;
    }
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } 

        Item item = last.item;
        Node current = first;

        if (size() == 1) {
            current = current.next;
            current = null;
            last = first = null;
        } else {
            while (current.next.next != null) {
                current = current.next;
            }
            last = current;
            last.next = null;
        }

        size--;
        return item;
    }
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> deck = new Deque<>();

        deck.addLast("item1");
        deck.removeFirst();
        // deck.addFirst("item2");
        // deck.addFirst("item3");
        // deck.addLast("item4");
        
        for (String n: deck)
            System.out.println(n);

        /*
        System.out.println(deck.first.item);
        System.out.println(deck.last.item);


        String item1 = deck.removeLast();
        System.out.println(item1);

        String item11 = deck.removeLast();
        System.out.println(item11);
        
        String item2 = deck.removeFirst();
        System.out.println(item2);
        */
    }
}
