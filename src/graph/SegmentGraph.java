package graph;


import java.util.Vector;


class SegmentGraph extends Graph {
    private Vector<Integer> contactPoints;
    private int faces;

    public SegmentGraph(int nPoints) {
        super(nPoints);
        contactPoints = new Vector<Integer>();
    }

    public void addEdge(int pointA, int pointB) {
        matrix[pointA][pointB] = 1;
        matrix[pointB][pointA] = 1;
    }

    public void addContactPoint (int point) {
        for (int contactPoint : contactPoints) {
            if ( contactPoint == point) {
                return;
            }
        }
        contactPoints.add(point);
    }

    public boolean isInFaces() {
        return (faces != 0);
    }

    public int getTheNumberOfFaces() {
        return faces;
    }

    public boolean canFitInto(Face face) {
        for(int point : contactPoints) {
            if (!face.hasPoint(point)) {
                return false;
            }
        }
        return true;
    }

    public void tryFitInto(Face face) {
        if (canFitInto(face)) {
            faces++;
        }
    }

    public Vector<Integer> getChain() {
        Vector<Integer> tmpChain = new Vector<Integer>();
        boolean[] used = new boolean[nPoints];
        findChainByDFS(tmpChain, used,contactPoints.firstElement(), -1);
        return tmpChain;
    }

    private void findChainByDFS(Vector<Integer> tmpChain,
                                boolean[] used, int point, int previous) {
        used[point] = true;
        tmpChain.add(point);

        for (int next = 0; next < nPoints; next++) {

            if ((matrix[point][next] == 1) && (next != previous) 
                    && (!used[next])) {
                if (contactPoints.contains(next)) {
                    tmpChain.add(next);
                } else {
                    findChainByDFS(tmpChain, used, next, point);
                }
                return;
            } 
        }


    }


    @Override
    public String toString() {
        String tmp = "Segment Matrix :\n";
        
        for(int i = 0; i < nPoints; i++) {
            for (int j = 0; j < nPoints; j++) {
                tmp += matrix[i][j] + "  ";
            }
            tmp += "\n";
        }
        
        tmp += "ContactPoints: \n";
        tmp += String.valueOf(contactPoints) + "\n";
        return tmp;
    }
}