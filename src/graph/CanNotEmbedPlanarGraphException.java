package graph;


public class CanNotEmbedPlanarGraphException extends Exception{
    private SegmentGraph segment;

    CanNotEmbedPlanarGraphException(SegmentGraph segment) {
        this.segment = segment;

    };

    public String toString() {
        return "CAN NOT EMBED GRAPH\n" + segment;

    }

}