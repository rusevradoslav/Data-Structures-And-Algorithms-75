package org.example.j_graph.closure;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class DfsTransitiveClosureTest extends TransitiveClosureTest {

    @Override
    protected TransitiveClosure<String, String> createClosure() {
        return new DfsTransitiveClosure<>();
    }

    // --- closureAsGraph ---

    @Test
    @DisplayName("closureAsGraph: returns a new graph instance, original is not modified")
    public void testClosureAsGraphReturnsNewInstance() {
        Graph<String, String> input = buildChain();
        Graph<String, String> result = new DfsTransitiveClosure<String, String>().closureAsGraph(input);
        assertNotSame(input, result);
    }

    @Test
    @DisplayName("closureAsGraph: result has the same number of vertices as the input")
    public void testClosureAsGraphVertexCount() {
        Graph<String, String> result = new DfsTransitiveClosure<String, String>().closureAsGraph(buildChain());
        assertEquals(4, result.numVertices());
    }

    @Test
    @DisplayName("closureAsGraph: transitive edge A->C is present in the result")
    public void testClosureAsGraphTransitiveEdge() {
        Graph<String, String> result = new DfsTransitiveClosure<String, String>().closureAsGraph(buildChain());
        Vertex<String, String> newA = null;
        Vertex<String, String> newC = null;
        for (Vertex<String, String> v : result.getVertices()) {
            if ("A".equals(v.getElement())) newA = v;
            if ("C".equals(v.getElement())) newC = v;
        }
        assertNotNull(result.getEdge(newA, newC));
    }

    @Test
    @DisplayName("closureAsGraph: result graph has exactly 3 edges (AB, BC, AC)")
    public void testClosureAsGraphEdgeCount() {
        Graph<String, String> result = new DfsTransitiveClosure<String, String>().closureAsGraph(buildChain());
        assertEquals(3, result.numEdges());
    }

    private Graph<String, String> buildChain() {
        Graph<String, String> g = new Graph<>(true);
        Vertex<String, String> a = g.insertVertex("A");
        Vertex<String, String> b = g.insertVertex("B");
        Vertex<String, String> c = g.insertVertex("C");
        g.insertVertex("D");
        g.insertEdge(a, b, "AB");
        g.insertEdge(b, c, "BC");
        return g;
    }
}
