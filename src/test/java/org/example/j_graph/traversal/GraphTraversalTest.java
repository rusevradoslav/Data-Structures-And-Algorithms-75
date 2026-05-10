package org.example.j_graph.traversal;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class GraphTraversalTest {

    // Undirected fixture graph:
    //
    //   Alice --- Bob --- Carol     Dan --- Eve
    //
    // Vertices : Alice, Bob, Carol, Dan, Eve
    // Edges    : (Alice,Bob,"AB"), (Bob,Carol,"BC"), (Dan,Eve,"DE")
    // Two disconnected components: {Alice,Bob,Carol} and {Dan,Eve}

    private GraphTraversal<String, String> traversal;
    private Graph<String, String> graph;
    private Vertex<String, String> alice;
    private Vertex<String, String> bob;
    private Vertex<String, String> carol;
    private Vertex<String, String> dan;
    private Vertex<String, String> eve;
    private Edge<String, String> edgeAB;
    private Edge<String, String> edgeBC;

    abstract GraphTraversal<String, String> createTraversal();

    @BeforeEach
    public void setUp() {
        traversal = createTraversal();
        graph = new Graph<>(false);
        alice = graph.insertVertex("Alice");
        bob = graph.insertVertex("Bob");
        carol = graph.insertVertex("Carol");
        dan = graph.insertVertex("Dan");
        eve = graph.insertVertex("Eve");
        edgeAB = graph.insertEdge(alice, bob, "AB");
        edgeBC = graph.insertEdge(bob, carol, "BC");
        graph.insertEdge(dan, eve, "DE");
    }

    // --- traverse ---

    @Test
    @DisplayName("From Alice: result map has exactly 2 entries (Bob and Carol)")
    public void testFromAliceMapSize() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, alice);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("From Alice: Bob is present in the result map")
    public void testFromAliceBobPresent() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, alice);
        assertTrue(result.containsKey(bob));
    }

    @Test
    @DisplayName("From Alice: Carol is present in the result map")
    public void testFromAliceCarolPresent() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, alice);
        assertTrue(result.containsKey(carol));
    }

    @Test
    @DisplayName("From Alice: Dan is absent (different component)")
    public void testFromAliceDanAbsent() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, alice);
        assertFalse(result.containsKey(dan));
    }

    @Test
    @DisplayName("From Alice: Eve is absent (different component)")
    public void testFromAliceEveAbsent() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, alice);
        assertFalse(result.containsKey(eve));
    }

    @Test
    @DisplayName("From Alice: start vertex Alice is NOT in the result map")
    public void testFromAliceStartNotInResult() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, alice);
        assertFalse(result.containsKey(alice));
    }

    @Test
    @DisplayName("From Alice: Bob's discovery edge is the (Alice,Bob) edge instance")
    public void testFromAliceBobDiscoveryEdge() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, alice);
        assertSame(edgeAB, result.get(bob));
    }

    @Test
    @DisplayName("From Alice: Carol's discovery edge is the (Bob,Carol) edge instance")
    public void testFromAliceCarolDiscoveryEdge() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, alice);
        assertSame(edgeBC, result.get(carol));
    }

    @Test
    @DisplayName("From Dan: result map has exactly 1 entry")
    public void testFromDanMapSize() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, dan);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("From Dan: Eve is present in the result map")
    public void testFromDanEvePresent() {
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(graph, dan);
        assertTrue(result.containsKey(eve));
    }

    @Test
    @DisplayName("Single-vertex graph: result map is empty")
    public void testSingleVertexGraph() {
        Graph<String, String> single = new Graph<>(false);
        Vertex<String, String> solo = single.insertVertex("Solo");
        assertTrue(traversal.traverse(single, solo).isEmpty());
    }

    @Test
    @DisplayName("Directed graph with cycle: terminates without infinite loop")
    public void testDirectedCycleTerminates() {
        Graph<String, String> cyclic = new Graph<>(true);
        Vertex<String, String> u = cyclic.insertVertex("U");
        Vertex<String, String> v = cyclic.insertVertex("V");
        Vertex<String, String> w = cyclic.insertVertex("W");
        cyclic.insertEdge(u, v, "UV");
        cyclic.insertEdge(v, w, "VW");
        cyclic.insertEdge(w, u, "WU");
        Map<Vertex<String, String>, Edge<String, String>> result = traversal.traverse(cyclic, u);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(v));
        assertTrue(result.containsKey(w));
    }

    @Test
    @DisplayName("Null graph throws IllegalArgumentException")
    public void testNullGraphThrows() {
        assertThrows(IllegalArgumentException.class, () -> traversal.traverse(null, alice));
    }

    @Test
    @DisplayName("Null start vertex throws IllegalArgumentException")
    public void testNullStartThrows() {
        assertThrows(IllegalArgumentException.class, () -> traversal.traverse(graph, null));
    }
}
