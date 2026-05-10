package org.example.j_graph.traversal;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.Map;

/**
 * Contract for graph traversal strategies.
 *
 * <p>Implementations explore a graph from a given start vertex and return the
 * discovery forest — a map from each reachable vertex (excluding the start) to
 * the edge that first reached it. The traversal order is algorithm-specific.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public interface GraphTraversal<V, E> {

    /**
     * Traverses the graph starting from {@code start} and returns the discovery forest.
     *
     * <p>The start vertex itself is not included in the returned map. Only vertices
     * reachable via outgoing edges are included, respecting graph directionality.
     *
     * @param graph the graph to traverse; must not be {@code null}
     * @param start the vertex from which traversal begins; must not be {@code null}
     * @return a map from each discovered vertex to its discovery edge
     * @throws IllegalArgumentException if {@code graph} or {@code start} is {@code null}
     */
    Map<Vertex<V, E>, Edge<V, E>> traverse(Graph<V, E> graph, Vertex<V, E> start);
}
