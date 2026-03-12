package org.example.j_tree.binary_tree_representation_and_traversal.bfs;

import org.example.j_tree.binary_tree_representation_and_traversal.BinaryTreeNode;

/**
 * Contract for binary tree BFS traversal strategies.
 *
 * <p>All implementations are forms of <b>Breadth-First Search (BFS)</b> — they
 * explore nodes level by level, visiting all nodes at depth {@code d} before
 * any node at depth {@code d + 1}.
 *
 * @param <E> the type of value stored in each tree node
 * @param <R> the return type of the traversal result
 */
public interface BfsBinaryTreeTraversal<E, R> {

    /**
     * Traverses the binary tree in BFS (level) order.
     *
     * @param node the root of the tree (or subtree) to traverse; may be {@code null}
     * @return the traversal result, or an empty collection if {@code node} is {@code null}
     */
    R traverse(BinaryTreeNode<E> node);

}
