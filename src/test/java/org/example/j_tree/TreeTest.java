package org.example.j_tree;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TreeTest {

    private Tree<Integer> tree;

    @BeforeEach
    public void setUp() {
        this.tree = new Tree<>(7,
                new Tree<>(19,
                        new Tree<>(1),
                        new Tree<>(12),
                        new Tree<>(31)),
                new Tree<>(21),
                new Tree<>(14,
                        new Tree<>(23),
                        new Tree<>(6))
        );
    }

    @Test
    public void testTreeConstructor() {
        assertNotNull(tree);
    }

    @Test
    public void testTreeBfs() {
        Integer[] expected = {7, 19, 21, 14, 1, 12, 31, 23, 6};
        int index = 0;
        for (Integer num : tree.orderBfs()) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    public void testTreePostorderDfs() {
        Integer[] expected = {1, 12, 31, 19, 21, 23, 6, 14, 7};
        int index = 0;
        for (Integer num : tree.postorderDfs()) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    public void testTreePreorderDfs() {
        Integer[] expected = {7, 19, 1, 12, 31, 21, 14, 23, 6};
        int index = 0;
        for (Integer num : tree.preorderDfs()) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    public void testAddTree() {
        tree.addChild(1, new Tree<>(-1, new Tree<>(-2), new Tree<>(-3)));
        Integer[] expected = {-2, -3, -1, 1, 12, 31, 19, 21, 23, 6, 14, 7};
        int index = 0;
        for (Integer num : tree.postorderDfs()) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    public void testRemoveNode() {
        tree.removeNode(19);
        Integer[] expected = {7, 21, 14, 23, 6};

        List<Integer> integers = tree.orderBfs();
        assertEquals(expected.length, integers.size());
        int index = 0;
        for (Integer num : integers) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    public void testSwap() {
        tree.swap(19, 14);
        Integer[] expected = {7, 14, 21, 19, 23, 6, 1, 12, 31};
        List<Integer> integers = tree.orderBfs();
        assertEquals(expected.length, integers.size());
        int index = 0;
        for (Integer num : integers) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    public void testSwapRoot() {
        tree.swap(7, 19);
        Integer[] expected = {19, 1, 12, 31};
        List<Integer> integers = tree.orderBfs();
        assertEquals(expected.length, integers.size());
        int index = 0;
        for (Integer num : integers) {
            assertEquals(expected[index++], num);
        }
    }

    // --- Single-node tree ---

    @Test
    @DisplayName("BFS on single-node tree returns only root")
    public void testBfsSingleNode() {
        Tree<Integer> single = new Tree<>(42);
        assertEquals(List.of(42), single.orderBfs());
    }

    @Test
    @DisplayName("Preorder DFS on single-node tree returns only root")
    public void testPreorderDfsSingleNode() {
        Tree<Integer> single = new Tree<>(42);
        assertEquals(List.of(42), single.preorderDfs());
    }

    @Test
    @DisplayName("Postorder DFS on single-node tree returns only root")
    public void testPostorderDfsSingleNode() {
        Tree<Integer> single = new Tree<>(42);
        assertEquals(List.of(42), single.postorderDfs());
    }

    // --- Leaf node operations ---

    @Test
    @DisplayName("Remove a leaf node")
    public void testRemoveLeafNode() {
        tree.removeNode(1);
        List<Integer> result = tree.orderBfs();
        assertEquals(List.of(7, 19, 21, 14, 12, 31, 23, 6), result);
    }

    @Test
    @DisplayName("Swap two leaf nodes")
    public void testSwapLeafNodes() {
        tree.swap(1, 6);
        List<Integer> result = tree.orderBfs();
        assertEquals(List.of(7, 19, 21, 14, 6, 12, 31, 23, 1), result);
    }

    // --- Add child variations ---

    @Test
    @DisplayName("Add child to root node")
    public void testAddChildToRoot() {
        tree.addChild(7, new Tree<>(99));
        List<Integer> result = tree.orderBfs();
        assertEquals(List.of(7, 19, 21, 14, 99, 1, 12, 31, 23, 6), result);
    }

    @Test
    @DisplayName("Add child to a leaf node turns it into a parent")
    public void testAddChildToLeaf() {
        tree.addChild(6, new Tree<>(100));
        List<Integer> result = tree.orderBfs();
        assertEquals(List.of(7, 19, 21, 14, 1, 12, 31, 23, 6, 100), result);
    }

    // --- Error handling ---

    @Test
    @DisplayName("Remove non-existent node throws IllegalArgumentException")
    public void testRemoveNonExistentNode() {
        assertThrows(IllegalArgumentException.class, () -> tree.removeNode(999));
    }

    @Test
    @DisplayName("Add child to non-existent parent throws IllegalArgumentException")
    public void testAddChildToNonExistentParent() {
        assertThrows(IllegalArgumentException.class,
                () -> tree.addChild(999, new Tree<>(50)));
    }

    @Test
    @DisplayName("Swap with non-existent first key throws IllegalArgumentException")
    public void testSwapNonExistentFirstKey() {
        assertThrows(IllegalArgumentException.class, () -> tree.swap(999, 19));
    }

    @Test
    @DisplayName("Swap with non-existent second key throws IllegalArgumentException")
    public void testSwapNonExistentSecondKey() {
        assertThrows(IllegalArgumentException.class, () -> tree.swap(19, 999));
    }

    @Test
    @DisplayName("Remove root node throws NullPointerException (root has no parent)")
    public void testRemoveRootNode() {
        assertThrows(NullPointerException.class, () -> tree.removeNode(7));
    }


    @Test
    @DisplayName("Swap a parent with its direct child")
    public void testSwapParentWithChild() {
        tree.swap(19, 1);
        List<Integer> result = tree.orderBfs();
        assertEquals(List.of(7, 1, 21, 14, 19, 12, 31, 23, 6), result);
    }


    @Test
    @DisplayName("Swap root as second argument (mirror of testSwapRoot)")
    public void testSwapRootReversedArgs() {
        tree.swap(19, 7);
        Integer[] expected = {19, 1, 12, 31};
        List<Integer> result = tree.orderBfs();
        assertEquals(expected.length, result.size());
        int index = 0;
        for (Integer num : result) {
            assertEquals(expected[index++], num);
        }
    }

    @Test
    @DisplayName("Swap child with its direct parent (reversed args of testSwapParentWithChild)")
    public void testSwapChildWithParent() {
        tree.swap(1, 19);
        List<Integer> result = tree.orderBfs();
        assertEquals(List.of(7, 1, 21, 14, 19, 12, 31, 23, 6), result);
    }

    @Test
    @DisplayName("Search skips nodes with null value in the tree")
    public void testFindNodeSkipsNullValue() {
        Tree<Integer> nullTree = new Tree<>(null, new Tree<>(10));
        assertThrows(IllegalArgumentException.class, () -> nullTree.addChild(10, new Tree<>(20)));
    }

    @Test
    @DisplayName("Tree with negative values traverses correctly")
    public void testNegativeValues() {
        Tree<Integer> negTree = new Tree<>(-5,
                new Tree<>(-10, new Tree<>(-20)),
                new Tree<>(-3));
        assertEquals(List.of(-5, -10, -3, -20), negTree.orderBfs());
        assertEquals(List.of(-5, -10, -20, -3), negTree.preorderDfs());
        assertEquals(List.of(-20, -10, -3, -5), negTree.postorderDfs());
    }
}