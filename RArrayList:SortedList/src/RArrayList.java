import java.util.Arrays;

/**
 * An RArrayList is a dynamic array that can grow in capacity.  It never shrinks in capacity.
 *
 * When adding a single item at a time, if the array would overflow, it always grows by an increment
 * of "SIZE_INCREMENT".
 *
 * When adding multiple items at once, if the array would overflow, the array grows in capacity to
 * accommodate the new items exactly (no extra space at the end).
 */
public class RArrayList<E> implements RList<E> {
    private Object[] data;  // stores the actual array elements.
    private int size;  // stores the size of the array from the user's perspective.

    private final static int SIZE_INCREMENT = 3;

    /**
     * Create a new RArrayList.  It starts as empty from the user's perspective, but 3 slots 
     * are allocated in the data array.
     */
    public RArrayList() {
        data = new Object[SIZE_INCREMENT];
        size = 0;
    }

    /**
     * Return the size of this array (from the user's perspective).
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove all elements in the array (from the user's perspective).
     */
    public void clear() {
        // sets the data of the RArrayList to a new array of the same length with size 0.
        data = new Object[data.length];
        size = 0;
    }

    /**
     * Return true if this array is empty (from the user's perspective).
     */
    public boolean isEmpty() {

        // If the size of the RArrayList is 0 then the RArrayList is empty.
        if (size == 0){
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Retrieve the item at position pos.  Throws an IndexOutOfBoundsException if pos
     * is not a legal position (from the user's perspective).
     */
    @Override
    public E get(int pos) throws IndexOutOfBoundsException {
        if (pos < 0 || pos >= size)
            throw new IndexOutOfBoundsException("Tried to access position "
                    + pos + " of an array of size " + size);

        return (E)data[pos];
    }

    /**
     * Set the item at position pos to the value given.  Throws an IndexOutOfBoundsException if pos
     * is not a legal position (from the user's perspective).
     */
    @Override
    public void set(int pos, E value) throws IndexOutOfBoundsException {
        if (pos < 0 || pos >= size)
            throw new IndexOutOfBoundsException("Tried to access position "
                    + pos + " of an array of size " + size);

        data[pos] = value;
    }

    /**
     * Append a new value to the end of this RArrayList.
     * Grow the data array by SIZE_INCREMENT if needed.
     */
    @Override
    public void append(E value) {
        if (size == data.length)    // if the data array is full
            expand();               // grow the data array

        data[size] = value;         // put the new item in place
        size++;                     // increase the size
    }

    /**
     * Append an entire other RArrayList to the end of this RArrayList.  This does not modify
     * the other RArrayList.  If our RArrayList is too small, it should grow by the exact amount
     * necessary to accommodate the new items.  
     */
    public void append(RArrayList<E> other)
    {
        int otherSize = other.size();
        /**
         * If the size of the combined array is greater than the size of the
         * current array, then expand to the size needed for the elements
         * of both arrays.
         */
        if ((size + otherSize) >= data.length) 
            expandToCapacity(size + otherSize);

        // Add the elements of the other array to the end of the current array.
        for (int x = 0; x < otherSize; x++) {
            data[size + x] = other.data[x];
        }
        // Increment size by the value of the other array's size.
        size += otherSize;
    }

    /**
     * Prepend a new value to the beginning of this RArrayList.
     */
    @Override
    public void prepend(E value) {
        if (size == data.length)    // if data array is full
            expand();               // grow the data array

        // slide everything to the right so we can put value at [0]
        for (int x = size; x > 0; x--) {
            data[x] = data[x - 1];
        }

        data[0] = value;    // add new value at beginning
        size++;             // increase size
    }

    /**
     * Prepend an entire other RArrayList to the beginning of this RArrayList.  This does not modify
     * the other RArrayList.  If our RArrayList is too small, it should grow by the exact amount
     * necessary to accommodate the new items. 
     */
    public void prepend(RArrayList<E> other)
    {
        int otherSize = other.size();
        /**
         * If the size of the combined array is greater than the size of the
         * current array, then expand to the size needed for the elements
         * of both arrays.
         */
        if ((size + otherSize) >= data.length) 
            expandToCapacity(size + otherSize);
        
        // Slide everything to the right by an increment of otherSize.
        for (int x = size + otherSize; x > otherSize; x--) {
            data[x - 1] = data[x - otherSize - 1];
        }

        // Add the elements of the other array to the beginning of the current array.
        for (int x = 0; x < otherSize; x++) {
            data[x] = other.data[x];
        }

        // Increment size by the value of the other array's size.
        size += otherSize;
    }

    /**
     * Get a Python-style "slice" of this RArrayList.  This creates a new RArrayList consisting
     * of all the elements between startPos and endPos-1, inclusive.  This RArrayList is not modified.
     */
    public RArrayList<E> slice(int startPos, int endPos) throws IndexOutOfBoundsException
    {
        if (startPos < 0 || startPos >= size)
            throw new IndexOutOfBoundsException("Tried to access position "
                    + startPos + " of an array of size " + size);
        if (endPos < 0 || endPos > size)
            throw new IndexOutOfBoundsException("Tried to access position "
                    + endPos + " of an array of size " + size);
        // Creates a new RArrayList.
        RArrayList<E> newlist = new RArrayList<E>();
        // Expands this RArray List to the value needed to store the sliced objects.
        newlist.expandToCapacity((endPos - startPos) + 1);

        // Assigns the sliced indexs from the original array to the new array.
        for (int x = startPos, y = 0; x < endPos && y <= (endPos - startPos); x++, y++) {
            newlist.data[y] = data[x];
            newlist.size++;
        }
        return newlist;
    }

    /**
     * Remove all items between startPos and endPos-1, inclusive.
     * Throw an IndexOutOfBoundsException if startPos or endPos are not legal positions.
     * This does not modify the actual capacity of the data array.
     */
    public void remove(int startPos, int endPos) throws IndexOutOfBoundsException
    {
        if (startPos < 0 || startPos >= size)
            throw new IndexOutOfBoundsException("Tried to access position "
                    + startPos + " of an array of size " + size);
        if (endPos < 0 || endPos > size)
            throw new IndexOutOfBoundsException("Tried to access position "
                    + endPos + " of an array of size " + size);
        
        int origSize = size;

        // Sets the removed indexes of the array to null.
        for (int x = startPos; x < endPos; x++) {
            data[x] = null;
            size--;
        }

        // if the endPos inputed is not equal to the end of the original array we need to move objects.
        if (endPos != origSize - 1) {
            // Calculates how far to move objects.
            int moveDist = endPos - startPos + 1;
            // Calculates how many objects to move.
            int numMove = origSize - endPos - 1;
            // Assigns the objects to the empty space created and nullifies their original index.
            for (int x = 0, y = startPos; x < numMove && y <= endPos; x++, y++) {
                data[y] = data[y + moveDist];
                data[y + moveDist] = null;
            }
        }
    }

    /**
     * Removes object at the index provided by user.
     * Throw an IndexOutOfBoundsException if startPos or endPos are not legal positions.
     * This does not modify the actual capacity of the data array.
     */
    public void remove(int pos) throws IndexOutOfBoundsException {
        if (pos < 0 || pos >= size)
            throw new IndexOutOfBoundsException("Tried to access position "
                    + pos + " of an array of size " + size);
        // Nullifies object at index pos.
        data[pos] = null;
        // Lowers size by one.
        size--;
        // If pos is not at the end of the array move index of following by one to left.
        if (pos != size) {
            // Calculates how many objects to move.
            int numMove = size - pos;
            for (int x = 0, y = pos; x < numMove && y <= size; x++, y++) {
                data[y] = data[y + 1];
                data[y + 1] = null;
            }
        }
    }

    /**
     * Return a representation of this RArrayList from the user's perspective.
     */
    @Override
    public String toString() {
        // Build a string that represents how the user perceives the array:
        String dataString = "[ ";
        for (int x = 0; x < size; x++)
            dataString += (data[x] + " ");
        dataString += "]";
        return dataString;
    }

    /**
     * Return a String that represents the internal representation of
     * this RArrayList.  
     */
    public String toInternalString() {
        // Build a string that represents how the user perceives the array:
        String dataString = "user view=[ ";
        for (int x = 0; x < size; x++)
            dataString += (data[x] + " ");
        dataString += "] ";

        // Add in the internal representation:
        dataString += ("internal view=" + Arrays.toString(data));
        return dataString + " size=" + size + " capacity=" + data.length;
    }

    /**
     * Expand the data array by SIZE_INCREMENT slots.
     */
    private void expand()
    {
        int newCapacity = data.length + SIZE_INCREMENT;   // calculate the new capacity
        Object[] newData = new Object[newCapacity];

        // copy old data into new data
        for (int x = 0; x < size; x++)
        {
            newData[x] = data[x];
        }

        data = newData; // reassign
    }

    /**
     * Expand the data array to be the exact newCapacity specified.  We assume
     * newCapacity >= the existing capacity.
     */
    private void expandToCapacity(int newCapacity)
    {
        Object[] newData = new Object[newCapacity]; // creates new empty array with new capacity.

        // copy old data into new data
        for (int x = 0; x < size; x++) {
            newData[x] = data[x];
        }

        // reassigns data.
        data = newData;
    }
}
