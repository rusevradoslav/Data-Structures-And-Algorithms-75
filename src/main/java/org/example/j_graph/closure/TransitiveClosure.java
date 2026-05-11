package org.example.j_graph.closure;

import org.example.j_graph.Graph;
import org.example.j_graph.Vertex;

import java.util.Map;
import java.util.Set;

/**
 * Contract for computing the transitive closure of a directed graph.
 *
 * <p>The transitive closure adds a virtual edge from vertex {@code u} to vertex {@code v}
 * whenever {@code v} is reachable from {@code u} via one or more hops, capturing all
 * reachability information in a single structure.
 *
 * <p>Three representations are offered: a boolean matrix, a map from each vertex to
 * its reachable set, and a graph whose edges encode reachability directly.
 *
 * @param <V> the type of vertex elements
 * @param <E> the type of edge elements
 */
public interface TransitiveClosure<V, E> {

    /**
     * Returns the transitive closure as an {@code n × n} boolean matrix, where
     * {@code matrix[i][j] == true} means vertex {@code i} can reach vertex {@code j}.
     *
     * <p>Row and column indices correspond to the order in which vertices are returned
     * by {@link Graph#getVertices()}. The diagonal ({@code matrix[i][i]}) is {@code false}
     * unless a vertex can reach itself via a cycle.
     *
     * @param graph the graph to compute the closure for; must not be {@code null}
     * @return an {@code n × n} reachability matrix
     * @throws IllegalArgumentException if {@code graph} is {@code null}
     */
    boolean[][] closureAsMatrix(Graph<V, E> graph);

    /**
     * Returns the transitive closure as a map from each vertex to the set of all
     * vertices it can reach (excluding itself, unless a cycle exists).
     *
     * @param graph the graph to compute the closure for; must not be {@code null}
     * @return a map from every vertex to its reachable set
     * @throws IllegalArgumentException if {@code graph} is {@code null}
     */
    Map<Vertex<V, E>, Set<Vertex<V, E>>> closureAsMap(Graph<V, E> graph);

    /**
     * Returns a directed graph whose edges represent the transitive closure: an edge
     * {@code u → v} exists if and only if {@code v} is reachable from {@code u}.
     *
     * @param graph the graph to compute the closure for; must not be {@code null}
     * @return a directed graph encoding the full reachability relation
     * @throws IllegalArgumentException if {@code graph} is {@code null}
     */
    Graph<V, E> closureAsGraph(Graph<V, E> graph);
}
