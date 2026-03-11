package org.example.j_tree.binary_tree_representation_and_traversal;

import org.example.j_tree.binary_tree_representation_and_traversal.dfs.PostorderBinaryTreeTraversalImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostorderBinaryTreeTraversalImplTest {

    private BinaryTreeNode<Integer> root;
    private PostorderBinaryTreeTraversalImpl<Integer> postorder;

    /**
     *        10
     *       /  \
     *      5    15
     *     / \     \
     *    3   7    20
     */
    @BeforeEach
    public void setUp() {
        root = new BinaryTreeNode<>(10);
        root.left = new BinaryTreeNode<>(5);
        root.right = new BinaryTreeNode<>(15);
        root.left.left = new BinaryTreeNode<>(3);
        root.left.right = new BinaryTreeNode<>(7);
        root.right.right = new BinaryTreeNode<>(20);

        postorder = new PostorderBinaryTreeTraversalImpl<>();
    }

    // --- Core traversal (Left → Right → Root) ---

    @Test
    @DisplayName("Postorder recursive: [3, 7, 5, 20, 15, 10]")
    public void testPostorderRecursive() {
        assertEquals(List.of(3, 7, 5, 20, 15, 10), postorder.recursive(root));
    }

    @Test
    @DisplayName("Postorder iterative: [3, 7, 5, 20, 15, 10]")
    public void testPostorderIterative() {
        assertEquals(List.of(3, 7, 5, 20, 15, 10), postorder.iterative(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Single node returns [10]")
    public void testSingleNode() {
        BinaryTreeNode<Integer> single = new BinaryTreeNode<>(10);
        assertEquals(List.of(10), postorder.recursive(single));
        assertEquals(List.of(10), postorder.iterative(single));
    }

    @Test
    @DisplayName("Null root returns []")
    public void testNullRoot() {
        assertEquals(List.of(), postorder.recursive(null));
        assertEquals(List.of(), postorder.iterative(null));
    }

    @Test
    @DisplayName("Left-skewed tree: [3, 5, 10]")
    public void testLeftSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.left = new BinaryTreeNode<>(5);
        skewed.left.left = new BinaryTreeNode<>(3);
        assertEquals(List.of(3, 5, 10), postorder.recursive(skewed));
        assertEquals(List.of(3, 5, 10), postorder.iterative(skewed));
    }

    @Test
    @DisplayName("Right-skewed tree: [20, 15, 10]")
    public void testRightSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.right = new BinaryTreeNode<>(15);
        skewed.right.right = new BinaryTreeNode<>(20);
        assertEquals(List.of(20, 15, 10), postorder.recursive(skewed));
        assertEquals(List.of(20, 15, 10), postorder.iterative(skewed));
    }
}
