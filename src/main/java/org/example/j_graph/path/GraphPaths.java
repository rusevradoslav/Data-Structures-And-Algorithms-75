package org.example.j_graph.path;

import lombok.experimental.UtilityClass;
import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for reconstructing paths from a graph traversal discovery forest.
 */
@UtilityClass
public class GraphPaths {

    /**
     * Reconstructs the path from {@code start} to {@code end} using the given discovery forest.
     *
     * <p>The forest is typically produced by a {@link org.example.j_graph.traversal.GraphTraversal} -
     * each entry maps a discovered vertex to the edge that first reached it. The path is
     * reconstructed by walking backwards from {@code end} to {@code start}.
     *
     * <p>If the forest came from BFS, the returned path uses the fewest edges; if from DFS,
     * it is whatever DFS found.
     *
     * <p>Returns an empty list if {@code start == end} or if {@code end} is not in the forest.
     *
     * @param graph  the graph the forest was built from
     * @param start  the origin vertex
     * @param end    the destination vertex
     * @param forest the discovery forest returned by a graph traversal
     * @return an ordered list of edges forming the path from {@code start} to {@code end}
     * @throws IllegalArgumentException if any argument is {@code null}
     */
    public static <V, E> List<Edge<V, E>> constructPath(Graph<V, E> graph, Vertex<V, E> start, Vertex<V, E> end,
                                                        Map<Vertex<V, E>, Edge<V, E>> forest) {

        if (Objects.isNull(start) || Objects.isNull(end) || Objects.isNull(forest) || Objects.isNull(graph)) {
            throw new IllegalArgumentException("Input arguments cannot be null");
        }
        List<Edge<V, E>> path = new LinkedList<>();

        if (start == end) {
            return path;
        }

        if (!forest.containsKey(end)) {
            return path;
        }


        Vertex<V, E> current = end;
        while (current != start) {
            Edge<V, E> edge = forest.get(current);
            path.addFirst(edge);
            current = graph.opposite(current, edge);
        }

        return path;
    }
}