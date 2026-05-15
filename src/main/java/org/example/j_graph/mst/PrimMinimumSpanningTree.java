package org.example.j_graph.mst;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Prim's minimum spanning tree algorithm — returns the set of edges forming an MST
 * of a connected undirected weighted graph.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Start from an arbitrary vertex; add all its edges to a min-heap</li>
 *   <li>Repeatedly poll the cheapest edge from the heap</li>
 *   <li>Skip edges where both endpoints are already in the tree (cycle prevention)</li>
 *   <li>Add the new vertex to the tree and enqueue its edges to non-tree vertices</li>
 *   <li>Stop when all vertices are in the tree or the heap is empty</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *   A -(1)- B, A -(4)- C, B -(2)- D, C -(3)- D
 *
 *   MST: {A-B(1), B-D(2), C-D(3)}   total weight 6   (A-C(4) excluded — creates a cycle)
 * </pre>
 *
 * <p>Time Complexity: O((V + E) log E) — each edge is enqueued at most twice.
 *
 * <p>Space Complexity: O(V + E) — inTree set, priority queue, and result list.
 *
 * @param <V> the type of element stored in vertices
 */
public class PrimMinimumSpanningTree<V> implements MinimumSpanningTree<V> {

    /** {@inheritDoc} */
    @Override
    public List<Edge<V, Integer>> mst(Graph<V, Integer> graph) {
        if (Objects.isNull(graph)) {
            throw new IllegalArgumentException("Graph must not be null");
        }
        if (graph.isDirected()) {
            throw new IllegalArgumentException("MST algorithm requires undirected graph");
        }
        Iterable<Vertex<V, Integer>> vertices = graph.getVertices();
        Iterator<Vertex<V, Integer>> iterator = vertices.iterator();

        List<Edge<V, Integer>> result = new ArrayList<>();

        if (!iterator.hasNext()) {
            return result;
        }

        Set<Vertex<V, Integer>> inTree = new HashSet<>();
        PriorityQueue<Edge<V, Integer>> priorityQueue = new PriorityQueue<>(Comparator.comparing(Edge::getElement));

        Vertex<V, Integer> currentVertex = iterator.next();
        inTree.add(currentVertex);

        for (Edge<V, Integer> outgoingEdge : graph.getOutgoingEdges(currentVertex)) {
            priorityQueue.add(outgoingEdge);
        }

        while (!priorityQueue.isEmpty() && inTree.size() < graph.numVertices()) {
            Edge<V, Integer> edge = priorityQueue.poll();
            Vertex<V, Integer>[] endpoints = edge.getEndpoints();
            Vertex<V, Integer> origin = endpoints[0];
            Vertex<V, Integer> destination = endpoints[1];


            if (inTree.contains(origin) && inTree.contains(destination)) {
                continue;
            }
            Vertex<V, Integer> vertex = inTree.contains(origin)
                    ? destination
                    : origin;

            result.add(edge);
            inTree.add(vertex);

            for (Edge<V, Integer> outgoingEdge : graph.getOutgoingEdges(vertex)) {
                Vertex<V, Integer> opposite = graph.opposite(vertex, outgoingEdge);
                if (inTree.contains(opposite)) {
                    continue;
                }
                priorityQueue.add(outgoingEdge);
            }
        }

        return result;
    }
}
