import graph.*;

import java.io.*;
import java.util.Scanner;


class Main {
    public static void main(String[] args) throws Exception {
        int[][] graphMatrix;
        int size;


        Scanner in = new Scanner(new File("input"));
        PrintWriter out = new PrintWriter(new FileWriter("output"));

        size = in.nextInt();
        graphMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                graphMatrix[i][j] = in.nextInt();
            }
        }
        Graph graph = new PlanarGraph(graphMatrix);
        out.close();
    }
        
}