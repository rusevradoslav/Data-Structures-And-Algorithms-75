package org.example.j_tree.binary_tree_representation_and_traversal.bfs;

import org.example.j_tree.binary_tree_representation_and_traversal.BinaryTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Level-by-level BFS binary tree traversal — returns node values grouped
 * by depth, where each inner list represents one level of the tree.
 *
 * <p><b>Approach:</b>
 * <ul>
 *   <li>Enqueue the root</li>
 *   <li>At the start of each iteration, snapshot the current queue size —
 *       this is the number of nodes on the current level</li>
 *   <li>Dequeue exactly that many nodes, collect their values into a level list,
 *       and enqueue their children</li>
 *   <li>Add the level list to the result and repeat</li>
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
 * Level BFS: [[10], [5, 15], [3, 7, 20]]
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node visited exactly once.
 *
 * <p>Space Complexity: O(w) — where w is the maximum width of the tree
 * (maximum number of nodes at any single level).
 *
 * @param <E> the type of value stored in each tree node
 */
public class BfsLevelBinaryTreeTraversalImpl<E> implements BfsBinaryTreeTraversal<E, List<List<E>>> {

    /**
     * {@inheritDoc}
     *
     * <p>Snapshots the queue size at the start of each level to process
     * exactly one level per outer iteration, grouping values into sublists.
     */
    @Override
    public List<List<E>> traverse(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return Collections.emptyList();
        }

        List<List<E>> result = new ArrayList<>();

        Queue<BinaryTreeNode<E>> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<E> levelElements = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                BinaryTreeNode<E> next = queue.poll();
                levelElements.add(next.val);
                if (Objects.nonNull(next.left)) {
                    queue.offer(next.left);
                }
                if (Objects.nonNull(next.right)) {
                    queue.offer(next.right);
                }
            }
            result.add(levelElements);
        }

        return result;
    }
}
