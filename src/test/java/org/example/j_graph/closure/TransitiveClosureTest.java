package org.example.j_graph.closure;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class TransitiveClosureTest {

    // Directed fixture graph:
    //
    //   A --> B --> C     D
    //
    // Vertices (insertion order): A=0, B=1, C=2, D=3
    // Edges    : (A,B), (B,C)
    // Transitive closure: A reaches {B,C}, B reaches {C}, C reaches {}, D reaches {}

    private TransitiveClosure<String, String> closure;
    private Graph<String, String> graph;
    private Vertex<String, String> a;
    private Vertex<String, String> b;
    private Vertex<String, String> c;
    private Vertex<String, String> d;

    protected abstract TransitiveClosure<String, String> createClosure();

    @BeforeEach
    public void setUp() {
        closure = createClosure();
        graph = new Graph<>(true);
        a = graph.insertVertex("A");
        b = graph.insertVertex("B");
        c = graph.insertVertex("C");
        d = graph.insertVertex("D");
        graph.insertEdge(a, b, "AB");
        graph.insertEdge(b, c, "BC");
    }

    // --- closureAsMatrix ---

    @Test
    @DisplayName("closureAsMatrix: A reaches B (direct edge)")
    public void testMatrixAReachesB() {
        boolean[][] matrix = closure.closureAsMatrix(graph);
        assertTrue(matrix[0][1]);
    }

    @Test
    @DisplayName("closureAsMatrix: A reaches C (transitive)")
    public void testMatrixAReachesC() {
        boolean[][] matrix = closure.closureAsMatrix(graph);
        assertTrue(matrix[0][2]);
    }

    @Test
    @DisplayName("closureAsMatrix: B reaches C (direct edge)")
    public void testMatrixBReachesC() {
        boolean[][] matrix = closure.closureAsMatrix(graph);
        assertTrue(matrix[1][2]);
    }

    @Test
    @DisplayName("closureAsMatrix: A does not reach itself")
    public void testMatrixDiagonalFalse() {
        boolean[][] matrix = closure.closureAsMatrix(graph);
        assertFalse(matrix[0][0]);
        assertFalse(matrix[1][1]);
        assertFalse(matrix[2][2]);
        assertFalse(matrix[3][3]);
    }

    @Test
    @DisplayName("closureAsMatrix: C reaches nothing")
    public void testMatrixCReachesNothing() {
        boolean[][] matrix = closure.closureAsMatrix(graph);
        assertFalse(matrix[2][0]);
        assertFalse(matrix[2][1]);
        assertFalse(matrix[2][3]);
    }

    @Test
    @DisplayName("closureAsMatrix: isolated D reaches nothing")
    public void testMatrixIsolatedDReachesNothing() {
        boolean[][] matrix = closure.closureAsMatrix(graph);
        assertFalse(matrix[3][0]);
        assertFalse(matrix[3][1]);
        assertFalse(matrix[3][2]);
    }

    @Test
    @DisplayName("closureAsMatrix: null graph throws IllegalArgumentException")
    public void testMatrixNullGraphThrows() {
        assertThrows(IllegalArgumentException.class, () -> closure.closureAsMatrix(null));
    }

    // --- closureAsMap ---

    @Test
    @DisplayName("closureAsMap: A's reachable set contains B and C")
    public void testMapAReachesBAndC() {
        Map<Vertex<String, String>, Set<Vertex<String, String>>> map = closure.closureAsMap(graph);
        assertTrue(map.get(a).contains(b));
        assertTrue(map.get(a).contains(c));
    }

    @Test
    @DisplayName("closureAsMap: A's reachable set has size 2")
    public void testMapAReachableSetSize() {
        Map<Vertex<String, String>, Set<Vertex<String, String>>> map = closure.closureAsMap(graph);
        assertEquals(2, map.get(a).size());
    }

    @Test
    @DisplayName("closureAsMap: B's reachable set contains only C")
    public void testMapBReachesOnlyC() {
        Map<Vertex<String, String>, Set<Vertex<String, String>>> map = closure.closureAsMap(graph);
        assertEquals(1, map.get(b).size());
        assertTrue(map.get(b).contains(c));
    }

    @Test
    @DisplayName("closureAsMap: C's reachable set is empty")
    public void testMapCReachableSetEmpty() {
        Map<Vertex<String, String>, Set<Vertex<String, String>>> map = closure.closureAsMap(graph);
        assertTrue(map.get(c).isEmpty());
    }

    @Test
    @DisplayName("closureAsMap: isolated D's reachable set is empty")
    public void testMapIsolatedDReachableSetEmpty() {
        Map<Vertex<String, String>, Set<Vertex<String, String>>> map = closure.closureAsMap(graph);
        assertTrue(map.get(d).isEmpty());
    }

    @Test
    @DisplayName("closureAsMap: result contains an entry for every vertex")
    public void testMapContainsAllVertices() {
        Map<Vertex<String, String>, Set<Vertex<String, String>>> map = closure.closureAsMap(graph);
        assertEquals(4, map.size());
        assertTrue(map.containsKey(a));
        assertTrue(map.containsKey(b));
        assertTrue(map.containsKey(c));
        assertTrue(map.containsKey(d));
    }

    @Test
    @DisplayName("closureAsMap: null graph throws IllegalArgumentException")
    public void testMapNullGraphThrows() {
        assertThrows(IllegalArgumentException.class, () -> closure.closureAsMap(null));
    }
}
