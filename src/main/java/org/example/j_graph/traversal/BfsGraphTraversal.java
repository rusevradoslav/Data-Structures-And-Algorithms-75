package org.example.j_graph.traversal;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Breadth-first search (BFS) graph traversal — returns the BFS discovery forest:
 * a map from each reachable vertex (excluding the start) to the edge that first discovered it.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Mark the start vertex visited and enqueue it</li>
 *   <li>While the queue is non-empty, dequeue a vertex and inspect its outgoing edges</li>
 *   <li>For each unvisited neighbour, record the discovery edge, mark it visited, and enqueue it</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *   Alice --- Bob --- Carol     Dan --- Eve
 *
 *   BFS from Alice: {Bob -> (Alice,Bob), Carol -> (Bob,Carol)}
 * </pre>
 *
 * <p>Time Complexity: O(V + E) — each vertex and edge visited at most once.
 *
 * <p>Space Complexity: O(V) — visited set and queue each hold at most V vertices.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public class BfsGraphTraversal<V, E> implements GraphTraversal<V, E> {

    /** {@inheritDoc} */
    @Override
    public Map<Vertex<V, E>, Edge<V, E>> traverse(Graph<V, E> graph, Vertex<V, E> start) {
        if (Objects.isNull(graph) || Objects.isNull(start)) {
            throw new IllegalArgumentException("Graph and start vertex must not be null");
        }
        Map<Vertex<V, E>, Edge<V, E>> forest = new HashMap<>();
        Set<Vertex<V, E>> visited = new HashSet<>();

        ArrayDeque<Vertex<V, E>> queue = new ArrayDeque<>();
        visited.add(start);
        queue.offer(start);
        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            for (Edge<V, E> edge : graph.outgoingEdges(vertex)) {
                Vertex<V, E> destination = graph.opposite(vertex, edge);
                if (visited.contains(destination)) {
                    continue;
                }
                visited.add(destination);
                forest.put(destination, edge);
                queue.offer(destination);
            }
        }

        return forest;
    }
}
