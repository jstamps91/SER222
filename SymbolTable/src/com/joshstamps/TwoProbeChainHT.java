package com.joshstamps;

/**
 * Implements a two probe chain hashtable
 *
 * @author (Joshua Stamps), Acuna
 * @version (10/3/19)
 */

import java.util.LinkedList;

import com.joshstamps.LinearProbingHT.Entry;

public class TwoProbeChainHT<Key, Value> implements SymbolTable<Key, Value> {


    private int N;
    private int M;
    private LinkedList<Entry<Key, Value>>[] entries;

    //LinkedList Entry class
    public class Entry<EntryKey, EntryValue> {
        private Key key;
        private Value value;

        //Constructor
        private Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }


    //Constructor for TwoProbeChain, Size M = 97
    public TwoProbeChainHT() {
        this(97);
    }

    private TwoProbeChainHT(int M) {
        this.M = M;
        //Create array of size M
        entries = (LinkedList<Entry<Key, Value>>[]) new LinkedList[M];
        //For each index, create a new LinkedList
        for (int i = 0; i < M; i++) {
            entries[i] = new LinkedList<>();
        }
    }

    //Hash Function for Separate Chain Probing HashTable
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    //Second Hash Function for separate chain probing HT
    private int hashTwo(Key key) {
        return (((key.hashCode() & 0x7fffffff) % M) * 31) % M;
    }


    @Override
    public void put(Key key, Value value) {
        if (updatePut(key, value)) {
            return;
        }

        smallerChain(key).add(new Entry(key, value));
        N++;

    }
    //Method for recursive put(), begins process of selecting smaller chain
    public boolean updatePut(Key key, Value value) {
        for (int i = 0; i < entries[hash(key)].size(); i++) {
            if (i < entries[hash(key)].size()) {
                return true;
            }
        }
        for (int i = 0; i < entries[hashTwo(key)].size(); i++) {
            if (i < entries[hashTwo(key)].size()) {
                return true;
            }
        }
        return false;
    }
    //Uses two separate hash value choices to select smaller chain
    private LinkedList<Entry> smallerChain(Key key) {

            if (entries[hash(key)].size() < entries[hashTwo(key)].size()) {
                return entries[hash(key)];
        }
            return entries[hashTwo(key)];
    }

    @Override
    public Value get(Key key) {
        for (int i = 0; i < entries[hash(key)].size(); i++) {
            if (entries[hash(key)].get(i).key.equals(key)) {
                return (Value) entries[hash(key)].get(i).value;
            }
        }
        for (int i = 0; i < entries[hashTwo(key)].size(); i++) {
            if (entries[hashTwo(key)].get(i).key.equals(key)) {
                return (Value) entries[hashTwo(key)].get(i).value;
            }
        }
        return null;
    }



    @Override
    public void delete(Key key) {
        for (int i = 0; i < entries[hash(key)].size(); i++) {
            if (entries[hash(key)].get(i).key.equals(key)) {
                entries[hash(key)].remove(i);
                N--;
            }

        }

        for (int i = 0; i < entries[hashTwo(key)].size(); i++) {
            if (entries[hashTwo(key)].get(i).equals(key)) {
                entries[hashTwo(key)].remove(i);
                N--;
            }
        }
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        LinkedList<Key> keys = new LinkedList<Key>();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < entries[i].size(); j++)
                keys.add((Key) entries[i].get(j).key);
        }
        return keys;
    }
}


