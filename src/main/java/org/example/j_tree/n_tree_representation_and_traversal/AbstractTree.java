package org.example.j_tree.n_tree_representation_and_traversal;

import java.util.List;

/**
 * Contract for a generic n-ary tree that supports traversal, mutation, and node swapping.
 *
 * <p>Each node holds a value of type {@code E} and may have zero or more children,
 * forming an unordered tree (not limited to binary).
 *
 * @param <E> the type of value stored in each tree node
 */
public interface AbstractTree<E> {

    /**
     * Returns the values of all nodes in breadth-first (level-order) order.
     *
     * @return a list of node values visited level by level, left to right
     */
    List<E> orderBfs();

    /**
     * Returns the values of all nodes in depth-first postorder
     * (children before parent).
     *
     * @return a list of node values in postorder
     */
    List<E> postorderDfs();

    /**
     * Returns the values of all nodes in depth-first preorder
     * (parent before children).
     *
     * @return a list of node values in preorder
     */
    List<E> preorderDfs();

    /**
     * Adds a child subtree under the node whose value equals {@code parentKey}.
     *
     * @param parentKey the value that identifies the target parent node
     * @param child     the subtree to attach
     * @throws IllegalArgumentException if no node with {@code parentKey} exists
     */
    void addChild(E parentKey, Tree<E> child);

    /**
     * Removes the node whose value equals {@code nodeKey} along with its entire subtree.
     *
     * @param nodeKey the value that identifies the node to remove
     * @throws IllegalArgumentException if no node with {@code nodeKey} exists
     */
    void removeNode(E nodeKey);

    /**
     * Swaps the positions of two nodes identified by their values.
     *
     * <p>Handles three cases: root swap, parent-child swap, and general (sibling) swap.
     *
     * @param firstKey  the value identifying the first node
     * @param secondKey the value identifying the second node
     * @throws IllegalArgumentException if either key is not found in the tree
     */
    void swap(E firstKey, E secondKey);
}
