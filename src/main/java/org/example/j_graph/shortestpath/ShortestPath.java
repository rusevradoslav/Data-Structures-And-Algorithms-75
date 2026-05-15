package org.example.j_graph.shortestpath;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.Map;

public interface ShortestPath<V> {

    Map<Vertex<V, Integer>, Integer> distances(Graph<V, Integer> graph, Vertex<V, Integer> start);
}
