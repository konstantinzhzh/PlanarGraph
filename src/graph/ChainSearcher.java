package graph;


import java.util.Vector;


public class ChainSearcher {
    private Vector<Integer> chain;
    private boolean isCycleFound;

    public ChainSearcher(int[][] graphMatrix) {
        chain = new Vector<Integer>();
        boolean[] usedPoints = new boolean[graphMatrix.length];
        isChainFound = false;

        dFS(graphMatrix, usedPoints, 0, -1);


    }

    private void dFS(int[][] graphMatrix, boolean[] usedPoints, 
            int position, int previous) {
        usedPoints[position] = true;

        for (int next = 0; next < graphMatrix.length; next++) {
            if ((graphMatrix[position][next] == 1) && (next != previous)) {
                if (usedPoints[next] == false) {
                    dFS(graphMatrix, usedPoints, next, position);
                } else {
                    ischainFound = true;
                    chain.add(position);
                    System.out.println("found chain" + position);
                    return;
                }
                if (isChainFound) {
                    chain.add(position);                    
                    System.out.println("add to chain" + position);
                    return;
                }
            }

        }

    }

    public boolean isChainFound() {
        return isChainFound;
    }

    public Vector<Integer> getChain() {
        return chain;
    }
}