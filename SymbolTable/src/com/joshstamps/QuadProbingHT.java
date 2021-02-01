package com.joshstamps;

/**
 * Implements a quadratic probe hashtable
 *
 * @author (Joshua Stamps), Acuna
 * @version (10/3/19)
 */

public class QuadProbingHT<Key, Value> extends LinearProbingHT<Key, Value> {

    private int M;

    //Constructor
    QuadProbingHT() {
        this(997);
    }

    private QuadProbingHT(int M) {
        super(M);
        this.M = M;
    }
    //Hash function for QuadProbing HashTable
    public int hash(Key key, int i) {
        return (((key.hashCode() & 0x7fffffff) + i * i) % M);
    }
}
