package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private class Node {

        private T item;
        private double priority;

        Node(T item, double p) {
            this.item = item;
            this.priority = p;
        }

        public T getItem() {
            return item;
        }

        public double getPriority() {
            return priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }

        public int compareTo(Node other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }
    }

    private int size;
    private ArrayList<Node> items;
    private HashMap<T, Integer> map;

    public ArrayHeapMinPQ() {
        size = 0;
        items = new ArrayList<>();
        items.add(null);
        map = new HashMap<>();
    }

    //Adds an item with the given priority value.
    public void add(T item, double priority) {

        if (contains(item)) {
            throw new IllegalArgumentException("Already in the PQ");
        }

        Node temp = new Node(item, priority);
        if (size == 0) {
            items.add(temp);
            size += 1;
            map.put(item, 1);
            return;
        }
        size += 1;
        int c = size;
        items.add(temp);
        map.put(item, size);
        while (c > 1 && priority < items.get(parent(c)).priority) {
            items.set(c, items.get(parent(c)));
            map.replace(items.get(parent(c)).getItem(), c);
            c /= 2;
        }
        items.set(c, temp);
        map.replace(item, c);
    }

    //Returns true if the PQ contains the given item.
    public boolean contains(T item) {
        return map.containsKey(item);
    }

    // Returns the minimum item. Throws NoSuchElementException if the PQ is empty.
    public T getSmallest() {

        if (size == 0) {
            throw new NoSuchElementException("There is no element here");
        }
        return items.get(1).getItem();
    }

    // Removes and returns the minimum item .
    public T removeSmallest() {

        if (size == 0) {
            throw new NoSuchElementException("There is no element here");
        } else if (size == 1) {
            size -= 1;
            map.remove(items.get(1).getItem());
            return items.remove(1).getItem();
        }

        T answer = items.get(1).getItem();
        map.replace(items.get(size).getItem(), 1);
        items.set(1, items.get(size));
        items.remove(size);
        size -= 1;

        int c = 1;
        double p = items.get(1).getPriority();
        while (c <= size / 2) {
            if (p < items.get(c * 2).getPriority() && (items.size() == c * 2 + 1
                    || p < items.get(c * 2 + 1).getPriority())) {
                break;
            } else if (c * 2 + 1 == items.size()) {
                if (p < items.get(c * 2).getPriority()) {
                    Node temp = items.get(c * 2);
                    items.set(c, temp);
                    items.set(c * 2, items.get(c));
                    map.replace(temp.getItem(), c);
                    map.replace(items.get(c).getItem(), c * 2);
                    c *= 2;
                } else {
                    break;
                }
            } else {
                Node node1 = items.get(c * 2);
                Node node2 = items.get(c * 2 + 1);
                if (node1.getPriority() <= node2.getPriority()) {
                    Node temp = items.get(c * 2);
                    items.set(c * 2, items.get(c));
                    items.set(c, temp);
                    map.replace(temp.getItem(), c);
                    map.replace(items.get(c).getItem(), c * 2);
                    c *= 2;
                } else {
                    Node temp = items.get(c * 2 + 1);
                    items.set(c * 2 + 1, items.get(c));
                    items.set(c, temp);
                    map.replace(temp.getItem(), c);
                    map.replace(items.get(c).getItem(), c * 2 + 1);
                    c = c * 2 + 1;
                }
            }
        }
        map.remove(answer);
        return answer;
    }

    /* Returns the number of items in the PQ. */
    public int size() {
        return size;
    }

    // Changes the priority of the given item.
    public void changePriority(T item, double priority) {

        if (!contains(item)) {
            throw new NoSuchElementException("Not an element");
        }

        int c = map.get(item);
        int c2 = map.get(item);
        Node temp = items.get(c);
        temp.setPriority(priority);

        while (c > 1 && priority < items.get(parent(c)).priority) {
            items.set(c, items.get(parent(c)));
            map.replace(items.get(parent(c)).getItem(), c);
            c /= 2;
        }
        double p = items.get(c).getPriority();
        while (c2 <= size / 2) {
            if (p < items.get(c * 2).getPriority() && (items.size() == c2 * 2 + 1
                    || p < items.get(c2 * 2 + 1).getPriority())) {
                break;
            } else if (c2 * 2 + 1 == items.size()) {
                if (p < items.get(c2 * 2).getPriority()) {
                    Node tempNode = items.get(c2 * 2);
                    items.set(c2, tempNode);
                    items.set(c2 * 2, items.get(c2));
                    map.replace(tempNode.getItem(), c2);
                    map.replace(items.get(c2).getItem(), c2 * 2);
                    c2 *= 2;
                } else {
                    break;
                }
            } else {
                Node node1 = items.get(c2 * 2);
                Node node2 = items.get(c2 * 2 + 1);
                if (node1.getPriority() <= node2.getPriority()) {
                    Node tempNode = items.get(c2 * 2);
                    items.set(c2 * 2, items.get(c2));
                    items.set(c2, tempNode);
                    map.replace(tempNode.getItem(), c2);
                    map.replace(items.get(c2).getItem(), c2 * 2);
                    c2 *= 2;
                } else {
                    Node tempNode = items.get(c2 * 2 + 1);
                    items.set(c2 * 2 + 1, items.get(c2));
                    items.set(c2, tempNode);
                    map.replace(tempNode.getItem(), c2);
                    map.replace(items.get(c2).getItem(), c2 * 2 + 1);
                    c2 = c2 * 2 + 1;
                }
            }
        }
    }

    private int leftChild(int place) {
        return place * 2;
    }

    private int rightChild(int place) {
        return place * 2 + 1;
    }

    private int parent(int place) {
        return place / 2;
    }
}
