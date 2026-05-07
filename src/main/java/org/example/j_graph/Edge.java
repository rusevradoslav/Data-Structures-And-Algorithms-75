package org.example.j_graph;

public class Edge<V, E> {
    private final Vertex<V, E>[] endPoints;
    private final E element;

    @SuppressWarnings("unchecked")
    public Edge(Vertex<V, E> source, Vertex<V, E> target,  E element) {
        this.endPoints = new Vertex[]{source, target};
        this.element = element;
    }
    public Vertex<V, E>[] getEndPoints() {
        return endPoints;
    }
    public E getElement() {
        return element;
    }
}
