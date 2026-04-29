package org.example.j_tree.binary_tree.dfs;

import org.example.j_tree.binary_tree.BinaryTreeNode;

import java.util.*;

/**
 * Postorder DFS (Left → Right → Root) binary tree traversal.
 *
 * <p>Recursively traverses the left subtree, then the right subtree,
 * and finally visits the current node.
 *
 * <p><b>Recursive approach ({@link #recursive}):</b>
 * <ul>
 *   <li>Recurse into the left child</li>
 *   <li>Recurse into the right child</li>
 *   <li>Add the current node's value to the result</li>
 * </ul>
 *
 * <p><b>Iterative approach ({@link #iterative}) — reversed preorder trick:</b>
 * <ol>
 *   <li>Pop a node, insert its value at the <em>front</em> of the result
 *       using {@code addFirst}</li>
 *   <li>Push left before right (so right is popped first)</li>
 *   <li>Front-insertion naturally reverses the Root → Right → Left order
 *       into Left → Right → Root</li>
 * </ol>
 *
 * <p>Example:
 * <pre>
 *        10
 *       /  \
 *      5    15
 *     / \     \
 *    3   7    20
 *
 * Postorder: [3, 7, 5, 20, 15, 10]
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node visited exactly once.
 *
 * <p>Space Complexity: O(h) — recursion stack depth or explicit stack size,
 * where h is the height of the tree.
 *
 * @param <E> the type of value stored in each tree node
 */
public class PostorderBinaryTreeTraversalImpl<E> implements BinaryTreeTraversal<E> {

    /**
     * {@inheritDoc}
     *
     * <p>Delegates to a recursive helper that visits the left subtree,
     * then the right subtree, then the node.
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

        executePostorderDFS(result, node);

        return result;
    }

    private void executePostorderDFS(List<E> result, BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return;
        }
        executePostorderDFS(result, node.left);
        executePostorderDFS(result, node.right);
        result.add(node.val);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Uses a reversed preorder strategy: pops a node, pushes left then right,
     * and inserts values at the front of a {@link LinkedList} via {@code addFirst}
     * to produce Left → Right → Root order.
     */
    @Override
    public List<E> iterative(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return Collections.emptyList();
        }

        if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
            return Collections.singletonList(node.val);
        }
        List<E> result = new LinkedList<>();
        Deque<BinaryTreeNode<E>> stack = new ArrayDeque<>();
        stack.offerLast(node);
        while (!stack.isEmpty()) {
            BinaryTreeNode<E> currentNode = stack.pollLast();
            if (Objects.nonNull(currentNode.left)) {
                stack.offerLast(currentNode.left);
            }
            if (Objects.nonNull(currentNode.right)) {
                stack.offerLast(currentNode.right);
            }
            result.addFirst(currentNode.val);
        }

        return result;
    }
}
