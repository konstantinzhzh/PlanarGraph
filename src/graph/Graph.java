package graph;


public class Graph {
    final protected int[][] matrix;
    protected int nPoints;

    public Graph(int nPoints) {
        matrix = new int[nPoints][nPoints];
        this.nPoints = nPoints;
    }

    public Graph(int[][] matrix) {
        this.matrix = matrix.clone();
        nPoints = matrix.length;
    }




    


}