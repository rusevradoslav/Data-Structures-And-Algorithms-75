package org.example.j_graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphTest {

    // Undirected fixture graph:
    //
    //   Alice --- Bob --- Carol     Dan --- Eve
    //
    // Vertices : Alice, Bob, Carol, Dan, Eve
    // Edges    : (Alice,Bob,"AB"), (Bob,Carol,"BC"), (Dan,Eve,"DE") — edgeDE not stored as a field
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
        bob   = graph.insertVertex("Bob");
        carol = graph.insertVertex("Carol");
        dan   = graph.insertVertex("Dan");
        eve   = graph.insertVertex("Eve");
        edgeAB = graph.insertEdge(alice, bob,   "AB");
        edgeBC = graph.insertEdge(bob,   carol, "BC");
        graph.insertEdge(dan, eve, "DE");
    }

    // --- numVertices / numEdges / vertices / edges ---

    @Test
    @DisplayName("Empty graph: numVertices returns 0")
    public void testNumVerticesEmptyGraph() {
        Graph<String, String> empty = new Graph<>(false);
        assertEquals(0, empty.numVertices());
    }

    @Test
    @DisplayName("Empty graph: numEdges returns 0")
    public void testNumEdgesEmptyGraph() {
        Graph<String, String> empty = new Graph<>(false);
        assertEquals(0, empty.numEdges());
    }

    @Test
    @DisplayName("Empty graph: vertices() iterable is empty")
    public void testVerticesEmptyGraph() {
        Graph<String, String> empty = new Graph<>(false);
        assertFalse(empty.vertices().iterator().hasNext());
    }

    @Test
    @DisplayName("Empty graph: edges() iterable is empty")
    public void testEdgesEmptyGraph() {
        Graph<String, String> empty = new Graph<>(false);
        assertFalse(empty.edges().iterator().hasNext());
    }

    @Test
    @DisplayName("Fixture graph: numVertices returns 5")
    public void testNumVerticesFixtureGraph() {
        assertEquals(5, graph.numVertices());
    }

    @Test
    @DisplayName("Fixture graph: numEdges returns 3")
    public void testNumEdgesFixtureGraph() {
        assertEquals(3, graph.numEdges());
    }

    // --- insertVertex ---

    @Test
    @DisplayName("insertVertex: single insertion increases numVertices by 1")
    public void testInsertVertexIncreasesCount() {
        Graph<String, String> g = new Graph<>(false);
        g.insertVertex("X");
        assertEquals(1, g.numVertices());
    }

    @Test
    @DisplayName("insertVertex: inserted vertex's element is retrievable")
    public void testInsertVertexElementIsRetrievable() {
        assertEquals("Alice", alice.getElement());
    }

    @Test
    @DisplayName("insertVertex: multiple insertions all appear in vertices()")
    public void testInsertVertexMultipleInsertions() {
        List<Vertex<String, String>> found = new ArrayList<>();
        graph.vertices().forEach(found::add);
        assertTrue(found.contains(alice));
        assertTrue(found.contains(bob));
        assertTrue(found.contains(carol));
        assertTrue(found.contains(dan));
        assertTrue(found.contains(eve));
    }

    // --- insertEdge ---

    @Test
    @DisplayName("insertEdge: new edge appears in graph's edges() iterable")
    public void testInsertEdgeAppearsInEdgesList() {
        List<Edge<String, String>> found = new ArrayList<>();
        graph.edges().forEach(found::add);
        assertTrue(found.contains(edgeAB));
    }

    @Test
    @DisplayName("insertEdge: edge element is retrievable via getElement()")
    public void testInsertEdgeElementIsRetrievable() {
        assertEquals("AB", edgeAB.getElement());
    }

    @Test
    @DisplayName("insertEdge (undirected): edge reachable from both endpoints via outgoingEdges and incomingEdges")
    public void testInsertEdgeUndirectedSharedMap() {
        List<Edge<String, String>> outAlice = new ArrayList<>();
        List<Edge<String, String>> inAlice  = new ArrayList<>();
        List<Edge<String, String>> outBob   = new ArrayList<>();
        List<Edge<String, String>> inBob    = new ArrayList<>();
        graph.outgoingEdges(alice).forEach(outAlice::add);
        graph.incomingEdges(alice).forEach(inAlice::add);
        graph.outgoingEdges(bob).forEach(outBob::add);
        graph.incomingEdges(bob).forEach(inBob::add);
        assertTrue(outAlice.contains(edgeAB));
        assertTrue(inAlice.contains(edgeAB));
        assertTrue(outBob.contains(edgeAB));
        assertTrue(inBob.contains(edgeAB));
    }

    @Test
    @DisplayName("insertEdge (directed): edge in u.outgoingEdges and v.incomingEdges only")
    public void testInsertEdgeDirectedCorrectDirection() {
        Graph<String, String> dg = new Graph<>(true);
        Vertex<String, String> u = dg.insertVertex("U");
        Vertex<String, String> v = dg.insertVertex("V");
        Edge<String, String> e = dg.insertEdge(u, v, "UV");

        List<Edge<String, String>> outU = new ArrayList<>();
        List<Edge<String, String>> inU  = new ArrayList<>();
        List<Edge<String, String>> outV = new ArrayList<>();
        List<Edge<String, String>> inV  = new ArrayList<>();
        dg.outgoingEdges(u).forEach(outU::add);
        dg.incomingEdges(u).forEach(inU::add);
        dg.outgoingEdges(v).forEach(outV::add);
        dg.incomingEdges(v).forEach(inV::add);

        assertTrue(outU.contains(e));
        assertTrue(inV.contains(e));
        assertFalse(inU.contains(e));
        assertFalse(outV.contains(e));
    }

    @Test
    @DisplayName("insertEdge: inserting duplicate edge throws IllegalArgumentException")
    public void testInsertEdgeDuplicateThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.insertEdge(alice, bob, "duplicate"));
    }

    // --- getEdge ---

    @Test
    @DisplayName("getEdge: returns the edge for a connected pair")
    public void testGetEdgeConnectedPair() {
        assertSame(edgeAB, graph.getEdge(alice, bob));
    }

    @Test
    @DisplayName("getEdge: returns null for an unconnected pair")
    public void testGetEdgeUnconnectedPair() {
        assertNull(graph.getEdge(alice, carol));
    }

    @Test
    @DisplayName("getEdge (undirected): getEdge(u,v) and getEdge(v,u) return the same Edge instance")
    public void testGetEdgeUndirectedSymmetric() {
        assertSame(graph.getEdge(alice, bob), graph.getEdge(bob, alice));
    }

    @Test
    @DisplayName("getEdge (directed): getEdge(v,u) returns null when only u->v exists")
    public void testGetEdgeDirectedNotReversed() {
        Graph<String, String> dg = new Graph<>(true);
        Vertex<String, String> u = dg.insertVertex("U");
        Vertex<String, String> v = dg.insertVertex("V");
        dg.insertEdge(u, v, "UV");
        assertNull(dg.getEdge(v, u));
    }

    // --- endVertices ---

    @Test
    @DisplayName("endVertices: returns array with both endpoints in insertion order")
    public void testEndVerticesInsertionOrder() {
        Vertex<String, String>[] endpoints = graph.endVertices(edgeAB);
        assertSame(alice, endpoints[0]);
        assertSame(bob,   endpoints[1]);
    }

    // --- opposite ---

    @Test
    @DisplayName("opposite: given origin, returns destination")
    public void testOppositeFromOrigin() {
        assertSame(bob, graph.opposite(alice, edgeAB));
    }

    @Test
    @DisplayName("opposite: given destination, returns origin")
    public void testOppositeFromDestination() {
        assertSame(alice, graph.opposite(bob, edgeAB));
    }

    @Test
    @DisplayName("opposite: throws IllegalArgumentException if vertex is not an endpoint")
    public void testOppositeNotAnEndpointThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.opposite(carol, edgeAB));
    }

    // --- outDegree / inDegree ---

    @Test
    @DisplayName("outDegree / inDegree (undirected): outDegree == inDegree for every vertex")
    public void testDegreeUndirectedSymmetric() {
        assertEquals(graph.outDegree(alice), graph.inDegree(alice));
        assertEquals(graph.outDegree(bob),   graph.inDegree(bob));
        assertEquals(graph.outDegree(carol), graph.inDegree(carol));
        assertEquals(graph.outDegree(dan),   graph.inDegree(dan));
        assertEquals(graph.outDegree(eve),   graph.inDegree(eve));
    }

    @Test
    @DisplayName("outDegree / inDegree (directed): insert u->v, verify degrees on both sides")
    public void testDegreeDirectedAsymmetric() {
        Graph<String, String> dg = new Graph<>(true);
        Vertex<String, String> u = dg.insertVertex("U");
        Vertex<String, String> v = dg.insertVertex("V");
        dg.insertEdge(u, v, "UV");
        assertEquals(1, dg.outDegree(u));
        assertEquals(0, dg.inDegree(u));
        assertEquals(0, dg.outDegree(v));
        assertEquals(1, dg.inDegree(v));
    }

    @Test
    @DisplayName("outDegree / inDegree: isolated vertex has degree 0 on both sides")
    public void testDegreeIsolatedVertex() {
        Vertex<String, String> iso = graph.insertVertex("Isolated");
        assertEquals(0, graph.outDegree(iso));
        assertEquals(0, graph.inDegree(iso));
    }

    // --- outgoingEdges / incomingEdges ---

    @Test
    @DisplayName("outgoingEdges (undirected): vertex with multiple edges contains all of them")
    public void testOutgoingEdgesMultipleEdges() {
        List<Edge<String, String>> out = new ArrayList<>();
        graph.outgoingEdges(bob).forEach(out::add);
        assertTrue(out.contains(edgeAB));
        assertTrue(out.contains(edgeBC));
    }

    @Test
    @DisplayName("outgoingEdges / incomingEdges (directed): differ correctly")
    public void testEdgesDirectedSeparation() {
        Graph<String, String> dg = new Graph<>(true);
        Vertex<String, String> u = dg.insertVertex("U");
        Vertex<String, String> v = dg.insertVertex("V");
        Edge<String, String> e = dg.insertEdge(u, v, "UV");

        List<Edge<String, String>> outU = new ArrayList<>();
        List<Edge<String, String>> inU  = new ArrayList<>();
        List<Edge<String, String>> outV = new ArrayList<>();
        List<Edge<String, String>> inV  = new ArrayList<>();
        dg.outgoingEdges(u).forEach(outU::add);
        dg.incomingEdges(u).forEach(inU::add);
        dg.outgoingEdges(v).forEach(outV::add);
        dg.incomingEdges(v).forEach(inV::add);

        assertTrue(outU.contains(e));
        assertFalse(inU.contains(e));
        assertFalse(outV.contains(e));
        assertTrue(inV.contains(e));
    }

    @Test
    @DisplayName("outgoingEdges / incomingEdges: isolated vertex returns empty iterables")
    public void testEdgesIsolatedVertexEmpty() {
        Vertex<String, String> iso = graph.insertVertex("Isolated");
        assertFalse(graph.outgoingEdges(iso).iterator().hasNext());
        assertFalse(graph.incomingEdges(iso).iterator().hasNext());
    }

    // --- removeEdge ---

    @Test
    @DisplayName("removeEdge: edge no longer present in graph's edges() iterable")
    public void testRemoveEdgeRemovedFromEdgesList() {
        graph.removeEdge(edgeAB);
        List<Edge<String, String>> remaining = new ArrayList<>();
        graph.edges().forEach(remaining::add);
        assertFalse(remaining.contains(edgeAB));
    }

    @Test
    @DisplayName("removeEdge: getEdge(u,v) returns null after removal")
    public void testRemoveEdgeGetEdgeReturnsNull() {
        graph.removeEdge(edgeAB);
        assertNull(graph.getEdge(alice, bob));
    }

    @Test
    @DisplayName("removeEdge (undirected): both endpoints' neighbour maps cleared")
    public void testRemoveEdgeUndirectedBothMapsCleared() {
        graph.removeEdge(edgeAB);
        assertNull(graph.getEdge(alice, bob));
        assertNull(graph.getEdge(bob, alice));
    }

    @Test
    @DisplayName("removeEdge (undirected): both endpoints' degrees decrease by 1")
    public void testRemoveEdgeUndirectedDegreeDecreases() {
        int aliceBefore = graph.outDegree(alice);
        int bobBefore   = graph.outDegree(bob);
        graph.removeEdge(edgeAB);
        assertEquals(aliceBefore - 1, graph.outDegree(alice));
        assertEquals(bobBefore   - 1, graph.outDegree(bob));
    }

    @Test
    @DisplayName("removeEdge (directed): appropriate side degree decreases")
    public void testRemoveEdgeDirectedDegreeDecreases() {
        Graph<String, String> dg = new Graph<>(true);
        Vertex<String, String> u = dg.insertVertex("U");
        Vertex<String, String> v = dg.insertVertex("V");
        Edge<String, String> e = dg.insertEdge(u, v, "UV");
        dg.removeEdge(e);
        assertEquals(0, dg.outDegree(u));
        assertEquals(0, dg.inDegree(v));
    }

    @Test
    @DisplayName("removeEdge: other edges on the same vertices are unaffected")
    public void testRemoveEdgeOtherEdgesUnaffected() {
        graph.removeEdge(edgeAB);
        assertSame(edgeBC, graph.getEdge(bob, carol));
        assertEquals(1, graph.outDegree(bob));
    }

    // --- removeVertex ---

    @Test
    @DisplayName("removeVertex: vertex no longer present in graph's vertices() iterable")
    public void testRemoveVertexRemovedFromVerticesList() {
        graph.removeVertex(alice);
        List<Vertex<String, String>> remaining = new ArrayList<>();
        graph.vertices().forEach(remaining::add);
        assertFalse(remaining.contains(alice));
    }

    @Test
    @DisplayName("removeVertex: all incident edges removed from graph's edges() iterable (3+ edges)")
    public void testRemoveVertexIncidentEdgesRemoved() {
        Vertex<String, String> frank = graph.insertVertex("Frank");
        Vertex<String, String> grace = graph.insertVertex("Grace");
        Edge<String, String> bobFrank = graph.insertEdge(bob, frank, "BF");
        Edge<String, String> bobGrace = graph.insertEdge(bob, grace, "BG");

        graph.removeVertex(bob);

        List<Edge<String, String>> remaining = new ArrayList<>();
        graph.edges().forEach(remaining::add);
        assertFalse(remaining.contains(edgeAB));
        assertFalse(remaining.contains(edgeBC));
        assertFalse(remaining.contains(bobFrank));
        assertFalse(remaining.contains(bobGrace));
    }

    @Test
    @DisplayName("removeVertex: other vertices' neighbour maps no longer reference removed vertex")
    public void testRemoveVertexNeighbourMapsCleared() {
        graph.removeVertex(alice);
        assertNull(graph.getEdge(bob, alice));
    }

    @Test
    @DisplayName("removeVertex (directed): incoming edges are also removed")
    public void testRemoveVertexDirectedIncomingEdgesRemoved() {
        Graph<String, String> dg = new Graph<>(true);
        Vertex<String, String> u = dg.insertVertex("U");
        Vertex<String, String> v = dg.insertVertex("V");
        Vertex<String, String> w = dg.insertVertex("W");

        dg.insertEdge(u, w, "UW");   // w has incoming from u
        dg.insertEdge(v, w, "VW");   // w has incoming from v

        dg.removeVertex(w);

        assertEquals(0, dg.numEdges());
        assertEquals(0, dg.outDegree(u));
        assertEquals(0, dg.outDegree(v));
    }

    // --- isDirected ---

    @Test
    @DisplayName("isDirected: returns false for undirected graph")
    public void testIsDirectedUndirected() {
        assertFalse(graph.isDirected());
    }

    @Test
    @DisplayName("isDirected: returns true for directed graph")
    public void testIsDirectedDirected() {
        Graph<String, String> dg = new Graph<>(true);
        assertTrue(dg.isDirected());
    }

    // --- fixture-based scenario ---

    @Test
    @DisplayName("Fixture scenario: Alice/Bob/Carol/Dan/Eve graph structural verification")
    public void testFixtureFullStructuralVerification() {
        assertEquals(5, graph.numVertices());
        assertEquals(3, graph.numEdges());
        assertEquals(2, graph.outDegree(bob));
        assertEquals(1, graph.outDegree(alice));
        assertEquals(1, graph.outDegree(dan));
        assertNull(graph.getEdge(alice, dan));
        assertNull(graph.getEdge(dan, alice));
        assertNull(graph.getEdge(alice, eve));
        assertNull(graph.getEdge(carol, dan));
    }

    // --- input validation ---

    @Test
    @DisplayName("getEdge: null first argument throws IllegalArgumentException")
    public void testGetEdgeNullUThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.getEdge(null, bob));
    }

    @Test
    @DisplayName("getEdge: null second argument throws IllegalArgumentException")
    public void testGetEdgeNullVThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.getEdge(alice, null));
    }

    @Test
    @DisplayName("opposite: null vertex argument throws IllegalArgumentException")
    public void testOppositeNullVertexThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.opposite(null, edgeAB));
    }

    @Test
    @DisplayName("outDegree: null vertex throws IllegalArgumentException")
    public void testOutDegreeNullVertexThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.outDegree(null));
    }

    @Test
    @DisplayName("inDegree: null vertex throws IllegalArgumentException")
    public void testInDegreeNullVertexThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.inDegree(null));
    }

    @Test
    @DisplayName("outgoingEdges: null vertex throws IllegalArgumentException")
    public void testOutgoingEdgesNullVertexThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.outgoingEdges(null));
    }

    @Test
    @DisplayName("incomingEdges: null vertex throws IllegalArgumentException")
    public void testIncomingEdgesNullVertexThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.incomingEdges(null));
    }


    @Test
    @DisplayName("removeVertex: null vertex throws IllegalArgumentException")
    public void testRemoveVertexNullVertexThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.removeVertex(null));
    }

    @Test
    @DisplayName("removeEdge: null edge throws IllegalArgumentException")
    public void testRemoveEdgeNullEdgeThrows() {
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(null));
    }
}