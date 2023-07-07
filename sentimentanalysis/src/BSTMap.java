import java.util.ArrayList;
import java.util.List;


/**
 * A map implemented with a binary search tree.
 */
public class BSTMap<K extends Comparable<K>, V> {

    private Node root; // points to the root of the BST.
    private int size; // size of BST.

    /**
     * Create a new, empty BST.
     */
    public BSTMap() {
        root = null;
        size = 0;
    }

    /**
     * Put (add a key-value pair) into this BST. If the key already exists, the old
     * value will be overwritten with the new one.
     */
    public void put(K newKey, V newValue) {
        // If the tree is empty create a new node to act as the root.
        if (root == null) {
            Node newNode = new Node();
            newNode.key = newKey;
            newNode.value = newValue;
            root = newNode;
            size = 1;
        }
        // Otherwise pass to the recursive call.
        else
            put(root, newKey, newValue);
    }

    /**
     * Helper function for put.
     */
    private void put(Node curr, K newKey, V newValue) {
        // If the current node's key is the same as the new key overwrite the value.
        if (curr.key.equals(newKey)) {
            curr.value = newValue;
            return;
        }
        // If the new key is less than the current key, key should be in left branch.
        else if (newKey.compareTo(curr.key) < 0) {
            // If the left branch is empty, create a node to be the left branch.
            if (curr.left == null) {
                Node newNode = new Node();
                newNode.key = newKey;
                newNode.value = newValue;
                curr.left = newNode;
                size++;
            }
            else
                put(curr.left, newKey, newValue);
        }
        // Otherwise the key goes into the right branch.
        else {
            // If the right branch is empty, create a node to be the right branch.
            if (curr.right == null) {
                Node newNode = new Node();
                newNode.key = newKey;
                newNode.value = newValue;
                curr.right = newNode;
                size++;
            }
            else
                put(curr.right, newKey, newValue);
        }
    }

    /**
     * Get a value from this BST, based on its key. If the key doesn't already exist in the BST,
     * this method returns null.
     */
    public V get(K searchKey) {
        // Recursively call helper function using root as current node.
        return get(root, searchKey);
    }

    /**
     * Helper function for get.
     */
    private V get(Node curr, K searchKey) {
        // If the current node is null return null.
        if (curr == null) {
            return null;
        }
        // If the current key is the one being searched for return its value.
        else if (searchKey.equals(curr.key)) {
            return curr.value;
        }
        // If the key being searched for is less than the current key go to the left branch.
        else if (searchKey.compareTo(curr.key) < 0) {
            return get(curr.left, searchKey);
        }
        // Otherwise search the right branch.
        else
            return get(curr.right, searchKey);
    }

    /**
     * Test if a key is present in this BST. Returns true if the key is found, false if not.
     */
    public boolean containsKey(K searchKey) {
        // Recursively call helper function using root as current node.
        return containsKey(root, searchKey);
    }

    /**
     * Helper function for containsKey.
     */
    private boolean containsKey(Node curr, K searchKey) {
        // If the current node is null return false.
        if (curr == null) {
            return false;
        }
        // If the current key is the one being searched for return true.
        else if (searchKey.equals(curr.key)) {
            return true;
        }
        // If the key being searched for is less than the current key go to the left branch.
        else if (searchKey.compareTo(curr.key) < 0) {
            return containsKey(curr.left, searchKey);
        }
        // Otherwise search the right branch.
        else
            return containsKey (curr.right, searchKey);
    }


    /**
     * Given a key, remove the corresponding key-value pair from this BST. If the key is not found, do nothing.
     */
    public void remove(K removeKey) {
        // Set the current node to the root.
        Node curr = root;
        // Points to the parent of the node to be deleted if we get to it.
        Node parent = null;

        while (curr != null && !curr.key.equals(removeKey)) {
            // Traverse the tree looking for the node to remove.
            // If found or if case of an encounter with a null pointer stop.
            parent = curr;
            if (removeKey.compareTo(curr.key) < 0) {
                curr = curr.left;
            }
            else
                curr = curr.right;
        }
        // If the current pointer is null return.
        if (curr == null) {
            return;
        }
        
        // 2 Child situation!
        if (curr.left != null && curr.right != null) {
            // Find its successor.
            Node successor = curr.right;
            Node successorParent = curr;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            // Copy successor's key and value into curr.
            curr.key = successor.key;
            curr.value = successor.value;
            // Set values needed to delete the successor node!
            curr = successor;
            parent = successorParent;
        }
        // 0-child and 1-child Situation!

        // This value will point to subtree if 1 child or be null if 0 child.
        Node subTree;

        if (curr.left == null && curr.right == null) {
            subTree = null;
        }
        else if (curr.left != null) {
            subTree = curr.left;
        }
        else
            subTree = curr.right;
        
        // Attach subtree to correct child pointer of parent if it exists.
        // If none make the subtree the new root.
        if (parent == null) {
            root = subTree;
            size--;
        }
        else if (parent.left == curr) {
            // Delete parent's left child.
            parent.left = subTree;
            size--;
        }
        else {
            // Delete parent's right child.
            parent.right = subTree;
            size--;
        }
        return;
    }

    /**
     * Return the number of key-value pairs in this BST.
     */
    public int size() {
        return size;
    }

    /**
     * Return the height of this BST.
     */
    public int height() {
        return height(root);
    }
    
    /**
     * Helper function for height.
     */
    private int height(Node curr) {
        // If the current node is null return -1.
        if (curr == null) {
            return -1;
        }
        // Otherwise recursively call the function on the left and right child
        // and take the max value of them.
        else {
            return Math.max(height(curr.left), height(curr.right)) + 1;
        }
    }

    /**
     * Return a List of the keys in this BST, ordered by a preorder traversal.
     */
    public List<K> preorderKeys() {
        List<K> list = new ArrayList<>();
        preorderKeys(root, list);
        return list;
    }

    /**
     * Helper function for preorderKeys.
     */
    private void preorderKeys(Node curr, List<K> list) {
        if (curr != null) {
            list.add(curr.key);
            preorderKeys(curr.left, list);
            preorderKeys(curr.right, list);
        }
    }

    /**
     * Return a List of the keys in this BST, ordered by a inorder traversal.
     */
    public List<K> inorderKeys() {
        List<K> list = new ArrayList<>();
        inorderKeys(root, list);
        return list;
    }

    /**
     * Helper function for inorderKeys.
     */
    private void inorderKeys(Node curr, List<K> list) {
        if (curr != null) {
            inorderKeys(curr.left, list);
            list.add(curr.key);
            inorderKeys(curr.right, list);
        }
    }

    /**
     * Return a List of the keys in this BST, ordered by a postorder traversal.
     */
    public List<K> postorderKeys() {
        List<K> list = new ArrayList<>();
        postorderKeys(root, list);
        return list;
    }

    /**
     * Helper function for postorderKeys.
     */
    private void postorderKeys(Node curr, List<K> list) {
        if (curr != null) {
            postorderKeys(curr.left, list);
            postorderKeys(curr.right, list);
            list.add(curr.key);
        }
    }

    private class Node {
        public K key = null;
        public V value = null;
        public Node left = null;
        public Node right = null;
    }
}
