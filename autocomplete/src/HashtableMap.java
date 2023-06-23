import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A Map from keys to values that, implemented with a hash table, using chaining to resolve collisions.
 * The user may set the starting size of the hash table, which never changes.
 */
public class HashtableMap<K, V> {

    // The hash table, an ArrayList of LinkedLists of KVPair objects.
    private final ArrayList<LinkedList<KVPair>> table;

    /**
     * Create a new hash table of the given capacity.
     */
    public HashtableMap(int capacity) {
        // Initialize table to have a pre-defined capacity.
        table = new ArrayList<LinkedList<KVPair>>(capacity);

        // Go through table and add an empty linked list to each slot.
        for (int x = 0; x < capacity; x++) {
            table.add(new LinkedList<KVPair>());
        }
    }

    /**
     * Adds a new key-value pair into the hash table.
     * If there is already an entry in the table for this key, then overwrite it with the new value.
     */
    public void put(K newKey, V newValue) {
        // This is the index you should use to insert the key-value pair.
        int hashIndex = Math.abs(newKey.hashCode() % table.size());
        // Create a boolean that will see if the key is already in the hash table
        boolean keyExists = false;
        // Iterate through the linked list at the index to see if the key is in the hash table
        
        for (KVPair pair : table.get(hashIndex)) {
            // If key is encountered switch the value and set keyExists to true.
            if (pair.key.equals(newKey)) {
                pair.value = newValue;
                keyExists = true;
            }
        }
        // If the key is not in the hash table create a new KVPair and add it to the beginning of the LinkedList.
        if (keyExists == false) {
            KVPair pair = new KVPair();
            pair.key = newKey;
            pair.value = newValue;
            table.get(hashIndex).addFirst(pair);
        }
    }

    /**
     * Gets a value from this hash table, based on its key. If the key doesn't already exist in the table,
     * this method returns null.
     */
    public V get(K searchKey) {
        // This is the index where the key should be located.
        int hashIndex = Math.abs(searchKey.hashCode() % table.size());
        // Iterate through the linked list to see if the key exists, if so, return the value.
        for (KVPair pair : table.get(hashIndex)) {
            if (pair.key.equals(searchKey)) {
                return pair.value;
            }
        }
        // If the key does not exist return null.
        return null;
    }

    /**
     * Test if this key exists in the hash table, and return true if it does, and false if it doesn't.
     */
    public boolean containsKey(K searchKey) {
        // This is the index where the key should be located.
        int hashIndex = Math.abs(searchKey.hashCode() % table.size());
        // Create a boolean that will see if the key is already in the hash table
        boolean keyExists = false;
        // Iterate through the linked list at the index to see if the key is in the hash table
        for (KVPair pair : table.get(hashIndex)) {
            // If key is encountered set keyExists to true.
            if (pair.key.equals(searchKey)) {
                keyExists = true;
            }
        }
        return keyExists;
    }

    /**
     * Prints the hash table. If the table has more than 100 slots, only prints the top 100 (indices 0-99).
     * Prints the contents of each index in the table on a single line. Includes the index number, the
     * number of entries at that index, and each individual entry in the format "{key=someKey, value=someValue}".
     */
    public void printTable() {
        int index = 0;
        // Iterate through the linked lists in the arrayList
        for (LinkedList<KVPair> list : table) {
            System.out.print("Index " + index + " (" + list.size() + "): [");
            int KVindex = 1;
            // If empty list close the bracket.
            if (list.size() == 0) {
                System.out.println("]");
            }
            else{
                // Iterate through the KVPairs in the linked lists and print them as you go.
                for (KVPair pair : list) {
                    System.out.print(pair.toString());
                    if (KVindex != list.size()) {
                        System.out.print(", ");
                    }
                    else {
                        System.out.println("]");
                    }
                    KVindex++;
                }
            }
            index++;
        } 
    }

    /**
     * Returns the total number of key-value pairs stored in the hash table.
     */
    public int size() {
        int size = 0;
        // Iterate through the linked lists in the arrayList
        for (LinkedList<KVPair> list : table) {
            // Iterate through the KVPairs in the linked lists and increment size as you go.
            for (KVPair pair : list) {
                size++;
            }
        }
        return size;
    }

    /**
     * Turns the hash table into a Set of all the keys in the table (values are ignored).
     */
    public Set<K> keySet() {
        // Create and empty set to hold the keys.
        HashSet<K> keys = new HashSet<>();
        // Iterate through the linked lists in the arrayList
        for (LinkedList<KVPair> list : table) {
            // Iterate through the KVPairs in the linked lists and add the keys to the set as you go.
            for (KVPair pair : list) {
                keys.add(pair.key);
            }
        }
        return keys;
    }

    private class KVPair {
        public K key = null;
        public V value = null;
        @Override 
        public String toString() {
            return "{key=" + this.key + ", value=" + this.value +"}";
        }
    }
}
