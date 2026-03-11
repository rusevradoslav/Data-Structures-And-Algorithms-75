package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LowestCommonAncestorTest {

    private LowestCommonAncestor solution;

    @BeforeEach
    public void setUp() {
        solution = new LowestCommonAncestor();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: p=5, q=1 → LCA is 3 (p and q on opposite sides of root)")
    public void testExample1() {
        TreeNode p = new TreeNode(5);
        TreeNode q = new TreeNode(1);
        TreeNode root = new TreeNode(3,
                new TreeNode(5,
                        new TreeNode(6),
                        new TreeNode(2,
                                new TreeNode(7),
                                new TreeNode(4))),
                new TreeNode(1,
                        new TreeNode(0),
                        new TreeNode(8)));

        assertEquals(3, solution.lowestCommonAncestor(root, p, q).val);
    }

    @Test
    @DisplayName("Example 2: p=5, q=4 → LCA is 5 (q is descendant of p)")
    public void testExample2() {
        TreeNode p = new TreeNode(5);
        TreeNode q = new TreeNode(4);
        TreeNode root = new TreeNode(3,
                new TreeNode(5,
                        new TreeNode(6),
                        new TreeNode(2,
                                new TreeNode(7),
                                new TreeNode(4))),
                new TreeNode(1,
                        new TreeNode(0),
                        new TreeNode(8)));

        assertEquals(5, solution.lowestCommonAncestor(root, p, q).val);
    }

    @Test
    @DisplayName("Example 3: root=[1,2], p=1, q=2 → LCA is 1")
    public void testExample3() {
        TreeNode p = new TreeNode(1);
        TreeNode q = new TreeNode(2);
        TreeNode root = new TreeNode(1, new TreeNode(2), null);

        assertEquals(1, solution.lowestCommonAncestor(root, p, q).val);
    }

    // --- Edge cases ---

    @Test
    @DisplayName("p is root → LCA is root")
    public void testPIsRoot() {
        TreeNode p = new TreeNode(1);
        TreeNode q = new TreeNode(2);
        TreeNode root = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3));

        assertEquals(1, solution.lowestCommonAncestor(root, p, q).val);
    }

    @Test
    @DisplayName("p and q are siblings → LCA is their parent")
    public void testSiblings() {
        TreeNode p = new TreeNode(2);
        TreeNode q = new TreeNode(3);
        TreeNode root = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3));

        assertEquals(1, solution.lowestCommonAncestor(root, p, q).val);
    }

    @Test
    @DisplayName("p and q deep in same subtree → LCA is their closest common ancestor")
    public void testDeepInSameSubtree() {
        TreeNode p = new TreeNode(7);
        TreeNode q = new TreeNode(4);
        TreeNode root = new TreeNode(3,
                new TreeNode(5,
                        new TreeNode(6),
                        new TreeNode(2,
                                new TreeNode(7),
                                new TreeNode(4))),
                new TreeNode(1,
                        new TreeNode(0),
                        new TreeNode(8)));

        assertEquals(2, solution.lowestCommonAncestor(root, p, q).val);
    }
}
