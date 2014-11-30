package graph;


import java.util.Vector;


public class PlanarGraph extends Graph {
    Vector<Face> faces;
    int nFaces;

    public PlanarGraph(int[][] matrix) {
        super(matrix);
        
        try {
            embedPlanarGraph(matrix);

            System.out.println("CREATED PLANAR GRAPH \n THERE ARE FACES :");
            for (Face face : faces) {
                System.out.println(face);
            }

        } catch(CanNotEmbedPlanarGraphException e) {
            System.out.println(e);
        }
    }


    public void embedPlanarGraph(int[][] matrix) 
            throws CanNotEmbedPlanarGraphException {

        Vector<Integer> cycle = (new CycleSearcher(matrix)).getCycle();

        faces = new Vector<Face>();

        faces.add(new Face(cycle)); 
        faces.add(new Face(cycle));

        boolean[] embeddedPoints = new boolean[super.nPoints];  
        for (int point : cycle) {
            embeddedPoints[point] = true;
        }


        int[][] embeddedEdges = new int[nPoints][nPoints];
        embeddedEdges[cycle.firstElement()][cycle.lastElement()] = 1;
        embeddedEdges[cycle.lastElement()][cycle.firstElement()] = 1;
        for (int i = 1; i < cycle.size(); i++) {
            embeddedEdges[cycle.elementAt(i)][cycle.elementAt(i - 1)] = 1;
            embeddedEdges[cycle.elementAt(i - 1)][cycle.elementAt(i)] = 1;
        }
        int[][] nonEmbeddedEdges = new int[nPoints][nPoints];

        while (true) {

            for (int i = 0; i < nPoints; i++) {
                for (int j = 0; j < nPoints; j++) {

                    nonEmbeddedEdges[i][j] 
                        = matrix[i][j] - embeddedEdges[i][j];
                }
            }


            Vector<SegmentGraph> segments 
                = getSegments(embeddedPoints, nonEmbeddedEdges);

            if (segments.isEmpty()) {
                return;
            }

            for (SegmentGraph segment : segments) {
                for (Face face : faces) {
                    segment.tryFitInto(face);
                }
                if (!segment.isInFaces()) {
                    throw new CanNotEmbedPlanarGraphException(segment);
                }
            }

            SegmentGraph segmentToGetChain = segments.firstElement();
            for (SegmentGraph segment : segments) {

                if (segment.getTheNumberOfFaces() 
                        < segmentToGetChain.getTheNumberOfFaces()) {
                    segmentToGetChain = segment;
                }
            }

            embedChain(segmentToGetChain, embeddedEdges, embeddedPoints);
        }

    }


    public Vector<SegmentGraph> getSegments(boolean[] embeddedPoints,
            int[][] nonEmbeddedEdges) {

        Vector<SegmentGraph> segments = new Vector<SegmentGraph>();

        boolean[] segmentedPoints = new boolean[nPoints];

        for (int point = 0; point < nPoints; point++) {

            if (embeddedPoints[point]) {
                for (int j = 0; j < nPoints; j++) {

                    if ((embeddedPoints[j]) 
                            && (nonEmbeddedEdges[point][j] == 1)) {
                        SegmentGraph segment = new SegmentGraph(nPoints);
                        segment.addEdge(point, j);
                        nonEmbeddedEdges[point][j] = 0;
                        nonEmbeddedEdges[j][point] = 0;

                        segment.addContactPoint(point);
                        segment.addContactPoint(j);

                        segments.add(segment);
                    }
                }
            } else {
                if (!segmentedPoints[point]) {
                    SegmentGraph segment = new SegmentGraph(nPoints);
                    
                    findComplexSegment(segment, point, embeddedPoints,
                            segmentedPoints, nonEmbeddedEdges);
                    segmentedPoints[point] = true;

                    segments.add(segment);
                }
            } 
        }
        return segments;


    }  

    private void findComplexSegment (SegmentGraph segment, int point,
            boolean[] embeddedPoints, boolean[] segmentedPoints,
            int[][] nonEmbeddedEdges) {
        segmentedPoints[point] = true;
        for (int next = 0; next < nPoints; next++) {
            if (nonEmbeddedEdges[point][next] == 1) {

                segment.addEdge(point, next);

                nonEmbeddedEdges[point][next] = 0;
                nonEmbeddedEdges[next][point] = 0;

                if (embeddedPoints[next]) {
                    segment.addContactPoint(next);
                } else {

                    findComplexSegment(segment, next, embeddedPoints,
                            segmentedPoints, nonEmbeddedEdges);
                }
            }

        }
    }


    private void embedChain(SegmentGraph segmentToGetChain, 
            int[][] embeddedEdges, boolean[] embeddedPoints) {

        Vector<Integer> chain = segmentToGetChain.getChain();
        int[] chainEndpointsPositions = {-1, -1};
        Face newFace1 = new Face();
        Face newFace2 = new Face();
        boolean doCollect = false;

        for(Face face : faces) {

            if ((face.hasPoints(chain)) 
                    && (segmentToGetChain.canFitInto(face))) {
                Face collectingFace = newFace1;

                for(int point : face.getPoints()) {

                    if (chain.firstElement() == chain.lastElement()) {
                        collectingFace.addPoint(point);
                        continue;
                    }
 
                    if ((point == chain.firstElement())
                            ||(point == chain.lastElement())) {
                        collectingFace.addPoint(point);
                        if (collectingFace == newFace1) {
                            collectingFace = newFace2;
                        } else {
                            collectingFace = newFace1;
                        }
                        collectingFace.addPoint(point);
                        continue;
                    }
                    collectingFace.addPoint(point);

                }
                faces.removeElement(face);
                break;

            }
        }

        newFace1.completeNewFace(chain);
        newFace2.completeNewFace(chain);

        faces.add(newFace1);
        faces.add(newFace2);

        if (chain.firstElement() != chain.lastElement()) {
            embeddedEdges[chain.firstElement()][chain.lastElement()] = 1;
            embeddedEdges[chain.lastElement()][chain.firstElement()] = 1;
        }
        for (int i = 1; i < chain.size(); i++) {
            embeddedEdges[chain.elementAt(i)][chain.elementAt(i - 1)] = 1;
            embeddedEdges[chain.elementAt(i - 1)][chain.elementAt(i)] = 1;
        }

        for (int point : chain) {
            embeddedPoints[point] = true;
        }

    }



}