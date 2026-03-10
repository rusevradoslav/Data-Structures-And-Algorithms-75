package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestZigZagPathTest {

    private LongestZigZagPath solution;

    @BeforeEach
    public void setUp() {
        solution = new LongestZigZagPath();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: root=[1,null,1,1,1,null,null,1,1,null,1], → 3")
    public void testExample1() {
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(1,
                        new TreeNode(1),
                        new TreeNode(1,
                                null,
                                new TreeNode(1,
                                        new TreeNode(1,
                                                null,
                                                new TreeNode(1)),
                                        null))));

        assertEquals(3, solution.longestZigZag(root));
    }

    @Test
    @DisplayName("Example 2: root=[1,1,1,null,1,null,null,1,1,null,1] → 4")
    public void testExample2() {
        TreeNode root = new TreeNode(1,
                new TreeNode(1,
                        null,
                        new TreeNode(1,
                                new TreeNode(1,
                                        null,
                                        new TreeNode(1)),
                                new TreeNode(1))),
                new TreeNode(1));

        assertEquals(4, solution.longestZigZag(root));
    }

    @Test
    @DisplayName("Example 3: root=[1,null,1,1,1,null,null,1,1,null,1,null,null,null,1] → 3")
    public void testExample3() {
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(1,
                        new TreeNode(1),
                        new TreeNode(1,
                                new TreeNode(1,
                                        null,
                                        new TreeNode(1,
                                                null,
                                                new TreeNode(1))),
                                new TreeNode(1))));

        assertEquals(3, solution.longestZigZag(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Single node → 0")
    public void testSingleNode() {
        assertEquals(0, solution.longestZigZag(new TreeNode(1)));
    }

    @Test
    @DisplayName("Null root → 0")
    public void testNullRoot() {
        assertEquals(0, solution.longestZigZag(null));
    }

    @Test
    @DisplayName("All left children → 1")
    public void testAllLeft() {
        TreeNode root = new TreeNode(1,
                new TreeNode(1,
                        new TreeNode(1), null),
                null);

        assertEquals(1, solution.longestZigZag(root));
    }

    @Test
    @DisplayName("All right children → 1")
    public void testAllRight() {
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(1,
                        null,
                        new TreeNode(1)));

        assertEquals(1, solution.longestZigZag(root));
    }

    @Test
    @DisplayName("Simple zigzag: root → right → left → 2")
    public void testSimpleZigZag() {
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(1,
                        new TreeNode(1),
                        null));

        assertEquals(2, solution.longestZigZag(root));
    }
}
