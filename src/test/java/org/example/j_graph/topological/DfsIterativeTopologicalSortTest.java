package org.example.j_graph.topological;

class DfsIterativeTopologicalSortTest extends TopologicalSortTest {

    @Override
    protected TopologicalSort<String, String> createSort() {
        return new DfsIterativeTopologicalSort<>();
    }
}
