package org.example.j_graph;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

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

    @Getter
    private final V element;

    /**
     * Maps each out-neighbour to the edge leading to it.
     * For undirected graphs this is the same object as {@code incoming}.
     */
    @Getter
    private final Map<Vertex<V, E>, Edge<V, E>> outgoing;

    /**
     * Maps each in-neighbour to the edge coming from it.
     * For undirected graphs this is the same object as {@code outgoing}.
     */
    @Getter
    private final Map<Vertex<V, E>, Edge<V, E>> incoming;

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

}
