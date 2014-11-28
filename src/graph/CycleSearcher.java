package graph;


import java.util.Vector;


public class CycleSearcher {
    private Vector<Integer> cycle;
    private boolean isCycleFound;
    private int cycleHead;
    private boolean isCycleGot;

    public CycleSearcher(int[][] graphMatrix) {
        cycle = new Vector<Integer>();
        boolean[] usedPoints = new boolean[graphMatrix.length];
        isCycleFound = false;

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
                    isCycleFound = true;
                    cycle.add(position);
                    cycleHead = next;
                    return;
                }
                if ((isCycleFound) && (!isCycleGot)) {
                    cycle.add(position);                    
                    if (position == cycleHead) {
                        isCycleGot = true;
                    }
                    return;
                }
            }

        }

    }

    public boolean isCycleFound() {
        return isCycleFound;
    }

    public Vector<Integer> getCycle() {
        return cycle;
    }
}