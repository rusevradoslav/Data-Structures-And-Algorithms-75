package org.example.i_tree.binary_tree.dfs;

import org.example.i_tree.binary_tree.BinaryTreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * Preorder DFS (Root → Left → Right) binary tree traversal.
 *
 * <p>Visits the current node first, then recursively traverses the left subtree,
 * followed by the right subtree.
 *
 * <p><b>Recursive approach ({@link #recursive}):</b>
 * <ul>
 *   <li>Add the current node's value to the result</li>
 *   <li>Recurse into the left child, then the right child</li>
 * </ul>
 *
 * <p><b>Iterative approach ({@link #iterative}):</b>
 * <ul>
 *   <li>Push the root onto a stack</li>
 *   <li>Pop a node, add its value, then push right before left
 *       (so left is popped first, preserving Root → Left → Right order)</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *        10
 *       /  \
 *      5    15
 *     / \     \
 *    3   7    20
 *
 * Preorder: [10, 5, 3, 7, 15, 20]
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node visited exactly once.
 *
 * <p>Space Complexity: O(h) — recursion stack depth or explicit stack size,
 * where h is the height of the tree.
 *
 * @param <E> the type of value stored in each tree node
 */
public class PreorderBinaryTreeTraversal<E> implements BinaryTreeTraversal<E> {

    /**
     * {@inheritDoc}
     *
     * <p>Delegates to a recursive helper that visits the node, then its left
     * and right children.
     */
    @Override
    public List<E> recursive(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return Collections.emptyList();
        }

        if (Objects.isNull(node.getLeft()) && Objects.isNull(node.getRight())) {
            return Collections.singletonList(node.getVal());
        }

        List<E> result = new ArrayList<>();
        executePreorderDFS(result, node);
        return result;
    }

    private void executePreorderDFS(List<E> res, BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return;
        }
        res.add(node.getVal());
        executePreorderDFS(res, node.getLeft());
        executePreorderDFS(res, node.getRight());
    }

    /**
     * {@inheritDoc}
     *
     * <p>Uses a stack: pops a node, records its value, then pushes right
     * before left so the left child is processed first.
     */
    @Override
    public List<E> iterative(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return Collections.emptyList();
        }

        if (Objects.isNull(node.getLeft()) && Objects.isNull(node.getRight())) {
            return Collections.singletonList(node.getVal());
        }

        List<E> result = new ArrayList<>();
        Deque<BinaryTreeNode<E>> stack = new ArrayDeque<>();
        stack.offerLast(node);

        while (!stack.isEmpty()) {
            BinaryTreeNode<E> topNode = stack.pollLast();
            result.add(topNode.getVal());

            if (Objects.nonNull(topNode.getRight())){
                stack.offerLast(topNode.getRight());
            }
            if (Objects.nonNull(topNode.getLeft())){
                stack.offerLast(topNode.getLeft());
            }
        }
        return result;
    }
}
