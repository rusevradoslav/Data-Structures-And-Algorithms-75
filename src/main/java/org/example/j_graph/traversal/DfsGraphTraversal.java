package org.example.j_graph.traversal;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Depth-first search (DFS) graph traversal — returns the DFS discovery forest:
 * a map from each reachable vertex (excluding the start) to the edge that first discovered it.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Mark the current vertex visited</li>
 *   <li>For each outgoing edge, skip already-visited neighbours</li>
 *   <li>For each unvisited neighbour, record the discovery edge and recurse</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *   Alice --- Bob --- Carol     Dan --- Eve
 *
 *   DFS from Alice: {Bob -> (Alice,Bob), Carol -> (Bob,Carol)}
 * </pre>
 *
 * <p>Time Complexity: O(V + E) — each vertex and edge visited at most once.
 *
 * <p>Space Complexity: O(V) — visited set and implicit call stack each hold at most V frames.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public class DfsGraphTraversal<V, E> implements GraphTraversal<V, E> {

    /** {@inheritDoc} */
    @Override
    public Map<Vertex<V, E>, Edge<V, E>> traverse(Graph<V, E> graph, Vertex<V, E> start) {
        if (Objects.isNull(graph) || Objects.isNull(start)) {
            throw new IllegalArgumentException("Graph and start vertex must not be null");
        }
        Map<Vertex<V, E>, Edge<V, E>> forest = new HashMap<>();
        Set<Vertex<V, E>> visited = new HashSet<>();
        dfs(graph, start, visited, forest);
        return forest;
    }

    private void dfs(Graph<V, E> graph, Vertex<V, E> origin, Set<Vertex<V, E>> visited,
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
