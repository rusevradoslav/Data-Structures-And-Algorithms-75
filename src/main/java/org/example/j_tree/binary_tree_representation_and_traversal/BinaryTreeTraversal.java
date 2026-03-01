package org.example.j_tree.binary_tree_representation_and_traversal;

import java.util.List;

/**
 * Contract for binary tree traversal strategies, providing both a recursive
 * and an iterative implementation of the same traversal order.
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
