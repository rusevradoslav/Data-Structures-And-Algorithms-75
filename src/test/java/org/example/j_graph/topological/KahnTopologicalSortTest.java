package org.example.j_graph.topological;

class KahnTopologicalSortTest extends TopologicalSortTest {

    @Override
    protected TopologicalSort<String, String> createSort() {
        return new KahnTopologicalSort<>();
    }
}
