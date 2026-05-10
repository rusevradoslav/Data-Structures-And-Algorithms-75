package org.example.j_graph.path;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.example.j_graph.traversal.BfsGraphTraversal;
import org.example.j_graph.traversal.DfsGraphTraversal;
import org.example.j_graph.traversal.GraphTraversal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphPathsTest {

    // Undirected fixture graph:
    //
    //   Alice --- Bob --- Carol     Dan --- Eve
    //
    // Vertices : Alice, Bob, Carol, Dan, Eve
    // Edges    : (Alice,Bob,"AB"), (Bob,Carol,"BC"), (Dan,Eve,"DE")
    // Two disconnected components: {Alice,Bob,Carol} and {Dan,Eve}

    private GraphTraversal<String, String> dfs;
    private GraphTraversal<String, String> bfs;
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
        dfs = new DfsGraphTraversal<>();
        bfs = new BfsGraphTraversal<>();
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

    // --- constructPath (DFS forest) ---

    @Test
    @DisplayName("DFS: path Alice -> Bob contains exactly edgeAB")
    public void testDfsPathAliceToBob() {
        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, bob, forest);
        assertEquals(1, path.size());
        assertSame(edgeAB, path.get(0));
    }

    @Test
    @DisplayName("DFS: path Alice -> Carol is [edgeAB, edgeBC]")
    public void testDfsPathAliceToCarol() {
        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, carol, forest);
        assertEquals(2, path.size());
        assertSame(edgeAB, path.get(0));
        assertSame(edgeBC, path.get(1));
    }

    @Test
    @DisplayName("DFS: path Alice -> Alice (same vertex) is empty")
    public void testDfsPathSameVertex() {
        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, alice, forest);
        assertTrue(path.isEmpty());
    }

    @Test
    @DisplayName("DFS: path Alice -> Dan (different component) is empty")
    public void testDfsPathDifferentComponent() {
        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, dan, forest);
        assertTrue(path.isEmpty());
    }

    // --- constructPath (BFS forest) ---

    @Test
    @DisplayName("BFS: path Alice -> Bob contains exactly edgeAB")
    public void testBfsPathAliceToBob() {
        Map<Vertex<String, String>, Edge<String, String>> forest = bfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, bob, forest);
        assertEquals(1, path.size());
        assertSame(edgeAB, path.get(0));
    }

    @Test
    @DisplayName("BFS: path Alice -> Carol is [edgeAB, edgeBC]")
    public void testBfsPathAliceToCarol() {
        Map<Vertex<String, String>, Edge<String, String>> forest = bfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, carol, forest);
        assertEquals(2, path.size());
        assertSame(edgeAB, path.get(0));
        assertSame(edgeBC, path.get(1));
    }

    @Test
    @DisplayName("BFS: path Alice -> Alice (same vertex) is empty")
    public void testBfsPathSameVertex() {
        Map<Vertex<String, String>, Edge<String, String>> forest = bfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, alice, forest);
        assertTrue(path.isEmpty());
    }

    @Test
    @DisplayName("BFS: path Alice -> Dan (different component) is empty")
    public void testBfsPathDifferentComponent() {
        Map<Vertex<String, String>, Edge<String, String>> forest = bfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, dan, forest);
        assertTrue(path.isEmpty());
    }

    // --- directed graphs ---

    @Test
    @DisplayName("Directed graph: forward path exists, reverse path returns empty")
    public void testDirectedForwardPathExistsReverseEmpty() {
        Graph<String, String> directed = new Graph<>(true);
        Vertex<String, String> u = directed.insertVertex("U");
        Vertex<String, String> v = directed.insertVertex("V");
        Edge<String, String> edgeUV = directed.insertEdge(u, v, "UV");

        Map<Vertex<String, String>, Edge<String, String>> forestFromU = bfs.traverse(directed, u);
        List<Edge<String, String>> forwardPath = GraphPaths.constructPath(directed, u, v, forestFromU);
        assertEquals(1, forwardPath.size());
        assertSame(edgeUV, forwardPath.get(0));

        Map<Vertex<String, String>, Edge<String, String>> forestFromV = bfs.traverse(directed, v);
        List<Edge<String, String>> reversePath = GraphPaths.constructPath(directed, v, u, forestFromV);
        assertTrue(reversePath.isEmpty());
    }

    // --- multi-hop paths ---

    @Test
    @DisplayName("DFS: 3-edge path correctly reconstructed in order")
    public void testDfsThreeEdgePath() {
        Vertex<String, String> frank = graph.insertVertex("Frank");
        Edge<String, String> edgeCF = graph.insertEdge(carol, frank, "CF");

        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, alice, frank, forest);
        assertEquals(3, path.size());
        assertSame(edgeAB, path.get(0));
        assertSame(edgeBC, path.get(1));
        assertSame(edgeCF, path.get(2));
    }

    // --- contract edge cases ---

    @Test
    @DisplayName("Forest from Alice: Carol -> Alice path returns empty (start cannot be path end)")
    public void testPathToTraversalRootReturnsEmpty() {
        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        List<Edge<String, String>> path = GraphPaths.constructPath(graph, carol, alice, forest);
        assertTrue(path.isEmpty());
    }

    // --- input validation ---

    @Test
    @DisplayName("constructPath: null graph throws IllegalArgumentException")
    public void testNullGraphThrows() {
        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        assertThrows(IllegalArgumentException.class, () -> GraphPaths.constructPath(null, alice, carol, forest));
    }

    @Test
    @DisplayName("constructPath: null start throws IllegalArgumentException")
    public void testNullStartThrows() {
        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        assertThrows(IllegalArgumentException.class, () -> GraphPaths.constructPath(graph, null, carol, forest));
    }

    @Test
    @DisplayName("constructPath: null end throws IllegalArgumentException")
    public void testNullEndThrows() {
        Map<Vertex<String, String>, Edge<String, String>> forest = dfs.traverse(graph, alice);
        assertThrows(IllegalArgumentException.class, () -> GraphPaths.constructPath(graph, alice, null, forest));
    }

    @Test
    @DisplayName("constructPath: null forest throws IllegalArgumentException")
    public void testNullForestThrows() {
        assertThrows(IllegalArgumentException.class, () -> GraphPaths.constructPath(graph, alice, carol, null));
    }
}
