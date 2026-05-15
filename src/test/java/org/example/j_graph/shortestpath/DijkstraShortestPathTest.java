package org.example.j_graph.shortestpath;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DijkstraShortestPathTest {

    // Directed weighted graph:
    //
    //   A --1--> B --5--> D
    //   |        |        ^
    //   4        2        1
    //   v        v        |
    //   `------> C -------'
    //
    // Shortest from A: A=0, B=1, C=3 (A→B→C beats A→C), D=4 (A→B→C→D beats A→B→D)

    private DijkstraShortestPath<String> dijkstra;
    private Graph<String, Integer> graph;
    private Vertex<String, Integer> a;
    private Vertex<String, Integer> b;
    private Vertex<String, Integer> c;
    private Vertex<String, Integer> d;
    private Vertex<String, Integer> isolated;

    @BeforeEach
    public void setUp() {
        dijkstra = new DijkstraShortestPath<>();
        graph = new Graph<>(true);
        a = graph.insertVertex("A");
        b = graph.insertVertex("B");
        c = graph.insertVertex("C");
        d = graph.insertVertex("D");
        isolated = graph.insertVertex("Isolated");
        graph.insertEdge(a, b, 1);
        graph.insertEdge(a, c, 4);
        graph.insertEdge(b, c, 2);
        graph.insertEdge(b, d, 5);
        graph.insertEdge(c, d, 1);
    }

    // --- start vertex ---

    @Test
    @DisplayName("Start vertex always has distance zero")
    public void testStartVertexDistanceIsZero() {
        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(graph, a);
        assertEquals(0, distances.get(a));
    }

    // --- direct neighbour ---

    @Test
    @DisplayName("Direct neighbour distance equals its edge weight")
    public void testDirectNeighbourDistance() {
        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(graph, a);
        assertEquals(1, distances.get(b));
    }

    // --- shortest path selection ---

    @Test
    @DisplayName("Two-hop path chosen over longer direct edge")
    public void testTwoHopPathBeatsDirectEdge() {
        // A→C direct = 4; A→B→C = 1+2 = 3
        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(graph, a);
        assertEquals(3, distances.get(c));
    }

    @Test
    @DisplayName("Three-hop path chosen over two-hop path when cheaper")
    public void testThreeHopPathBeatsLongerTwoHop() {
        // A→B→C→D = 1+2+1 = 4; A→B→D = 1+5 = 6
        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(graph, a);
        assertEquals(4, distances.get(d));
    }

    // --- reachability ---

    @Test
    @DisplayName("All reachable vertices appear in the result map")
    public void testAllReachableVerticesPresent() {
        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(graph, a);
        assertTrue(distances.containsKey(a));
        assertTrue(distances.containsKey(b));
        assertTrue(distances.containsKey(c));
        assertTrue(distances.containsKey(d));
    }

    @Test
    @DisplayName("Unreachable vertex absent from result map")
    public void testUnreachableVertexAbsent() {
        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(graph, a);
        assertFalse(distances.containsKey(isolated));
    }

    // --- start from inner node ---

    @Test
    @DisplayName("Starting from inner node excludes vertices with no incoming path")
    public void testStartFromInnerNode() {
        // B has no path back to A or to isolated
        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(graph, b);
        assertEquals(0, distances.get(b));
        assertEquals(2, distances.get(c));  // B→C
        assertEquals(3, distances.get(d));  // B→C→D = 2+1
        assertFalse(distances.containsKey(a));
        assertFalse(distances.containsKey(isolated));
    }

    // --- directed: reverse edges not traversed ---

    @Test
    @DisplayName("Directed graph: starting from a sink yields only the start vertex")
    public void testSinkVertexReachesNobody() {
        // D has no outgoing edges
        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(graph, d);
        assertEquals(1, distances.size());
        assertEquals(0, distances.get(d));
    }

    // --- single vertex ---

    @Test
    @DisplayName("Single vertex with no edges: result contains only the start vertex")
    public void testSingleVertexGraph() {
        Graph<String, Integer> single = new Graph<>(true);
        Vertex<String, Integer> only = single.insertVertex("Only");

        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(single, only);
        assertEquals(1, distances.size());
        assertEquals(0, distances.get(only));
    }

    // --- undirected graph ---

    @Test
    @DisplayName("Undirected graph: edges traversed in both directions")
    public void testUndirectedGraph() {
        // X --3-- Y --2-- Z
        Graph<String, Integer> undirected = new Graph<>(false);
        Vertex<String, Integer> x = undirected.insertVertex("X");
        Vertex<String, Integer> y = undirected.insertVertex("Y");
        Vertex<String, Integer> z = undirected.insertVertex("Z");
        undirected.insertEdge(x, y, 3);
        undirected.insertEdge(y, z, 2);

        Map<Vertex<String, Integer>, Integer> distances = dijkstra.distances(undirected, z);
        assertEquals(0, distances.get(z));
        assertEquals(2, distances.get(y));
        assertEquals(5, distances.get(x));
    }

    // --- input validation ---

    @Test
    @DisplayName("Null graph throws IllegalArgumentException")
    public void testNullGraphThrows() {
        assertThrows(IllegalArgumentException.class, () -> dijkstra.distances(null, a));
    }

    @Test
    @DisplayName("Null start vertex throws IllegalArgumentException")
    public void testNullStartVertexThrows() {
        assertThrows(IllegalArgumentException.class, () -> dijkstra.distances(graph, null));
    }

    @Test
    @DisplayName("Negative edge weight throws IllegalArgumentException")
    public void testNegativeEdgeWeightThrows() {
        Graph<String, Integer> negative = new Graph<>(true);
        Vertex<String, Integer> u = negative.insertVertex("U");
        Vertex<String, Integer> v = negative.insertVertex("V");
        negative.insertEdge(u, v, -1);
        assertThrows(IllegalArgumentException.class, () -> dijkstra.distances(negative, u));
    }
}
