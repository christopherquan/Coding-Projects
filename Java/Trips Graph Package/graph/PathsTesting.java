package graph;

import org.junit.Test;
import static org.junit.Assert.*;
import static java.util.Arrays.asList;


public class PathsTesting {
    class VideoGraphPaths extends SimpleShortestPaths {

        public VideoGraphPaths(Graph g, int source, int dest) {
            super(g, source, dest);
        }

        @Override
        public double getWeight(int u, int v) {
            if (u == 4 && v == 2) {
                return 12.2;
            } else if (u == 4 && v == 5) {
                return 11.2;
            } else if (u == 2 && v == 3) {
                return 6.5;
            } else if (u == 4 && v == 3) {
                return 102;
            } else if (u == 5 && v == 3) {
                return 9.1;
            } else if (u == 5 && v == 6) {
                return 30;
            } else if (u == 4 && v == 7) {
                return 1;
            } else if (u == 7 && v == 8) {
                return 104;
            } else if (u == 6 && v == 9) {
                return 104;
            }
            return 1000000;
        }

        @Override
        protected double estimatedDistance(int v) {
            if (v == 4) {
                return 102;
            } else if (v == 2) {
                return 4;
            } else if (v == 5) {
                return 5.1;
            } else if (v == 6) {
                return 40;
            } else if (v == 7) {
                return 100000;
            } else if (v == 8) {
                return 100000;
            } else if (v == 9) {
                return 100000;
            } else if (v == 1) {
                return 100000;
            }
            return 0;
        }

    }

    @Test
    public void testWeights() {
        Graph videoGraph = new DirectedGraph();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add(4, 2);
        videoGraph.add(4, 5);
        videoGraph.add(2, 3);
        videoGraph.add(4, 3);
        videoGraph.add(5, 3);
        videoGraph.add(5, 6);
        videoGraph.add(4, 7);
        videoGraph.add(7, 8);
        videoGraph.add(6, 9);
        VideoGraphPaths vgp = new VideoGraphPaths(videoGraph, 4, 3);
        vgp.setPaths();
        assertTrue(vgp.getSource() == 4);
        assertTrue(vgp.getDest() == 3);
        assertTrue(vgp.pathTo().equals(asList(4, 2, 3)));
        assertTrue(vgp.getPredecessor(6) != 0);
        assertTrue(vgp.getPredecessor(9) == 0);
        assertTrue(vgp.getPredecessor(8) == 0);
    }
}
