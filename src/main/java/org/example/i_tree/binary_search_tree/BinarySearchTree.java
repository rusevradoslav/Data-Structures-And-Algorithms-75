package org.example.i_tree.binary_search_tree;

import org.example.i_tree.binary_tree.BinaryTreeNode;

import java.util.*;

/**
 * A generic Binary Search Tree supporting standard BST operations.
 *
 * <p>All operations assume the tree satisfies the BST invariant: for every node N,
 * all values in N's left subtree are strictly less than N.val, and all values in
 * N's right subtree are strictly greater. Duplicate values are ignored on insert.
 *
 * @param <E> the element type, which must be {@link Comparable}
 */
public class BinarySearchTree<E extends Comparable<E>> {

    /**
     * Searches for {@code value} in the BST rooted at {@code node}.
     *
     * @param node  the root of the (sub)tree to search
     * @param value the value to find
     * @return the node whose val equals {@code value}, or {@code null} if not found
     */
    public BinaryTreeNode<E> search(BinaryTreeNode<E> node, E value) {
        while (Objects.nonNull(node)) {
            int comparingResult = value.compareTo(node.val);
            if (comparingResult < 0) {
                node = node.left;
            } else if (comparingResult > 0) {
                node = node.right;
            } else {
                return node;
            }
        }

        return null;
    }

    /**
     * Inserts {@code value} into the BST rooted at {@code node}.
     *
     * <p>If {@code node} is {@code null} a new single-node tree is returned.
     * Duplicate values are silently ignored.
     *
     * @param node  the root of the (sub)tree to insert into
     * @param value the value to insert
     * @return the root of the updated tree
     */
    public BinaryTreeNode<E> insert(BinaryTreeNode<E> node, E value) {
        if (Objects.isNull(node)) {
            return new BinaryTreeNode<>(value);
        }

        int comparingResult = value.compareTo(node.val);

        if (comparingResult < 0) {
            node.left = insert(node.left, value);
        } else if (comparingResult > 0) {
            node.right = insert(node.right, value);
        }

        return node;
    }

    /**
     * Deletes the node with {@code value} from the BST rooted at {@code node}.
     *
     * <p>Handles three cases:
     * <ol>
     *   <li>Leaf node — simply removed.</li>
     *   <li>One child — replaced by that child.</li>
     *   <li>Two children — replaced by its inorder successor (minimum of right subtree),
     *       then the successor is deleted from the right subtree.</li>
     * </ol>
     * If {@code value} is not present the tree is returned unchanged.
     *
     * @param node  the root of the (sub)tree to delete from
     * @param value the value to delete
     * @return the root of the updated tree
     */
    public BinaryTreeNode<E> delete(BinaryTreeNode<E> node, E value) {
        if (Objects.isNull(node)) {
            return null;
        }

        int comparingResult = value.compareTo(node.val);
        if (comparingResult < 0) {
            node.left = delete(node.left, value);
        } else if (comparingResult > 0) {
            node.right = delete(node.right, value);
        } else {

            if (Objects.isNull(node.left)) {
                return node.right;
            }
            if (Objects.isNull(node.right)) {
                return node.left;
            }

            E minValue = findMin(node.right);
            node.val = minValue;
            node.right = delete(node.right, minValue);
        }

        return node;
    }

    /**
     * Finds the inorder successor of {@code value} in the BST rooted at {@code root}.
     *
     * <p>The inorder successor is the node with the smallest value strictly greater
     * than {@code value}. The search does not require {@code value} to exist in the tree.
     *
     * @param root  the root of the BST
     * @param value the reference value
     * @return the node containing the inorder successor, or {@code null} if none exists
     */
    public BinaryTreeNode<E> findInorderSuccessor(BinaryTreeNode<E> root, E value) {
        BinaryTreeNode<E> successor = null;
        while (Objects.nonNull(root)) {
            int comparingResult = value.compareTo(root.val);
            if (comparingResult < 0) {
                successor = root;
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return successor;
    }

    /**
     * Finds the inorder predecessor of {@code value} in the BST rooted at {@code root}.
     *
     * <p>The inorder predecessor is the node with the largest value strictly less
     * than {@code value}. The search does not require {@code value} to exist in the tree.
     *
     * @param root  the root of the BST
     * @param value the reference value
     * @return the node containing the inorder predecessor, or {@code null} if none exists
     */
    public BinaryTreeNode<E> findInorderPredecessor(BinaryTreeNode<E> root, E value) {
        BinaryTreeNode<E> predecessor = null;
        while (Objects.nonNull(root)) {
            int comparingResult = value.compareTo(root.val);
            if (comparingResult > 0) {
                predecessor = root;
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return predecessor;
    }

    /**
     * Returns the minimum value in the BST rooted at {@code node}.
     *
     * @param node the root of the (sub)tree
     * @return the minimum value, or {@code null} if {@code node} is {@code null}
     */
    public E findMin(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return null;
        }
        while (Objects.nonNull(node.left)) {
            node = node.left;
        }
        return node.val;
    }

    /**
     * Returns the maximum value in the BST rooted at {@code node}.
     *
     * @param node the root of the (sub)tree
     * @return the maximum value, or {@code null} if {@code node} is {@code null}
     */
    public E findMax(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return null;
        }
        while (Objects.nonNull(node.right)) {
            node = node.right;
        }
        return node.val;
    }

    /**
     * Returns the node with the largest value less than or equal to {@code value}
     * (the "floor") in the BST rooted at {@code node}.
     *
     * @param node  the root of the BST
     * @param value the query value
     * @return the floor node, or {@code null} if all values in the tree exceed {@code value}
     */
    public BinaryTreeNode<E> floor(BinaryTreeNode<E> node, E value) {
        BinaryTreeNode<E> floorNode = null;
        while (Objects.nonNull(node)) {
            int comparingResult = value.compareTo(node.val);
            if (comparingResult == 0) {
                floorNode = node;
                break;
            }
            if (comparingResult < 0) {
                node = node.left;
            } else {
                floorNode = node;
                node = node.right;
            }
        }
        return floorNode;
    }

    /**
     * Returns the node with the smallest value greater than or equal to {@code value}
     * (the "ceiling") in the BST rooted at {@code node}.
     *
     * @param node  the root of the BST
     * @param value the query value
     * @return the ceiling node, or {@code null} if all values in the tree are below {@code value}
     */
    public BinaryTreeNode<E> ceiling(BinaryTreeNode<E> node, E value) {
        BinaryTreeNode<E> ceilingNode = null;
        while (Objects.nonNull(node)) {
            int comparingResult = value.compareTo(node.val);
            if (comparingResult == 0) {
                ceilingNode = node;
                break;
            }

            if (comparingResult < 0) {
                ceilingNode = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return ceilingNode;
    }

    /**
     * Returns {@code true} if the tree rooted at {@code node} is a valid BST.
     *
     * <p>Validates the global BST invariant by propagating min/max bounds through
     * the recursion, catching cases that are locally valid but globally invalid.
     *
     * @param node the root of the tree to validate
     * @return {@code true} if the tree satisfies the BST property, {@code false} otherwise
     */
    public boolean isValidBST(BinaryTreeNode<E> node) {
        return isValid(node, null, null);
    }

    private boolean isValid(BinaryTreeNode<E> node, E min, E max) {
        if (Objects.isNull(node)) {
            return true;
        }
        if (Objects.nonNull(min) && node.val.compareTo(min) <= 0) {
            return false;
        }
        if (Objects.nonNull(max) && node.val.compareTo(max) >= 0) {
            return false;
        }

        return isValid(node.left, min, node.val) &&
                isValid(node.right, node.val, max);
    }

    /**
     * Returns the values of the BST rooted at {@code node} in ascending (inorder) order,
     * using a recursive traversal.
     *
     * @param node the root of the BST
     * @return a list of values in sorted ascending order; empty list if {@code node} is {@code null}
     */
    public List<E> inorderRecursively(BinaryTreeNode<E> node) {
        List<E> elements = new ArrayList<>();

        if (Objects.isNull(node)) {
            return elements;
        }
        collectElementsRecursively(elements, node);
        return elements;
    }

    private void collectElementsRecursively(List<E> elements, BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return;
        }
        collectElementsRecursively(elements, node.left);
        elements.add(node.val);
        collectElementsRecursively(elements, node.right);
    }

    /**
     * Returns the values of the BST rooted at {@code node} in ascending (inorder) order,
     * using an iterative traversal with an explicit stack.
     *
     * @param node the root of the BST
     * @return a list of values in sorted ascending order; empty list if {@code node} is {@code null}
     */
    public List<E> inorderIteratively(BinaryTreeNode<E> node) {
        List<E> elements = new ArrayList<>();

        if (Objects.isNull(node)) {
            return elements;
        }

        Deque<BinaryTreeNode<E>> stack = new ArrayDeque<>();
        BinaryTreeNode<E> current = node;
        while (Objects.nonNull(current) || !stack.isEmpty()) {
            while (Objects.nonNull(current)) {
                stack.push(current);
                current = current.left;
            }
            BinaryTreeNode<E> lastNode = stack.pop();
            elements.add(lastNode.val);
            current = lastNode.right;
        }

        return elements;
    }
}
