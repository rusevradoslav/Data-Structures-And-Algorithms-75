package org.example.j_graph.topological;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.*;

/**
 * Kahn's BFS-based topological sort — produces a linear ordering of all vertices
 * in a directed acyclic graph (DAG) by repeatedly removing vertices with no remaining
 * incoming dependencies.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Compute the in-degree of every vertex</li>
 *   <li>Enqueue all vertices with in-degree 0 (no dependencies)</li>
 *   <li>Dequeue a vertex, add it to the result, decrement each neighbor's in-degree;
 *       enqueue any neighbor whose in-degree reaches 0</li>
 *   <li>Detect cycles passively: if the result is smaller than the total vertex count,
 *       some vertices were never reachable with in-degree 0 — a cycle exists</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *   A → B → D
 *   A → C → D
 *
 *   In-degrees: A=0, B=1, C=1, D=2
 *   Queue: [A] → dequeue A, decrement B and C → Queue: [B, C]
 *          → dequeue B, decrement D (in-degree 1) → Queue: [C]
 *          → dequeue C, decrement D (in-degree 0) → Queue: [D]
 *          → dequeue D → result: [A, B, C, D]
 * </pre>
 *
 * <p>Time Complexity: O(V + E) — each vertex enqueued once, each edge visited once.
 *
 * <p>Space Complexity: O(V) — in-degree map, queue, and result each hold at most V entries.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public class KahnTopologicalSort<V, E> implements TopologicalSort<V, E> {

    /** {@inheritDoc} */
    @Override
    public List<Vertex<V, E>> sort(Graph<V, E> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be directed");
        }
        Iterable<Vertex<V, E>> vertices = graph.getVertices();
        Map<Vertex<V, E>, Integer> indegreesVertexMap = new HashMap<>();
        Deque<Vertex<V, E>> queue = new ArrayDeque<>();
        vertices.forEach(vertex -> {
            int incomingEdges = vertex.getIncoming().size();
            indegreesVertexMap.put(vertex, incomingEdges);
            if (incomingEdges == 0) {
                queue.offer(vertex);
            }
        });

        List<Vertex<V, E>> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            Map<Vertex<V, E>, Edge<V, E>> outgoing = vertex.getOutgoing();
            for (Vertex<V, E> outgoingVertex : outgoing.keySet()) {
                Integer counter = indegreesVertexMap.get(outgoingVertex) - 1;
                indegreesVertexMap.put(outgoingVertex, counter);
                if (counter == 0) {
                    queue.offer(outgoingVertex);
                }
            }
            result.add(vertex);
        }
        if (result.size() != indegreesVertexMap.size()) {
            throw new IllegalStateException("Cycle detected");
        }
        return result;
    }
}
