package org.example.i_tree.binary_tree.bfs;

import org.example.i_tree.binary_tree.BinaryTreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * Flat BFS binary tree traversal — returns all node values in level order
 * as a single flat list.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Enqueue the root</li>
 *   <li>While the queue is non-empty, dequeue a node, add its value to the result,
 *       then enqueue its left and right children (if present)</li>
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
 * Flat BFS: [10, 5, 15, 3, 7, 20]
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node visited exactly once.
 *
 * <p>Space Complexity: O(w) — where w is the maximum width of the tree
 * (maximum number of nodes at any single level).
 *
 * @param <E> the type of value stored in each tree node
 */
public class BfsFlatBinaryTreeTraversalImpl<E> implements BfsBinaryTreeTraversal<E, List<E>> {

    /**
     * {@inheritDoc}
     *
     * <p>Uses a queue to visit nodes level by level, collecting all values
     * into a single flat list.
     */
    @Override
    public List<E> traverse(BinaryTreeNode<E> node) {
        List<E> result = new ArrayList<>();
        if (Objects.isNull(node)) {
            return result;
        }

        Deque<BinaryTreeNode<E>> queue = new ArrayDeque<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            BinaryTreeNode<E> element = queue.poll();
            result.add(element.getVal());
            if (Objects.nonNull(element.getLeft())) {
                queue.offer(element.getLeft());
            }
            if (Objects.nonNull(element.getRight())) {
                queue.offer(element.getRight());
            }
        }

        return result;
    }
}
