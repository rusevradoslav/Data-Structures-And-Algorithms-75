package org.example.j_tree.binary_tree;

import org.example.j_tree.binary_tree.bfs.BfsFlatBinaryTreeTraversalImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BfsFlatBinaryTreeTraversalImplTest {

    private BinaryTreeNode<Integer> root;
    private BfsFlatBinaryTreeTraversalImpl<Integer> bfs;

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

        bfs = new BfsFlatBinaryTreeTraversalImpl<>();
    }

    // --- Core traversal ---

    @Test
    @DisplayName("Flat BFS: [10, 5, 15, 3, 7, 20]")
    public void testFlatBfs() {
        assertEquals(List.of(10, 5, 15, 3, 7, 20), bfs.traverse(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Null root returns []")
    public void testNullRoot() {
        assertEquals(List.of(), bfs.traverse(null));
    }

    @Test
    @DisplayName("Single node returns [10]")
    public void testSingleNode() {
        assertEquals(List.of(10), bfs.traverse(new BinaryTreeNode<>(10)));
    }

    @Test
    @DisplayName("Left-skewed tree: [10, 5, 3]")
    public void testLeftSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.left = new BinaryTreeNode<>(5);
        skewed.left.left = new BinaryTreeNode<>(3);
        assertEquals(List.of(10, 5, 3), bfs.traverse(skewed));
    }

    @Test
    @DisplayName("Right-skewed tree: [10, 15, 20]")
    public void testRightSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.right = new BinaryTreeNode<>(15);
        skewed.right.right = new BinaryTreeNode<>(20);
        assertEquals(List.of(10, 15, 20), bfs.traverse(skewed));
    }

    @Test
    @DisplayName("Complete binary tree: [1, 2, 3, 4, 5, 6, 7]")
    public void testCompleteBinaryTree() {
        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(1);
        node.left = new BinaryTreeNode<>(2);
        node.right = new BinaryTreeNode<>(3);
        node.left.left = new BinaryTreeNode<>(4);
        node.left.right = new BinaryTreeNode<>(5);
        node.right.left = new BinaryTreeNode<>(6);
        node.right.right = new BinaryTreeNode<>(7);
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), bfs.traverse(node));
    }
}
