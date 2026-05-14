package org.example.j_graph.topological;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class TopologicalSortTest {

    // Fixture: linear chain  A --> B --> C
    private TopologicalSort<String, String> sort;
    private Graph<String, String> chain;
    private Vertex<String, String> a;
    private Vertex<String, String> b;
    private Vertex<String, String> c;

    // Fixture: diamond  A --> B --> D,  A --> C --> D
    private Graph<String, String> diamond;
    private Vertex<String, String> dA;
    private Vertex<String, String> dB;
    private Vertex<String, String> dC;
    private Vertex<String, String> dD;

    protected abstract TopologicalSort<String, String> createSort();

    @BeforeEach
    void setUp() {
        sort = createSort();

        chain = new Graph<>(true);
        a = chain.insertVertex("A");
        b = chain.insertVertex("B");
        c = chain.insertVertex("C");
        chain.insertEdge(a, b, "AB");
        chain.insertEdge(b, c, "BC");

        diamond = new Graph<>(true);
        dA = diamond.insertVertex("A");
        dB = diamond.insertVertex("B");
        dC = diamond.insertVertex("C");
        dD = diamond.insertVertex("D");
        diamond.insertEdge(dA, dB, "AB");
        diamond.insertEdge(dA, dC, "AC");
        diamond.insertEdge(dB, dD, "BD");
        diamond.insertEdge(dC, dD, "CD");
    }

    // --- guard conditions ---

    @Test
    @DisplayName("sort: null graph throws IllegalArgumentException")
    void testNullGraphThrows() {
        assertThrows(IllegalArgumentException.class, () -> sort.sort(null));
    }

    @Test
    @DisplayName("sort: undirected graph throws IllegalArgumentException")
    void testUndirectedGraphThrows() {
        Graph<String, String> undirected = new Graph<>(false);
        undirected.insertVertex("X");
        assertThrows(IllegalArgumentException.class, () -> sort.sort(undirected));
    }

    @Test
    @DisplayName("sort: graph with cycle throws IllegalStateException")
    void testCyclicGraphThrows() {
        Graph<String, String> cyclic = new Graph<>(true);
        Vertex<String, String> x = cyclic.insertVertex("X");
        Vertex<String, String> y = cyclic.insertVertex("Y");
        Vertex<String, String> z = cyclic.insertVertex("Z");
        cyclic.insertEdge(x, y, "XY");
        cyclic.insertEdge(y, z, "YZ");
        cyclic.insertEdge(z, x, "ZX");
        assertThrows(IllegalStateException.class, () -> sort.sort(cyclic));
    }

    // --- result size ---

    @Test
    @DisplayName("sort: result contains all vertices")
    void testResultContainsAllVertices() {
        List<Vertex<String, String>> result = sort.sort(chain);
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("sort: single vertex graph returns list of size 1")
    void testSingleVertex() {
        Graph<String, String> single = new Graph<>(true);
        Vertex<String, String> only = single.insertVertex("X");
        List<Vertex<String, String>> result = sort.sort(single);
        assertEquals(1, result.size());
        assertEquals(only, result.get(0));
    }

    @Test
    @DisplayName("sort: empty graph returns empty list")
    void testEmptyGraph() {
        List<Vertex<String, String>> result = sort.sort(new Graph<>(true));
        assertTrue(result.isEmpty());
    }

    // --- ordering correctness ---

    @Test
    @DisplayName("sort: linear chain respects A before B before C")
    void testLinearChainOrder() {
        List<Vertex<String, String>> result = sort.sort(chain);
        assertTrue(result.indexOf(a) < result.indexOf(b));
        assertTrue(result.indexOf(b) < result.indexOf(c));
    }

    @Test
    @DisplayName("sort: diamond — A is first, D is last")
    void testDiamondFirstAndLast() {
        List<Vertex<String, String>> result = sort.sort(diamond);
        assertEquals(dA, result.get(0));
        assertEquals(dD, result.get(result.size() - 1));
    }

    @Test
    @DisplayName("sort: diamond — every edge u->v has u before v")
    void testDiamondEdgeOrder() {
        assertTrue(isValidTopologicalOrder(sort.sort(diamond), diamond));
    }

    @Test
    @DisplayName("sort: disconnected graph — all vertices present, ordering valid")
    void testDisconnectedGraph() {
        Graph<String, String> g = new Graph<>(true);
        Vertex<String, String> x = g.insertVertex("X");
        Vertex<String, String> y = g.insertVertex("Y");
        g.insertVertex("Z");
        g.insertEdge(x, y, "XY");

        List<Vertex<String, String>> result = sort.sort(g);
        assertEquals(3, result.size());
        assertTrue(isValidTopologicalOrder(result, g));
    }

    // --- helper ---

    private boolean isValidTopologicalOrder(List<Vertex<String, String>> order, Graph<String, String> g) {
        for (Edge<String, String> edge : g.getEdges()) {
            Vertex<String, String>[] endpoints = edge.getEndpoints();
            int srcIdx = order.indexOf(endpoints[0]);
            int dstIdx = order.indexOf(endpoints[1]);
            if (srcIdx >= dstIdx) {
                return false;
            }
        }
        return true;
    }
}
