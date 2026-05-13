package org.example.i_tree.binary_tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A node in a binary tree, holding a value and references to left and right children.
 *
 * <p>Both {@code left} and {@code right} default to {@code null}, representing
 * absent children (leaf node when both are {@code null}).
 *
 * @param <E> the type of value stored in this node
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BinaryTreeNode<E> {
    private E val;
    private BinaryTreeNode<E> left;
    private BinaryTreeNode<E> right;

    public BinaryTreeNode(E val) {
        this.val = val;
    }
}
