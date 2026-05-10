package org.example.j_graph.traversal;

public class DfsGraphTraversalTest extends GraphTraversalTest {

    @Override
    GraphTraversal<String, String> createTraversal() {
        return new DfsGraphTraversal<>();
    }
}
