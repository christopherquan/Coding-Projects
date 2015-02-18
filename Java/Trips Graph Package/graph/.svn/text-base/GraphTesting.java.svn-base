package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Christopher Quan
 */
public class GraphTesting {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void addVertex() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        assertEquals("Initial graph has vertices", 1, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
        assertTrue(g.contains(1));
        assertEquals(1, g.maxVertex());
        g.add();
        g.add();
        assertEquals("Initial graph has vertices", 3, g.vertexSize());
        g.remove(2);
        assertEquals("Initial graph has vertices", 2, g.vertexSize());
        assertEquals(3, g.maxVertex());
        UndirectedGraph h = new UndirectedGraph();
        h.add();
        assertEquals("Initial graph has vertices", 1, h.vertexSize());
        assertEquals("Initial graph has edges", 0, h.edgeSize());
        assertTrue(h.contains(1));
        assertEquals(1, h.maxVertex());
        h.add();
        h.add();
        assertEquals("Initial graph has vertices", 3, h.vertexSize());
        h.remove(2);
        assertEquals("Initial graph has vertices", 2, h.vertexSize());
        assertEquals(3, h.maxVertex());
    }

    @Test
    public void addSides() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        assertEquals("Initial graph has edges", 0, g.edgeSize());
        g.add(1, 3);
        assertEquals("Initial graph has edges", 1, g.edgeSize());
        g.add(3, 1);
        assertEquals("Initial graph has edges", 2, g.edgeSize());
        g.add(1, 3);
        assertEquals("Initial graph has edges", 2, g.edgeSize());
        UndirectedGraph h = new UndirectedGraph();
        h.add();
        h.add();
        h.add();
        assertEquals("Initial graph has edges", 0, h.edgeSize());
        h.add(1, 3);
        assertEquals("Initial graph has edges", 1, h.edgeSize());
        h.add(3, 1);
        assertEquals("Initial graph has edges", 1, h.edgeSize());
        h.add(1, 3);
        assertEquals("Initial graph has edges", 1, h.edgeSize());
    }

    @Test
    public void removeTestDir() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 3);
        g.add(3, 1);
        g.add(2, 3);
        assertEquals(1, g.outDegree(3));
        g.add(3, 2);
        assertEquals(2, g.outDegree(3));
        assertEquals(2, g.inDegree(3));
        g.remove(3, 2);
        g.remove(3, 2);
        assertEquals(1, g.outDegree(3));
        assertEquals(4, g.vertexSize());
        g.remove(3);
        g.remove(10);
        assertEquals(0, g.edgeSize());
        assertEquals(3, g.vertexSize());
    }

    @Test
    public void removeTestUn() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add(1, 3);
        g.add(3, 1);
        g.add(2, 3);
        assertEquals(2, g.outDegree(3));
        g.add(3, 2);
        assertEquals(2, g.outDegree(3));
        assertEquals(2, g.inDegree(3));
        g.remove(3, 2);
        assertEquals(1, g.outDegree(3));
        assertEquals(3, g.vertexSize());
        g.remove(3);
        assertEquals(0, g.edgeSize());
        assertEquals(2, g.vertexSize());
    }

}
