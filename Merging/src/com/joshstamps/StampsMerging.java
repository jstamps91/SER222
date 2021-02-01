package com.joshstamps;

import java.util.Random;

/**
 * Implements various merge style algorithms.
 * 
 * Completion time: 10 hours
 *
 * @author Joshua Stamps, Acuna, Sedgewick and Wayne
 * @verison 9/17/2019
 */

public class StampsMerging {

    /**
     * Entry point for sample output.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue<Comparable> q1 = new ListQueue<Comparable>();
        q1.enqueue("T");
        q1.enqueue("R");
        q1.enqueue("O");
        q1.enqueue("L");
        q1.enqueue("E");
        Queue<Comparable> q2 = new ListQueue<Comparable>();
        q2.enqueue("X");
        q2.enqueue("S");
        q2.enqueue("P");
        q2.enqueue("M");
        q2.enqueue("E");
        q2.enqueue("A");
        Queue<Comparable> q3 = new ListQueue<Comparable>();
        q3.enqueue(20);
        q3.enqueue(17);
        q3.enqueue(15);
        q3.enqueue(12);
        q3.enqueue(5);
        Queue<Comparable> q4 = new ListQueue<Comparable>();
        q4.enqueue(18);
        q4.enqueue(16);
        q4.enqueue(13);
        q4.enqueue(12);
        q4.enqueue(4);
        q4.enqueue(1);

        //Q1 - sample test cases
        Queue merged1 = mergeQueues(q1, q2);
        System.out.println(merged1.toString());
        Queue merged2 = mergeQueues(q3, q4);
        System.out.println(merged2.toString());

        //Q2 - sample test cases
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        sort(a);
        assert isSorted(a);
        show(a);

        //Q3 - sample test cases
        String[] b = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        shuffle(b);
        show(b);

        shuffle(b);
        show(b);
    }

    private static Queue mergeQueues(Queue<Comparable> q1, Queue<Comparable> q2) {

        //Creates an empty Queue list
        Queue<Comparable> result = new ListQueue<Comparable>();

        //When both queues passed as arguments are not empty,
        // move the element of greater value to the front of the list
        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (!less(q1.front(), q2.front())) {
                result.enqueue(q1.dequeue());
            } else result.enqueue(q2.dequeue());
        }
        //Continue moving greatest value element to the front until empty
        while (!q1.isEmpty()) {
            result.enqueue(q1.dequeue());
        }

        while (!q2.isEmpty()) {
            result.enqueue(q2.dequeue());
        }
        //Return newly created queue list
        return result;
    }

    //Creates a new comparable array b, and copies elements from array a
    public static void sort(Comparable[] a) {
        Comparable[] b = mergesort(a);

        for(int i = 0; i < b.length; i++) {
             a[i] = b[i];
        }
    }

    private static Comparable[] mergesort(Comparable[] a) {

        //return array if only 1 element exists
        if (a.length < 2)
            return a;

        //Defines middle of the array
        int midPosition = a.length / 2;

        //Creates two new arrays, one for first half, another for second half
        Comparable[] firstHalf = new Comparable[midPosition];
        Comparable[] secondHalf = new Comparable[a.length - midPosition];

        //Fills first half of array with values
        for (int i = 0; i < midPosition; i++) {
            firstHalf[i] = a[i];
        }
        //Fills second half with values
        for (int i = midPosition, j = 0; i < a.length; i++, j++) {
                secondHalf[j] = a[i];
        }
        //Call to mergesort method, passes both halves of array to be sorted
        firstHalf = mergesort(firstHalf);
        secondHalf = mergesort(secondHalf);

        //Reassigns array a with sorted first and second halves
        a = merge(firstHalf, secondHalf);

        return a;
    }

    private static Comparable[] merge(Comparable[] a, Comparable[] b) {

        //Creates result array to retrieve values from firstHalf and secondHalf
        Comparable[] result = new Comparable[a.length + b.length];

        int i = 0, j = 0, k = 0;

        //While loop used for sorting values based on comparisons between arrays a and b
        while(i < a.length || j < b.length) {
            if (i < a.length && j < b.length) {
                if (a[i].compareTo(b[j]) < 0) {
                    result[k] = a[i];
                    k++;
                    i++;
                }
                else if (a[i].compareTo(b[j]) > 0) {
                    result[k] = b[j];
                    k++;
                    j++;
                }
                else {
                    result[k] = a[i];
                    k++;
                    i++;
                    result[k] = b[j];
                    k++;
                    j++;
                }
            }
            else if (i < a.length) {
                result[k] = a[i];
                k++;
                i++;
            }
            else {
                result[k] = b[j];
                k++;
                j++;
            }
        }

        return result;
    }

    //This shuffle method runs at Big-Oh = O(nlog(n)), the first for loop
    // runs at O(n), while the recursive call to swap using i + randIndex runs at O(log(n)).
    private static void shuffle(Object[] a) {
        Random random = new Random();

        for (int i = 0; i < a.length; i++) {
            int randIndex = random.nextInt( a.length - i);
            swap(a, i, i + randIndex);
        }
    }

    //Helper method for shuffle, runs at O(log(n))
    private static void swap(Object[] a, int i, int j) {
        Object temp;

        if (i != j) {
            temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }


    //sorting helper from text
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //sorting helper from text
    private static void show(Comparable[] a) {
        for (Comparable a1 : a)
            System.out.print(a1 + " ");
        System.out.println();
    }
    
    //sorting helper from text
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1]))
                return false;
        
        return true;
    }
}