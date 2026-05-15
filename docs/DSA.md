# Data Structures & Algorithms — Interview Reference

## Table of Contents

- [Binary Tree](#binary-tree)
  - [Fundamentals](#fundamentals)
  - [DFS Traversals](#dfs-traversals)
  - [BFS Traversal](#bfs-traversal)
  - [Binary Search Tree (BST)](#binary-search-tree-bst)
    - [Search](#search)
    - [Insert](#insert)
    - [Delete](#delete)
    - [findMin / findMax](#findmin--findmax)
    - [Inorder Successor / Predecessor](#inorder-successor--predecessor)
    - [Floor / Ceiling](#floor--ceiling)
    - [isValidBST](#isvalidbst)
    - [Inorder Traversal](#inorder-traversal)
  - [Quick Reference](#quick-reference)
- [Graphs](#graphs)
  - [Fundamentals](#fundamentals-1)
  - [Data Structures for Graphs](#data-structures-for-graphs)
  - [Graph Traversals](#graph-traversals)
    - [Depth-First Search (DFS)](#depth-first-search-dfs)
    - [Breadth-First Search (BFS)](#breadth-first-search-bfs)
    - [DFS vs BFS](#dfs-vs-bfs)
    - [Path Reconstruction](#path-reconstruction)
    - [Transitive Closure](#transitive-closure)
    - [Topological Sort](#topological-sort)
      - [DFS Recursive Approach](#dfs-recursive-approach)
      - [DFS Iterative Approach](#dfs-iterative-approach)
      - [Recursive vs Iterative DFS](#recursive-vs-iterative-dfs)
      - [Kahn's Algorithm](#kahns-algorithm)
      - [DFS vs Kahn's](#dfs-vs-kahns)
  - [Shortest Paths](#shortest-paths)
    - [Dijkstra's Algorithm](#dijkstras-algorithm)
    - [BFS vs Dijkstra's](#bfs-vs-dijkstras)
  - [Minimum Spanning Tree](#minimum-spanning-tree)
    - [Prim's Algorithm](#prims-algorithm)
- [Heaps & Priority Queues](#heaps--priority-queues)
- [Binary Search](#binary-search)
- [Dynamic Programming](#dynamic-programming)

---

## Binary Tree

### Fundamentals

**Definition**

A binary tree is a hierarchical data structure where each node has at most 2 children (0, 1, or 2). "At most 2" is the key phrase — not "exactly 2" (that's a full binary tree).

**Node Structure (LeetCode Standard)**

Public fields, no getters/setters.

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
}
```

**Terminology**

| Term | Definition | Key Detail |
|---|---|---|
| Depth | Edges from root DOWN to a node | Root depth = 0 |
| Height | Edges on longest path from node DOWN to leaf | Leaf height = 0 |
| Ancestors | All nodes from given node UP to root (inclusive) | A = Above |
| Descendants | All nodes from given node DOWN to leaves (inclusive) | D = Down |
| Subtree | Node + all descendants; forms a valid binary tree | Enables recursion |
| Width | Max nodes at any single level | Important for BFS space |
| Degree | Number of children a node has | Max 2 in binary tree |

**Five Types of Binary Trees**

*Full* — every node has exactly 0 or 2 children. Never 1.

```
    1
   / \
  2   3
 / \
4   5
```

*Complete* — all levels completely filled except possibly the last, which fills left-to-right. This is the shape a heap uses.

```
    1
   / \
  2   3
 / \  /
4  5  6
```

*Perfect* — all internal nodes have 2 children AND all leaves are at the same level. Every level is fully packed. 2^(h+1) - 1 total nodes.

```
      1
     / \
    2   3
   / \ / \
  4  5 6  7
```

*Balanced* — for every node, the height difference between left and right subtrees is ≤ 1. Guarantees O(log n) height.

```
    1
   / \
  2   3
 / \
4   5
```

*Degenerate (Skewed)* — every internal node has exactly 1 child. Effectively a linked list. Height = n - 1. All operations become O(n).

```
1
 \
  2
   \
    3
     \
      4
```

**Type Relationships**

| Tree | Properties |
|---|---|
| Perfect | Always full, complete, and balanced |
| Complete | Always balanced, but NOT always full |
| Single node | Full, complete, perfect, balanced. NOT degenerate |
| Two nodes (root + left child) | Complete, balanced. NOT full, NOT perfect |

**Key Properties**

| Property | Formula |
|---|---|
| Max nodes at level L | 2^L |
| Max total nodes at height H | 2^(H+1) - 1 (don't forget the -1) |
| Min height for N nodes | ⌊log₂(N)⌋ (best case: complete tree) |
| Max height for N nodes | N - 1 (worst case: degenerate) |
| Edges in N-node tree | N - 1 |
| Full tree: leaves vs internal | L = I + 1 (leaves = internal nodes + 1) |

**Edge Cases to Always Consider**

Null tree, single node, skewed tree (left-only or right-only), node with only one child.

**Interview Thinking Rule**

- Someone says "balanced" → immediately think O(log n) height.
- Nothing said about shape → assume O(n) worst case (degenerate).

---

### DFS Traversals

**Why Trees Are Recursive**

Every binary tree is either empty (null) or a root with two subtrees, each of which is itself a valid binary tree. This recursive definition maps directly to recursive code.

**Universal Recursive Pattern**

Three questions for every tree problem:
1. What's the base case?
2. If I had answers for left and right, how do I combine them?
3. What do I do with the current node?

```java
Result solve(TreeNode node) {
    if (Objects.isNull(node)) {
        return baseCase;
    }
    Result left = solve(node.left);
    Result right = solve(node.right);
    return combine(node.val, left, right);
}
```

*Example — Tree Height*

Returns -1 for null so that a leaf node correctly gets height 0. Postorder pattern — results from children needed before computing the parent.

```java
int height(TreeNode node) {
    if (Objects.isNull(node)) {
        return -1;
    }
    int leftH = height(node.left);
    int rightH = height(node.right);
    return Math.max(leftH, rightH) + 1;
}
```

**Three DFS Traversals**

The name tells you where Node goes. Left always comes before Right.

- **Preorder** (N → L → R) — process node BEFORE children.
- **Inorder** (L → N → R) — process node BETWEEN children.
- **Postorder** (L → R → N) — process node AFTER children.

Example tree:

```
    1
   / \
  2   3
 / \   \
4   5   6
```

| Traversal | Output |
|---|---|
| Preorder | 1, 2, 4, 5, 3, 6 |
| Inorder | 4, 2, 5, 1, 3, 6 |
| Postorder | 4, 5, 2, 6, 3, 1 |

**Recursive Implementations**

All three are identical except the placement of `result.add(node.val)`:

```java
void traverse(TreeNode node, List<Integer> result) {
    if (Objects.isNull(node)) {
        return;
    }
    // result.add(node.val);     ← PREORDER (before recursion)
    traverse(node.left, result);
    // result.add(node.val);     ← INORDER (between recursion)
    traverse(node.right, result);
    // result.add(node.val);     ← POSTORDER (after recursion)
}
```

**Iterative Implementations**

*Preorder* — push right before left (stack is LIFO, so left gets popped first):

```java
Deque<TreeNode> stack = new ArrayDeque<>();
stack.push(root);
while (!stack.isEmpty()) {
    TreeNode node = stack.pop();
    result.add(node.val);
    if (Objects.nonNull(node.right)) {
        stack.push(node.right);
    }
    if (Objects.nonNull(node.left)) {
        stack.push(node.left);
    }
}
```

*Inorder* — drill left, pop and process, move right:

```java
TreeNode current = root;
while (Objects.nonNull(current) || !stack.isEmpty()) {
    while (Objects.nonNull(current)) {
        stack.push(current);
        current = current.left;
    }
    current = stack.pop();
    result.add(current.val);
    current = current.right;
}
```

> The outer `while` needs `Objects.nonNull(current)` because after popping and moving to `current.right`, the stack might be empty but current still points to an unvisited right subtree.
>
> Missing `current = current.right` is the most common bug — right subtrees with left children get skipped entirely.

*Postorder* — modified preorder (N → R → L) with insert at front:

```java
stack.push(root);
while (!stack.isEmpty()) {
    TreeNode node = stack.pop();
    result.addFirst(node.val);
    if (Objects.nonNull(node.left)) {
        stack.push(node.left);
    }
    if (Objects.nonNull(node.right)) {
        stack.push(node.right);
    }
}
```

Normal preorder gives N → L → R. Swap push order to get N → R → L. Insert at front reverses it into L → R → N (postorder).

**When to Use Which Traversal**

Decision rule: *Do I need info from my parents or from my children?*

| Traversal | Direction | Use Cases |
|---|---|---|
| Preorder | Top-down (from parent) | Copy tree, serialization, prefix expressions, passing info downward (e.g., LC 1448) |
| Postorder | Bottom-up (from children) | Calculate height, delete tree, evaluate expression trees, compute subtree sizes (e.g., LC 104) |
| Inorder | — | Almost exclusively for BSTs — produces sorted output |

*Real-world analogies:*
- Preorder = `ls -R` — prints folder name first, then recurses into subfolders.
- Inorder = database B-tree index scan — returns rows in sorted order.
- Postorder = `rm -rf` — deletes all files inside a folder before the folder itself. Also `du` (disk usage).

**Inorder Walk-Through (Iterative)**

Tree:

```
    1
   / \
  2   3
 / \   \
4   5   6
```

| Step | Action | Stack | Current | Output |
|---|---|---|---|---|
| 1 | Drill left: push 1, 2, 4 | [1, 2, 4] | null | [] |
| 2 | Pop 4, output 4, move right | [1, 2] | null | [4] |
| 3 | Pop 2, output 2, move right | [1] | 5 | [4, 2] |
| 4 | Push 5, drill left | [1, 5] | null | [4, 2] |
| 5 | Pop 5, output 5, move right | [1] | null | [4, 2, 5] |
| 6 | Pop 1, output 1, move right | [] | 3 | [4, 2, 5, 1] |
| 7 | Push 3, drill left | [3] | null | [4, 2, 5, 1] |
| 8 | Pop 3, output 3, move right | [] | 6 | [4, 2, 5, 1, 3] |
| 9 | Push 6, drill left | [6] | null | [4, 2, 5, 1, 3] |
| 10 | Pop 6, output 6, move right | [] | null | [4, 2, 5, 1, 3, 6] |

> Step 6: stack is empty after popping 1, but `current = 3` (right child of 1). Without `Objects.nonNull(current)` in the outer while, we'd stop here and miss 3 and 6 entirely.

**Call Stack Behavior**

Recursion goes as deep as possible on the left first, then backtracks and goes right. The call stack at any point holds the nodes along the current path from root to the current position — not all n nodes.

**Complexity**

| Metric | Value |
|---|---|
| Time | O(n) — visit each node exactly once |
| Space | O(h) — call stack holds one root-to-node path at a time |

Balanced tree: h = O(log n). Degenerate tree: h = O(n). The O(log n) follows from 2^h ≈ n → h ≈ log₂(n). 1,000,000 nodes in a balanced tree means height ≈ 20.

---

### BFS Traversal

**What Is BFS?**

DFS goes deep — follows one path all the way down before backtracking. BFS goes wide — visits every node on the current level before moving to the next.

```
    1
   / \
  2   3
 / \   \
4   5   6
```

BFS visits: 1 → 2, 3 → 4, 5, 6 (level by level, left to right).

**Why a Queue?**

DFS uses a Stack (LIFO). BFS uses a Queue (FIFO). A queue ensures nodes discovered first are processed first — children wait in line until the entire current level is done.

**Flat BFS Template**

Visits all nodes level by level but doesn't distinguish level boundaries:

```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);
while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    // process node
    if (Objects.nonNull(node.left)) {
        queue.offer(node.left);
    }
    if (Objects.nonNull(node.right)) {
        queue.offer(node.right);
    }
}
```

**Level-by-Level Template**

`queue.size()` at the start of each iteration tells you exactly how many nodes are on the current level. Snapshot it BEFORE the inner loop — new children get added during the loop and we only want to process the current level's nodes.

```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);
while (!queue.isEmpty()) {
    int levelSize = queue.size();
    List<Integer> currentLevel = new ArrayList<>();
    for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        currentLevel.add(node.val);
        if (Objects.nonNull(node.left)) {
            queue.offer(node.left);
        }
        if (Objects.nonNull(node.right)) {
            queue.offer(node.right);
        }
    }
    result.add(currentLevel);
}
```

This is the base template for almost every BFS tree problem (LC 102 — Binary Tree Level Order Traversal).

**Walk-Through**

Tree:

```
    1
   / \
  2   3
 / \   \
4   5   6
```

| Level | Queue before | Process | Enqueue | Output |
|---|---|---|---|---|
| 0 | [1] | Poll 1 | 2, 3 | [1] |
| 1 | [2, 3] | Poll 2, poll 3 | 4, 5, 6 | [2, 3] |
| 2 | [4, 5, 6] | Poll 4, 5, 6 | — | [4, 5, 6] |

Result: `[[1], [2, 3], [4, 5, 6]]`

**Common Variations**

All are minor tweaks to the level-by-level template:

| Variation | Tweak |
|---|---|
| Right side view | Take the last node of each level |
| Level averages | Compute average of each level |
| Zigzag traversal | Alternate left-to-right and right-to-left per level |
| Minimum depth | Return depth when first leaf is hit — BFS guarantees shallowest |

**Complexity**

| Metric | Value |
|---|---|
| Time | O(n) — every node visited once |
| Space | O(w) where w = max width of the tree |

Balanced tree: bottom level holds up to n/2 nodes → O(n). Degenerate tree: each level has 1 node → O(1). This is the opposite of DFS: DFS space is worst on degenerate trees; BFS space is worst on balanced trees.

---

### Binary Search Tree (BST)

**Definition**

A BST is a binary tree where for every node:
- All nodes in its **left subtree** have values **strictly less than** the node's value.
- All nodes in its **right subtree** have values **strictly greater than** the node's value.

Each comparison skips about half the remaining tree, making BST operations much faster than linear structures.

**Key Properties**

- Duplicates are usually not allowed (each key is unique).
- Inorder traversal produces elements in **sorted ascending order**.
- Average height: O(log n) for a balanced BST.
- Worst-case height: O(n) when the tree becomes skewed (sorted insertions).

**Applications**

- Searching and indexing (maps, sets).
- Dynamic sorting and range queries.
- Symbol tables in compilers.
- Foundation for self-balancing structures (AVL, Red-Black, Splay trees).

**Example**

```
      8
     / \
    3   12
   / \  / \
  1   6 9  15
```

Every node satisfies the ordering property: left subtree values are smaller, right subtree values are greater.

---

#### Search

Start at root, compare target with current node, recurse into the matching subtree. The other subtree is discarded entirely — this is the "skip half the tree" property.

**Algorithm**
1. If node is null → not found, return null.
2. If target == node.val → found, return node.
3. If target < node.val → go left.
4. If target > node.val → go right.

**Recursive**

```java
TreeNode search(TreeNode node, int value) {
    if (Objects.isNull(node)) {
        return null;
    }
    if (value == node.val) {
        return node;
    }
    if (value < node.val) {
        return search(node.left, value);
    }
    return search(node.right, value);
}
```

**Iterative** (search is tail-recursive, so a loop uses O(1) extra space)

```java
TreeNode search(TreeNode node, int value) {
    while (Objects.nonNull(node)) {
        if (value == node.val) {
            return node;
        }
        if (value < node.val) {
            node = node.left;
        } else {
            node = node.right;
        }
    }
    return null;
}
```

**Walk-Through**

Search 6:

| Step | Current | Compare | Action |
|---|---|---|---|
| 1 | 8 | 6 < 8 | go left |
| 2 | 3 | 6 > 3 | go right |
| 3 | 6 | 6 == 6 | found, return |

Search 99 (not present):

| Step | Current | Compare | Action |
|---|---|---|---|
| 1 | 8 | 99 > 8 | go right |
| 2 | 12 | 99 > 12 | go right |
| 3 | 15 | 99 > 15 | go right |
| 4 | null | — | return null |

**Complexity:** Time O(h) — O(log n) balanced, O(n) skewed. Space O(h) recursive / O(1) iterative.

---

#### Insert

Same navigation as search — when you hit null, that slot is exactly where the new node belongs. Duplicates are ignored.

**Algorithm**
1. If node is null → create and return new node (insertion point).
2. If value < node.val → recurse left, attach result to `node.left`.
3. If value > node.val → recurse right, attach result to `node.right`.
4. If value == node.val → do nothing (no duplicates).
5. Return node.

**Recursive**

```java
TreeNode insert(TreeNode node, int value) {
    if (Objects.isNull(node)) {
        return new TreeNode(value);
    }
    if (value < node.val) {
        node.left = insert(node.left, value);
    } else if (value > node.val) {
        node.right = insert(node.right, value);
    }
    return node;
}
```

`node.left = insert(...)` reattaches the (possibly new) subtree to its parent. Returning `node` keeps the unchanged path intact.

**Walk-Through**

Insert 5 into the example tree:

| Step | Current | Action |
|---|---|---|
| 1 | 8 | 5 < 8, go left |
| 2 | 3 | 5 > 3, go right |
| 3 | 6 | 5 < 6, go left |
| 4 | null | create node 5 here |

Result: 5 becomes the left child of 6.

**Insertion Order Shapes the Tree**

Inserting `1, 3, 6, 8, 9, 12, 15` (already sorted) produces a fully right-skewed degenerate tree with O(n) height. Self-balancing BSTs (AVL, Red-Black) auto-rotate to prevent this; plain BSTs do not.

**Complexity:** Time O(h), Space O(h) recursive.

---

#### Delete

Most complex BST operation. Three cases depending on the target node's children.

**Algorithm**
1. If node is null → not found, return null.
2. If value < node.val → recurse left, attach result to `node.left`.
3. If value > node.val → recurse right, attach result to `node.right`.
4. If value == node.val → apply one of the three cases below.

**The Three Cases**

| Case | Condition | Action |
|---|---|---|
| 1 | Leaf (no children) | Return null — parent's pointer becomes null |
| 2 | One child | Return the surviving child — parent links directly to it |
| 3 | Two children | Copy inorder successor's value in, then delete successor from right subtree |

*Why the successor works:* it is the smallest value greater than the deleted node, so substituting it preserves the BST invariant — everything in the left subtree is still smaller, the rest of the right subtree is still greater. The inorder predecessor (max of left subtree) works equally well.

```java
TreeNode delete(TreeNode node, int value) {
    if (Objects.isNull(node)) {
        return null;
    }
    if (value < node.val) {
        node.left = delete(node.left, value);
    } else if (value > node.val) {
        node.right = delete(node.right, value);
    } else {
        if (Objects.isNull(node.left)) {
            return node.right;
        }
        if (Objects.isNull(node.right)) {
            return node.left;
        }
        int successorVal = findMin(node.right);
        node.val = successorVal;
        node.right = delete(node.right, successorVal);
    }
    return node;
}
```

`node.left = delete(...)` rewires the parent's pointer to whatever the recursive call returns. The deleted node becomes unreachable and is garbage collected.

**Walk-Through**

*Case 1 — delete a leaf (delete 1):*

Node 1 is removed. Node 3's left pointer becomes null.

*Case 2 — delete a node with one child (delete 12):*

Node 12 has only a right child (15). 12 is replaced by 15.

*Case 3 — delete a node with two children (delete 8, the root):*

`findMin(12)` = 9. Copy 9 into root's slot, then delete 9 from the right subtree.

```
Before:        After:
     8              9
    / \            / \
   3   12          3   12
  / \  / \        / \    \
 1   6 9  15     1   6   15
```

**Complexity:** Time O(h), Space O(h) recursive.

---

#### findMin / findMax

The smallest value in a BST is the leftmost node. The largest is the rightmost. This falls directly from the BST invariant.

```java
int findMin(TreeNode node) {
    while (Objects.nonNull(node.left)) node = node.left;
    return node.val;
}

int findMax(TreeNode node) {
    while (Objects.nonNull(node.right)) node = node.right;
    return node.val;
}
```

`findMin` is called in case 3 of delete to locate the inorder successor (`findMin(node.right)`).

**Walk-Through**

```
      8
     / \
    3   12
   / \  / \
  1   6 9  15
```

- `findMin`: 8 → 3 → 1 (no left child) → return 1
- `findMax`: 8 → 12 → 15 (no right child) → return 15

**Complexity:** Time O(h), Space O(1) iterative.

---

#### Inorder Successor / Predecessor

The **inorder successor** of V is the next value after V in sorted order. The **inorder predecessor** is the value immediately before V.

For `[1, 3, 6, 8, 9, 12, 15]`: successor of 6 is 8, predecessor of 6 is 3. Successor of 15 is null, predecessor of 1 is null.

**The Two Cases (for successor)**

| Case | Condition | Action |
|---|---|---|
| A | V's node has a right subtree | Successor = `findMin(node.right)` |
| B | V's node has no right subtree | Successor = closest ancestor where path turned left to reach V |

Predecessor mirrors: right subtree → `findMax(node.left)`; no left subtree → closest ancestor where path turned right.

**Algorithms**

Both cases collapse into a single descent from root, tracking the last "turn":

```java
TreeNode findSuccessor(TreeNode root, int value) {
    TreeNode successor = null;
    while (Objects.nonNull(root)) {
        if (value < root.val) {
            successor = root;
            root = root.left;
        } else {
            root = root.right;
        }
    }
    return successor;
}

TreeNode findPredecessor(TreeNode root, int value) {
    TreeNode predecessor = null;
    while (Objects.nonNull(root)) {
        if (value > root.val) {
            predecessor = root;
            root = root.right;
        } else {
            root = root.left;
        }
    }
    return predecessor;
}
```

*Why "last left turn" gives the successor:* every left turn finds a node bigger than the value you're heading toward. The final left turn before falling off the tree gives the smallest value still greater than V — exactly the successor by definition. If you never turn left, no value in the tree is greater than V and the successor is null.

**Walk-Through**

Tree:

```
      20
     /    \
   10      30
  /  \    /  \
 5   15  25   35
```

Successor of 15 (Case B — 15 has no right subtree):

| Step | root | Compare | Action | successor |
|---|---|---|---|---|
| 1 | 20 | 15 < 20 | save 20, go left | 20 |
| 2 | 10 | 15 > 10 | go right | 20 |
| 3 | 15 | 15 == 15 | go right (else) | 20 |
| 4 | null | — | exit | **20** |

Predecessor of 25 (Case B — 25 has no left subtree):

| Step | root | Compare | Action | predecessor |
|---|---|---|---|---|
| 1 | 20 | 25 > 20 | save 20, go right | 20 |
| 2 | 30 | 25 < 30 | go left | 20 |
| 3 | 25 | 25 == 25 | go left (else) | 20 |
| 4 | null | — | exit | **20** |

**Properties**

- Equality (`V == node.val`) goes into the "else" branch in both algorithms — strict inequality (`>` for successor, `<` for predecessor).
- Both algorithms work whether V exists in the tree or not.
- Without parent pointers, you must start from root — you cannot compute successor by walking up from the target node.

**Complexity:** Time O(h), Space O(1).

---

#### Floor / Ceiling

**Floor(V)** = largest value in BST ≤ V.
**Ceiling(V)** = smallest value in BST ≥ V.

```
      8
     / \
    3   12
   / \  / \
  1   6 9  15
```

| Query | Result |
|---|---|
| floor(7) | 6 (largest value ≤ 7) |
| floor(6) | 6 (exact match counts) |
| floor(0) | null (no value ≤ 0) |
| ceiling(7) | 8 (smallest value ≥ 7) |
| ceiling(15) | 15 (exact match counts) |
| ceiling(20) | null (no value ≥ 20) |

**Algorithms**

Same descent as successor/predecessor — only difference is that equality counts.

```java
TreeNode ceiling(TreeNode node, int value) {
    TreeNode ceiling = null;
    while (Objects.nonNull(node)) {
        if (value == node.val) {
            return node;
        }
        if (value < node.val) {
            ceiling = node;
            node = node.left;
        } else {
            node = node.right;
        }
    }
    return ceiling;
}

TreeNode floor(TreeNode node, int value) {
    TreeNode floor = null;
    while (Objects.nonNull(node)) {
        if (value == node.val) {
            return node;
        }
        if (value < node.val) {
            node = node.left;
        } else {
            floor = node;
            node = node.right;
        }
    }
    return floor;
}
```

**Relationship to Successor / Predecessor**

| Operation | Goal | Track | Equality |
|---|---|---|---|
| Successor | smallest value > V | last left turn | goes right (skip) |
| Ceiling | smallest value >= V | last left turn | counts (return) |
| Predecessor | largest value < V | last right turn | goes left (skip) |
| Floor | largest value <= V | last right turn | counts (return) |

Same descent, same tracking pattern, same complexity — only the equality switch flips strict to inclusive.

**Walk-Through**

Tree:

```
      20
     /    \
   10      30
  /  \    /  \
 5   15  25   35
```

`floor(33)` — largest value ≤ 33:

| Step | node | Compare | Action | floor |
|---|---|---|---|---|
| 1 | 20 | 33 > 20 | save 20, go right | 20 |
| 2 | 30 | 33 > 30 | save 30, go right | 30 |
| 3 | 35 | 33 < 35 | go left | 30 |
| 4 | null | — | exit | **30** |

`ceiling(16)` — smallest value ≥ 16:

| Step | node | Compare | Action | ceiling |
|---|---|---|---|---|
| 1 | 20 | 16 < 20 | save 20, go left | 20 |
| 2 | 10 | 16 > 10 | go right | 20 |
| 3 | 15 | 16 > 15 | go right | 20 |
| 4 | null | — | exit | **20** |

> Java's `TreeSet` exposes all four operations directly: `higher()` (successor), `ceiling()`, `lower()` (predecessor), `floor()`. Internally implemented on a Red-Black tree with the same descent algorithm.

**Complexity:** Time O(h), Space O(1).

---

#### isValidBST

A naive parent-child check (`node.left.val < node.val < node.right.val`) is insufficient — it misses ancestor-descendant violations.

```
   10
  /  \
 5    15
     /  \
    8    20
```

Each parent-child pair looks valid in isolation, but 8 sits in the right subtree of 10 and 8 < 10. The invariant is broken by an ancestor-descendant pair.

**Range Narrowing**

Each node lives inside a valid range `(min, max)`. The root starts with `(-∞, +∞)`. As you descend:
- Going **left** from V → new range becomes `(min, V)`.
- Going **right** from V → new range becomes `(V, max)`.

Bounds are strict — equality fails (no duplicates allowed).

```java
boolean isValidBST(TreeNode root) {
    return validate(root, null, null);
}

boolean validate(TreeNode node, Integer min, Integer max) {
    if (Objects.isNull(node)) {
        return true;
    }
    if (Objects.nonNull(min) && node.val <= min) {
        return false;
    }
    if (Objects.nonNull(max) && node.val >= max) {
        return false;
    }
    return validate(node.left, min, node.val)
        && validate(node.right, node.val, max);
}
```

> Use `Integer` (nullable) instead of `int` for bounds — `null` represents "no bound on this side". Do NOT use `Integer.MIN_VALUE` / `Integer.MAX_VALUE` as sentinels; LeetCode 98 has test cases with node values equal to those constants.

**Walk-Through on the broken tree:**

| Step | node | range (min, max) | Result |
|---|---|---|---|
| 1 | 10 | (-∞, +∞) | passes |
| 2 | 5 | (-∞, 10) | passes |
| 3 | 15 | (10, +∞) | passes |
| 4 | 8 | (10, 15) | 8 ≤ 10 → **FAILS** |

The lower bound 10 was inherited from the root — local parent-child checks miss this.

**Walk-Through on a valid tree:**

```
      8
     / \
    3   12
   / \  / \
  1   6 9  15
```

| Step | node | range (min, max) | Result |
|---|---|---|---|
| 1 | 8 | (-∞, +∞) | passes |
| 2 | 3 | (-∞, 8) | passes |
| 3 | 1 | (-∞, 3) | passes |
| 4 | 6 | (3, 8) | passes |
| 5 | 12 | (8, +∞) | passes |
| 6 | 9 | (8, 12) | passes |
| 7 | 15 | (12, +∞) | passes |

**Alternative — Inorder Check**

```java
boolean isValidBST(TreeNode root) {
    List<Integer> values = inorder(root);
    for (int i = 0; i < values.size() - 1; i++) {
        if (values.get(i) >= values.get(i + 1)) {
            return false;
        }
    }
    return true;
}
```

Same O(n) time but O(n) extra space and no short-circuit on first violation. Prefer range narrowing for interviews.

**Complexity:** Time O(n), Space O(h).

---

#### Inorder Traversal

The mechanics of inorder traversal (recursive and iterative) are covered in [DFS Traversals](#dfs-traversals). In the BST context, inorder is more than a generic traversal — it is the primary tool for extracting the sorted sequence the BST implicitly stores.

**Why Inorder Produces Sorted Output**

By the BST invariant, inorder visits `(values < N)`, then `N`, then `(values > N)`. Apply the same argument recursively to every subtree and the entire traversal is sorted ascending.

**Recursive**

```java
List<Integer> inorder(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    collect(root, result);
    return result;
}

void collect(TreeNode node, List<Integer> result) {
    if (Objects.isNull(node)) {
        return;
    }
    collect(node.left, result);
    result.add(node.val);
    collect(node.right, result);
}
```

**Iterative**

```java
List<Integer> inorder(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode current = root;
    while (Objects.nonNull(current) || !stack.isEmpty()) {
        while (Objects.nonNull(current)) {
            stack.push(current);
            current = current.left;
        }
        current = stack.pop();
        result.add(current.val);
        current = current.right;
    }
    return result;
}
```

**Example**

```
      8
     / \
    3   12
   / \  / \
  1   6 9  15
```

Inorder → `[1, 3, 6, 8, 9, 12, 15]`

**Common Uses in BST Problems**

| Use Case | Notes |
|---|---|
| Validate BST | Check inorder output is strictly ascending (alternative to range narrowing) |
| Kth smallest (LC 230) | Stop after k elements — no need to traverse the whole tree |
| Range sum / count | Sum or count values in [lo, hi] using inorder with pruning |
| Convert BST to sorted list | Inorder is the natural extraction |
| Two Sum in BST | Inorder gives sorted sequence, then apply two-pointer |

**Complexity:** Time O(n), Space O(h).

---

### Quick Reference

> Placeholder — to be added.

---

## Graphs

### Fundamentals

**Definition**

A graph is a data structure that models things and the connections between them. Formally written as G = (V, E), where V is a set of vertices (the things) and E is a set of edges (the connections between them).

If a problem can be phrased as "X is related to Y," it can almost always be modeled as a graph. Common applications: social networks, road maps, web links, dependency systems, network routing.

**Terminology**

| Term | Definition |
|---|---|
| Vertex (node) | An entity in the graph (person, city, page) |
| Edge | A connection between two vertices |
| Endpoints | The two vertices an edge connects |
| Adjacent | Two vertices connected by an edge (neighbors) |
| Incident | An edge "touches" a vertex when that vertex is its endpoint |
| Degree | Number of edges incident to a vertex |
| In-degree / Out-degree | For directed graphs: arrows in vs. arrows out |
| Path | Sequence of vertices connected by edges |
| Cycle | Path that starts and ends at the same vertex |
| Connected | Every vertex is reachable from every other vertex |
| Connected component | A maximal connected subgraph (an "island") |

**Edge Types**

| Type | Direction | Example |
|---|---|---|
| Undirected | Mutual / symmetric | Facebook friendship |
| Directed | One-way (arrow) | Twitter follow |

A graph is classified by its edges. All-directed graphs are also called digraphs. A "mutual" relationship in a directed graph is modeled as two separate edges (A→B and B→A), not one.

*Undirected edge vs. two opposite directed edges*

For reachability, both let you move A↔B. Structurally they differ:

| Aspect | One undirected edge | Two directed edges |
|---|---|---|
| Edge count | 1 | 2 |
| Data per direction | Shared | Independent |
| Remove one direction | Not possible | Possible |
| Algorithm semantics | Symmetric by default | Asymmetric (in-degree ≠ out-degree) |

Use undirected when the relationship is inherently mutual (Alice - Bob friendship). Use directed when directions can carry different data or change independently (Alice → Bob follow on Twitter, where Bob may or may not follow back).

**Simple Graph (default assumption)**

A simple graph forbids two things:
- Self-loops — an edge from a vertex to itself.
- Parallel edges — multiple edges between the same pair in the same direction.

Under these rules:

| Graph type | Max edges between A and B |
|---|---|
| Simple undirected | 1 |
| Simple directed | 2 (one each direction) |

**Example**

```
Alice ────── Bob ────── Carol      Dan ────── Eve

V = { Alice, Bob, Carol, Dan, Eve }
E = { (Alice, Bob), (Bob, Carol), (Dan, Eve) }
```

| Query | Result |
|---|---|
| numVertices | 5 |
| numEdges | 3 |
| Bob's neighbors | Alice, Carol |
| Bob's degree | 2 |
| Alice's degree | 1 |
| Dan's degree | 1 |
| Path from Alice to Carol | Alice → Bob → Carol |
| Alice and Carol adjacent? | No (connected through Bob) |
| Path from Alice to Dan? | None — different components |
| Connected? | No — graph splits into two islands |
| Connected components | { Alice, Bob, Carol }, { Dan, Eve } |
| Sum of degrees | 6 = 2 × \|E\| (each edge counted from both endpoints) |

If we add the edge (Carol, Dan), both components merge into one connected graph with |E| = 4 and sum of degrees = 8.

---

### Data Structures for Graphs

**Shared example**

```
    Alice ────── Bob ────── Carol
                  │
                  │
                 Dan

V = { Alice, Bob, Carol, Dan }, E = { (Alice, Bob), (Bob, Carol), (Bob, Dan) }
Vertex indices for the matrix: Alice=0, Bob=1, Carol=2, Dan=3.
```

**Adjacency Matrix**

A 2D array of size n × n, where cell [i][j] answers "is there an edge from vertex i to vertex j?"

```
        Alice  Bob  Carol  Dan
Alice [   0     1     0     0  ]
Bob   [   1     0     1     1  ]
Carol [   0     1     0     0  ]
Dan   [   0     1     0     0  ]
```

For undirected graphs the matrix is symmetric. Edge-existence check is O(1), but memory is always n² regardless of edge count, and "get neighbors of v" is O(n). Best fit: dense graphs.

**Adjacency List**

Each vertex holds its own list of neighbors.

```
Alice → [Bob]
Bob   → [Alice, Carol, Dan]
Carol → [Bob]
Dan   → [Bob]
```

Memory is O(n + m). "Get neighbors of v" is O(deg(v)). Edge-existence check is O(deg(u)). Best fit: sparse graphs.

**Adjacency Map**

Same shape as adjacency list, but each vertex stores neighbors in a hash map keyed by the neighbor vertex; the value is the connecting edge.

```
Alice → { Bob → edge1 }
Bob   → { Alice → edge1, Carol → edge2, Dan → edge3 }
Carol → { Bob → edge2 }
Dan   → { Bob → edge3 }
```

Memory is O(n + m). All operations — including edge existence, neighbor lookup, insert, remove — become O(1) average. Best fit: general-purpose default for sparse graphs.

**Comparison**

n = vertices, m = edges, deg(v) = degree of vertex v.

| Operation | Matrix | List | Map |
|---|---|---|---|
| Space | O(n²) | O(n + m) | O(n + m) |
| Get all edges | O(n²) | O(m) | O(m) |
| Get neighbors of v | O(n) | O(deg(v)) | O(deg(v)) |
| Is there edge (u, v)? | O(1) | O(deg(u)) | O(1) |
| Insert vertex | O(n²) resize | O(1) | O(1) |
| Insert edge | O(1) | O(1) | O(1) |
| Remove edge | O(1) | O(deg(v)) | O(1) |

**Why Adjacency Map**

Matches or beats the alternatives on every operation while keeping memory at O(n + m). Matrix wastes memory on sparse graphs (n² regardless of edge count). List is slow on edge-existence and edge removal, both common inside graph algorithms. Map gives matrix-quality lookup speed at list-quality memory cost. Most real-world graphs are sparse, making this the right default.

**Java Implementation**

`Vertex.java`

```java
public class Vertex<V, E> {

    private final V element;
    private final Map<Vertex<V, E>, Edge<V, E>> outgoing;
    private final Map<Vertex<V, E>, Edge<V, E>> incoming;

    public Vertex(V element, boolean directed) {
        this.element = element;
        this.outgoing = new HashMap<>();
        this.incoming = !directed ? this.outgoing : new HashMap<>();
    }

    public V getElement() { return element; }
    public Map<Vertex<V, E>, Edge<V, E>> getOutgoing() { return outgoing; }
    public Map<Vertex<V, E>, Edge<V, E>> getIncoming() { return incoming; }
}
```

`Edge.java`

```java
public class Edge<V, E> {

    private final E element;
    private final Vertex<V, E>[] endpoints;

    public Edge(Vertex<V, E> origin, Vertex<V, E> destination, E element) {
        this.endpoints = new Vertex[]{origin, destination};
        this.element = element;
    }

    public E getElement() { return element; }
    public Vertex<V, E>[] getEndpoints() { return endpoints; }
}
```

`Graph.java`

```java
public class Graph<V, E> {

    private final boolean directed;
    private final List<Vertex<V, E>> vertices = new ArrayList<>();
    private final List<Edge<V, E>> edges = new ArrayList<>();

    public Graph(boolean directed) { this.directed = directed; }

    public boolean isDirected() { return directed; }
    public int numVertices() { return vertices.size(); }
    public int numEdges() { return edges.size(); }
    public Iterable<Vertex<V, E>> getVertices() { return vertices; }
    public Iterable<Edge<V, E>> getEdges() { return edges; }

    public Edge<V, E> getEdge(Vertex<V, E> u, Vertex<V, E> v) { ... }
    public Vertex<V, E>[] endVertices(Edge<V, E> e) { ... }
    public Vertex<V, E> opposite(Vertex<V, E> v, Edge<V, E> e) { ... }
    public int outDegree(Vertex<V, E> v) { ... }
    public int inDegree(Vertex<V, E> v) { ... }
    public Iterable<Edge<V, E>> outgoingEdges(Vertex<V, E> v) { ... }
    public Iterable<Edge<V, E>> incomingEdges(Vertex<V, E> v) { ... }
    public Vertex<V, E> insertVertex(V element) { ... }
    public Edge<V, E> insertEdge(Vertex<V, E> u, Vertex<V, E> v, E element) { ... }
    public void removeVertex(Vertex<V, E> v) { ... }
    public void removeEdge(Edge<V, E> e) { ... }
}
```

**Graph methods**

| Method                | Description | Complexity |
|-----------------------|---|---|
| `numVertices()`       | Number of vertices in the graph | O(1) |
| `numEdges()`          | Number of edges in the graph | O(1) |
| `getVertices()`       | Iterable over all vertices | O(1) |
| `getEdges()`          | Iterable over all edges | O(1) |
| `isDirected()`        | Whether the graph is directed | O(1) |
| `getEdge(u, v)`       | Edge from u to v, or null | O(1) avg |
| `endVertices(e)`      | Two endpoints of e as [origin, destination] | O(1) |
| `opposite(v, e)`      | The endpoint of e that is not v | O(1) |
| `outDegree(v)`        | Number of outgoing edges from v | O(1) |
| `inDegree(v)`         | Number of incoming edges to v | O(1) |
| `outgoingEdges(v)`    | Iterable over v's outgoing edges | O(1) |
| `incomingEdges(v)`    | Iterable over v's incoming edges | O(1) |
| `insertVertex(x)`     | Add a new vertex with element x | O(1) avg |
| `insertEdge(u, v, x)` | Add a new edge from u to v with element x | O(1) avg |
| `removeVertex(v)`     | Remove v and all incident edges | O(deg(v) · m) |
| `removeEdge(e)`       | Remove edge e | O(m) |

---

### Graph Traversals

**Definition**

A graph traversal systematically visits every vertex reachable from a starting vertex by following edges. The two canonical strategies are Depth-First Search (DFS) and Breadth-First Search (BFS), distinguished by the order in which they explore vertices.

**Why a visited set is required**

Unlike trees, graphs can contain cycles. Without tracking which vertices have been visited, traversal would loop forever. Every traversal maintains a `Set<Vertex>` to prevent revisiting.

**Discovery forest**

Both DFS and BFS return the same structure: a `Map<Vertex, Edge>` where each discovered vertex maps to the edge that first reached it. The start vertex is not included (it wasn't discovered through any edge). This map captures the parent-pointer representation of the traversal tree, allowing path reconstruction.

| Term | Definition |
|---|---|
| Discovery edge | The edge through which a vertex was first reached |
| Discovery forest | Map from each discovered vertex to its discovery edge |
| Tree edge | Synonym for discovery edge |
| Connected component | The set of vertices reachable from any vertex in it |

---

#### Depth-First Search (DFS)

Strategy: Go as deep as possible along one path before backtracking. Recursive by nature.

**Algorithm**

```java
public Map<Vertex<V, E>, Edge<V, E>> traverse(Graph<V, E> graph, Vertex<V, E> start) {
    Map<Vertex<V, E>, Edge<V, E>> forest = new HashMap<>();
    Set<Vertex<V, E>> visited = new HashSet<>();
    dfs(graph, start, visited, forest);
    return forest;
}

private void dfs(Graph<V, E> graph, Vertex<V, E> origin,
                 Set<Vertex<V, E>> visited,
                 Map<Vertex<V, E>, Edge<V, E>> forest) {
    visited.add(origin);
    for (Edge<V, E> edge : graph.outgoingEdges(origin)) {
        Vertex<V, E> destination = graph.opposite(origin, edge);
        if (visited.contains(destination)) {
            continue;
        }
        forest.put(destination, edge);
        dfs(graph, destination, visited, forest);
    }
}
```

**Walk-Through**

Graph:

```
    Alice ────── Bob ────── Carol
                  │
                 Dan
```

Example walk from Alice:

| Step | Current | Visited so far | Action |
|---|---|---|---|
| 1 | Alice | {Alice} | Visit Bob |
| 2 | Bob | {Alice, Bob} | Visit Carol |
| 3 | Carol | {Alice, Bob, Carol} | Dead end — backtrack |
| 4 | Bob | (same) | Visit Dan |
| 5 | Dan | {Alice, Bob, Carol, Dan} | Dead end — done |

Visit order: Alice → Bob → Carol → Dan.

**Properties**

| Property | Value |
|---|---|
| Time complexity | O(n + m) |
| Space complexity | O(n) — visited set + call stack |
| Path reconstructed via `constructPath` | Some path (not necessarily shortest) |
| Best for | Cycle detection, topological sort, connected components |

---

#### Breadth-First Search (BFS)

Strategy: Explore all vertices at distance 1, then all at distance 2, and so on. Iterative with a queue.

**Algorithm**

```java
public Map<Vertex<V, E>, Edge<V, E>> traverse(Graph<V, E> graph, Vertex<V, E> start) {
    Map<Vertex<V, E>, Edge<V, E>> forest = new HashMap<>();
    Set<Vertex<V, E>> visited = new HashSet<>();
    ArrayDeque<Vertex<V, E>> queue = new ArrayDeque<>();

    visited.add(start);
    queue.offer(start);
    while (!queue.isEmpty()) {
        Vertex<V, E> vertex = queue.poll();
        for (Edge<V, E> edge : graph.outgoingEdges(vertex)) {
            Vertex<V, E> destination = graph.opposite(vertex, edge);
            if (visited.contains(destination)) {
                continue;
            }
            visited.add(destination);
            forest.put(destination, edge);
            queue.offer(destination);
        }
    }
    return forest;
}
```

> Vertices are marked visited at the moment they are enqueued, not when dequeued. This prevents the same vertex from being enqueued multiple times.

**Walk-Through**

Graph:

```
    Alice ────── Bob ────── Carol
                  │
                 Dan
```

Example walk from Alice:

| Step | Queue | Dequeued | Action |
|---|---|---|---|
| Init | [Alice] | — | Mark Alice visited |
| 1 | [Bob] | Alice | Enqueue Bob |
| 2 | [Carol, Dan] | Bob | Enqueue Carol and Dan |
| 3 | [Dan] | Carol | No new neighbors |
| 4 | [] | Dan | No new neighbors — done |

Visit order: Alice → Bob → Carol → Dan.

**Properties**

| Property | Value |
|---|---|
| Time complexity | O(n + m) |
| Space complexity | O(n) — visited set + queue |
| Path reconstructed via `constructPath` | Shortest path (fewest edges) |
| Best for | Shortest paths in unweighted graphs, level-order processing |

---

#### DFS vs BFS

| Aspect | DFS | BFS |
|---|---|---|
| Strategy | Deep then back | Layer by layer |
| Data structure | Recursion / stack | Queue (FIFO) |
| Memory | O(depth) | O(width) |
| Finds shortest path? | No | Yes (unweighted) |
| Natural for | Cycle detection, DAG ordering | Shortest paths, distance from source |

For weighted graphs, BFS does not give shortest paths — use Dijkstra's algorithm.

---

#### Path Reconstruction

Given a discovery forest, the path from start to end is reconstructed by walking backwards from end through discovery edges until start is reached.

**Algorithm**

```java
public static <V, E> List<Edge<V, E>> constructPath(
        Graph<V, E> graph,
        Vertex<V, E> start,
        Vertex<V, E> end,
        Map<Vertex<V, E>, Edge<V, E>> forest) {

    List<Edge<V, E>> path = new LinkedList<>();
    if (start == end) return path;
    if (!forest.containsKey(end)) return path;

    Vertex<V, E> walk = end;
    while (walk != start) {
        Edge<V, E> edge = forest.get(walk);
        path.addFirst(edge);
        walk = graph.opposite(walk, edge);
    }
    return path;
}
```

**Example**

Forest from BFS starting at Alice:

```
forest = {
    Bob   → edge(Alice, Bob),
    Carol → edge(Bob, Carol)
}
```

`constructPath(graph, alice, carol, forest)` walks: Carol → (via edge BC) → Bob → (via edge AB) → Alice. Result: `[edge(Alice, Bob), edge(Bob, Carol)]`.

**Behavior**

| Case | Result |
|---|---|
| `start == end` | Empty list |
| `end` not in forest (unreachable) | Empty list |
| Forest from BFS | Shortest path (fewest edges) |
| Forest from DFS | Some valid path |
| Path requested toward original traversal root | Empty list (root is not in forest) |

**Complexity:** O(path length), at most O(n).

---

### Transitive Closure

**Definition**

The transitive closure of a directed graph G is the answer to the question "for every pair of vertices (u, v), can u reach v through any sequence of edges?" The result is typically represented as a new graph (or matrix, or map) where there is a direct edge u → v whenever v is reachable from u in the original graph.

**Use cases**

- Dependency analysis (does module A transitively depend on module B?)
- Permission inheritance (does role A eventually grant permission B?)
- Network reachability (can a packet eventually travel from router A to router B?)
- Query optimization (are tables A and B joinable through any chain of foreign keys?)

The point is precomputing reachability once when it will be asked many times. A single reachability question is answered cheaply with one DFS or BFS — the closure is the precomputed answer for all pairs.

**Complexity notation**

Throughout this section, n is the number of vertices and m is the number of edges in the graph.

**Example**

Consider a graph with direct edges A→B, B→C, B→D.

Reachability from each vertex:

| From | Can reach |
|---|---|
| A | B, C, D |
| B | C, D |
| C | (nothing) |
| D | (nothing) |

The closure adds the implied edges A→C and A→D — vertices reachable through chains in the original.

---

#### DFS Approach

For each vertex u, run a DFS. Every vertex discovered by that DFS is reachable from u.

```java
public Map<Vertex<V, E>, Set<Vertex<V, E>>> closureAsMap(Graph<V, E> graph) {
    Map<Vertex<V, E>, Set<Vertex<V, E>>> result = new HashMap<>();
    for (Vertex<V, E> vertex : graph.getVertices()) {
        Map<Vertex<V, E>, Edge<V, E>> forest = dfs.traverse(graph, vertex);
        result.put(vertex, new HashSet<>(forest.keySet()));
    }
    return result;
}
```

Reuses the existing DfsGraphTraversal. Does not mutate the input.

**Complexity:** O(n · (n + m))

A single DFS visits every vertex once and every edge at most twice, costing O(n + m). We run DFS once per vertex, so the total is n × O(n + m) = O(n · (n + m)).

---

#### Floyd-Warshall Approach

For every possible "middle" vertex k, check every (source, destination) pair: if source reaches k and k reaches destination, then source reaches destination — add that edge if missing.

```java
private void floydWarshall(Graph<V, E> g) {
    for (Vertex<V, E> middle : g.getVertices()) {
        for (Vertex<V, E> source : g.getVertices()) {

            boolean sourceReachesMiddle = Objects.nonNull(g.getEdge(source, middle));
            boolean sourceIsNotMiddle = source != middle;

            if (sourceIsNotMiddle && sourceReachesMiddle) {
                for (Vertex<V, E> destination : g.getVertices()) {

                    boolean middleReachesDestination = Objects.nonNull(g.getEdge(middle, destination));
                    boolean sourceAlreadyReachesDestination = Objects.nonNull(g.getEdge(source, destination));
                    boolean destinationIsNotMiddle = destination != middle;
                    boolean destinationIsNotSource = source != destination;

                    if (destinationIsNotSource &&
                            destinationIsNotMiddle &&
                            middleReachesDestination &&
                            !sourceAlreadyReachesDestination) {
                        g.insertEdge(source, destination, null);
                    }
                }
            }
        }
    }
}
```

**Why middle is the outer loop:** each pass adds shortcuts that later passes can build on. After the k-th iteration, the graph contains every reachability path that uses any of the first k vertices as intermediates.

**Trace on a chain A → B → C → D:**

| Pass | Action |
|---|---|
| middle = A | Nothing reaches A. No work. |
| middle = B | A reaches B, B reaches C → add A → C |
| middle = C | A reaches C (just added), B reaches C, C reaches D → add A → D and B → D |
| middle = D | D has no outgoing edges. No work. |

The key moment: middle = C adds A → D, which depends on the A → C edge added during middle = B. Earlier passes enable later ones — the reason middle must be outer.

This approach mutates the input graph — after the call, the input graph IS its closure.

**Complexity:** O(n³)

Three nested loops, each iterating over all n vertices. The body of the innermost loop is constant work (a few O(1) edge lookups and possibly one insertion). Total: n × n × n = O(n³). The complexity does not depend on edge count.

---

#### DFS vs Floyd-Warshall

| Aspect | DFS-from-every-vertex | Floyd-Warshall |
|---|---|---|
| Time complexity | O(n · (n + m)) | O(n³) |
| Mutates input | No | Yes |
| Best for | Sparse graphs | Dense graphs |

For most real-world (sparse) graphs the DFS approach is preferable. Floyd-Warshall earns its place because the relaxation pattern ("if going through k helps, update the answer") generalizes to other algorithms like all-pairs shortest paths.

---

### Topological Sort

**Definition**

A topological sort of a directed acyclic graph (DAG) is a linear ordering of all vertices such that for every directed edge u → v, u appears before v in the ordering. It answers the question: "in what order can we process these tasks so that every dependency is satisfied before the task that depends on it?"

A topological ordering exists if and only if the graph is a DAG — any cycle makes it impossible (a cycle implies A must come before B, B before C, and C before A simultaneously).

**Key properties**

| Property | Detail |
|---|---|
| Requires | Directed acyclic graph (DAG) |
| Result | Linear ordering of all vertices |
| Uniqueness | Not unique — multiple valid orderings may exist |
| Cycle | Detected as a side-effect of the DFS-based approach |

**Use cases**

- Build systems and package managers (compile dependencies before dependents)
- Course scheduling (prerequisites before the courses that require them)
- Task scheduling in workflow engines
- Spreadsheet formula evaluation order

---

#### DFS Recursive Approach

Strategy: run a post-order DFS. A vertex is added to the result only after all vertices reachable from it have been processed. Reversing the post-order list yields topological order.

**Algorithm**

```java
public List<Vertex<V, E>> sort(Graph<V, E> graph) {
    Set<Vertex<V, E>> done = new HashSet<>();
    Set<Vertex<V, E>> inProgress = new HashSet<>();
    List<Vertex<V, E>> result = new ArrayList<>();

    for (Vertex<V, E> vertex : graph.getVertices()) {
        if (!done.contains(vertex)) {
            dfs(vertex, done, inProgress, result);
        }
    }

    Collections.reverse(result);
    return result;
}

private void dfs(Vertex<V, E> vertex, Set<Vertex<V, E>> done,
                 Set<Vertex<V, E>> inProgress, List<Vertex<V, E>> result) {
    inProgress.add(vertex);
    for (Vertex<V, E> neighbour : vertex.getOutgoing().keySet()) {
        if (inProgress.contains(neighbour)) {
            throw new IllegalStateException("Cycle detected");
        }
        if (!done.contains(neighbour)) {
            dfs(neighbour, done, inProgress, result);
        }
    }
    inProgress.remove(vertex);
    done.add(vertex);
    result.add(vertex);
}
```

**Three-colour model**

Each vertex is in exactly one of three states at any point:

| Colour | Set | Meaning |
|---|---|---|
| White | neither | Not yet visited |
| Grey | `inProgress` | On the current DFS path — descendants not yet finished |
| Black | `done` | Fully processed — all descendants committed |

A back edge (grey → grey) indicates a cycle. A cross or forward edge (grey → black) is safely skipped.

**Walk-through on A → B → C → D, A → C**

| Step | Action | inProgress | done | result (pre-reverse) |
|---|---|---|---|---|
| 1 | Visit A | {A} | {} | [] |
| 2 | Visit B | {A,B} | {} | [] |
| 3 | Visit C (via B) | {A,B,C} | {} | [] |
| 4 | Visit D | {A,B,C,D} | {} | [] |
| 5 | D done | {A,B,C} | {D} | [D] |
| 6 | C done | {A,B} | {D,C} | [D,C] |
| 7 | B done | {A} | {D,C,B} | [D,C,B] |
| 8 | A's edge A→C: C already in done — skip | | | |
| 9 | A done | {} | {D,C,B,A} | [D,C,B,A] |
| 10 | Reverse | | | [A,B,C,D] |

**Properties**

| Property | Value |
|---|---|
| Time complexity | O(V + E) |
| Space complexity | O(V) — `done`, `inProgress`, result, call stack |
| Cycle detection | Yes — back edge throws `IllegalStateException` |
| Limitation | Call stack depth O(V); risk of `StackOverflowError` on a long chain |

---

#### DFS Iterative Approach

Strategy: identical post-order logic but replaces the call stack with an explicit `Deque`. Each vertex is pushed twice — once as a "first visit" frame (push children) and once as a "second visit" frame (commit to result). The two-visit pattern replicates the pre/post-order behaviour of recursion.

**Algorithm**

```java
public List<Vertex<V, E>> sort(Graph<V, E> graph) {
    Set<Vertex<V, E>> done = new HashSet<>();
    Set<Vertex<V, E>> inProgress = new HashSet<>();
    List<Vertex<V, E>> result = new ArrayList<>();
    Deque<Frame<V, E>> stack = new ArrayDeque<>();

    for (Vertex<V, E> vertex : graph.getVertices()) {
        if (!done.contains(vertex)) {
            stack.push(new Frame<>(vertex, false));
            while (!stack.isEmpty()) {
                Frame<V, E> frame = stack.pop();
                Vertex<V, E> frameVertex = frame.vertex();
                if (!frame.secondVisit()) {
                    inProgress.add(frameVertex);
                    stack.push(new Frame<>(frameVertex, true));
                    for (Vertex<V, E> neighbour : frameVertex.getOutgoing().keySet()) {
                        if (inProgress.contains(neighbour)) {
                            throw new IllegalStateException("Cycle detected");
                        }
                        if (!done.contains(neighbour)) {
                            stack.push(new Frame<>(neighbour, false));
                        }
                    }
                } else {
                    inProgress.remove(frameVertex);
                    done.add(frameVertex);
                    result.add(frameVertex);
                }
            }
        }
    }

    Collections.reverse(result);
    return result;
}

private record Frame<V, E>(Vertex<V, E> vertex, boolean secondVisit) {}
```

**Push order is critical**

On a first visit, `Frame(vertex, true)` is pushed before the children. Because `push` is `addFirst`, children land on top and are processed first. The second-visit frame sits below, waiting until all descendants are done — exactly mirroring the recursive call stack.

Pushing the second-visit frame after the children would place it on top, causing the parent to be committed before its subtree is processed.

**Properties**

| Property | Value |
|---|---|
| Time complexity | O(V + E) |
| Space complexity | O(V) — `done`, `inProgress`, result, explicit stack |
| Cycle detection | Yes — back edge throws `IllegalStateException` |
| Advantage over recursive | No risk of `StackOverflowError` on deep graphs |

---

#### Recursive vs Iterative DFS

| Aspect | Recursive | Iterative |
|---|---|---|
| Call stack depth | O(V) — risk of `StackOverflowError` | O(V) — explicit `Deque`, no JVM limit |
| Code complexity | Simple — post-order is implicit | Requires two-visit frame pattern |
| Cycle detection | Same back-edge check | Same back-edge check |
| Result correctness | Identical | Identical |

Prefer the iterative version when operating on graphs with potentially long chains (deep dependency trees, linear workflows). The recursive version is simpler to read and sufficient for bounded-depth graphs.

---

#### Kahn's Algorithm

Strategy: BFS-based. Repeatedly pick a vertex with no remaining incoming dependencies, emit it, and remove its contribution to its neighbors' in-degrees. Vertices are added to the result in topological order directly — no reversal needed.

**Algorithm**

```java
public List<Vertex<V, E>> sort(Graph<V, E> graph) {
    Map<Vertex<V, E>, Integer> inDegree = new HashMap<>();
    Deque<Vertex<V, E>> queue = new ArrayDeque<>();

    for (Vertex<V, E> vertex : graph.getVertices()) {
        int degree = vertex.getIncoming().size();
        inDegree.put(vertex, degree);
        if (degree == 0) {
            queue.offer(vertex);
        }
    }

    List<Vertex<V, E>> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        Vertex<V, E> vertex = queue.poll();
        result.add(vertex);
        for (Vertex<V, E> neighbour : vertex.getOutgoing().keySet()) {
            int updated = inDegree.get(neighbour) - 1;
            inDegree.put(neighbour, updated);
            if (updated == 0) {
                queue.offer(neighbour);
            }
        }
    }

    if (result.size() != inDegree.size()) {
        throw new IllegalStateException("Cycle detected");
    }
    return result;
}
```

**Walk-through on A → B → D, A → C → D**

| Step | Queue | Action | in-degree map (changes only) | result |
|---|---|---|---|---|
| Init | [A] | A has in-degree 0 | A=0, B=1, C=1, D=2 | [] |
| 1 | [B, C] | Dequeue A; decrement B→0, C→0; enqueue both | B=0, C=0 | [A] |
| 2 | [C, D] | Dequeue B; decrement D→1 | D=1 | [A, B] |
| 3 | [D] | Dequeue C; decrement D→0; enqueue D | D=0 | [A, B, C] |
| 4 | [] | Dequeue D; no outgoing | — | [A, B, C, D] |

result.size() (4) == inDegree.size() (4) → no cycle.

**Cycle detection — passive**

Vertices in a cycle never reach in-degree 0 — they're waiting on each other. They are never enqueued, so the result ends up smaller than the total vertex count. The size check at the end catches this. Unlike DFS, the cycle is not detected mid-traversal — the algorithm runs to completion first.

**Properties**

| Property | Value |
|---|---|
| Time complexity | O(V + E) |
| Space complexity | O(V) — in-degree map, queue, result |
| Cycle detection | Passive — size check after BFS completes |
| Reversal needed | No — result is in topological order directly |

---

#### DFS vs Kahn's

| Aspect | DFS (recursive or iterative) | Kahn's |
|---|---|---|
| Strategy | Post-order DFS + reverse | BFS from in-degree-0 vertices |
| Cycle detection | Active — throws immediately on back edge | Passive — detected at end via size check |
| Reversal | Required | Not required |
| Order produced | Reverse post-order | BFS-level order (sources first, naturally) |
| Stack overflow risk | Yes (recursive only) | No |
| Intuition | Follow paths to their end, then commit | Peel off dependency-free vertices layer by layer |

Kahn's is often preferred when you need the result in a natural "dependency-first" order or when you want to process vertices as they become available (streaming). DFS is preferred when cycle detection needs to fail fast rather than after a full traversal.

---

### Shortest Paths

**Definition**

The shortest path between two vertices is the path with the minimum total edge weight. For unweighted graphs, weight is implicitly 1 per hop, and BFS finds the shortest path directly. For weighted graphs with non-negative weights, Dijkstra's algorithm is the standard approach.

---

#### Dijkstra's Algorithm

**Strategy**

Greedy. Maintain a tentative distance from the source to every vertex, initialized to ∞. Repeatedly extract the vertex with the smallest known distance, mark it final, and relax its outgoing edges — updating any neighbor whose new distance would be shorter. A vertex marked final is never reconsidered.

**Correctness assumption: non-negative edge weights**

The greedy step is only safe because distances can never decrease once a vertex is finalized. With a negative edge, a path discovered later through that edge could undercut a distance already marked final — Dijkstra's would miss it. For graphs with negative weights, use Bellman-Ford instead.

**Algorithm**

```java
public Map<Vertex<V, Integer>, Integer> distances(Graph<V, Integer> graph, Vertex<V, Integer> start) {
    Map<Vertex<V, Integer>, Integer> distances = new HashMap<>();
    Set<Vertex<V, Integer>> visited = new HashSet<>();
    PriorityQueue<VertexDistance<V>> queue =
            new PriorityQueue<>(Comparator.comparing(VertexDistance::distance));

    distances.put(start, 0);
    queue.offer(new VertexDistance<>(start, 0));

    while (!queue.isEmpty()) {
        Vertex<V, Integer> current = queue.poll().vertex();
        if (visited.contains(current)) {
            continue;
        }
        visited.add(current);

        for (Edge<V, Integer> edge : graph.getOutgoingEdges(current)) {
            int weight = edge.getElement();
            if (weight < 0) {
                throw new IllegalArgumentException("Edge weight cannot be negative");
            }
            Vertex<V, Integer> neighbour = graph.opposite(current, edge);
            if (visited.contains(neighbour)) {
                continue;
            }
            int newDist = distances.get(current) + weight;
            if (newDist < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                distances.put(neighbour, newDist);
                queue.offer(new VertexDistance<>(neighbour, newDist));
            }
        }
    }
    return distances;
}

private record VertexDistance<V>(Vertex<V, Integer> vertex, int distance) {}
```

**Lazy deletion pattern**

When a shorter path to a vertex is found, the old entry in the priority queue is not removed — it's cheaper to leave it and skip it on dequeue (the `visited` check). This is the standard approach because Java's `PriorityQueue` does not support O(log n) key-decrease.

**Walk-through**

Graph:

```
A --1--> B --2--> C
|                 ^
`--------5--------'
```

Direct path A→C costs 5. Two-hop path A→B→C costs 1+2=3.

| Step | Dequeued | visited | distances after relaxation |
|---|---|---|---|
| Init | — | {} | {A=0} |
| 1 | A (dist 0) | {A} | {A=0, B=1, C=5} |
| 2 | B (dist 1) | {A,B} | {A=0, B=1, C=3} |
| 3 | C (dist 3) | {A,B,C} | unchanged — done |
| 4 | C (dist 5, stale) | already visited — skip | |

Step 1: relaxing A sets B=1 and C=5. Step 2: relaxing B finds C at 1+2=3, shorter than 5 — updates distances and enqueues C again. The stale C=5 entry is skipped when dequeued at step 4.

**Properties**

| Property | Value |
|---|---|
| Input requirement | Non-negative edge weights |
| Works on | Directed and undirected graphs |
| Returns | Shortest distance to every reachable vertex from source |
| Unreachable vertices | Absent from result map |
| Algorithm type | Greedy |

**Complexity**

| Metric | Value |
|---|---|
| Time | O((V + E) log V) |
| Space | O(V + E) |

Each edge triggers at most one priority queue insertion: O(E log V). Each vertex is dequeued once (stale entries are skipped in O(log V)): O(V log V). Total: O((V + E) log V). A Fibonacci heap lowers this to O(V log V + E) but Java's `PriorityQueue` is a binary heap.

---

#### BFS vs Dijkstra's

| Aspect | BFS | Dijkstra's |
|---|---|---|
| Edge weights | Unweighted (uniform cost 1) | Non-negative integer or real weights |
| Data structure | Queue (FIFO) | Min-heap (priority queue) |
| Time | O(V + E) | O((V + E) log V) |
| Shortest path guarantee | Yes (by hop count) | Yes (by weight sum) |

BFS is a special case of Dijkstra's where every edge has weight 1. When all edges are uniform, the FIFO queue naturally processes vertices in distance order, making the priority queue unnecessary.

---

### Minimum Spanning Tree

**Definition**

A minimum spanning tree (MST) of a connected, undirected, weighted graph is a spanning tree — a subgraph that includes every vertex and is acyclic — whose total edge weight is minimal among all possible spanning trees. For n vertices the MST always has exactly n-1 edges.

**Key properties**

| Property | Detail |
|---|---|
| Requires | Connected, undirected graph with weighted edges |
| Result | A set of n-1 edges spanning all vertices with minimum total weight |
| Uniqueness | Unique when all edge weights are distinct; multiple MSTs possible with ties |
| Cycles | An MST is acyclic by definition — adding any edge to it creates exactly one cycle |

**Use cases**

- Network design: minimum cable to connect all buildings, minimum pipe to connect all cities.
- Cluster analysis: remove the heaviest MST edges to split a graph into k components.
- Approximation algorithms: MSTs serve as the backbone for the traveling salesman approximation.

---

#### Prim's Algorithm

**Strategy**

Greedy. Grow the tree one edge at a time, always picking the cheapest edge that connects a vertex already in the tree to one that is not. A priority queue ordered by edge weight drives the selection. Continue until all vertices are in the tree (or the queue is exhausted for disconnected graphs).

**Algorithm**

```java
public List<Edge<V, Integer>> mst(Graph<V, Integer> graph) {
    Iterable<Vertex<V, Integer>> vertices = graph.getVertices();
    Iterator<Vertex<V, Integer>> iterator = vertices.iterator();

    List<Edge<V, Integer>> result = new ArrayList<>();
    if (!iterator.hasNext()) {
        return result;
    }

    Set<Vertex<V, Integer>> inTree = new HashSet<>();
    PriorityQueue<Edge<V, Integer>> queue =
            new PriorityQueue<>(Comparator.comparing(Edge::getElement));

    Vertex<V, Integer> start = iterator.next();
    inTree.add(start);
    for (Edge<V, Integer> edge : graph.getOutgoingEdges(start)) {
        queue.add(edge);
    }

    while (!queue.isEmpty() && inTree.size() < graph.numVertices()) {
        Edge<V, Integer> edge = queue.poll();
        Vertex<V, Integer> origin = edge.getEndpoints()[0];
        Vertex<V, Integer> destination = edge.getEndpoints()[1];

        if (inTree.contains(origin) && inTree.contains(destination)) {
            continue;
        }
        Vertex<V, Integer> next = inTree.contains(origin) ? destination : origin;

        result.add(edge);
        inTree.add(next);
        for (Edge<V, Integer> outgoing : graph.getOutgoingEdges(next)) {
            if (!inTree.contains(graph.opposite(next, outgoing))) {
                queue.add(outgoing);
            }
        }
    }
    return result;
}
```

**Walk-through**

Graph:

```
A --1-- B
|       |
4       2
|       |
C --3-- D
```

| Step | Polled edge | inTree | Action |
|---|---|---|---|
| Init | — | {A} | Enqueue A-B(1), A-C(4) |
| 1 | A-B(1) | {A,B} | Add A-B. Enqueue B-D(2) |
| 2 | B-D(2) | {A,B,D} | Add B-D. Enqueue D-C(3) |
| 3 | D-C(3) | {A,B,D,C} | Add D-C. All vertices in tree — done |
| 4 | A-C(4, stale) | skipped — both endpoints in tree | |

The stale A-C edge is never added to the result because both its endpoints are already in the tree when it is dequeued.

**Properties**

| Property | Value |
|---|---|
| Input requirement | Connected, undirected, weighted graph |
| Disconnected graph | Spans only the component containing the start vertex |
| Algorithm type | Greedy |

**Complexity**

| Metric | Value |
|---|---|
| Time | O((V + E) log E) |
| Space | O(V + E) |

Each edge is enqueued at most twice (once from each endpoint): O(E) insertions at O(log E) each. Each dequeue is O(log E). Total: O((V + E) log E). Since E ≤ V², this simplifies to O((V + E) log V).

---

## Heaps & Priority Queues

> Placeholder — to be added.

---

## Binary Search

> Placeholder — to be added.

---

## Dynamic Programming

> Placeholder — to be added.
