package com.joshstamps;

import java.util.LinkedList;
import java.util.HashMap;
/**
 *  Program for generating kanji component dependency order via
 topological sort.
 * @time-spent 18 hours
 *  @author Joshua Stamps, Acuna
 *  @version 10/10/19
 */

public class IntuitiveTopological implements TopologicalSort {
    BetterDiGraph diGraph;

    IntuitiveTopological(BetterDiGraph graph) {
        diGraph = graph;
    }

    @Override
    public Iterable<Integer> order() {
        LinkedList<Integer> topologicalSort = new LinkedList<>();
        int count = diGraph.getVertexCount();
        if (isDAG()) {
            while (count != 0) {
                for (Object v : diGraph.vertices()) {
                    if (diGraph.getIndegree((Integer) v) == 0) {
                        topologicalSort.add((Integer) v);
                        diGraph.removeVertex((Integer) v);
                        count++;
                    }

                }

            }

        }
        return topologicalSort;
    }

    @Override
    public boolean isDAG() {
        return !isCyclic();
    }

    private boolean isCyclicUtil(int i, HashMap<Integer, Boolean> visited,
                                 HashMap<Integer, Boolean> recStack) {

        // Mark the current node as visited and
        // part of recursion stack
        if (recStack.get(i))
            return true;

        if (visited.get(i))
            return false;

        visited.put(i, true);
        recStack.put(i, true);
        Iterable<Integer> children = diGraph.getAdj(i);
        for (Integer c : children)
            if (isCyclicUtil(c, visited, recStack))
                return true;

        recStack.put(i, false);

        return false;
    }

    private boolean isCyclic() {

        // Mark all the vertices as not visited and
        // not part of recursion stack
        HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
        HashMap<Integer, Boolean> recStack = new HashMap<Integer, Boolean>();
        for (Object v : diGraph.vertices()) {
            visited.put((Integer) v, false);
            recStack.put((Integer) v, false);
        }


        // Call the recursive helper function to
        // detect cycle in different DFS trees
        for (Object v : diGraph.vertices())
            if (isCyclicUtil((Integer) v, visited, recStack))
                return true;

        return false;
    }

    private boolean hasCycle(int node, LinkedList<Integer> visited) {
        if (visited.contains(node)) {
            return true;
        }
        visited.add(node);
        for (Object nextNode : diGraph.getAdj(node)) {
            if (hasCycle((Integer) nextNode, visited)) {
                return true;
            }
        }
        visited.remove(visited.size() - 1);
        return false;
    }

}