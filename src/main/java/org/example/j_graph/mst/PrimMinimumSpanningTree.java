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

public class PrimMinimumSpanningTree<V> implements MinimumSpanningTree<V> {
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
