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

> Placeholder — to be added.

---

## Heaps & Priority Queues

> Placeholder — to be added.

---

## Binary Search

> Placeholder — to be added.

---

## Dynamic Programming

> Placeholder — to be added.
