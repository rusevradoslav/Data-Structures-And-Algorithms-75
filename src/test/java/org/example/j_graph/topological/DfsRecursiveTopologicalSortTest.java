package org.example.j_graph.topological;

class DfsRecursiveTopologicalSortTest extends TopologicalSortTest {

    @Override
    protected TopologicalSort<String, String> createSort() {
        return new DfsRecursiveTopologicalSort<>();
    }
}
