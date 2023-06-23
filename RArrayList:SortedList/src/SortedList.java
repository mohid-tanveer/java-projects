

import java.util.Iterator;

public class SortedList<E extends Comparable<E>> implements Iterable<E> {

    private Node<E> head; // points to the head of the list.
    private Node<E> tail; // points to the tail of the list.
    private int size; // number of nodes (items) in the list.

    /**
     * Create a new, empty SortedList.
     */
    public SortedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Return the size of this SortedList.
     */
    public int size() {
        return size;
    }

    /**
     * Return the item at a specified index in this SortedList.
     * If index < the halfway point in the list (based on the size), the list should be traversed
     * forwards from the head.  If index > the halfway point, the traversal should start at the tail
     * and proceed in reverse.  For an index exactly halfway, you may start at either end.
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index out of bounds (" + index + ") for SortedList with size=" + size);

        }
        int halfway = size / 2;

        // If the index is less than the halfway point traverse from beginning.
        if (index < halfway) {
            Node<E> curr = head;
            int traversed = 0;
            while (index != traversed) {
                curr = curr.next;
                traversed += 1;
            }
            return curr.data;
        }
        // If the index is greater than the halfway point traverse from the end.
        else {
            Node<E> curr = tail; 
            // Calculate the distance from the tail to the required index.
            int reverseIndex = size - index - 1;
            int traversed = 0;
            while (reverseIndex != traversed) {
                curr = curr.prev;
                traversed += 1;
            }
            return curr.data;
        }
    }

    /**
     * Remove all the items in the SortedList.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Add a new item into this SortedList. Position will be determined
     * automatically based on sorted order. An item less than head or greater
     * than tail should be added in O(1) time. Other items requiring a traversal
     * may be added in O(n) time.
     */
    public void add(E item) {
        // If head is null add new item
        if (head == null) {
            Node<E> itemNode = new Node<>(item, null, null);
            head = itemNode;
            tail = itemNode;
        }
        // If the first item is greater than the new item make the new item the head.
        else if (head.data.compareTo(item) > 0) {
            Node<E> itemNode = new Node<>(item, head, null);
            head.prev = itemNode;
            head = itemNode;
        }
        // If the last item is less than the new item make the new item the head.
        else if (tail.data.compareTo(item) < 0) {
            Node<E> itemNode = new Node<>(item, null, tail);
            tail.next = itemNode;
            tail = itemNode;
        }
        // Traverse list to find correct location for new item and insert the node there.
        else {
            Node<E> prev = head;
            
            while (!((prev.data.compareTo(item) < 0) && (prev.next.data.compareTo(item) > 0))) {
                prev = prev.next;
            }
            Node<E> curr = prev.next;
            Node<E> itemNode = new Node<>(item, curr, prev);
            prev.next = itemNode;
            curr.prev = itemNode;
        }
        size++;
    }

    /**
     * Returns true if this SortedList contains item, false otherwise.
     */
    public boolean contains(E item)
    {
        Node<E> curr = head;
        // Traverse the SortedList until the end. If we encounter the item then return true.
        while (curr != null) {
            if (curr.data == item) {
                return true;
            }
            curr = curr.next;
        }
        return false;

    }

    /**
     * Remove an item from this SortedList.  If the item occurs multiple times,
     * only one copy will be removed.
     */
    public void remove(E item)
    {
        // If empty list return.
        if (head == null) {
            return;
        }
        // If the removed item is at the beginning just switch the head to the next item
        else if (head.data == item) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            // If the list only had one element set the tail to null as well.
            else {
                tail = null;
            }
            size--;
            return;
        }
        // If the removed item is at the end just switch the tail to the previous item
        else if (tail.data == item) {
            tail = tail.prev;
            tail.next = null;
            size--;
            return;
        }
        Node<E> curr = head;
        while (curr != null && curr.data != item) {
            curr = curr.next;
        }
        // If the item is not in the list return.
        if (curr == null) {
            return;
        }
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        size--;
    }

    /**
     * This function should return an "internal" representation of the string,
     * which consists of the list printed both head-to-tail and tail-to-head,
     * and the size. 
     */
    public String toInternalString() {
        String string = "forward = [ ";
        Node<E> curr = head;
        // Traverse the list forwards.
        while(curr != null) {
            string += (curr.data + " ");
            curr = curr.next;
        }
        string += "] backward = [ ";
        Node<E> currBack = tail;
        // Traverse the list backwards.
        while (currBack != null) {
            string += (currBack.data + " ");
            currBack = currBack.prev;
        }
        string += "] size=" + size;
        return string;
    }

    /**
     * Return a string representation of this list from the user's perspective.
     */
    public String toString()
    {
        String string = "[ ";
        Node<E> curr = head;
        // Traverse the list.
        while(curr != null) {
            string += (curr.data + " ");
            curr = curr.next;
        }
        return string + "]";
    }

    private static class Node<E> {
        public E data = null;
        public Node<E> next = null;
        public Node<E> prev = null;

        public Node(E data, Node<E> next, Node<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public E next() {
                E data = curr.data;
                curr = curr.next;
                return data;
            }
        };
    }
}
