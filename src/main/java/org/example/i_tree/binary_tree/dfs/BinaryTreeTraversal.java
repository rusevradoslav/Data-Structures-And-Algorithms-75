package org.example.i_tree.binary_tree.dfs;

import org.example.i_tree.binary_tree.BinaryTreeNode;

import java.util.List;

/**
 * Contract for binary tree DFS traversal strategies, providing both a recursive
 * and an iterative implementation of the same traversal order.
 *
 * <p>All implementations (inorder, preorder, postorder) are forms of
 * <b>Depth-First Search (DFS)</b> — they explore as far down a branch as possible
 * before backtracking.
 *
 * @param <E> the type of value stored in each tree node
 */
public interface BinaryTreeTraversal<E> {

    /**
     * Traverses the binary tree recursively.
     *
     * @param node the root of the tree (or subtree) to traverse; may be {@code null}
     * @return a list of node values in traversal order, or an empty list if {@code node} is {@code null}
     */
    List<E> recursive(BinaryTreeNode<E> node);

    /**
     * Traverses the binary tree iteratively using an explicit stack.
     *
     * @param node the root of the tree (or subtree) to traverse; may be {@code null}
     * @return a list of node values in traversal order, or an empty list if {@code node} is {@code null}
     */
    List<E> iterative(BinaryTreeNode<E> node);

}
