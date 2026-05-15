package org.example.j_graph.mst;

import org.example.j_graph.Edge;
import org.example.j_graph.Graph;

import java.util.List;

public interface MinimumSpanningTree<V> {
    List<Edge<V, Integer>> mst(Graph<V, Integer> graph);
}