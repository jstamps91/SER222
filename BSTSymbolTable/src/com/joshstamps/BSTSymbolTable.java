package com.joshstamps;

/*
 * A binary search tree based implementation of a symbol table.
 *
 * @author Joshua Stamps, Sedgewick and Wayne, Acuna
 * @version 9.26.19
 */

import java.util.LinkedList;
import java.util.Queue;

public class StampsBSTST<Key extends Comparable<Key>, Value> implements OrderedSymbolTable<Key, Value> {
    //Root of BST
    private Node root;

    private class Node {
        private final Key key;
        private Value val;
        private Node left, right;
        private int N;

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    @Override
    //Returns number of key-value pairs in ST
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);

        if (cmp < 0)
            return get(x.left, key);

        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        // Change key’s value to val if key in subtree rooted at x.
        // Otherwise, add new node to subtree associating key with val.
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    @Override
    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    @Override
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    @Override
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        // Return number of keys less than x.key in the subtree rooted at x.
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    @Override
    //Method for contains. Returns true if the key exists in BST Symbol Table.
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key value.");
        }

        return get(key) != null;
    }

    @Override
    //Tests if ST is empty.
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    //Ceiling method implementation.
    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null)
            return null;

        else
            return x.key;
    }
    //Recursive ceiling method.
    private Node ceiling(Node x, Key key) {
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;

        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null)
                return t;
            else return x;
        }
        return ceiling(x.right, key);
    }

    @Override
    //Deletes the largest key-value pair.
    public void deleteMax() {
        root = deleteMax(root);
    }
    //Recursive implementation.
    private Node deleteMax(Node x) {
        if (x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.right) + size(x.left) + 1;
        return x;
    }

    @Override
    //Size method for defined interval.
    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (contains(hi))
            return rank(hi) - rank(lo) + 1;
        else
            return rank(hi) - rank(lo);
    }
    //Balance method.
    public void balance() {
        LinkedList<Node> nodes = new LinkedList<Node>();

        sortNodes(nodes, root);
        root = balancedBST(nodes, 0, size() -1);
        updateBST(root);
    }
    //Fills nodes in sorted order.
    private void sortNodes(LinkedList<Node> nodes, Node x) {
        if (x == null)
            return;
        sortNodes(nodes, x.left);
        nodes.add(x);
        sortNodes(nodes, x.right);
    }
    //Balances BST after sorting nodes.
    private Node balancedBST(LinkedList<Node> nodes, int first, int last) {
        if (first > last)
            return null;

        int middle = (first + last) / 2;

        if ((first + last) % 2 == 1) {
            middle++;
        }

        Node midNode = nodes.get(middle);

        midNode.left = balancedBST(nodes, first, middle-1);
        midNode.right = balancedBST(nodes, middle+1, last);

        return midNode;
    }
    //Updates sizes of balanced tree nodes.
    public void updateBST(Node x) {
        if (x == null)
            return;

        x.N = getChildSize(x);
        updateBST(x.left);
        updateBST(x.right);
    }
    //Gets number of children in subtree.
    private int getChildSize(Node x) {
        if (x == null)
            return 0;
        else
            return 1 + getChildSize(x.left) + getChildSize(x.right);
    }
    //Prints keys in subtree in order of their distance from the root.
    public void printLevel(Key key) {

        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                break;
        }

        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while (!queue.isEmpty()) {

            x = queue.poll();
            System.out.println(x.key);

            if (x.left != null) {
                queue.add(x.left);
            }
            if (x.right != null) {
                queue.add(x.right);
            }
        }
    }
    //Non-recursive implementation of get() method.
    public Value getFast(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);

            if (cmp == 0)
                return x.val;
            else if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }
        return null;
    }
    //Non-recursive implementation of put() method.
    public void putFast(Key key, Value val) {
        if (root == null) {
            root = new Node(key, val, 1);
        }
        else {
            Node x = root;
            Node parent = null;

            while (x != null) {
                parent = x;

                if (key.compareTo(x.key) < 0) {
                    x = x.left;
                }
                else if (key.compareTo(x.key) > 0) {
                    x = x.right;
                }
                else {
                    x.val = val;
                    return;
                }
            }

            x = root;
            while (x != null) {
                x.N = x.N + 1;

                if (key.compareTo(x.key) < 0) {
                    x = x.left;
                }
                else if (key.compareTo(x.key) > 0) {
                    x = x.right;
                }
            }

            Node newNode = new Node(key, val, 1);
            if (key.compareTo(parent.key) < 0)
                parent.left = newNode;
            else
                parent.right = newNode;
        }
    }


    /**
     * entry point for testing.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StampsBSTST<Integer, String> bst = new StampsBSTST();

        bst.putFast(10, "TEN");
        bst.putFast(3, "THREE");
        bst.putFast(1, "ONE");
        bst.putFast(5, "FIVE");
        bst.putFast(2, "TWO");
        bst.putFast(7, "SEVEN");


        System.out.println("Contains 1?");
        System.out.println(bst.contains(1));
        System.out.println("Contains 6?");
        System.out.println(bst.contains(6));
        System.out.println("");
        System.out.print("Ceiling of 8: ");
        System.out.println(bst.ceiling(8));
        System.out.print("Value of 3: ");
        System.out.println(bst.getFast(3));

        System.out.println("");

        System.out.println("Before balance:");
        bst.printLevel(10); //root

        System.out.println("After balance:");
        bst.balance();
        bst.printLevel(5); //root

        System.out.println("");

        bst.deleteMax();
        bst.balance();
        System.out.println("After deleteMax and re-Balance");
        bst.printLevel(5);
    }
}