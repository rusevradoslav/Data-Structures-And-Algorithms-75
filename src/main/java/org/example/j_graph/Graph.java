package org.example.j_graph;

import java.util.ArrayList;
import java.util.List;

public class Graph<V, E> {

    private final boolean directed;
    private final List<Vertex<V, E>> vertices = new ArrayList<>();
    private final List<Edge<V, E>> edges = new ArrayList<>();

    public Graph(boolean directed) {
        this.directed = directed;
    }

    public boolean isDirected() {
        // TODO
        return false;
    }

    public int numVertices() {
        // TODO
        return 0;
    }

    public Iterable<Vertex<V, E>> vertices() {
        // TODO
        return null;
    }

    public int numEdges() {
        // TODO
        return 0;
    }

    public Iterable<Edge<V, E>> edges() {
        // TODO
        return null;
    }

    public Edge<V, E> getEdge(Vertex<V, E> u, Vertex<V, E> v) {
        // TODO
        return null;
    }

    public Vertex<V, E>[] endVertices(Edge<V, E> e) {
        // TODO
        return null;
    }

    public Vertex<V, E> opposite(Vertex<V, E> v, Edge<V, E> e) {
        // TODO
        return null;
    }

    public int outDegree(Vertex<V, E> v) {
        // TODO
        return 0;
    }

    public int inDegree(Vertex<V, E> v) {
        // TODO
        return 0;
    }

    public Iterable<Edge<V, E>> outgoingEdges(Vertex<V, E> v) {
        // TODO
        return null;
    }

    public Iterable<Edge<V, E>> incomingEdges(Vertex<V, E> v) {
        // TODO
        return null;
    }

    public Vertex<V, E> insertVertex(V element) {
        // TODO
        return null;
    }

    public Edge<V, E> insertEdge(Vertex<V, E> u, Vertex<V, E> v, E element) {
        // TODO
        return null;
    }

    public void removeVertex(Vertex<V, E> v) {
        // TODO
    }

    public void removeEdge(Edge<V, E> e) {
        // TODO
    }
}
