package org.example.j_graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a vertex in a {@link Graph}.
 *
 * <p>Each vertex stores an element of type {@code V} and two adjacency maps:
 * one for outgoing edges and one for incoming edges. In an undirected graph both
 * maps reference the <em>same</em> {@link HashMap} instance, so every insertion
 * or removal is automatically visible from both directions without any extra
 * branching. In a directed graph the two maps are independent.
 *
 * <p>The maps use the neighbouring {@code Vertex} as key and the connecting
 * {@link Edge} as value, giving O(1) average-case lookup for {@code getEdge}.
 *
 * @param <V> the type of element stored in this vertex
 * @param <E> the type of element stored on edges incident to this vertex
 */
public class Vertex<V, E> {

    private V element;

    /**
     * Maps each out-neighbour to the edge leading to it.
     * For undirected graphs this is the same object as {@code incoming}.
     */
    private Map<Vertex<V, E>, Edge<V, E>> outgoing;

    /**
     * Maps each in-neighbour to the edge coming from it.
     * For undirected graphs this is the same object as {@code outgoing}.
     */
    private Map<Vertex<V, E>, Edge<V, E>> incoming;

    /**
     * Constructs a new vertex holding {@code element}.
     *
     * <p>If {@code directed} is {@code false}, {@code outgoing} and {@code incoming}
     * reference the same {@link HashMap} instance (shared-map trick).
     * If {@code directed} is {@code true}, they are two separate maps.
     *
     * @param element  the element to store
     * @param directed {@code true} if this vertex belongs to a directed graph,
     *                 {@code false} if undirected
     */
    public Vertex(V element, boolean directed) {
        this.element = element;
        this.outgoing = new HashMap<>();
        this.incoming = !directed ? this.outgoing : new HashMap<>();
    }

    /**
     * Returns the element stored at this vertex.
     *
     * @return the vertex element
     */
    public V getElement() {
        return this.element;
    }

    /**
     * Returns the outgoing-edge map for this vertex.
     *
     * <p>In an undirected graph this is the same map returned by {@link #getIncoming()}.
     *
     * @return a mutable map from neighbouring vertex to outgoing edge
     */
    public Map<Vertex<V, E>, Edge<V, E>> getOutgoing() {
        return this.outgoing;
    }

    /**
     * Returns the incoming-edge map for this vertex.
     *
     * <p>In an undirected graph this is the same map returned by {@link #getOutgoing()}.
     *
     * @return a mutable map from neighbouring vertex to incoming edge
     */
    public Map<Vertex<V, E>, Edge<V, E>> getIncoming() {
        return this.incoming;
    }
}
