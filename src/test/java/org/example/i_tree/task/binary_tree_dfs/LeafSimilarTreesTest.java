package org.example.i_tree.task.binary_tree_dfs;

import org.example.i_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeafSimilarTreesTest {

    private LeafSimilarTrees solution;

    @BeforeEach
    public void setUp() {
        solution = new LeafSimilarTrees();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: leaf sequences [6,7,4,9,8] → true")
    public void testExample1() {
        TreeNode root1 = new TreeNode(3,
                new TreeNode(5,
                        new TreeNode(6),
                        new TreeNode(2,
                                new TreeNode(7),
                                new TreeNode(4))),
                new TreeNode(1,
                        new TreeNode(9),
                        new TreeNode(8)));

        TreeNode root2 = new TreeNode(3,
                new TreeNode(5,
                        new TreeNode(6),
                        new TreeNode(7)),
                new TreeNode(1,
                        new TreeNode(4),
                        new TreeNode(2,
                                new TreeNode(9),
                                new TreeNode(8))));

        assertTrue(solution.leafSimilar(root1, root2));
    }

    @Test
    @DisplayName("Example 2: leaf sequences [2,3] vs [3,2] → false, same leaves different order")
    public void testExample2() {
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2), new TreeNode(3));
        TreeNode root2 = new TreeNode(1,
                new TreeNode(3), new TreeNode(2));

        assertFalse(solution.leafSimilar(root1, root2));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Both roots null → true")
    public void testBothNull() {
        assertTrue(solution.leafSimilar(null, null));
    }

    @Test
    @DisplayName("One root null, other non-null → false")
    public void testOneNullRoot() {
        assertFalse(solution.leafSimilar(null, new TreeNode(1)));
        assertFalse(solution.leafSimilar(new TreeNode(1), null));
    }

    @Test
    @DisplayName("Single-node trees with same value → true")
    public void testSingleNodeSameValue() {
        assertTrue(solution.leafSimilar(new TreeNode(1), new TreeNode(1)));
    }

    @Test
    @DisplayName("Single-node trees with different values → false")
    public void testSingleNodeDifferentValues() {
        assertFalse(solution.leafSimilar(new TreeNode(1), new TreeNode(2)));
    }

    @Test
    @DisplayName("Different leaf count → false")
    public void testDifferentLeafCount() {
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3));
        TreeNode root2 = new TreeNode(1,
                null,
                new TreeNode(2));

        assertFalse(solution.leafSimilar(root1, root2));
    }

    @Test
    @DisplayName("Same structure, same leaves → true")
    public void testIdenticalTrees() {
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4),
                        new TreeNode(5)),
                new TreeNode(3));
        TreeNode root2 = new TreeNode(9,
                new TreeNode(8,
                        new TreeNode(4),
                        new TreeNode(5)),
                new TreeNode(7,
                        null,
                        new TreeNode(3)));

        assertTrue(solution.leafSimilar(root1, root2));
    }

    @Test
    @DisplayName("Different structures, same leaf sequence → true")
    public void testDifferentStructureSameLeaves() {
        // root1: leaves [1, 2]  (left-skewed internals)
        TreeNode root1 = new TreeNode(10,
                new TreeNode(20,
                        new TreeNode(1),
                        null),
                new TreeNode(2));

        // root2: leaves [1, 2]  (right-skewed internals)
        TreeNode root2 = new TreeNode(30,
                new TreeNode(1),
                new TreeNode(40,
                        null,
                        new TreeNode(2)));

        assertTrue(solution.leafSimilar(root1, root2));
    }

    @Test
    @DisplayName("Same leaves but in different order → false")
    public void testSameLeavesWrongOrder() {
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3));
        TreeNode root2 = new TreeNode(1,
                new TreeNode(3),
                new TreeNode(2));

        assertFalse(solution.leafSimilar(root1, root2));
    }
}
