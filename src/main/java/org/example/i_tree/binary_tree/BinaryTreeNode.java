package org.example.i_tree.binary_tree;

/**
 * A node in a binary tree, holding a value and references to left and right children.
 *
 * <p>Both {@code left} and {@code right} default to {@code null}, representing
 * absent children (leaf node when both are {@code null}).
 *
 * @param <E> the type of value stored in this node
 */
public class BinaryTreeNode<E> {
   public E val;
   public BinaryTreeNode<E> left;
   public BinaryTreeNode<E> right;

    /**
     * Creates a leaf node with the given value.
     *
     * @param val the value to store in this node
     */
    public BinaryTreeNode(E val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}
