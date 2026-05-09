package org.example.j_graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A generic graph supporting both directed and undirected modes via the
 * Adjacency Map representation
 *
 * <p>Each {@link Vertex} maintains its own map of neighbours, giving O(1)
 * average-case performance for edge lookup, insertion, and removal. The graph
 * also maintains master {@link ArrayList} lists of all vertices and edges for
 * O(1) iteration start; removal from these lists is O(n) by design.
 *
 * <p>For undirected graphs the outgoing and incoming maps inside each
 * {@link Vertex} are the same {@link java.util.HashMap} instance, so a
 * single {@code put}/{@code remove} keeps both directions consistent
 * without any extra branching in the mutators.
 *
 * @param <V> the type of element stored in vertices
 * @param <E> the type of element stored in edges
 */
public class Graph<V, E> {

    private final boolean directed;
    private final List<Vertex<V, E>> vertices = new ArrayList<>();
    private final List<Edge<V, E>> edges = new ArrayList<>();

    /**
     * Constructs an empty graph.
     *
     * @param directed {@code true} for a directed graph, {@code false} for undirected
     */
    public Graph(boolean directed) {
        this.directed = directed;
    }

    /**
     * Returns whether this graph is directed.
     *
     * @return {@code true} if directed, {@code false} if undirected
     */
    public boolean isDirected() {
        return this.directed;
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return vertex count
     */
    public int numVertices() {
        return vertices.size();
    }

    /**
     * Returns an iterable collection of all vertices in the graph.
     *
     * @return all vertices
     */
    public Iterable<Vertex<V, E>> vertices() {
        return this.vertices;
    }

    /**
     * Returns the number of edges in the graph.
     *
     * @return edge count
     */
    public int numEdges() {
        return edges.size();
    }

    /**
     * Returns an iterable collection of all edges in the graph.
     *
     * @return all edges
     */
    public Iterable<Edge<V, E>> edges() {
        return this.edges;
    }

    /**
     * Returns the edge from vertex {@code u} to vertex {@code v}, or {@code null}
     * if no such edge exists.
     *
     * <p>For undirected graphs {@code getEdge(u, v)} and {@code getEdge(v, u)}
     * return the same {@link Edge} instance.
     *
     * @param u the source vertex
     * @param v the destination vertex
     * @return the edge from {@code u} to {@code v}, or {@code null}
     * @throws IllegalArgumentException if either vertex is {@code null}
     */
    public Edge<V, E> getEdge(Vertex<V, E> u, Vertex<V, E> v) {
        if (Objects.isNull(u) || Objects.isNull(v)) {
            throw new IllegalArgumentException("Vertices must not be null");
        }
        return u.getOutgoing().get(v);
    }

    /**
     * Returns the two endpoint vertices of edge {@code e} as a two-element array.
     *
     * <p>Index 0 is the origin and index 1 is the destination, in insertion order.
     *
     * @param e the edge to query
     * @return array {@code [origin, destination]}
     */
    public Vertex<V, E>[] endVertices(Edge<V, E> e) {
        if (Objects.isNull(e)) {
            throw new IllegalArgumentException("Edges must not be null");
        }
        return e.getEndpoints();
    }

    /**
     * Returns the vertex of edge {@code e} that is opposite to {@code v}.
     *
     * @param v a vertex that must be an endpoint of {@code e}
     * @param e the edge to query
     * @return the endpoint of {@code e} that is not {@code v}
     * @throws IllegalArgumentException if either argument is {@code null}, or if
     *                                  {@code v} is not an endpoint of {@code e}
     */
    public Vertex<V, E> opposite(Vertex<V, E> v, Edge<V, E> e) {

        Vertex<V, E>[] endPoints = e.getEndpoints();
        Vertex<V, E> origin = endPoints[0];
        Vertex<V, E> destination = endPoints[1];

        if (v == origin) {
            return destination;
        }
        if (v == destination) {
            return origin;
        }

        throw new IllegalArgumentException("Vertex is not an endpoint of this edge");
    }

    /**
     * Returns the number of outgoing edges from vertex {@code v}.
     *
     * <p>For undirected graphs this equals {@link #inDegree(Vertex)}.
     *
     * @param v the vertex to query
     * @return out-degree of {@code v}
     * @throws IllegalArgumentException if {@code v} is {@code null}
     */
    public int outDegree(Vertex<V, E> v) {
        if (Objects.isNull(v)) {
            throw new IllegalArgumentException("Vertex must not be null");
        }
        return v.getOutgoing().size();
    }

    /**
     * Returns the number of incoming edges to vertex {@code v}.
     *
     * <p>For undirected graphs this equals {@link #outDegree(Vertex)}.
     *
     * @param v the vertex to query
     * @return in-degree of {@code v}
     * @throws IllegalArgumentException if {@code v} is {@code null}
     */
    public int inDegree(Vertex<V, E> v) {
        if (Objects.isNull(v)) {
            throw new IllegalArgumentException("Vertex must not be null");
        }
        return directed ? v.getIncoming().size() : v.getOutgoing().size();
    }

    /**
     * Returns an iterable collection of all outgoing edges from vertex {@code v}.
     *
     * @param v the vertex to query
     * @return outgoing edges of {@code v}
     * @throws IllegalArgumentException if {@code v} is {@code null}
     */
    public Iterable<Edge<V, E>> outgoingEdges(Vertex<V, E> v) {
        if (Objects.isNull(v)) {
            throw new IllegalArgumentException("Vertex must not be null");
        }
        return v.getOutgoing().values();
    }

    /**
     * Returns an iterable collection of all incoming edges to vertex {@code v}.
     *
     * @param v the vertex to query
     * @return incoming edges of {@code v}
     * @throws IllegalArgumentException if {@code v} is {@code null}
     */
    public Iterable<Edge<V, E>> incomingEdges(Vertex<V, E> v) {
        if (Objects.isNull(v)) {
            throw new IllegalArgumentException("Vertex must not be null");
        }
        return directed ? v.getIncoming().values() : v.getOutgoing().values();
    }

    /**
     * Creates a new vertex storing {@code element} and adds it to the graph.
     *
     * @param element the element for the new vertex
     * @return the newly created vertex
     * @throws IllegalArgumentException if {@code element} is {@code null}
     */
    public Vertex<V, E> insertVertex(V element) {
        Vertex<V, E> vertex = new Vertex<>(element, directed);
        vertices.add(vertex);
        return vertex;
    }

    /**
     * Creates a new edge from {@code u} to {@code v} storing {@code element}
     * and adds it to the graph.
     *
     * <p>For undirected graphs the edge is reachable from both endpoints via
     * {@link #outgoingEdges(Vertex)} and {@link #incomingEdges(Vertex)}.
     *
     * @param u       the source vertex
     * @param v       the destination vertex
     * @param element the element for the new edge
     * @return the newly created edge
     * @throws IllegalArgumentException if an edge between {@code u} and {@code v}
     *                                  already exists
     */
    public Edge<V, E> insertEdge(Vertex<V, E> u, Vertex<V, E> v, E element) {
        if (Objects.isNull(u) || Objects.isNull(v)) {
            throw new IllegalArgumentException("Vertex and edge must not be null");
        }

        Edge<V, E> edge = u.getOutgoing().get(v);
        if (Objects.nonNull(edge)) {
            throw new IllegalArgumentException("Edge already exists");
        }
        edge = new Edge<>(u, v, element);
        u.getOutgoing().put(v, edge);
        if (directed) {
            v.getIncoming().put(u, edge);
        } else {
            v.getOutgoing().put(u, edge);
        }
        edges.add(edge);
        return edge;
    }

    /**
     * Removes vertex {@code v} and all its incident edges from the graph.
     *
     * <p>Incident edges are removed via {@link #removeEdge(Edge)} to keep all
     * neighbour maps consistent. A defensive copy of the edge collections is
     * made before iteration to avoid {@link java.util.ConcurrentModificationException}.
     *
     * @param v the vertex to remove
     * @throws IllegalArgumentException if {@code v} is {@code null}
     */
    public void removeVertex(Vertex<V, E> v) {
        if (Objects.isNull(v)) {
            throw new IllegalArgumentException("Vertex must not be null");
        }

        List<Edge<V, E>> outgoingEdges = new ArrayList<>(v.getOutgoing().values());
        for (Edge<V, E> outgoingEdge : outgoingEdges) {
            removeEdge(outgoingEdge);
        }

        if (directed) {
            List<Edge<V, E>> incomingEdges = new ArrayList<>(v.getIncoming().values());
            for (Edge<V, E> incomingEdge : incomingEdges) {
                removeEdge(incomingEdge);
            }
        }
        vertices.remove(v);
    }

    /**
     * Removes edge {@code e} from the graph and updates both endpoint vertices'
     * adjacency maps.
     *
     * @param e the edge to remove
     * @throws IllegalArgumentException if {@code e} is {@code null}
     */
    public void removeEdge(Edge<V, E> e) {
        if (Objects.isNull(e)) {
            throw new IllegalArgumentException("Edge must not be null");
        }

        Vertex<V, E>[] endPoints = e.getEndpoints();
        Vertex<V, E> firstEndpoint = endPoints[0];
        Vertex<V, E> secondEndpoint = endPoints[1];
        firstEndpoint.getOutgoing().remove(secondEndpoint);
        if (directed) {
            secondEndpoint.getIncoming().remove(firstEndpoint);
        } else {
            secondEndpoint.getOutgoing().remove(firstEndpoint);
        }
        edges.remove(e);
    }
}
