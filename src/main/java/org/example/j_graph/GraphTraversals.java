package org.example.j_graph;

import java.util.*;

/**
 * Static utility class providing graph traversal algorithms.
 *
 * <p>All methods operate on {@link Graph} instances and are generic over vertex
 * element type {@code V} and edge element type {@code E}.  The class is
 * non-instantiable; use its static factory methods directly.
 */
public class GraphTraversals {

    private GraphTraversals() {
    }

    /**
     * Performs a depth-first search starting from {@code start} and returns the
     * DFS discovery forest: a map from each reachable vertex (excluding the start)
     * to the edge that first discovered it.
     *
     * <p>The traversal follows outgoing edges only. In an undirected graph this
     * reaches every vertex in the same connected component as {@code start};
     * in a directed graph it reaches every vertex on a directed path from
     * {@code start}.
     *
     * @param <V>   the type of vertex elements
     * @param <E>   the type of edge elements
     * @param graph the graph to traverse
     * @param start the vertex from which traversal begins
     * @return a map from each discovered vertex to its discovery edge
     * @throws IllegalArgumentException if {@code graph} or {@code start} is {@code null}
     */
    public static <V, E> Map<Vertex<V, E>, Edge<V, E>> dfs(Graph<V, E> graph, Vertex<V, E> start) {
        if (Objects.isNull(graph) || Objects.isNull(start)) {
            throw new IllegalArgumentException("Graph and start vertex must not be null");
        }
        Map<Vertex<V, E>, Edge<V, E>> forest = new HashMap<>();
        Set<Vertex<V, E>> visited = new HashSet<>();
        dfs(graph, start, visited, forest);
        return forest;
    }

    /** Recursive DFS helper; marks {@code origin} visited and explores its outgoing edges. */
    private static <V, E> void dfs(Graph<V, E> graph, Vertex<V, E> origin, Set<Vertex<V, E>> visited,
                                   Map<Vertex<V, E>, Edge<V, E>> forest) {
        visited.add(origin);
        for (Edge<V, E> edge : graph.outgoingEdges(origin)) {
            Vertex<V, E> destination = graph.opposite(origin, edge);
            if (visited.contains(destination)) {
                continue;
            }
            forest.put(destination, edge);
            dfs(graph, destination, visited, forest);
        }
    }
}
