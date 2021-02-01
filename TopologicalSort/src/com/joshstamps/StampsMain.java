package com.joshstamps;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
/**
 *  Program for generating kanji component dependency order via
 topological sort.
 * @time-spent 18 hours
 *  @author Joshua Stamps, Acuna
 *  @version 10/10/19
 */
public class StampsMain {
    /**
     * Entry point for testing.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        BetterDiGraph diGraph = new BetterDiGraph();
        IntuitiveTopological tSort = new IntuitiveTopological(diGraph);
        HashMap<Integer, LinkedList<Integer>> graphEdges = new HashMap<Integer, LinkedList<Integer>>();

        diGraph.addEdge(2, 3);
        diGraph.addEdge(1, 4);
        diGraph.addEdge(2, 4);
        diGraph.addEdge(5, 4);
        diGraph.addEdge(6, 4);

        diGraph.addVertex(7);



        Scanner sc = new Scanner(System.in);
        BufferedReader indexReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File
                ("data-kanji.txt")), "UTF-8"));

        String line = indexReader.readLine();
        while (line != null) {
            if (line.contains("#")) {
                continue;
            }
            String[] tokens = line.split("\\s+");
            String key = tokens[0];
            String value = tokens[1];

            line = indexReader.readLine();

        }

        indexReader.close();
    }
}