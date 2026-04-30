package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PathSum3 {


    /**
     * Counts the number of downward paths in the tree whose node values sum to targetSum.
     * A path can start and end at any node, not just the root or a leaf.
     *
     * <p>Approach (prefix sum O(n)):
     * A single DFS tracks the running prefix sum from root to the current node.
     * At each node, if {@code prefixSum - targetSum} was seen earlier on the path,
     * the segment between that earlier point and the current node is a valid path.
     * A HashMap stores prefix sum frequencies; backtracking removes the current node's
     * contribution so sibling subtrees are not affected.
     *
     * @param root      the root of the binary tree
     * @param targetSum the target path sum
     * @return number of paths that sum to targetSum
     */
    public int pathSum(TreeNode root, int targetSum) {
        if (Objects.isNull(root)) {
            return 0;
        }

        Map<Long, Integer> map = new HashMap<>();
        map.put(0L, 1);
        return dfs(root, 0, targetSum, map);

    }

    /**
     * DFS helper that counts valid paths ending at the current node and below.
     *
     * <p>At each node:
     * <ol>
     *   <li>Compute {@code currentPrefixSum = prefixSum + node.val}</li>
     *   <li>Look up how many times {@code currentPrefixSum - targetSum} appeared before —
     *       each occurrence means a valid path ends here</li>
     *   <li>Record {@code currentPrefixSum} in the map, recurse, then remove it (backtrack)</li>
     * </ol>
     *
     * @param node      current node
     * @param prefixSum running sum from root to the parent of this node
     * @param targetSum the target path sum
     * @param map       prefix sum frequency map for the current root-to-node path
     * @return number of valid paths in the subtree rooted at this node
     */
    private int dfs(TreeNode node, long parentSum, int targetSum, Map<Long, Integer> prefixSumFrequency) {
        if (Objects.isNull(node)) {
            return 0;
        }
        long currentSum = parentSum + node.val;
        int pathCount = prefixSumFrequency.getOrDefault(currentSum - targetSum, 0);
        prefixSumFrequency.put(currentSum, prefixSumFrequency.getOrDefault(currentSum, 0) + 1);
        int leftCount = dfs(node.left, currentSum, targetSum, prefixSumFrequency);
        int rightCount = dfs(node.right, currentSum, targetSum, prefixSumFrequency);

        pathCount += leftCount + rightCount;

        int frequency = prefixSumFrequency.get(currentSum);
        if (frequency == 1) {
            prefixSumFrequency.remove(currentSum);
        } else {
            prefixSumFrequency.put(currentSum, frequency - 1);
        }

        return pathCount;
    }

    /**
     * Counts the number of downward paths in the tree whose node values sum to targetSum.
     * A path can start and end at any node, not just the root or a leaf.
     *
     * <p>Approach (brute-force O(n²)):
     * For every node in the tree, count all paths starting at that node going downward.
     * This is done by two cooperating recursive methods:
     * <ul>
     *   <li>{@code countGoodPaths} — visits every node in the tree (outer recursion)</li>
     *   <li>{@code countFrom} — counts paths starting at a specific node (inner recursion)</li>
     * </ul>
     *
     * @param root      the root of the binary tree
     * @param targetSum the target path sum
     * @return number of paths that sum to targetSum
     */
    public int pathSumBruteForce(TreeNode root, int targetSum) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return countGoodPaths(root, targetSum);
    }

    /**
     * Visits every node in the tree and accumulates the count of valid paths
     * starting at each node by delegating to {@code countFrom}.
     *
     * @param root      current node being visited
     * @param targetSum the target path sum
     * @return total number of valid paths in the subtree rooted at this node
     */
    private int countGoodPaths(TreeNode root, int targetSum) {
        if (Objects.isNull(root)) {
            return 0;
        }

        int total = 0;
        total += countFrom(root, targetSum);
        total += countGoodPaths(root.left, targetSum);
        total += countGoodPaths(root.right, targetSum);
        return total;
    }

    /**
     * Counts valid paths that start at this specific node and go downward.
     * At each step, {@code remaining} is reduced by the current node's value.
     * When {@code remaining} reaches zero, a valid path is found — but we
     * continue recursing to catch additional valid paths deeper in the subtree.
     *
     * @param node      current node in the path
     * @param remaining how much sum is still needed to reach targetSum
     * @return number of valid paths starting at the original node and passing through here
     */
    private int countFrom(TreeNode node, long remaining) {
        if (Objects.isNull(node)) {
            return 0;
        }
        int currentCount = node.val == remaining ? 1 : 0;
        int leftCount = countFrom(node.left, remaining - node.val);
        int rightCount = countFrom(node.right, remaining - node.val);

        return currentCount + leftCount + rightCount;
    }
}
