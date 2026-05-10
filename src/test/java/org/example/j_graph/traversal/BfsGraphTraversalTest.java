package org.example.j_graph.traversal;

public class BfsGraphTraversalTest extends GraphTraversalTest {

    @Override
    GraphTraversal<String, String> createTraversal() {
        return new BfsGraphTraversal<>();
    }
}
