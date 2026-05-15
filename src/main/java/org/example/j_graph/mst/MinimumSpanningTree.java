package org.example.j_graph.mst;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;

import java.util.List;

/**
 * Contract for minimum spanning tree algorithms on undirected weighted graphs.
 *
 * <p>Implementations return a list of edges forming the MST — a spanning tree
 * whose total edge weight is minimal. For a connected graph with n vertices the
 * result always contains exactly n-1 edges. For disconnected graphs the result
 * spans only the component containing the arbitrary start vertex.
 *
 * @param <V> the type of element stored in vertices
 */
public interface MinimumSpanningTree<V> {

    /**
     * Computes a minimum spanning tree of the given undirected weighted graph.
     *
     * @param graph the graph to span; must not be {@code null} and must be undirected
     * @return a list of edges forming the MST; empty if the graph has no edges
     * @throws IllegalArgumentException if {@code graph} is {@code null} or directed
     */
    List<Edge<V, Integer>> mst(Graph<V, Integer> graph);
}