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

public class DijkstraShortestPath<V> implements ShortestPath<V> {

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
