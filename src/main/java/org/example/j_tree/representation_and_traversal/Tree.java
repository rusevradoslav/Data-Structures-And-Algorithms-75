package org.example.j_tree.representation_and_traversal;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * Generic n-ary tree backed by parent pointers and an adjacency list of children.
 *
 * <p>Each node stores a value, a reference to its parent (or {@code null} for the root),
 * and an ordered list of child nodes. This representation supports an arbitrary number
 * of children per node.
 *
 * <p><b>Traversals:</b>
 * <ul>
 *   <li>{@link #orderBfs()} — breadth-first using an {@code ArrayDeque} as a queue</li>
 *   <li>{@link #preorderDfs()} — recursive depth-first, parent visited before children</li>
 *   <li>{@link #postorderDfs()} — recursive depth-first, children visited before parent</li>
 * </ul>
 *
 * <p><b>Mutations:</b>
 * <ul>
 *   <li>{@link #addChild(Object, Tree)} — locate a parent by key (DFS) and append a subtree</li>
 *   <li>{@link #removeNode(Object)} — detach a node from its parent and nullify it</li>
 *   <li>{@link #swap(Object, Object)} — swap two nodes handling three cases:
 *       root swap, parent-child swap, and general (sibling) swap</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *          7
 *        / | \
 *      19  21  14
 *     /|\      /\
 *    1 12 31  23  6
 *
 * BFS:      [7, 19, 21, 14, 1, 12, 31, 23, 6]
 * Preorder: [7, 19, 1, 12, 31, 21, 14, 23, 6]
 * Postorder:[1, 12, 31, 19, 21, 23, 6, 14, 7]
 * </pre>
 *
 * <p>Time Complexity: O(n) for all traversals, addChild, removeNode, and swap
 * (each requires a DFS lookup by key).
 *
 * <p>Space Complexity: O(n) for BFS (queue), O(h) for DFS (recursion stack,
 * where h is the height of the tree).
 *
 * @param <E> the type of value stored in each tree node
 */
public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    /**
     * Creates a tree node with the given value and optional children.
     *
     * <p>Each child's parent reference is set to this node automatically.
     *
     * @param value    the value stored in this node
     * @param children zero or more child subtrees to attach
     */
    @SafeVarargs
    public Tree(E value, Tree<E>... children) {
        this.value = value;
        this.children = new ArrayList<>();
        for (Tree<E> child : children) {
            this.children.add(child);
            child.parent = this;
        }
    }


    /**
     * {@inheritDoc}
     *
     * <p>Uses an {@link ArrayDeque} as a FIFO queue, polling one node at a time
     * and enqueuing all its children.
     */
    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();

        queue.add(this);
        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();
            result.add(tree.value);
            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Recursively visits all children before recording the current node's value.
     */
    @Override
    public List<E> postorderDfs() {
        List<E> result = new ArrayList<>();
        executePostorderDfs(this, result);
        return result;
    }

    private void executePostorderDfs(Tree<E> tree, List<E> result) {
        for (Tree<E> child : tree.children) {
            executePostorderDfs(child, result);
        }
        result.add(tree.value);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Records the current node's value first, then recursively visits each child.
     */
    @Override
    public List<E> preorderDfs() {
        List<E> result = new ArrayList<>();
        executePreorderDfs(this, result);
        return result;

    }

    private void executePreorderDfs(Tree<E> tree, List<E> result) {
        result.add(tree.value);
        for (Tree<E> child : tree.children) {
            executePreorderDfs(child, result);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Performs a DFS lookup for the parent, then appends the child and
     * sets its parent reference.
     */
    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parentNode = findNodeByKey(parentKey, this);
        if (Objects.isNull(parentNode)) {
            throw new IllegalArgumentException(String.format("The parent node is null for key: %s", parentKey));
        }
        child.parent = parentNode;
        parentNode.children.add(child);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Detaches the node from its parent's children list, clears its own
     * children, and nullifies its value.
     */
    @Override
    public void removeNode(E nodeKey) {
        Tree<E> node = findNodeByKey(nodeKey, this);
        if (Objects.isNull(node)) {
            throw new IllegalArgumentException(String.format("The node is null for key: %s", nodeKey));
        }

        node.parent.children.remove(node);
        node.children.clear();
        node.value = null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Three cases are handled:
     * <ol>
     *   <li><b>Root swap</b> — if either node is the root, copy the other node's
     *       value and children into the root</li>
     *   <li><b>Parent-child swap</b> — transfers children between the two nodes
     *       and fixes all parent references to avoid circular links</li>
     *   <li><b>General swap</b> — replaces each node in its parent's children list
     *       with the other node and updates parent pointers</li>
     * </ol>
     */
    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode = findNodeByKey(firstKey, this);
        Tree<E> secondNode = findNodeByKey(secondKey, this);
        if (Objects.isNull(firstNode)) {
            throw new IllegalArgumentException(String.format("The first node is null for key: %s", firstKey));
        }
        if (Objects.isNull(secondNode)) {
            throw new IllegalArgumentException(String.format("The second node is null for key: %s", secondKey));
        }
        Tree<E> firstNodeParent = firstNode.parent;
        Tree<E> secondNodeParent = secondNode.parent;
        if (Objects.isNull(firstNodeParent)) {
            swapRoot(secondNode);
            return;
        } else if (Objects.isNull(secondNodeParent)) {
            swapRoot(firstNode);
            return;
        }

        if (secondNodeParent == firstNode) {
            swapParentChild(firstNode, secondNode, firstNodeParent);
        } else if (firstNodeParent == secondNode) {
            swapParentChild(secondNode, firstNode, secondNodeParent);
        } else {
            int firstIndex = firstNodeParent.children.indexOf(firstNode);
            int secondIndex = secondNodeParent.children.indexOf(secondNode);
            firstNodeParent.children.set(firstIndex, secondNode);
            secondNodeParent.children.set(secondIndex, firstNode);
            firstNode.parent = secondNodeParent;
            secondNode.parent = firstNodeParent;
        }


    }

    private Tree<E> findNodeByKey(E parentKey, Tree<E> node) {
        if (Objects.isNull(node.value)) {
            return null;
        }
        if (node.value.equals(parentKey)) {
            return node;
        }
        for (Tree<E> child : node.children) {
            Tree<E> found = findNodeByKey(parentKey, child);
            if (Objects.nonNull(found)) {
                return found;
            }
        }

        return null;
    }

    private void swapParentChild(Tree<E> parentNode, Tree<E> childNode, Tree<E> grandParent) {
        int parentIndex = grandParent.children.indexOf(parentNode);
        int childIndex = parentNode.children.indexOf(childNode);

        grandParent.children.set(parentIndex, childNode);
        parentNode.children.set(childIndex, parentNode);

        List<Tree<E>> parentChildren = new ArrayList<>(parentNode.children);
        parentNode.children = new ArrayList<>(childNode.children);
        childNode.children = parentChildren;

        childNode.parent = grandParent;
        parentNode.parent = childNode;
        updateParentRefs(childNode);
        updateParentRefs(parentNode);
    }

    private void updateParentRefs(Tree<E> node) {
        for (Tree<E> child : node.children) {
            child.parent = node;
        }
    }

    private void swapRoot(Tree<E> node) {
        this.parent = null;
        this.value = node.value;
        this.children = node.children;
    }
}



