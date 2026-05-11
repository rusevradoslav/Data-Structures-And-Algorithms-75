package org.example.j_graph.closure;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FloydWarshallTransitiveClosureTest extends TransitiveClosureTest {

    @Override
    protected TransitiveClosure<String, String> createClosure() {
        return new FloydWarshallTransitiveClosure<>();
    }

    // --- closureAsGraph ---

    @Test
    @DisplayName("closureAsGraph: returns the same graph instance (mutates in place)")
    public void testClosureAsGraphReturnsSameInstance() {
        Graph<String, String> input = buildChain();
        Graph<String, String> result = new FloydWarshallTransitiveClosure<String, String>().closureAsGraph(input);
        assertSame(input, result);
    }

    @Test
    @DisplayName("closureAsGraph: transitive edge A->C added directly to the input graph")
    public void testClosureAsGraphTransitiveEdgeMutatesInput() {
        Graph<String, String> input = buildChain();
        Vertex<String, String> fa = null;
        Vertex<String, String> fc = null;
        for (Vertex<String, String> v : input.getVertices()) {
            if ("A".equals(v.getElement())) fa = v;
            if ("C".equals(v.getElement())) fc = v;
        }
        new FloydWarshallTransitiveClosure<String, String>().closureAsGraph(input);
        assertNotNull(input.getEdge(fa, fc));
    }

    @Test
    @DisplayName("closureAsGraph: input graph has exactly 3 edges after closure (AB, BC, AC)")
    public void testClosureAsGraphEdgeCount() {
        Graph<String, String> input = buildChain();
        new FloydWarshallTransitiveClosure<String, String>().closureAsGraph(input);
        assertEquals(3, input.numEdges());
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
