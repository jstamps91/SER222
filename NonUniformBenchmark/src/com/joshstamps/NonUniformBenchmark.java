package com.joshstamps;

import java.lang.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Program which uses the doubling formula to compute the benchmark of insertion sort and
 * shell sort using three arrays with varying data sets.
 * 
 * Completion time: (16 hours)
 *
 * @author (Joshua Stamps), Acuna, Sedgewick
 * @version (9/11/2019)
 */
 public class StampsNonUniform implements SER222_02_01_HW02_Submission {

    /***************************************************************************
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK)               *
     **************************************************************************/

    public static void insertionSort(Comparable[] a) {
        int N = a.length;

        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }

    public static void shellsort(Comparable[] a) {
        int N = a.length;
        int h = 1;

        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...

        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                    exch(a, j, j-h);
            }
            h = h/3;
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES, DO NOT MODIFY                                  *
     **************************************************************************/

@Override
//Implementation of Interface Method
    public Integer[] generateTestDataBinary(int size) {

    //Create array of N size
    Integer[] arr1 = new Integer[size];

    //Middle point of the array
    int half = (size / 2);

    //for loop to assign first half of elements 0, and second half 1s
    for (int i = 0; i < size; i++) {
        if (i < half)
            arr1[i] = 0;
         else if (i >= half) {
            arr1[i] = 1;

        }

    }
    return arr1;
}

    public Integer[] generateTestDataHalfs(int size) {

        Integer[] arr2 = new Integer[size];

        //Middle point of the array which cuts its size in half for each iteration
        int half = (size / 2) + (size % 2);
        int index = half;
        int value = 0;

        //for loop to assign first half of elements 0, and second half 1s
        for (int i = 0; i < size; i++) {
            if (i == index) {
                half = (half / 2) + (half % 2);
                index += half;
                value++;
            }
            arr2[i] = value;
        }
        return arr2;
    }

@Override
    public Integer[] generateTestDataHalfRandom(int size) {

    Integer[] arr3 = new Integer[size];

    //class constructor for random integers
    Random random = new Random();

    int half = (size / 2);

    //for loop to assign first half of elements 0, and second half random integers
    for (int i = 0; i < size; i++) {
        if (i < half)
            arr3[i] = 0;

        else if (i >= half) {
            arr3[i] = random.nextInt(1000000 + 10000) - 1000000;
        }

    }
    return arr3;
}
@Override
    public double computeDoublingFormula(double t1, double t2) {

    //doubling formula result in log base e
    double b = Math.log(t2/t1);

    //conversion to log base 2
    return (b/Math.log(2.0));
    }

@Override
    public double benchmarkInsertionSort(Integer[] small, Integer[] large) {

        //stopwatch class used to time insertion sort on initial size array
        Stopwatch stopwatchS = new Stopwatch();
        insertionSort(small);
        double t1 = stopwatchS.elapsedTime();

        //time of initial array doubled in size
        Stopwatch stopwatchL = new Stopwatch();
        insertionSort(large);
        double t2 = (stopwatchL.elapsedTime());

        return computeDoublingFormula(t1, t2);
    }
@Override
    public double benchmarkShellsort(Integer[] small, Integer[] large) {

        Stopwatch stopwatchS = new Stopwatch();
        shellsort(small);
        double t1 = stopwatchS.elapsedTime();

        Stopwatch stopwatchL = new Stopwatch();
        shellsort(large);
        double t2 = (stopwatchL.elapsedTime());

        return computeDoublingFormula(t1, t2);
    }

    @Override
    public void runBenchmarks(int size) {

        //class constructor for reformatting decimal place to 3 digits to the right.
        DecimalFormat df3 = new DecimalFormat("#.000");

        //Column Headers
        System.out.println("            " + "Insertion" + "       " + "Shellsort");

        //Initialize array with call to binary data set method
        Integer[] arr1 = generateTestDataBinary(size*100); //Multiplied by 100 for more consistent b values
        Integer[] arr1L = generateTestDataBinary(size*200);
        benchmarkInsertionSort(arr1, arr1L);
        benchmarkShellsort(arr1, arr1L);

        //print b values
        System.out.print("b =          " + df3.format(benchmarkInsertionSort(arr1, arr1L)));
        System.out.println("          " + df3.format(benchmarkShellsort(arr1, arr1L)));

        //Initialize array with call to halfs data set method
        Integer[] arr2 = generateTestDataHalfs(size*100); //Multiplied by 100 for more consistent b values
        Integer[] arr2L = generateTestDataHalfs(size*200);
        benchmarkInsertionSort(arr2, arr2L);
        benchmarkShellsort(arr2, arr2L);

        System.out.print("b =          " + df3.format(benchmarkInsertionSort(arr2, arr2L)));
        System.out.println("          " + df3.format(benchmarkShellsort(arr2, arr2L)));

        //Initialize array with call to random data set method
        Integer[] arr3 = generateTestDataHalfRandom(size);
        Integer[] arr3L = generateTestDataHalfRandom(size*2);
        benchmarkInsertionSort(arr3, arr3L);
        benchmarkShellsort(arr3, arr3L);

        System.out.print("b =          " + df3.format(benchmarkInsertionSort(arr3, arr3L)));
        System.out.println("          " + df3.format(benchmarkShellsort(arr3, arr3L)));

    }

    public static void main(String[] args) {

        SER222_02_01_HW02_Submission me = new StampsNonUniform();

        int size = 4096;
        //NOTE: feel free to change size here. all other code must go in the methods.

        me.runBenchmarks(size);

    }
}