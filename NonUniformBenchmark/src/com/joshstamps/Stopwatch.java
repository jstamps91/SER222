package com.joshstamps;
/**
 * A utility class to measure the running time (wall clock) of a program.
 * 
 * @author Sedgewick
 * @author Wayne
 * @version 1.0
 */
public class Stopwatch { 

    private final long start;

    /**
     * Initializes a new stopwatch.
     */
    public Stopwatch() {
        start = System.currentTimeMillis();
    } 

    /**
     * Returns the elapsed CPU time (in seconds) since the stopwatch was created.
     *
     * @return elapsed CPU time (in seconds) since the stopwatch was created
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start)  / 1000.0;
    }
}


//
//    BaseNonUniform bsn = new BaseNonUniform();
//
//    Integer[] small = bsn.benchmarkInsertionSort(small, small);
//    Integer[] large = bsn.benchmarkInsertionSort(large, large);
//
//        t1 =  bsn.benchmarkInsertionSort(small, large);
//                t2 =  bsn.benchmarkInsertionSort(Integer[].class.cast(4096), Integer[].class.cast(4096));
