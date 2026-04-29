package org.example.j_tree.binary_tree.dfs;

import org.example.j_tree.binary_tree.BinaryTreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * Inorder DFS (Left → Root → Right) binary tree traversal.
 *
 * <p>Recursively traverses the left subtree, visits the current node,
 * then recursively traverses the right subtree. For a BST this produces
 * values in sorted ascending order.
 *
 * <p><b>Recursive approach ({@link #recursive}):</b>
 * <ul>
 *   <li>Recurse into the left child</li>
 *   <li>Add the current node's value to the result</li>
 *   <li>Recurse into the right child</li>
 * </ul>
 *
 * <p><b>Iterative approach ({@link #iterative}):</b>
 * <ul>
 *   <li>Drill left as far as possible, pushing each node onto the stack</li>
 *   <li>Pop a node, add its value to the result</li>
 *   <li>Move to the popped node's right child and repeat</li>
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
 * Inorder: [3, 5, 7, 10, 15, 20]
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node visited exactly once.
 *
 * <p>Space Complexity: O(h) — recursion stack depth or explicit stack size,
 * where h is the height of the tree.
 *
 * @param <E> the type of value stored in each tree node
 */
public class InorderBinaryTreeTraversalImpl<E> implements BinaryTreeTraversal<E> {

    /**
     * {@inheritDoc}
     *
     * <p>Delegates to a recursive helper that visits the left subtree,
     * then the node, then the right subtree.
     */
    @Override
    public List<E> recursive(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return Collections.emptyList();
        }

        if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
            return Collections.singletonList(node.val);
        }

        List<E> result = new ArrayList<>();
        executeInorderDFS(result, node);
        return result;
    }

    private void executeInorderDFS(List<E> result, BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return;
        }
        executeInorderDFS(result, node.left);
        result.add(node.val);
        executeInorderDFS(result, node.right);
    }


    /**
     * {@inheritDoc}
     *
     * <p>Uses a stack to drill left, then pops and visits each node before
     * moving to its right child.
     */
    @Override
    public List<E> iterative(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return Collections.emptyList();
        }

        if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
            return Collections.singletonList(node.val);
        }

        List<E> result = new ArrayList<>();
        Deque<BinaryTreeNode<E>> stack = new ArrayDeque<>();
        BinaryTreeNode<E> currentNode = node;

        while (Objects.nonNull(currentNode) || !stack.isEmpty()) {
            while (Objects.nonNull(currentNode)) {
                stack.offerLast(currentNode);
                currentNode = currentNode.left;
            }

            BinaryTreeNode<E> lastNode = stack.removeLast();
            result.add(lastNode.val);
            currentNode = lastNode.right;
        }
        return result;
    }
}
