package org.example.j_graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTraversalsTest {

    // Undirected fixture graph:
    //
    //   Alice --- Bob --- Carol     Dan --- Eve
    //
    // Vertices : Alice, Bob, Carol, Dan, Eve
    // Edges    : (Alice,Bob,"AB"), (Bob,Carol,"BC"), (Dan,Eve,"DE")
    // Two disconnected components: {Alice,Bob,Carol} and {Dan,Eve}

    private Graph<String, String> graph;
    private Vertex<String, String> alice;
    private Vertex<String, String> bob;
    private Vertex<String, String> carol;
    private Vertex<String, String> dan;
    private Vertex<String, String> eve;
    private Edge<String, String> edgeAB;
    private Edge<String, String> edgeBC;

    @BeforeEach
    public void setUp() {
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

    // --- dfs ---

    @Test
    @DisplayName("DFS from Alice: result map has exactly 2 entries (Bob and Carol)")
    public void testDfsFromAliceMapSize() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, alice);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("DFS from Alice: Bob is present in the result map")
    public void testDfsFromAliceBobPresent() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, alice);
        assertTrue(result.containsKey(bob));
    }

    @Test
    @DisplayName("DFS from Alice: Carol is present in the result map")
    public void testDfsFromAliceCarolPresent() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, alice);
        assertTrue(result.containsKey(carol));
    }

    @Test
    @DisplayName("DFS from Alice: Dan is absent (different component)")
    public void testDfsFromAliceDanAbsent() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, alice);
        assertFalse(result.containsKey(dan));
    }

    @Test
    @DisplayName("DFS from Alice: Eve is absent (different component)")
    public void testDfsFromAliceEveAbsent() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, alice);
        assertFalse(result.containsKey(eve));
    }

    @Test
    @DisplayName("DFS from Alice: start vertex Alice is NOT in the result map")
    public void testDfsFromAliceStartNotInResult() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, alice);
        assertFalse(result.containsKey(alice));
    }

    @Test
    @DisplayName("DFS from Alice: Bob's discovery edge is the (Alice,Bob) edge instance")
    public void testDfsFromAliceBobDiscoveryEdge() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, alice);
        assertSame(edgeAB, result.get(bob));
    }

    @Test
    @DisplayName("DFS from Alice: Carol's discovery edge is the (Bob,Carol) edge instance")
    public void testDfsFromAliceCarolDiscoveryEdge() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, alice);
        assertSame(edgeBC, result.get(carol));
    }

    @Test
    @DisplayName("DFS from Dan: result map has exactly 1 entry (Eve)")
    public void testDfsFromDanMapSize() {
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(graph, dan);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(eve));
    }

    @Test
    @DisplayName("DFS on single-vertex graph: result map is empty")
    public void testDfsSingleVertexGraph() {
        Graph<String, String> single = new Graph<>(false);
        Vertex<String, String> solo = single.insertVertex("Solo");
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(single, solo);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("DFS on directed graph with cycle: terminates without infinite loop")
    public void testDfsDirectedCycleTerminates() {
        Graph<String, String> cyclic = new Graph<>(true);
        Vertex<String, String> u = cyclic.insertVertex("U");
        Vertex<String, String> v = cyclic.insertVertex("V");
        Vertex<String, String> w = cyclic.insertVertex("W");
        cyclic.insertEdge(u, v, "UV");
        cyclic.insertEdge(v, w, "VW");
        cyclic.insertEdge(w, u, "WU");
        Map<Vertex<String, String>, Edge<String, String>> result = GraphTraversals.dfs(cyclic, u);
        assertEquals(2, result.size());
        assertTrue(result.containsKey(v));
        assertTrue(result.containsKey(w));
    }

    @Test
    @DisplayName("DFS: null graph throws IllegalArgumentException")
    public void testDfsNullGraphThrows() {
        assertThrows(IllegalArgumentException.class, () -> GraphTraversals.dfs(null, alice));
    }

    @Test
    @DisplayName("DFS: null start vertex throws IllegalArgumentException")
    public void testDfsNullStartThrows() {
        assertThrows(IllegalArgumentException.class, () -> GraphTraversals.dfs(graph, null));
    }
}
