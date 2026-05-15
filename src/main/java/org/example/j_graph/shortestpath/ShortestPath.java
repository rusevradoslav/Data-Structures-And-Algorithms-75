package org.example.j_graph.shortestpath;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.Map;

/**
 * Contract for single-source shortest-path algorithms on weighted graphs.
 *
 * <p>Implementations compute the shortest distance from a given start vertex to every
 * reachable vertex. Unreachable vertices are absent from the result map.
 *
 * @param <V> the type of element stored in vertices
 */
public interface ShortestPath<V> {

    /**
     * Computes the shortest distance from {@code start} to every reachable vertex.
     *
     * @param graph the graph to search; must not be {@code null}
     * @param start the source vertex; must not be {@code null}
     * @return a map from each reachable vertex to its shortest distance from {@code start}
     * @throws IllegalArgumentException if {@code graph} or {@code start} is {@code null},
     *                                  or if any edge weight is negative
     */
    Map<Vertex<V, Integer>, Integer> distances(Graph<V, Integer> graph, Vertex<V, Integer> start);
}
