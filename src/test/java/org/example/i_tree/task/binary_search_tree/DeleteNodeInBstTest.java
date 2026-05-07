package org.example.i_tree.task.binary_search_tree;

import org.example.i_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteNodeInBstTest {

    private DeleteNodeInBst solution;

    /**
     *       5
     *      / \
     *     3   6
     *    / \    \
     *   2   4    7
     */
    @BeforeEach
    public void setUp() {
        solution = new DeleteNodeInBst();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: delete 3 (two children) → inorder stays [2, 4, 5, 6, 7]")
    public void testExample1() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        TreeNode result = solution.deleteNode(root, 3);
        assertEquals(List.of(2, 4, 5, 6, 7), inorder(result));
    }

    @Test
    @DisplayName("Example 2: delete 0 (key not in tree) → tree unchanged")
    public void testExample2() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        TreeNode result = solution.deleteNode(root, 0);
        assertEquals(List.of(2, 3, 4, 5, 6, 7), inorder(result));
    }

    @Test
    @DisplayName("Example 3: delete from empty tree → null")
    public void testExample3() {
        assertNull(solution.deleteNode(null, 0));
    }

    // --- Case 1: leaf node ---

    @Test
    @DisplayName("Delete leaf 2: parent 3 loses its left child")
    public void testDeleteLeaf() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        TreeNode result = solution.deleteNode(root, 2);
        assertNull(result.left.left);
        assertEquals(List.of(3, 4, 5, 6, 7), inorder(result));
    }

    // --- Case 2: one child ---

    @Test
    @DisplayName("Delete 6 (only right child 7): parent 5 right pointer becomes 7")
    public void testDeleteNodeWithOneChild() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        TreeNode result = solution.deleteNode(root, 6);
        assertEquals(7, result.right.val);
        assertNull(result.right.left);
        assertNull(result.right.right);
        assertEquals(List.of(2, 3, 4, 5, 7), inorder(result));
    }

    // --- Case 3: two children ---

    @Test
    @DisplayName("Delete root 5 (two children) → inorder stays [2, 3, 4, 6, 7]")
    public void testDeleteRoot() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        TreeNode result = solution.deleteNode(root, 5);
        assertEquals(List.of(2, 3, 4, 6, 7), inorder(result));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Delete only node in tree → null")
    public void testDeleteOnlyNode() {
        assertNull(solution.deleteNode(new TreeNode(1), 1));
    }

    @Test
    @DisplayName("Delete key larger than all values → tree unchanged")
    public void testDeleteKeyLargerThanAll() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        TreeNode result = solution.deleteNode(root, 99);
        assertEquals(List.of(2, 3, 4, 5, 6, 7), inorder(result));
    }

    private List<Integer> inorder(TreeNode node) {
        List<Integer> result = new ArrayList<>();
        collectInorder(node, result);
        return result;
    }

    private void collectInorder(TreeNode node, List<Integer> result) {
        if (node == null) return;
        collectInorder(node.left, result);
        result.add(node.val);
        collectInorder(node.right, result);
    }
}
