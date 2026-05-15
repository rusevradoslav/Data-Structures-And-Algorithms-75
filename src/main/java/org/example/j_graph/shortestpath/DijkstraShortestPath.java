package org.example.j_graph.shortestpath;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Dijkstra's single-source shortest-path algorithm — returns a map from every reachable
 * vertex to its minimum total edge weight from the source.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Seed the priority queue with the start vertex at distance 0</li>
 *   <li>Repeatedly poll the vertex with the smallest known distance</li>
 *   <li>Skip vertices already finalised (lazy-deletion of stale queue entries)</li>
 *   <li>Relax each outgoing edge and enqueue the neighbour if a shorter path is found</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *   A -(1)- B, A -(5)- C, B -(2)- C
 *
 *   distances from A: {A=0, B=1, C=3}   (A-B-C costs 3, beats direct A-C = 5)
 * </pre>
 *
 * <p>Time Complexity: O((V + E) log V) — each edge produces at most one priority queue insertion.
 *
 * <p>Space Complexity: O(V + E) — distances map, visited set, and priority queue.
 *
 * @param <V> the type of element stored in vertices
 */
public class DijkstraShortestPath<V> implements ShortestPath<V> {

    /** {@inheritDoc} */
    @Override
    public Map<Vertex<V, Integer>, Integer> distances(Graph<V, Integer> graph, Vertex<V, Integer> start) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (Objects.isNull(start)) {
            throw new IllegalArgumentException("Start vertex cannot be null");
        }

        Map<Vertex<V, Integer>, Integer> distances = new HashMap<>();
        Set<Vertex<V, Integer>> visited = new HashSet<>();
        PriorityQueue<VertexDistance<V>> queue = new PriorityQueue<>(Comparator.comparing(VertexDistance::distance));

        distances.put(start, 0);
        queue.offer(new VertexDistance<>(start, 0));

        while (!queue.isEmpty()) {
            VertexDistance<V> current = queue.poll();
            Vertex<V, Integer> currentVertex = current.vertex();
            if (visited.contains(currentVertex)) {
                continue;
            }
            visited.add(currentVertex);


            for (Edge<V, Integer> edge : graph.getOutgoingEdges(currentVertex)) {
                int edgeWeight = edge.getElement();
                if (edgeWeight < 0) {
                    throw new IllegalArgumentException("Edge weight cannot be negative");
                }

                Vertex<V, Integer> neighbour = graph.opposite(currentVertex, edge);
                if (visited.contains(neighbour)) {
                    continue;
                }


                int currentVertexDistance = distances.get(currentVertex);
                int newDistance = currentVertexDistance + edgeWeight;

                if (newDistance < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, newDistance);
                    queue.offer(new VertexDistance<>(neighbour, newDistance));
                }
            }
        }

        return distances;
    }

    private record VertexDistance<V>(Vertex<V, Integer> vertex, int distance) {
    }
}
