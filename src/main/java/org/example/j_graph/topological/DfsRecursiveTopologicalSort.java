package org.example.j_graph.topological;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Recursive DFS-based topological sort — produces a linear ordering of all vertices
 * in a directed acyclic graph (DAG) such that every edge u → v has u before v.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Run DFS from every unvisited vertex</li>
 *   <li>Add each vertex to the result in post-order (after all descendants finish)</li>
 *   <li>Reverse the result to obtain topological order</li>
 *   <li>Detect cycles via the {@code inProgress} set: a back edge (grey → grey) means a cycle</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *   A → B → C → D,  A → C
 *
 *   Post-order: [D, C, B, A]   →   reversed: [A, B, C, D]
 * </pre>
 *
 * <p>Time Complexity: O(V + E) — each vertex and edge visited at most once.
 *
 * <p>Space Complexity: O(V) — {@code done}, {@code inProgress}, result, and call stack
 * each hold at most V entries.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public class DfsRecursiveTopologicalSort<V, E> implements TopologicalSort<V, E> {

    /** {@inheritDoc} */
    @Override
    public List<Vertex<V, E>> sort(Graph<V, E> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Graph must be directed");
        }

        Set<Vertex<V, E>> done = new HashSet<>();
        Set<Vertex<V, E>> inProgress = new HashSet<>();
        List<Vertex<V, E>> result = new ArrayList<>();
        for (Vertex<V, E> vertex : graph.getVertices()) {
            if (!done.contains(vertex)) {
                dfs(vertex, done, inProgress, result);
            }
        }

        Collections.reverse(result);
        return result;
    }

    private void dfs(Vertex<V, E> vertex, Set<Vertex<V, E>> done, Set<Vertex<V, E>> inProgress,
                     List<Vertex<V, E>> result) {
        inProgress.add(vertex);
        for (Vertex<V, E> outgoingVertex : vertex.getOutgoing().keySet()) {
            if (inProgress.contains(outgoingVertex)) {
                throw new IllegalStateException("Cycle detected");
            }
            if (!done.contains(outgoingVertex)) {
                dfs(outgoingVertex, done, inProgress, result);
            }
        }
        inProgress.remove(vertex);
        done.add(vertex);
        result.add(vertex);
    }
}
