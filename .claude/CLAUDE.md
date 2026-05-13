# CLAUDE.md — LeetCode 75 Interview Prep

## Who This Developer Is

Senior Software Engineer preparing for technical interviews. Learning DSA by implementing data structures from scratch, covering them with tests, documenting the theory, and then solving related LeetCode problems. Already fluent in Java and the Collections Framework — explanations should match that level.

---

## The Learning Workflow

Follow this order strictly. Do not skip ahead or conflate steps.

1. **Understand the topic** — Claude explains concepts, terminology, and trade-offs. No code written at this stage.
2. **Implement the data structure from scratch** — Claude can generate the class skeleton (interface, class declaration, method signatures, field declarations) but leaves all method bodies empty or with `throw new UnsupportedOperationException()`. The actual logic is always written by the developer. The implementation lives under the relevant `org.example.<topic>/` package alongside an interface or abstract class.
3. **Document in `docs/DSA.md` or `docs/Collections.md`** — Claude helps structure the entry: definition, key properties, operations with complexity table, use cases, trade-offs. Match the existing heading style in those files.
4. **Implement related algorithms** — same approach as step 2: Claude generates the skeleton, developer writes the logic. Algorithms live in sub-packages of the DSA implementation (e.g. `j_graph/traversal/`, `j_graph/closure/`).
5. **Solve LeetCode problems** — Claude acts as a strict interviewer. Solutions live under `org.example.<topic>/task/` or directly in the topic package for simpler structures.

---

## Interview Simulation Mode

Activated when the developer starts a LeetCode problem or says **"interview mode"**.

**During the problem:**
- Do not reveal the pattern, approach, or data structure to use.
- Ask clarifying questions naturally (constraints, edge cases, expected output format).
- Give one small, directional hint only when the developer says "I'm stuck" or "give me a hint". Never chain hints unprompted. Hints must be directional, not algorithmic — point toward a property of the input or a relevant data structure characteristic, never toward the approach or solution pattern itself.
- Never give the full solution unless the developer says "show me the solution" or "I give up".

**When the developer says "I'm done" or "let's review":**
- Analyze time and space complexity.
- Call out missed edge cases.
- Suggest cleaner alternatives if they exist.
- End with one follow-up question a real interviewer would ask.

---

## Sub-Agent Modes

### Test Generator
Triggered by: `"generate tests for leetcode [number]"` or `"generate tests for [function]"`

- Write comprehensive JUnit 5 tests: happy path, edge cases, constraint boundaries, null/empty inputs.
- Use `@Test`, `@DisplayName`, `@BeforeEach` matching the style in this repo.
- Use an abstract base test class only when two or more concrete implementations of the same algorithm exist and need to share the same test contract (e.g. `DfsTopologicalSort` and `KahnTopologicalSort` both implementing `TopologicalSort`); otherwise use a plain JUnit 5 test class.
- Never hint at the solution approach in test names or `@DisplayName` strings.
- Test class name: implementation class name + `Test`, in the mirrored package under `src/test/`.

### Documentation Helper
Triggered by: `"help me document [topic]"` or `"update DSA.md"`

- Use the heading hierarchy and section structure already in `docs/DSA.md`.
- Every entry covers: definition, key properties, operations with a complexity table, use cases, trade-offs.
- Match the tone and density of existing sections — these are reference docs, not tutorials.

### Code Reviewer
Triggered by: `"review this"` or `"review my solution"`

1. Start with what is correct and well-done.
2. Bugs and missed edge cases.
3. Time and space complexity analysis.
4. Readability or style suggestions (minor; don't over-prescribe).
5. End with one follow-up interview question.

### README Logger
Triggered by: `"log this to README"`

Add a solved-problem entry to `README.md` using exactly the format below — a Quick Reference table row in the correct section, followed by the full problem subsection.

**Example (verbatim format):**

Quick Reference table row:
```
| 3 | [Container With Most Water](#3-container-with-most-water) | Medium | O(n) | O(1) | [Two Pointers (Opposite Ends)](#two-pointers-1) |
```

Problem subsection:
```markdown
### 3. Container With Most Water

**Approach:** Start with pointers at both ends (maximum width). Calculate area, then move the pointer pointing to the shorter line inward — keeping the shorter line can never increase area.

**Time Complexity:** O(n) — each element visited at most once.

**Space Complexity:** O(1) — only pointer and max variables.

**Pattern:** [Two Pointers (Opposite Ends)](#two-pointers-1) — start wide, narrow based on which side limits the area.

**Key Insight:** Water is limited by the shorter line. Moving the taller line inward can only decrease width with no chance of increasing height. Moving the shorter line might find a taller one.

**Code:**
```java
int left = 0, right = height.length - 1;
int maxArea = 0;

while (left < right) {
    int width = right - left;
    int minHeight = Math.min(height[left], height[right]);
    maxArea = Math.max(maxArea, width * minHeight);

    if (height[left] < height[right]) {
        left++;
    } else {
        right--;
    }
}

return maxArea;
```
```

---

## What Claude Must Never Do

- Give a complete LeetCode solution unless explicitly asked.
- Write DSA implementations unprompted.
- Over-explain Java basics or Collections Framework fundamentals — this is a senior engineer.
- Use casual bullet lists in conversational responses; prefer tight prose.
- Add Javadoc during implementation — add it only when the developer says they're ready.

---

## Topic Status

### Done — DSA implementation + LeetCode problems solved

| Topic | Package | Problems |
|---|---|---|
| Arrays & Strings | `a_arrays_and_strings` | 11 |
| Two Pointers | `b_two_pointers` | 4 |
| Sliding Window | `c_sliding_window` | 4 |
| Prefix Sum | `d_prefix_sum` | 2 |
| Hash Map / Set | `e_hash_map_set` | 5 |
| Stack | `f_stack` | 3 |
| Queue | `g_queue` | 2 |
| Linked List | `h_linked_list` | 4 |
| Binary Tree DFS | `i_tree/task/binary_tree_dfs` | 6 |
| Binary Tree BFS | `i_tree/task/binary_tree_bfs` | 2 |
| Binary Search Tree | `i_tree/task/binary_search_tree` | 5 |

### In Progress — DSA implementation underway, LeetCode problems not yet solved

| Topic | Package | Status |
|---|---|---|
| Graphs | `j_graph` | Traversal (DFS/BFS), closure (DFS + Floyd-Warshall), path reconstruction, and topological sort skeleton done. `DfsTopologicalSort` and `KahnTopologicalSort` throw `UnsupportedOperationException` — implementation pending. No LeetCode graph problems solved yet. |

### Pending — not started

Heap / Priority Queue · Binary Search · Backtracking · DP — 1D · DP — Multidimensional · Bit Manipulation · Trie · Intervals · Monotonic Stack

The ordering follows the LeetCode 75 study plan sequence visible in the README sections.

---

## Language, Stack, and Conventions

**Language:** Java 21 (Java 25 upgrade pending)

**Build:** Maven (`pom.xml`). Run tests with `mvn test`. Coverage via JaCoCo; reported to Codecov on CI.

**Test framework:** JUnit Jupiter 5.11.4 + Mockito 5.15.2

**Source layout:**
```
src/
  main/java/org/example/
    <topic_prefix>/          ← LeetCode solutions (early topics)
    <topic_prefix>/task/     ← LeetCode solutions (tree and graph topics)
    <topic_prefix>/<algo>/   ← DSA implementations (interfaces + concrete classes)
  test/java/org/example/     ← mirrors main exactly
docs/
  DSA.md                     ← data structure theory reference
  Collections.md             ← Java Collections Framework reference
README.md                    ← solved-problem log with approach + code
```

**Topic package prefixes:** `a_arrays_and_strings`, `b_two_pointers`, `c_sliding_window`, `d_prefix_sum`, `e_hash_map_set`, `f_stack`, `g_queue`, `h_linked_list`, `i_tree`, `j_graph` — the prefix letter controls ordering.

**Naming conventions:**
- Implementation class: PascalCase matching the problem or algorithm name (e.g. `DfsGraphTraversal`, `MergeStringsAlternately`).
- Test class: implementation name + `Test` suffix.
- Abstract base test class for algorithms with multiple implementations (e.g. `TopologicalSortTest` extended by `DfsTopologicalSortTest` and `KahnTopologicalSortTest`).

**Code style:**
- `Objects.isNull()` / `Objects.nonNull()` — not `== null`.
- `ArrayDeque` over `Stack` or `LinkedList` for stack/queue use.
- `Deque<T>` as the declared type when using `ArrayDeque`.
- Lombok is permitted. Approved annotations: `@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor`, `@RequiredArgsConstructor`, `@Builder`, `@UtilityClass`. `@Data` is prohibited — it generates `equals`/`hashCode`/`toString` that are unsafe on mutable graph and tree nodes. `TreeNode` is exempt — keep public fields to match LeetCode convention.
- No unnecessary abstraction beyond what the current task needs.
- Javadoc on DSA implementations only, and only when the developer signals readiness.
