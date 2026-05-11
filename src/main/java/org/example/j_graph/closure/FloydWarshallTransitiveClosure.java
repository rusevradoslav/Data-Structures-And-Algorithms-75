package org.example.j_graph.closure;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Transitive closure computed via the Floyd-Warshall algorithm.
 *
 * <p><b>Approach:</b> iterates over all triples (source, middle, destination); if
 * {@code source → middle} and {@code middle → destination} both exist, a direct edge
 * {@code source → destination} is inserted, progressively building full reachability.
 *
 * <p><b>Important:</b> all three methods ({@link #closureAsMatrix}, {@link #closureAsMap},
 * {@link #closureAsGraph}) <em>mutate the input graph</em> by adding the transitive edges
 * directly to it. {@link #closureAsGraph} returns the same (modified) graph instance, not a copy.
 *
 * <p>Time Complexity: O(V³) — three nested loops over all vertices.
 *
 * <p>Space Complexity: O(1) auxiliary — edges are written directly into the input graph.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public class FloydWarshallTransitiveClosure<V, E> implements TransitiveClosure<V, E> {

    /** {@inheritDoc} */
    @Override
    public boolean[][] closureAsMatrix(Graph<V, E> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }

        floydWarshall(graph);

        Map<Vertex<V, E>, Integer> verticesIndexMap = new HashMap<>();
        int index = 0;
        for (Vertex<V, E> vertex : graph.getVertices()) {
            verticesIndexMap.put(vertex, index);
            index++;
        }

        int size = verticesIndexMap.size();
        boolean[][] matrix = new boolean[size][size];

        for (Map.Entry<Vertex<V, E>, Integer> entry : verticesIndexMap.entrySet()) {
            int sourceIndex = entry.getValue();
            for (Vertex<V, E> destination : entry.getKey().getOutgoing().keySet()) {
                int destinationIndex = verticesIndexMap.get(destination);
                matrix[sourceIndex][destinationIndex] = true;
            }
        }

        return matrix;
    }

    /** {@inheritDoc} */
    @Override
    public Map<Vertex<V, E>, Set<Vertex<V, E>>> closureAsMap(Graph<V, E> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }

        floydWarshall(graph);

        Map<Vertex<V, E>, Set<Vertex<V, E>>> result = new HashMap<>();

        for (Vertex<V, E> vertex : graph.getVertices()) {
            result.put(vertex, new HashSet<>(vertex.getOutgoing().keySet()));
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public Graph<V, E> closureAsGraph(Graph<V, E> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }

        floydWarshall(graph);
        return graph;
    }

    private void floydWarshall(Graph<V, E> g) {
        for (Vertex<V, E> middle : g.getVertices()) {
            for (Vertex<V, E> source : g.getVertices()) {

                boolean sourceReachesMiddle = Objects.nonNull(g.getEdge(source, middle));
                boolean sourceIsNotMiddle = source != middle;

                if (sourceIsNotMiddle && sourceReachesMiddle) {
                    for (Vertex<V, E> destination : g.getVertices()) {

                        boolean middleReachesDestination = Objects.nonNull(g.getEdge(middle, destination));
                        boolean sourceAlreadyReachesDestination = Objects.nonNull(g.getEdge(source, destination));
                        boolean destinationIsNotMiddle = destination != middle;
                        boolean destinationIsNotSource = source != destination;

                        if (destinationIsNotSource &&
                                destinationIsNotMiddle &&
                                middleReachesDestination &&
                                !sourceAlreadyReachesDestination) {
                            g.insertEdge(source, destination, null);
                        }
                    }
                }
            }
        }
    }
}
