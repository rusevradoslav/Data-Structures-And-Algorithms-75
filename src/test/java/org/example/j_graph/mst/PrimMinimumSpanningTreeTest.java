package org.example.j_graph.mst;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimMinimumSpanningTreeTest {

    // Undirected weighted graph:
    //
    //   A --1-- B
    //   |       |
    //   4       2
    //   |       |
    //   C --3-- D
    //
    // MST: A-B(1), B-D(2), D-C(3) — total weight 6
    // edgeAC(4) is the heaviest edge and forms a cycle — excluded

    private PrimMinimumSpanningTree<String> prim;
    private Graph<String, Integer> graph;
    private Vertex<String, Integer> a;
    private Vertex<String, Integer> b;
    private Vertex<String, Integer> c;
    private Vertex<String, Integer> d;
    private Edge<String, Integer> edgeAB;
    private Edge<String, Integer> edgeAC;
    private Edge<String, Integer> edgeBD;
    private Edge<String, Integer> edgeCD;

    @BeforeEach
    public void setUp() {
        prim = new PrimMinimumSpanningTree<>();
        graph = new Graph<>(false);
        a = graph.insertVertex("A");
        b = graph.insertVertex("B");
        c = graph.insertVertex("C");
        d = graph.insertVertex("D");
        edgeAB = graph.insertEdge(a, b, 1);
        edgeAC = graph.insertEdge(a, c, 4);
        edgeBD = graph.insertEdge(b, d, 2);
        edgeCD = graph.insertEdge(c, d, 3);
    }

    // --- result size ---

    @Test
    @DisplayName("MST of n-vertex connected graph has n-1 edges")
    public void testMstSizeIsVerticesMinus1() {
        List<Edge<String, Integer>> result = prim.mst(graph);
        assertEquals(3, result.size());
    }

    // --- edge selection ---

    @Test
    @DisplayName("MST contains the three cheapest edges that span all vertices")
    public void testMstContainsMinWeightEdges() {
        List<Edge<String, Integer>> result = prim.mst(graph);
        assertTrue(result.contains(edgeAB));
        assertTrue(result.contains(edgeBD));
        assertTrue(result.contains(edgeCD));
    }

    @Test
    @DisplayName("MST excludes the heaviest edge that would form a cycle")
    public void testMstExcludesHeaviestCycleEdge() {
        List<Edge<String, Integer>> result = prim.mst(graph);
        assertFalse(result.contains(edgeAC));
    }

    @Test
    @DisplayName("MST total weight is minimal")
    public void testMstTotalWeight() {
        List<Edge<String, Integer>> result = prim.mst(graph);
        int total = result.stream().mapToInt(Edge::getElement).sum();
        assertEquals(6, total);
    }

    // --- edge cases ---

    @Test
    @DisplayName("Empty graph returns empty list")
    public void testEmptyGraph() {
        Graph<String, Integer> empty = new Graph<>(false);
        assertTrue(prim.mst(empty).isEmpty());
    }

    @Test
    @DisplayName("Single vertex with no edges returns empty list")
    public void testSingleVertex() {
        Graph<String, Integer> single = new Graph<>(false);
        single.insertVertex("Only");
        assertTrue(prim.mst(single).isEmpty());
    }

    @Test
    @DisplayName("Two vertices connected by one edge: MST contains that edge")
    public void testTwoVertices() {
        Graph<String, Integer> two = new Graph<>(false);
        Vertex<String, Integer> u = two.insertVertex("U");
        Vertex<String, Integer> v = two.insertVertex("V");
        Edge<String, Integer> edge = two.insertEdge(u, v, 7);

        List<Edge<String, Integer>> result = prim.mst(two);
        assertEquals(1, result.size());
        assertTrue(result.contains(edge));
    }

    // --- disconnected graph ---

    @Test
    @DisplayName("Disconnected graph: MST spans only the component containing the start vertex")
    public void testDisconnectedGraph() {
        // E and F form a separate component — unreachable from A
        Vertex<String, Integer> e = graph.insertVertex("E");
        Vertex<String, Integer> f = graph.insertVertex("F");
        graph.insertEdge(e, f, 1);

        List<Edge<String, Integer>> result = prim.mst(graph);
        assertEquals(3, result.size()); // spans {A, B, C, D} only
    }

    // --- equal-weight edges ---

    @Test
    @DisplayName("Equal-weight edges: MST still has n-1 edges with correct total weight")
    public void testEqualWeightEdges() {
        Graph<String, Integer> triangle = new Graph<>(false);
        Vertex<String, Integer> x = triangle.insertVertex("X");
        Vertex<String, Integer> y = triangle.insertVertex("Y");
        Vertex<String, Integer> z = triangle.insertVertex("Z");
        triangle.insertEdge(x, y, 5);
        triangle.insertEdge(y, z, 5);
        triangle.insertEdge(x, z, 5);

        List<Edge<String, Integer>> result = prim.mst(triangle);
        assertEquals(2, result.size());
        assertEquals(10, result.stream().mapToInt(Edge::getElement).sum());
    }

    // --- input validation ---

    @Test
    @DisplayName("Null graph throws IllegalArgumentException")
    public void testNullGraphThrows() {
        assertThrows(IllegalArgumentException.class, () -> prim.mst(null));
    }

    @Test
    @DisplayName("Directed graph throws IllegalArgumentException")
    public void testDirectedGraphThrows() {
        Graph<String, Integer> directed = new Graph<>(true);
        directed.insertVertex("A");
        assertThrows(IllegalArgumentException.class, () -> prim.mst(directed));
    }
}
