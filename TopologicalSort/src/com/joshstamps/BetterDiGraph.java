package com.joshstamps;
/**
 *  Program for generating kanji component dependency order via
 topological sort.
 * @time-spent 18 hours
 *  @author Joshua Stamps, Acuna
 *  @version 10/10/19
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BetterDiGraph implements EditableDiGraph {

    private HashMap<Integer, HashSet<Integer>> adjacencyMap;
    private int V;
    private int E;

    BetterDiGraph() {
        this.adjacencyMap = new HashMap<>();
    }

    @Override
    public void addEdge(int v, int w) {
        addVertex(v);
        //addVertex(w);
        if (this.adjacencyMap.containsKey(v)) {
            if (!this.adjacencyMap.get(v).contains(w)) {
                this.adjacencyMap.get(v).add(w);
                this.E++;
            }
        }
    }

    @Override
    public void addVertex(int v) {
        if (!this.adjacencyMap.containsKey(v)) {
            this.adjacencyMap.put(v, new HashSet<>());
            this.V++;
        }
    }

    @Override
    public Iterable<Integer> getAdj(int v) {
        return this.adjacencyMap.get(v);
    }

    @Override
    public int getEdgeCount() {
        return this.E;
    }

    @Override
    public int getIndegree(int v) throws NoSuchElementException {

        if (!containsVertex(v)) {
            throw new NoSuchElementException();
        }

        LinkedList<Integer> inList = new LinkedList<>();
        for (Integer to : this.adjacencyMap.keySet()) {
            for (Integer e : this.adjacencyMap.get(to))
                if (e.equals(v))
                    inList.add(to);
        }
        return inList.size();

    }

    @Override
    public int getVertexCount() {
        return this.V;
    }

    @Override
    public void removeEdge(int v, int w) {
        if (this.adjacencyMap.containsKey(v) && this.adjacencyMap.containsKey(w)) {
            if (this.adjacencyMap.get(v).contains(w)) {
                this.adjacencyMap.get(v).remove(w);
                this.E--;
            }
        }
    }

    @Override
    public void removeVertex(int vertex) {
        if (this.adjacencyMap.containsKey(vertex)) {
            this.adjacencyMap.remove(vertex);
            for (HashMap.Entry<Integer, HashSet<Integer>> entry : this.adjacencyMap.entrySet()) {
                if (entry.getValue().contains(vertex)) {
                    this.adjacencyMap.get(entry.getKey()).remove(vertex);
                }
            }
            this.V--;
        }
    }

    @Override
    public Iterable<Integer> vertices() {
        LinkedList<Integer> inList = new LinkedList<>();
        for (Integer to : this.adjacencyMap.keySet()) {
            if (!inList.contains(to))
                inList.add(to);
        }
        return inList;
    }

    @Override
    public boolean isEmpty() {
        return this.V == 0;
    }

    @Override
    public boolean containsVertex(int v) {
        return this.adjacencyMap.containsKey(v);
    }

}