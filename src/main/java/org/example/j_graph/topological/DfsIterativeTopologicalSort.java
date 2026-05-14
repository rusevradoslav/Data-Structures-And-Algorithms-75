package org.example.j_graph.topological;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Iterative DFS-based topological sort — equivalent to {@link DfsRecursiveTopologicalSort}
 * but replaces the JVM call stack with an explicit {@link java.util.Deque} to avoid
 * {@link StackOverflowError} on deep graphs.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Each vertex is pushed twice: once as a first-visit frame (push children) and
 *       once as a second-visit frame (commit to result)</li>
 *   <li>The second-visit frame is pushed <em>before</em> the children so children land
 *       on top and are processed first — replicating recursive post-order</li>
 *   <li>Cycle detection uses the same {@code inProgress} (grey) set as the recursive version</li>
 *   <li>Result is reversed after the traversal to obtain topological order</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *   A → B → C
 *
 *   Stack events: push A(false) → push A(true), push B(false) → push B(true), push C(false)
 *                 → commit C, commit B, commit A → reverse → [A, B, C]
 * </pre>
 *
 * <p>Time Complexity: O(V + E) — each vertex and edge visited at most once.
 *
 * <p>Space Complexity: O(V) — {@code done}, {@code inProgress}, result, and explicit stack
 * each hold at most V entries.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public class DfsIterativeTopologicalSort<V, E> implements TopologicalSort<V, E> {

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
        Deque<Frame<V, E>> stack = new ArrayDeque<>();

        for (Vertex<V, E> vertex : graph.getVertices()) {
            if (!done.contains(vertex)) {
                stack.push(new Frame<>(vertex, false));
                while (!stack.isEmpty()) {
                    Frame<V, E> frame = stack.pop();
                    Vertex<V, E> frameVertex = frame.vertex;
                    if (!frame.secondVisit) {
                        stack.push(new Frame<>(frameVertex, true));
                        inProgress.add(frameVertex);
                        for (Vertex<V, E> outgoingVertex : frameVertex.getOutgoing().keySet()) {
                            if (inProgress.contains(outgoingVertex)) {
                                throw new IllegalStateException("Cycle detected");
                            }
                            if (!done.contains(outgoingVertex)) {
                                stack.push(new Frame<>(outgoingVertex, false));
                            }
                        }
                    } else {
                        inProgress.remove(frameVertex);
                        done.add(frameVertex);
                        result.add(frameVertex);
                    }
                }
            }
        }

        Collections.reverse(result);
        return result;
    }

    private record Frame<V, E>(Vertex<V, E> vertex, boolean secondVisit) {
    }
}
