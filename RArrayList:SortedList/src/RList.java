public interface RList<E> {
    /**
     * Get the size of this list.
     */
    public int size();

    /**
     * Returns the item in this list at position pos.
     */
    public E get(int pos) throws IndexOutOfBoundsException;

    /**
     * Set the item in this list at position pos.
     */
    public void set(int pos, E value) throws IndexOutOfBoundsException;

    /**
     * Append an item to the end of this list.
     */
    public void append(E value);

    /**
     * Prepend an item to the beginning of this list.
     */
    public void prepend(E value);
}
