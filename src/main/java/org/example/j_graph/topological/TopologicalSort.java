package org.example.j_graph.topological;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.List;

public interface TopologicalSort<V, E> {

    /**
     * Returns the vertices of {@code graph} in topological order.
     *
     * @throws IllegalArgumentException if {@code graph} is {@code null} or undirected
     * @throws IllegalStateException    if {@code graph} contains a cycle
     */
    List<Vertex<V, E>> sort(Graph<V, E> graph);
}
