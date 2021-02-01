package com.joshstamps;
/**
 *  This program provides an implementation of the Deque interface
 *  and demonstrates it using a LinkedList
 *
 *  @author (Joshua Stamps), Acuna
 *  @version (9/5/19)
 */
import java.util.NoSuchElementException;
//TODO: implement.
public class BaseDeque<Item> implements Deque<Item> {

    // Node attributes
    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    // Default constructor to initialize empty deque
    public BaseDeque() {

        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void enqueueFront(Item element) {

        // Create Node object with element
        Node<Item> node = new Node<Item>(element);

        // Set as head/tail if head is null
        if (head == null) {
            head = node;
            tail = node;
        }

        else {
            // Add before head node; Update head node
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
        size++;
    }

    @Override
    public void enqueueBack(Item element) {

        // Create Node object with element
        Node<Item> node = new Node<Item>(element);

        // Set as head/tail if tail is null
        if (tail == null) {
            head = node;
            tail = node;
        }

        else {
            // Add after tail node; Update tail node
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
        }
        size++;
    }

    @Override
    public Item dequeueFront() throws NoSuchElementException {

        if (head == null) {
            throw new NoSuchElementException("Deque is empty!");
        }
        // Get item at head, and store in variable
        Item item = head.getItem();

        // Udate head node
        head = head.getNext();

        // Update tail node to null if deque becomes empty
        if (head == null) {
            tail = null;
        }

        else {
            // removing previous node
            head.setPrev(null);
        }
        size--;

        return item;
    }



    @Override
    public Item dequeueBack() throws NoSuchElementException {

        if (tail == null) {
            throw new NoSuchElementException("Deque is empty!");
        }

        // Get item at tail node
        Item item = tail.getItem();

        // Update tail node
        tail = tail.getPrev();

        // Update head and tail if deque becomes null
        if (tail == null) {
            head = null;
        }

        else {
            // Remove next tail node
            tail.setNext(null);
        }
        size--;

        return item;
    }

    @Override
    public Item first() throws NoSuchElementException {

        if (head == null) {
            throw new NoSuchElementException("Deque is empty!");
        }

        // Get item at head node
        Item item = head.getItem();

        return item;
    }

    @Override
    public Item last() throws NoSuchElementException {

        if (tail == null) {
            throw new NoSuchElementException("Deque is empty!");
        }

        // Get item at tail node and return
        Item item = tail.getItem();

        return item;
    }

    @Override
    public boolean isEmpty() {

        // Deque is empty if head or tail is null
        return head == null || tail == null;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public String toString() {

        String result = "";

        if (isEmpty()) {
            result = "empty";
        }

        else {
            // Get last node
            Node<Item> node = head;

            // Append each node value to the tail
            while (node != null) {
                result += node.getItem() + " ";
                node = node.getNext();
            }
        }
        return result;
    }

    //Class to represent single Node in Deque
    class Node<Item> {

        //Node Attributes
        Item item;
        Node<Item> prev;
        Node<Item> next;


        //Constructor taking value for item
        public Node(Item item) {

            this.item = item;
            prev = null;
            next = null;

        }


        //Getters and Setters
        public Item getItem() {

            return item;
        }

        public void setItem(Item item) {

            this.item = item;
        }

        public Node<Item> getPrev() {

            return prev;
        }

        public void setPrev(Node<Item> prev) {

            this.prev = prev;
        }

        public Node<Item> getNext() {

            return next;
        }

        public void setNext(Node<Item> next) {

            this.next = next;
        }
    }


    /**
     * Program entry point for deque.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        BaseDeque<Integer> deque = new BaseDeque();
        //standard queue behavior

        deque.enqueueBack(new Integer (3));
        Integer X = deque.dequeueFront();
        deque.enqueueBack(new Integer(7));
        deque.enqueueBack(new Integer(5));
        Integer Y = deque.first();
        deque.enqueueBack(new Integer(3));
        deque.enqueueBack(new Integer(9));
        Integer Z = deque.dequeueFront();
        System.out.println(Y);
        System.out.println("contents:\n" + deque.toString());

    }

}

