package org.example.j_graph.closure;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;
import org.example.j_graph.traversal.DfsGraphTraversal;
import org.example.j_graph.traversal.GraphTraversal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Transitive closure computed by running a DFS from every vertex.
 *
 * <p><b>Approach:</b> for each vertex {@code u}, a full DFS is performed; every vertex
 * discovered in that DFS is reachable from {@code u} and is recorded accordingly.
 *
 * <p>The input graph is never modified. {@link #closureAsGraph(Graph)} returns a
 * new directed graph whose vertices carry the same elements as the originals.
 *
 * <p>Time Complexity: O(V · (V + E)) — one DFS per vertex.
 *
 * <p>Space Complexity: O(V²) — the closure can contain up to V² reachability entries.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public class DfsTransitiveClosure<V, E> implements TransitiveClosure<V, E> {

    private final GraphTraversal<V, E> dfsGraphTraversal;

    public DfsTransitiveClosure() {
        this.dfsGraphTraversal = new DfsGraphTraversal<>();
    }

    /** {@inheritDoc} */
    @Override
    public boolean[][] closureAsMatrix(Graph<V, E> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }

        Map<Vertex<V, E>, Integer> verticesIndexMap = new HashMap<>();

        int index = 0;
        for (Vertex<V, E> vertex : graph.getVertices()) {
            verticesIndexMap.put(vertex, index);
            index++;
        }

        int size = verticesIndexMap.size();
        boolean[][] resultMatrix = new boolean[size][size];

        for (Map.Entry<Vertex<V, E>, Integer> entry : verticesIndexMap.entrySet()) {
            Vertex<V, E> origin = entry.getKey();
            int originIndex = entry.getValue();
            Map<Vertex<V, E>, Edge<V, E>> forest = dfsGraphTraversal.traverse(graph, origin);
            for (Vertex<V, E> destination : forest.keySet()) {
                int destinationIndex = verticesIndexMap.get(destination);
                resultMatrix[originIndex][destinationIndex] = true;
            }
        }

        return resultMatrix;
    }

    /** {@inheritDoc} */
    @Override
    public Map<Vertex<V, E>, Set<Vertex<V, E>>> closureAsMap(Graph<V, E> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }

        Map<Vertex<V, E>, Set<Vertex<V, E>>> result = new HashMap<>();

        for (Vertex<V, E> vertex : graph.getVertices()) {
            Map<Vertex<V, E>, Edge<V, E>> forest = dfsGraphTraversal.traverse(graph, vertex);
            result.put(vertex, new HashSet<>(forest.keySet()));
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public Graph<V, E> closureAsGraph(Graph<V, E> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }

        Graph<V, E> resultGraph = new Graph<>(true);
        Map<Vertex<V, E>, Vertex<V, E>> oldToNewMap = new HashMap<>();
        List<Vertex<V, E>> vertices = new ArrayList<>();
        graph.getVertices().forEach(vertices::add);

        for (Vertex<V, E> oldVertex : vertices) {
            Vertex<V, E> newVertex = resultGraph.insertVertex(oldVertex.getElement());
            oldToNewMap.put(oldVertex, newVertex);
        }

        for (Vertex<V, E> origin : vertices) {
            Map<Vertex<V, E>, Edge<V, E>> forest = dfsGraphTraversal.traverse(graph, origin);
            for (Vertex<V, E> destination : forest.keySet()) {
                resultGraph.insertEdge(oldToNewMap.get(origin), oldToNewMap.get(destination), null);
            }
        }
        return resultGraph;
    }
}
