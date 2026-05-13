package org.example.j_graph;

import java.util.ArrayList;
import java.util.Arrays;
import lombok.Getter;

/**
 * Represents an edge in a {@link Graph}.
 *
 * <p>Each edge stores an element of type {@code E} and a fixed-size array of
 * exactly two endpoints: {@code endpoints[0]} is the origin vertex and
 * {@code endpoints[1]} is the destination vertex. For undirected graphs the
 * origin/destination distinction is purely the insertion order; neither direction
 * carries semantic meaning.
 *
 * @param <V> the type of element stored in the vertices this edge connects
 * @param <E> the type of element stored in this edge
 */
public class Edge<V, E> {

    @Getter
    private final E element;

    private final Vertex<V, E>[] endpoints;

    /**
     * Constructs a new edge connecting {@code origin} to {@code destination}.
     *
     * @param origin      the source vertex
     * @param destination the target vertex
     * @param element     the element to store on this edge
     */
    @SuppressWarnings("unchecked")
    public Edge(Vertex<V, E> origin, Vertex<V, E> destination, E element) {
        this.endpoints = new Vertex[]{origin, destination};
        this.element = element;
    }

    /**
     * Returns the two endpoint vertices of this edge as a two-element array.
     *
     * <p>Index 0 is the origin vertex and index 1 is the destination vertex,
     * in the order they were supplied to the constructor.
     *
     * @return array of length 2 containing {@code [origin, destination]}
     */
    public Vertex<V, E>[] getEndpoints() {
        return this.endpoints.clone();
    }
}
