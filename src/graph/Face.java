package graph;


import java.util.Vector;
import java.util.Collections;


class Face {
    private Vector<Integer> points;
    private boolean isExternal;

    public Face(Vector<Integer> cycle) {
        this.points = new Vector<Integer>(cycle);
    }
    public Face() {
        this.points = new Vector<Integer>();
    }

    public void addPoint(int x) {
        points.add(x);
    }
    public Vector<Integer> getPoints() {
        return points;
    }

    public boolean hasPoints(Vector<Integer> chain) {
        return (hasPoint(chain.firstElement()) 
                && hasPoint(chain.lastElement()));
    }

    public void setExternal() {
        isExternal = true;
    }

    public void setInternal() {
        isExternal = false;
    }

    public boolean hasPoint(int point) {
        return (points.contains(point));
    }

    public void completeNewFace(Vector<Integer> chain) {
        Vector<Integer> tmpChain = new Vector<Integer>(chain);
        int positionA = points.indexOf(tmpChain.firstElement());
        int positionB = points.indexOf(tmpChain.lastElement());


        if (points.isEmpty()) {
            points.addAll(tmpChain);
            points.removeElement(points.firstElement());
            return;
        }

        tmpChain.removeElementAt(0);

        if (positionA == positionB) {
            points.addAll(positionA + 1, tmpChain);
            return;
        }
        
        tmpChain.removeElementAt(tmpChain.size() - 1);
        
        if ((positionA == 0) && (positionB == points.size() - 1)) {
            Collections.reverse(tmpChain);
            points.addAll(tmpChain);
            return;
        }
        if ((positionB == 0) && (positionA == points.size() - 1)) {
            points.addAll(tmpChain);
            return;
        }
        if (positionA > positionB) {
            Collections.reverse(tmpChain);
            points.addAll(positionA, tmpChain);
            return;
        }
        if (positionB > positionA) {
            points.addAll(positionB, tmpChain);
            return;
        }
    }

    @Override
    public String toString() {
        String tmp = new String();
        for(int point : points) {
            tmp += String.valueOf(point) + "  ";
        }
        return tmp;
    }
 
}