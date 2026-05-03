# Java Collections Framework — Complete Interview Study Guide & Cheat Sheet

## Table of Contents

- [Collection Hierarchy Overview](#collection-hierarchy-overview)
- [Arrays](#arrays)
- [List Implementations](#list-implementations)
- [Queue / Deque Family](#queue--deque-family)
- [equals() and hashCode()](#equals-and-hashcode)
- [Hash Tables](#hash-tables)
- [Map Implementations](#map-implementations)
- [Set Implementations](#set-implementations)
- [Collections Utility Class](#collections-utility-class)
- [Streams and Collections (Java 8+)](#streams-and-collections-java-8)
- [Thread-Safe Collections](#thread-safe-collections)
- [Generics and PECS](#generics-and-pecs)
- [Quick Reference](#quick-reference)

---

## Collection Hierarchy Overview

The Java Collections Framework (JCF) is a unified architecture for representing and manipulating groups of objects. It provides interfaces, implementations, and algorithms forming a consistent hierarchy. Understanding this hierarchy is the foundation for choosing the right data structure.

At the top sits `Iterable`, which provides `iterator()` enabling for-each loop support. Below it is `Collection`, defining the fundamental contract: adding, removing, querying, iterating. From `Collection`, three major interfaces branch out: `List` (ordered, index-based), `Queue` (FIFO/deque), and `Set` (no duplicates). `Map` stands entirely outside this hierarchy — it manages key-value pairs rather than individual elements.

```
java.lang.Iterable
└── java.util.Collection
    ├── List         → Ordered, duplicates allowed, index access
    │     ├── ArrayList          (dynamic array - RECOMMENDED default)
    │     ├── LinkedList         (doubly-linked list)
    │     └── Vector → Stack     (legacy, synchronized - AVOID)
    ├── Queue        → FIFO ordering, deque operations
    │     ├── LinkedList         (also implements Deque)
    │     ├── ArrayDeque         (circular array - RECOMMENDED)
    │     └── PriorityQueue      (binary heap, priority-ordered)
    └── Set          → No duplicates, enforced via equals/hashCode
          ├── HashSet            (backed by HashMap)
          ├── LinkedHashSet      (backed by LinkedHashMap)
          └── TreeSet            (backed by TreeMap, sorted)

java.util.Map  ← NOT a Collection! Separate hierarchy.
├── HashMap         (hash table, O(1), no order)
├── LinkedHashMap   (hash table + linked list, insertion order)
└── TreeMap         (red-black tree, sorted, O(log n))
```

| Interface | Key Contract | Notable Additions over Collection |
|---|---|---|
| Iterable | iterator() for for-each loops | Foundation — not a Collection itself |
| Collection | add, remove, size, contains, clear, iterator | Base contract for all collections |
| List | Ordered, duplicates allowed | get(index), set(index), add(index), indexOf, subList, ListIterator |
| Queue | FIFO ordering | offer, poll, peek (plus throwing versions: add, remove, element) |
| Deque | Both-ends operations | addFirst, addLast, removeFirst, removeLast, peekFirst, peekLast |
| Set | No duplicates | No additions — same as Collection but enforces uniqueness |
| Map | Unique keys → values | put, get, remove, containsKey, containsValue, keySet, values, entrySet |

> **WARNING:** Map is NOT a Collection! It has its own separate hierarchy. Interviewers frequently test this.

---

## Arrays

### Memory and Structure

An array is a fixed-size, contiguous block of memory. When you declare `int[] arr = new int[5]`, the JVM allocates 5 consecutive memory slots, each 4 bytes wide. O(1) access is possible because the JVM computes the address of any element as `baseAddress + (index × elementSize)`. No traversal needed — direct address math.

This contiguous layout is why ArrayList, ArrayDeque, and HashMap bucket arrays benefit from excellent CPU cache performance. Modern CPUs pre-fetch 64-byte cache lines from RAM. When you access one array element, adjacent elements are already in L1/L2 cache. LinkedList nodes scattered throughout the heap get none of this.

Primitive arrays (`int[]`, `double[]`) store raw values directly. Object arrays (`Integer[]`, `String[]`) store 8-byte references with actual objects elsewhere in the heap — up to 6× more memory.

```java
int[] primitive = new int[5];      // 5 × 4 bytes = 20 bytes (raw values)
                                   // + 16 bytes header + 4 bytes padding
                                   // Total: ~40 bytes (one heap object)

Integer[] objects = new Integer[5]; // 5 × 8 bytes (refs) = 40 bytes
                                    // + 16 bytes header = 56 bytes for array
                                    // + 5 × 24 bytes (Integer objects)
                                    // Total: ~176 bytes (six heap objects)

// IMPORTANT: arr.equals(arr2) compares REFERENCES — always false!
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};
a.equals(b);         // false! (reference comparison)
Arrays.equals(a, b); // true!  (content comparison)
```

### Complexity

| Operation | Complexity | Why |
|---|---|---|
| Access/update by index | O(1) | Direct: baseAddress + index × elementSize |
| Search (unsorted) | O(n) | Must check every element linearly |
| Search (sorted) | O(log n) | Binary search — halve space each iteration |
| Insert at end (space available) | O(1) | Direct placement |
| Insert at index i | O(n) | Shift elements at i, i+1…n-1 rightward |
| Delete at index i | O(n) | Shift elements at i+1…n-1 leftward |
| Space — int[n] | O(n) | n × 4 bytes |
| Space — Integer[n] | O(n) | n × ~28 bytes (8 ref + 20 object) |

### Arrays Utility Class

| Method | Time | Space | Notes |
|---|---|---|---|
| Arrays.sort(arr) | O(n log n) | O(log n) | Dual-pivot quicksort (primitives), TimSort (objects) |
| Arrays.equals(a, b) | O(n) | O(1) | Content comparison — use this, not arr.equals()! |
| Arrays.asList(arr) | O(1) | O(1) | FIXED-SIZE wrapper! Cannot add() or remove()! |
| Arrays.copyOf(arr, n) | O(n) | O(n) | Creates new array, copies elements up to n |
| System.arraycopy() | O(n) | O(1) | Native copy between existing arrays (fastest!) |
| Arrays.binarySearch() | O(log n) | O(1) | Array MUST be sorted first or result undefined! |
| Arrays.fill(arr, val) | O(n) | O(1) | Sets all elements to the given value |
| Arrays.stream(arr) | O(1) | O(1) | Creates a stream (operations are lazy) |

### Common Traps

```java
// TRAP 1: Arrays.asList() is FIXED SIZE!
List<String> fixed = Arrays.asList("a", "b", "c");
fixed.set(0, "x");   // OK — modification allowed
fixed.add("d");       // UnsupportedOperationException!
fixed.remove(0);      // UnsupportedOperationException!
// Fix: new ArrayList<>(Arrays.asList("a", "b", "c"))

// TRAP 2: arr.equals() compares references, not content!
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};
a.equals(b);         // FALSE! Always use Arrays.equals(a, b)

// TRAP 3: Arrays.sort() modifies the original array
int[] arr = {3, 1, 2};
int[] sorted = Arrays.copyOf(arr, arr.length); // Copy first!
Arrays.sort(sorted); // Only sorts the copy

// TRAP 4: Arrays.binarySearch() on unsorted array
int[] unsorted = {3, 1, 2};
Arrays.binarySearch(unsorted, 1); // Undefined result!
// Always sort before binarySearch!
```

---

## List Implementations

The `List` interface extends `Collection` to add positional (index-based) access. Lists are ordered, allow duplicates, and provide `get(index)`, `set(index)`, `add(index)`, `indexOf`, `subList`, and a bidirectional `ListIterator`.

### ArrayList

ArrayList is backed by a plain Java array (`Object[] elementData`). When elements exceed capacity, ArrayList creates a new 1.5× larger array and copies all existing elements. Modern Java uses lazy initialization: the backing array starts empty (capacity 0) and only allocates 10 slots on the first `add`.

```java
private Object[] elementData; // The backing array
private int size;              // Actual element count (NOT array length!)
// capacity = elementData.length (always >= size)
// Growth factor: 1.5x
// newCapacity = oldCapacity + (oldCapacity >> 1)
// Sequence: 0 -> 10 -> 15 -> 22 -> 33 -> 49...

// O(1) amortized proof for add(e):
// Add 17 elements with resizes at 10 and 15:
// - 10 simple adds (O(1) each) + 1 resize copying 10: 20 ops
// - 5 simple adds (O(1) each) + 1 resize copying 15: 20 ops
// - 2 simple adds: 2 ops
// Total: 42 operations for 17 adds = ~2.5 per add = O(1) amortized
```

**Complexity:**

| Operation | Complexity | Why |
|---|---|---|
| get(index) | O(1) | Direct: elementData[index] |
| set(index, e) | O(1) | Direct array write |
| add(e) at end | O(1) amortized | Place at end; rare O(n) resize amortized to O(1) |
| add(0, e) at start | O(n) | Shift ALL n elements one position right |
| add(index, e) | O(n) | Shift elements from index to end right |
| remove(index) | O(n) | Shift elements left to fill the gap |
| remove(Object) | O(n) | Linear search O(n) + shift O(n) |
| contains(e) | O(n) | Linear scan — List has no hashing |
| size() | O(1) | Direct field read |
| subList(from, to) | O(1) | Returns a VIEW not a copy! |

> **WARNING:** Classic O(n²) trap: `add(0, e)` in a loop. Each call is O(n), n calls = O(n²) total!
>
> **TIP:** Use `ensureCapacity(n)` before adding n elements to avoid repeated resizing.

### LinkedList

LinkedList is a doubly-linked list implementing both `List` and `Deque`. Each element is wrapped in a `Node` with the element, a `next` pointer, and a `prev` pointer. Nodes are scattered throughout heap memory — no contiguous backing array. Java's LinkedList maintains both a `head` (first) and `tail` (last) pointer for O(1) access to both ends.

```java
private static class Node<E> {
    E item;         // The element itself
    Node<E> next;   // Pointer to next node
    Node<E> prev;   // Pointer to previous node
}
Node<E> first;  // Head pointer — O(1) access to start
Node<E> last;   // Tail pointer — O(1) access to end

// Memory per Node: 40 bytes
//   16 bytes: object header (mark word + class pointer)
//    8 bytes: item reference
//    8 bytes: next pointer
//    8 bytes: prev pointer
//
// vs ArrayList: ~8 bytes per element (just one reference in the backing array)
// LinkedList Node alone uses ~5x more memory than an ArrayList slot.
```

**Complexity:**

| Operation | Complexity | Why |
|---|---|---|
| get(index) | O(n) | Traverse from head (or tail if index > n/2) |
| set(index, e) | O(n) | Traverse first, then O(1) update |
| addFirst(e) | O(1) | Update head pointer, create one Node |
| addLast(e) | O(1) | Update tail pointer, create one Node |
| add(index, e) | O(n) | Traverse to index, then O(1) Node insert |
| removeFirst() | O(1) | Update head pointer, dereference Node |
| removeLast() | O(1) | Update tail pointer, dereference Node |
| remove(index) | O(n) | Traverse to index, then O(1) relink |
| contains(e) | O(n) | Linear scan from head |

> **WARNING:** O(n²) trap: `get(i)` in a for loop. Each get traverses up to n nodes. n calls = O(n²). Use iterator or for-each!

### ArrayList vs LinkedList

| Aspect | ArrayList | LinkedList |
|---|---|---|
| Internal structure | Dynamic array (contiguous) | Doubly-linked nodes (scattered) |
| Access by index | O(1) — best | O(n) — worst |
| Add/remove at start | O(n) — worst | O(1) — best |
| Add/remove at end | O(1) amortized — best | O(1) — best |
| Add/remove at middle | O(n) | O(n) |
| Memory per element | ~8 bytes (just reference) | ~48 bytes (Node object) |
| CPU cache | Excellent (contiguous = pre-fetch) | Poor (scattered = cache miss per node) |
| Implements | List only | List AND Deque |
| Null elements | Allowed | Allowed |

Key points:
- **Random access is O(1) vs O(n).** `get(index)` on ArrayList is a direct memory offset. On LinkedList it means walking the chain node by node.
- **ArrayList uses less memory.** Each element costs just one reference. LinkedList wraps every element in a Node with extra pointers.
- **ArrayList's "weakness" rarely matters.** Inserting at the front is O(n) because elements shift — but shifting contiguous memory is a single native copy, extremely fast.
- **LinkedList's "O(1) insert" is mostly theoretical.** It's only O(1) at the head or tail. Inserting anywhere else requires walking the list to find the spot → O(n).
- **Default choice: ArrayList.** Only use LinkedList if you specifically need a Deque that accepts null elements — rare, and usually a design smell.

> **TIP:** For queues, stacks, or deques → use ArrayDeque, not LinkedList. Faster and lower memory.

### Legacy: Vector and Stack

`Vector` is a synchronized ArrayList with 2× growth. `Stack` extends Vector to provide LIFO. Both are legacy — avoid in new code. The synchronization overhead makes them slower even in single-threaded programs.

| Class | Backed By | Thread-Safe | Why Avoid | Modern Alternative |
|---|---|---|---|---|
| Vector | Dynamic array (2× growth) | Yes (synchronized) | Sync overhead in every operation | ArrayList |
| Stack | Vector (operations at END) | Yes (synchronized) | Extends Vector (bad design) | ArrayDeque |

### Fail-Fast Behavior & ConcurrentModificationException

All ArrayList and LinkedList iterators are fail-fast. They track the structural modification count (`modCount`). If the collection is structurally modified during iteration by any means other than the iterator's own `remove()`, it immediately throws `ConcurrentModificationException`.

```java
List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));

// WRONG — ConcurrentModificationException!
for (String s : list) {
    if (s.equals("b")) {
        list.remove(s); // Structural modification during iteration!
    }
}

// CORRECT Option 1 — Iterator.remove():
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    if (s.equals("b")) {
        it.remove(); // Safe! Updates modCount correctly
    }
}

// CORRECT Option 2 — removeIf() (Java 8+):
list.removeIf(s -> s.equals("b")); // Cleanest approach

// CORRECT Option 3 — collect to new list:
List<String> result = list.stream()
    .filter(s -> !s.equals("b"))
    .collect(Collectors.toList());
```

> **WARNING:** `ConcurrentModificationException` is one of the most common bugs. Only `Iterator.remove()` is safe during iteration!

### remove(int) vs remove(Object) Trap

The `List` interface has two overloaded `remove()` methods: `remove(int index)` removes by position and `remove(Object o)` removes by value. For a `List<Integer>`, passing an `int` literal calls `remove(int index)`!

```java
List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
list.remove(1);                  // Removes INDEX 1 -> list: [1, 3, 4, 5]
list.remove(Integer.valueOf(1)); // Removes VALUE 1 -> list: [2, 3, 4, 5]

// WHY? Java resolves overloads at compile time:
// remove(1)                 -> int literal    -> remove(int index)
// remove(Integer.valueOf(1))-> Integer object -> remove(Object o)
```

> **WARNING:** Always use `remove(Integer.valueOf(x))` to remove by VALUE in an Integer list.

### Iterator and ListIterator

The Iterator pattern provides a universal way to traverse any Collection without exposing its internal structure.

```java
List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
Iterator<String> it = list.iterator();

// The three Iterator methods:
it.hasNext();  // true if more elements remain
it.next();     // returns next element, advances cursor
               // throws NoSuchElementException if no more elements!
it.remove();   // removes element returned by LAST next() call
               // throws IllegalStateException if next() not yet called

// Standard pattern:
while (it.hasNext()) {
    String s = it.next();
    if (condition) {
        it.remove(); // safe removal during iteration
    }
}

// for-each loop is sugar for Iterator — but does NOT expose the iterator object.
// You cannot call it.remove() inside a for-each loop!
```

`ListIterator` is an extension exclusive to `List` that adds bidirectional traversal and in-place modification:

```java
// ListIterator adds 5 extra methods over Iterator:
ListIterator<String> lit = list.listIterator();
lit.hasPrevious();   // true if can go backward
lit.previous();      // returns previous element, moves cursor back
lit.nextIndex();     // index of element that next() would return
lit.previousIndex(); // index of element that previous() would return
lit.set(e);          // replaces element returned by last next()/previous()
lit.add(e);          // inserts element BEFORE the current cursor position

// Start from a specific position:
ListIterator<String> lit2 = list.listIterator(2); // start at index 2

// Traverse backwards:
ListIterator<String> lit3 = list.listIterator(list.size()); // start at end
while (lit3.hasPrevious()) {
    System.out.println(lit3.previous());
}
```

| Feature | Iterator | ListIterator |
|---|---|---|
| Available on | All Collections | List only |
| Direction | Forward only | Both forward and backward |
| Remove current | it.remove() | lit.remove() |
| Replace current | Not supported | lit.set(e) |
| Add element | Not supported | lit.add(e) |
| Get index | Not supported | nextIndex() / previousIndex() |

---

## Queue / Deque Family

### Queue Interface

Queue models FIFO (First In, First Out). Every operation has two versions: one that throws and one that returns null/false. Use the returning versions (`offer`/`poll`/`peek`) when an empty or full queue is an expected normal condition.

| Operation | Throws Exception | Returns null/false | When to use returning version |
|---|---|---|---|
| Insert | add(e) | offer(e) | When a full queue (capacity limit) is expected |
| Remove | remove() | poll() | When an empty queue is an expected condition |
| Examine | element() | peek() | When reading from an empty queue is expected |

### Deque Interface

Deque (Double-Ended Queue, pronounced "deck") extends Queue allowing add/remove at BOTH ends. It unifies Queue and Stack into one interface.

- **Stack** = add and remove from the SAME end.
- **Queue** = add to one end, remove from the OTHER end.

```java
// Used as Queue (FIFO — different ends):
deque.offer(e);    // enqueue at tail
deque.poll();      // dequeue from head
deque.peek();      // examine head

// Used as Stack (LIFO — same end, OPTION 1: HEAD)
deque.addFirst(e);    // push to head  <- push()/pop() use this!
deque.removeFirst();  // pop from head

// Used as Stack (LIFO — same end, OPTION 2: TAIL)
deque.addLast(e);    // push to tail  <- ALSO valid!
deque.removeLast();  // pop from tail

// Both Stack options are O(1). Key rule: same end = stack!
```

### Throwing vs Non-Throwing

Just like Queue, Deque has two versions of every operation:

```java
// THROWING versions (throw exception on failure):
deque.addFirst(e);     // adds to HEAD    — throws IllegalStateException if capacity limited
deque.addLast(e);      // adds to TAIL    — throws IllegalStateException if capacity limited
deque.removeFirst();   // removes HEAD    — throws NoSuchElementException if empty
deque.removeLast();    // removes TAIL    — throws NoSuchElementException if empty
deque.getFirst();      // examines HEAD   — throws NoSuchElementException if empty
deque.getLast();       // examines TAIL   — throws NoSuchElementException if empty

// NON-THROWING versions — explicit ends (return null/false on failure):
deque.offerFirst(e);   // adds to HEAD    — returns false if capacity limited
deque.offerLast(e);    // adds to TAIL    — returns false if capacity limited
deque.pollFirst();     // removes HEAD    — returns null if empty
deque.pollLast();      // removes TAIL    — returns null if empty
deque.peekFirst();     // examines HEAD   — returns null if empty
deque.peekLast();      // examines TAIL   — returns null if empty

// NON-THROWING versions — Queue aliases (when used as FIFO):
deque.offer(e);        // adds to TAIL    — = offerLast
deque.poll();          // removes HEAD    — = pollFirst
deque.peek();          // examines HEAD   — = peekFirst
```

### ArrayDeque & Circular Array

ArrayDeque is backed by a resizable circular array with two integer pointers: `head` and `tail`. The circular design eliminates the need to shift elements when adding or removing from either end, making all end operations O(1).

#### The Problem with Regular Arrays

In a regular array (like ArrayList), index 0 is always the first element. Removing it requires shifting every other element one position left: O(n). To make `removeFirst` O(1), the start of the logical array must be flexible — not fixed at index 0.

#### Circular Array Solution

```
// Initial state (empty):
head at index 0, tail at index 0 (head==tail means empty)
Index:    0   1   2   3   4   5   6   7
Array:  [ _ | _ | _ | _ | _ | _ | _ | _ ]

// After addLast(1), addLast(2), addLast(3):
head at index 0, tail at index 3
Index:    0   1   2   3   4   5   6   7
Array:  [ 1 | 2 | 3 | _ | _ | _ | _ | _ ]

// After removeFirst() — just move head pointer forward! No shifting!
head at index 1, tail at index 3
Index:    0   1   2   3   4   5   6   7
Array:  [ _ | 2 | 3 | _ | _ | _ | _ | _ ]

// After addFirst(9) — move head BACKWARD, place element:
// head = (head - 1 + capacity) % capacity = (1-1+8)%8 = 0
head at index 0, tail at index 3
Index:    0   1   2   3   4   5   6   7
Array:  [ 9 | 2 | 3 | _ | _ | _ | _ | _ ]

// After many adds/removes — head wraps around to end of array:
head at index 6 (element 3), tail at index 2
Logical order: [3, 4, 5, 6]
Index:    0   1   2   3   4   5   6   7
Array:  [ 5 | 6 | _ | _ | _ | _ | 3 | 4 ]
Reading order: start at index 6 = 3, 4, wrap to index 0 = 5, 6
```

#### Modulo Arithmetic — The Wrapping Mechanism

```java
// FORMULAS — memorize these!
// Move FORWARD:  new_index = (current + 1) % capacity
// Move BACKWARD: new_index = (current - 1 + capacity) % capacity
//   (+capacity before % prevents negative results)

// Operation-to-formula mapping:
// addFirst(e)   -> head moves BACKWARD: head = (head-1+cap) % cap
// removeFirst() -> head moves FORWARD:  head = (head+1) % cap
// addLast(e)    -> tail moves FORWARD:  tail = (tail+1) % cap
// removeLast()  -> tail moves BACKWARD: tail = (tail-1+cap) % cap

// Example wrapping (capacity=8):
// (7 + 1) % 8 = 0  // Forward wrap: index 7 -> index 0
// (0 - 1 + 8) % 8 = 7  // Backward wrap: index 0 -> index 7
```

**Complexity:**

| Operation | Complexity | Notes |
|---|---|---|
| addFirst(e) | O(1) amortized | Move head backward (modulo), store element |
| addLast(e) | O(1) amortized | Store element, move tail forward (modulo) |
| removeFirst() | O(1) | Read array[head], move head forward |
| removeLast() | O(1) | Move tail backward, read element |
| peekFirst() | O(1) | Read array[head], no pointer movement |
| peekLast() | O(1) | Read array[(tail-1+cap)%cap] |
| get(index) | NOT SUPPORTED | ArrayDeque does NOT implement List! |
| Resize | O(n) rarely | When head==tail after add: 2× growth |

**Properties:**

| Property | Value |
|---|---|
| Initial capacity | 16 |
| Growth factor | 2× (doubles on resize) |
| Empty condition | head == tail |
| Null elements | NOT allowed — NullPointerException |
| Thread safety | Not thread-safe |

#### Stack Method Aliases (push / pop / peek)

All three delegate to the HEAD end:

```java
Deque<Integer> stack = new ArrayDeque<>();
// Stack alias  ->  actual method   ->  end operated on
stack.push(e);    // = addFirst(e)     -> HEAD
stack.pop();      // = removeFirst()   -> HEAD
stack.peek();     // = peekFirst()     -> HEAD
// All are O(1). This is why ArrayDeque replaces Stack class.
```

| Stack Alias | Deque Equivalent | End Used | Returns |
|---|---|---|---|
| push(e) | addFirst(e) | HEAD | void |
| pop() | removeFirst() | HEAD | element (throws if empty) |
| peek() | peekFirst() | HEAD | element or null if empty |

### PriorityQueue

PriorityQueue is NOT FIFO. It uses a binary heap — a complete binary tree stored in an array where every parent is smaller than its children (min-heap). The smallest element is always at `array[0]` (the root), so `peek()` is O(1). When you `add()`, the element goes to the end and "bubbles up" by swapping with its parent until the heap property is restored (O(log n) swaps). When you `poll()`, the root is removed, the last element takes its place, then "sifts down" by swapping with the smaller child (O(log n) swaps).

> **CRITICAL:** The heap only guarantees `array[0]` is the min/max. The rest of the array is NOT sorted — iteration order will NOT match priority order. Only `peek()`/`poll()` give you elements in priority order.

#### Ordering Mechanism

PriorityQueue determines priority using one of two mechanisms:
- **Natural ordering (Comparable)** — if no Comparator provided, elements MUST implement Comparable.
- **Custom ordering (Comparator)** — if Comparator provided in constructor, uses `compare()`.

```java
// Custom objects — MUST provide Comparator OR implement Comparable:
class Person {
    String name;
    int age;
}

// WRONG — ClassCastException at runtime!
PriorityQueue<Person> broken = new PriorityQueue<>();
broken.add(new Person()); // Crashes! "Person cannot be cast to Comparable"

// CORRECT Option 1 — Provide Comparator:
PriorityQueue<Person> byAge = new PriorityQueue<>(
    (a, b) -> Integer.compare(a.age, b.age)
);

// CORRECT Option 2 — Implement Comparable:
class Person implements Comparable<Person> {
    int age;
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);
    }
}
PriorityQueue<Person> pq = new PriorityQueue<>(); // Now works!

// Min-heap (default — smallest first):
PriorityQueue<Integer> minPQ = new PriorityQueue<>();
minPQ.add(5); minPQ.add(2); minPQ.add(8); minPQ.add(1);
minPQ.peek();  // 1 — smallest, no removal
minPQ.poll();  // 1 — removed
minPQ.poll();  // 2 — next smallest

// Max-heap (largest first):
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

// Custom priority:
PriorityQueue<int[]> customPQ = new PriorityQueue<>(
    (a, b) -> Integer.compare(a[1], b[1]) // Sort by second element
);
```

**Complexity:**

| Operation | Complexity | Why |
|---|---|---|
| add(e) / offer(e) | O(log n) | Insert at bottom, bubble UP through log n levels |
| poll() / remove() | O(log n) | Remove root, replace with last, sift DOWN log n levels |
| peek() | O(1) | Root always holds min/max — direct array[0] access |
| remove(Object) | O(n) | Must find element first (linear scan) |
| contains(e) | O(n) | Linear scan — no hashing in heap |

**Properties:**

| Property | Value |
|---|---|
| Default initial capacity | 11 (not 16 like ArrayDeque!) |
| Growth | ~1.5× on resize |
| Null elements | NOT allowed — NullPointerException |
| Thread safety | Not thread-safe. Use PriorityBlockingQueue for concurrency |
| Ordering guarantee | Only peek()/poll() order is guaranteed, NOT iteration order |

> **WARNING:** PriorityQueue default capacity is 11, NOT 16. Iteration order is NOT the priority order!

### Queue Family Comparison

| Need | Best Choice | Why Not Others |
|---|---|---|
| Queue (FIFO) | ArrayDeque | Cache-friendly array vs LinkedList scattered nodes |
| Stack (LIFO) | ArrayDeque | No sync overhead like Stack class. Better design. |
| Priority ordering | PriorityQueue | Heap-based, O(log n) ordered access |
| Queue + null elements | LinkedList | Only Deque implementation that accepts null |
| Thread-safe queue | ArrayBlockingQueue / ConcurrentLinkedQueue | Built-in thread safety |

---

## equals() and hashCode()

The `equals()` and `hashCode()` methods form the foundational contract that makes all hash-based collections (HashMap, HashSet, LinkedHashMap, LinkedHashSet) work correctly. They must always be overridden together.

- `equals()` answers: "Are these two objects logically the same?"
- `hashCode()` answers: "Which bucket should this object live in?"

### Default Behavior

Every Java class inherits `equals()` and `hashCode()` from `java.lang.Object`. The default `equals()` uses reference comparison (`==`). The default `hashCode()` returns an integer derived from the object's memory address.

```java
Person p1 = new Person("John", 25); // memory: 0x1000
Person p2 = new Person("John", 25); // memory: 0x5000

// Default behavior (no override):
p1.equals(p2);  // false — different references!
p1.hashCode();  // some int derived from 0x1000
p2.hashCode();  // different int from 0x5000

HashSet<Person> set = new HashSet<>();
set.add(p1);
set.add(p2); // Both added! Treated as different!
set.size();  // 2 — WRONG, should be 1
```

### The Contract (3 Rules)

| Rule | Statement | Why It Matters |
|---|---|---|
| 1. Consistency | Multiple `hashCode()` calls on same object must return same value (unless fields change) | HashMap stores object at bucket index = hash & (capacity-1). If hashCode changes between put() and get(), the bucket index changes and the object is permanently lost! |
| 2. equals → hashCode | If `a.equals(b)` is true, then `a.hashCode()` MUST equal `b.hashCode()` | HashMap lookup finds bucket via hashCode. If equal objects have different hashCodes, they are in different buckets and can never be found as duplicates. |
| 3. Collision is OK | Equal hashCodes do NOT require equals() to return true | This is simply a collision — two different objects landing in the same bucket. Normal and expected behavior. |

### Contract Violations & Consequences

| Violation | What Happens | Symptom |
|---|---|---|
| Override equals() only | Equal objects get different hashCodes — land in different buckets | `set.contains(new Person("John"))` returns false. HashSet allows logically duplicate entries. |
| Override hashCode() only | Same bucket BUT equals() uses reference — every new object is 'different' | Both p1 and p2 added to same bucket, yet set treats them as different. Duplicates allowed. |
| hashCode() always returns constant (e.g., 42) | All objects in ONE bucket — O(n) operations everywhere | HashMap/HashSet degrade to O(n). After 8 elements, treeify to O(log n). |

### Correct Implementation

Both methods must use exactly the same fields.

```java
@Override
public boolean equals(Object o) {
    if (this == o) {
        return true;                 // Same reference
    }
    if (!(o instanceof Person)) {
        return false;                // Type safety
    }
    Person p = (Person) o;
    return age == p.age &&                       // == for primitives
        Objects.equals(name, p.name);            // null-safe for objects
}

@Override
public int hashCode() {
    return Objects.hash(name, age); // SAME fields as equals()!
}
// Objects.hash() combines each field's hashCode consistently.
```

---

## Hash Tables

A hash table stores key-value pair entries and finds them in O(1) time. It is backed by an internal array called a `table`, where each slot (a **bucket**) holds one or more entries. Java's HashMap, LinkedHashMap, HashSet, and LinkedHashSet are all built on this.

### What is Hashing?

Hashing turns any piece of data into a fixed-size number that acts as its address. A good hash function spreads inputs evenly across buckets — that even spread is what makes get/put O(1) on average.

HashMap doesn't use `hashCode()` directly. It mixes the bits first to avoid clustering, then picks the bucket with a fast bitwise operation instead of modulo. That's why capacity is always a power of 2 — it makes the math a single CPU instruction.

### Internal Bucket Structure

```java
Node<K,V>[] table;  // The bucket array (always power-of-2 size)

static class Node<K,V> {
    final int hash;    // Cached hash (computed once, reused on resize)
    final K key;
    V value;
    Node<K,V> next;    // Next node in chain (for collision handling)
}

// Visual: 3 entries, capacity=8
// bucket[0] -> null
// bucket[1] -> null
// bucket[2] -> Node("John", 25)
// bucket[3] -> null
// bucket[4] -> Node("Mary", 30) -> Node("Lisa", 28)  <- Collision!
// bucket[5] -> null
// ...
```

Most buckets are empty. Some hold a single node. When two different keys hash to the same bucket, a collision occurs and the nodes form a small linked list — **separate chaining**.

### Collision Resolution — Separate Chaining

A collision occurs when two different keys hash to the same bucket index. When inserting, the chain is checked using `equals()` to detect duplicates. When searching, `equals()` identifies the exact matching entry within the bucket chain.

```java
// get("John") step by step:
// 1. hash = spread("John".hashCode())       O(1) arithmetic
// 2. index = hash & (capacity - 1)          O(1) bitwise AND
// 3. node = table[index]                    O(1) array access
// 4. Walk the chain using Objects.nonNull(node.next):
//    while (Objects.nonNull(node)) {
//        if (node.key.equals("John"))
//            return node.value;
//        node = node.next;
//    }
// 5. return null; // Not found

// put("Lisa", 28) when bucket 4 already has "Mary":
// 1-3. Same as above -> find bucket 4
// 4. Check chain: "Mary".equals("Lisa")? NO
// 5. Add Lisa as new Node: bucket[4] -> [Mary -> Lisa]
```

### Java 8 Treeification

When a bucket's chain grows to 8 or more elements (`TREEIFY_THRESHOLD=8`), the singly-linked chain is converted from a linked list to a Red-Black Tree. This drops worst-case lookup within that bucket from O(n) to O(log n). When the chain shrinks back to 6, it converts back. The gap between 8 and 6 prevents constant flipping.

With a decent hash function, reaching 8 collisions in one bucket is extremely rare. If it happens often, your `hashCode()` is bad.

### Load Factor

Load factor = entries / capacity. When entries exceed `capacity * loadFactor` (default 0.75), the map resizes: a new array of 2× capacity is created and every entry is rehashed. Resize is O(n), but spreading that cost over many inserts makes add amortized O(1).

| Load Factor | Tradeoff |
|---|---|
| Low (0.25) | Few collisions but wastes memory and resizes often |
| Default (0.75) | Balanced sweet spot |
| High (0.99) | Memory-efficient but collision-heavy and slow |

### Why O(1) Average?

Three things must hold:
1. Hash function distributes keys evenly (buckets hold ~1 entry each)
2. Load factor stays reasonable (0.75) so buckets don't overfill
3. `hashCode()` is consistent (same key always hashes the same)

Worst case is O(n) if all keys collide into one bucket — but Java 8's tree conversion caps it at O(log n).

---

## Map Implementations

Map represents key-value pairs where each key is unique. It is NOT part of the Collection hierarchy. The three main implementations differ in ordering and performance.

### HashMap

HashMap is backed by a hash table providing O(1) average for all basic operations. It makes no ordering guarantees.

#### Complete put(key, value) Flow

```
map.put("John", 25);

Step 1: Compute spread hash
    hash = "John".hashCode() ^ (hashCode >>> 16)

Step 2: Find bucket
    index = hash & (capacity - 1)  // e.g. index = 5

Step 3: Bucket empty?
    YES -> create new Node(hash, "John", 25, null)
           table[5] = node. Done!

Step 4: Bucket occupied (collision)?
    Traverse chain looking for key match:
    node.key.equals("John")? YES -> overwrite value, return old
    node.key.equals("John")? NO  -> continue to next in chain
    End of chain -> add new Node at end

Step 5: Check load factor
    if (++size > capacity * loadFactor)
        resize! (capacity *= 2, rehash all entries)

Step 6: Chain length >= TREEIFY_THRESHOLD (8)?
    YES -> convert LinkedList to Red-Black Tree
```

**Key Characteristics:**

| Characteristic | Value / Behavior |
|---|---|
| Internal structure | Array of Node buckets (hash table) |
| Iteration order | None — unpredictable, changes on resize |
| Null keys | ONE null key allowed (bucket 0) |
| Null values | Multiple null values allowed |
| Thread safety | Not thread-safe. Use ConcurrentHashMap |
| Initial capacity / Load factor | 16 / 0.75 |
| Growth | 2× capacity on resize, all entries rehashed |

**Key Methods:**

```java
map.put(key, value)              // Add or overwrite. Returns OLD value or null
map.putIfAbsent(key, value)      // Only inserts if key not already present
map.get(key)                     // Returns value or null
map.getOrDefault(key, default)   // Returns value or default if absent
map.containsKey(key)             // O(1) — uses hashing
map.containsValue(value)         // O(n) — must scan ALL buckets!
map.remove(key)                  // Remove and return old value
map.size()                       // O(1) direct field

// Iteration (all O(n)):
for (Map.Entry<K,V> e : map.entrySet()) { // key + value — most common
    System.out.println(e.getKey() + "=" + e.getValue());
}
map.forEach((k, v) -> System.out.println(k + "=" + v)); // Java 8+
```

> **WARNING:** `containsKey()` = O(1). `containsValue()` = O(n). Classic interview trap — values are NOT hashed!

### LinkedHashMap

LinkedHashMap extends HashMap by adding two extra pointers (`before` and `after`) directly onto each entry node. This threads all entries into a doubly-linked sequence in insertion order — there is no separate LinkedList object. The same node sits in the hash table bucket AND holds before/after pointers, serving both structures at once.

These two operations work completely independently:
- `get(key)` / `put(key)` / `containsKey()` — uses the hash table entirely, ignores the linked sequence. O(1).
- `for` loop / iterator — follows the before/after chain from head to tail, ignores the hash table. O(n), visits only real entries, never scans empty buckets.

#### LRU Cache Mode

LinkedHashMap has a special access-order mode (enabled by passing `true` as the third constructor argument). Whenever an entry is accessed via `get()` or `put()`, it is unlinked from its current position and reattached to tail. This makes `head` always the least recently used entry.

To turn this into a real LRU cache that automatically evicts old entries, override `removeEldestEntry()`:

```java
LinkedHashMap<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return size() > 100; // cap at 100 entries
    }
};

map.put("A", 1); map.put("B", 2); map.put("C", 3);
// chain: head -> [A] <-> [B] <-> [C] <- tail
// Insertion-order iteration: A=1, B=2, C=3

map.get("A"); // access-order mode: A moves to tail
// chain: head -> [B] <-> [C] <-> [A] <- tail
// Now iteration: B=2, C=3, A=1

// HEAD = least recently used, TAIL = most recently used
// When size exceeds 100, removeEldestEntry returns true and HEAD is evicted.
// Without the override, access-order only reorders — map grows forever.

// WHY iteration is faster than HashMap:
// LinkedHashMap: follows before/after chain  -> O(n), visits only real entries
// HashMap:       scans entire bucket array   -> O(capacity+n), skips empty buckets
```

| Characteristic | Value / Behavior |
|---|---|
| Internal structure | HashMap + doubly-linked through all entries |
| Order | Insertion order (default) or access order (LRU mode) |
| Iteration complexity | O(n) — only visits entries, skips empty buckets |
| get/put/remove | O(1) — same as HashMap |
| Null keys/values | Same as HashMap |
| Memory overhead | Two extra pointers per entry (before/after in linked list) |

### TreeMap

TreeMap stores entries sorted by key using a Red-Black Tree — a self-balancing BST. Every left child is less than its parent, every right child is greater. The tree rebalances after insertions and deletions to maintain height ≤ 2 × log n, guaranteeing O(log n) worst-case for all operations.

Because TreeMap must compare keys, **null keys are not allowed**. Null values are allowed. By default, natural ordering is used (keys must implement Comparable). A custom Comparator can be provided at construction.

```java
// Natural ordering (alphabetical for String, numerical for Integer):
TreeMap<String, Integer> map = new TreeMap<>();
map.put("Banana", 2); map.put("Apple", 1); map.put("Cherry", 3);
// Always iterates: Apple=1, Banana=2, Cherry=3

// Custom Comparator (reverse order):
TreeMap<String, Integer> desc = new TreeMap<>(Collections.reverseOrder());

// NavigableMap methods (UNIQUE to TreeMap!):
// Assuming keys: Apple, Banana, Cherry, Date, Elder
map.firstKey();              // Apple  — smallest
map.lastKey();               // Elder  — largest
map.floorKey("Coconut");     // Cherry — largest key <= "Coconut"
map.ceilingKey("Coconut");   // Date   — smallest key >= "Coconut"
map.lowerKey("Cherry");      // Banana — largest key STRICTLY < "Cherry"
map.higherKey("Cherry");     // Date   — smallest key STRICTLY > "Cherry"
map.headMap("Cherry");       // {Apple, Banana} — STRICT < Cherry
map.tailMap("Cherry");       // {Cherry, Date, Elder} — >= Cherry
map.subMap("Banana","Date"); // {Banana, Cherry} — Banana <= x < Date

// These return VIEWS — modifying them modifies the original TreeMap!
```

| Characteristic | Value / Behavior |
|---|---|
| Internal structure | Red-Black Tree (self-balancing BST) |
| Iteration order | Sorted by natural ordering or custom Comparator |
| Null keys | NOT allowed — NullPointerException |
| Null values | Allowed |
| All operations | O(log n) guaranteed |
| Unique advantage | Range queries: headMap, tailMap, subMap, floor, ceiling |

### Comparable vs Comparator

| Aspect | Comparable | Comparator |
|---|---|---|
| Location | Implemented inside the class | External class, lambda, or method reference |
| Method | int compareTo(T other) | int compare(T a, T b) |
| Return convention | negative: this<other, 0: equal, positive: this>other | Same convention |
| Sort orders possible | Only ONE natural ordering | Multiple — create different Comparators |
| Use when | You own the class and there is a clear natural order | You don't own the class, or need multiple sort strategies |
| Built-in examples | String, Integer, Date, LocalDate | Custom sort by any field combination |

```java
// Comparable — define natural ordering inside the class:
public class Person implements Comparable<Person> {
    String name;
    int age;

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name); // alphabetical by name
        // return Integer.compare(this.age, other.age); // by age
        // AVOID: return this.age - other.age; // risky: integer overflow!
    }
}

// Comparator — three ways to create:

// WAY 1: Anonymous class (old style)
Comparator<Person> byAge = new Comparator<Person>() {
    public int compare(Person a, Person b) {
        return Integer.compare(a.age, b.age);
    }
};

// WAY 2: Lambda (Java 8+) — most common
Comparator<Person> byAgeLambda = (a, b) -> Integer.compare(a.age, b.age);

// WAY 3: Comparator.comparing() + method reference (cleanest)
Comparator<Person> byName = Comparator.comparing(Person::getName);
Comparator<Person> byAgeRef = Comparator.comparingInt(Person::getAge);

// Use in TreeMap:
TreeMap<Person, String> treeMap = new TreeMap<>(byAgeRef);
```

#### Comparator Chaining (Java 8+)

```java
// Sort by name, then by age if names are equal:
Comparator<Person> byNameThenAge = Comparator
    .comparing(Person::getName)         // primary: alphabetical name
    .thenComparingInt(Person::getAge);  // secondary: ascending age

// Reverse the entire order:
Comparator<Person> reversed = byNameThenAge.reversed();

// Sort by name descending, then age ascending:
Comparator<Person> complex = Comparator
    .comparing(Person::getName, Comparator.reverseOrder())
    .thenComparingInt(Person::getAge);

// Null-safe sorting (nulls first or last):
Comparator<Person> nullSafe = Comparator.nullsLast(
    Comparator.comparing(Person::getName)
);

// Use with Collections.sort() or List.sort():
list.sort(byNameThenAge);
Collections.sort(list, reversed);
```

### Map Comparison Table

| Feature | HashMap | LinkedHashMap | TreeMap |
|---|---|---|---|
| Internal structure | Hash table | Hash table + doubly-linked sequence | Red-Black Tree |
| Order | None (unpredictable) | Insertion order | Sorted (natural/Comparator) |
| Null keys | 1 allowed | 1 allowed | NOT allowed |
| Null values | Allowed | Allowed | Allowed |
| get / put / remove | O(1) average | O(1) average | O(log n) |
| containsKey | O(1) | O(1) | O(log n) |
| containsValue | O(n) | O(n) | O(n) |
| Iteration | O(capacity + n) | O(n) | O(n) |
| Thread safety | No | No | No |
| Range queries | No | No | Yes — headMap, tailMap, subMap |
| Use when | Fast access, no order | Fast + insertion order | Sorted or range queries |

### Modern Map Methods (Java 8+)

These methods replace common verbose patterns and are heavily tested in senior interviews.

#### computeIfAbsent — Initialize on First Access

```java
// PROBLEM: Build a group-by map manually — verbose, 4 lines:
Map<String, List<String>> groups = new HashMap<>();
if (!groups.containsKey(key)) {
    groups.put(key, new ArrayList<>());
}
groups.get(key).add(value);

// SOLUTION: computeIfAbsent
Map<String, List<String>> groups2 = new HashMap<>();
groups2.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
// If key absent: create new list, put it, return it, add value
// If key present: return existing list, add value

// Classic LeetCode pattern — group anagrams:
Map<String, List<String>> map = new HashMap<>();
for (String word : words) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    String key = new String(chars);
    map.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
}
```

#### merge — Combine Values Cleanly

```java
// PROBLEM: Count word frequency manually:
if (map.containsKey(word)) {
    map.put(word, map.get(word) + 1);
} else {
    map.put(word, 1);
}

// SOLUTION: merge(key, initialValue, remappingFunction)
map.merge(word, 1, Integer::sum);
// If key absent:  put(key, 1)
// If key present: put(key, existingValue + 1)

// Signature: merge(K key, V value, BiFunction<V, V, V> remapping)
// The BiFunction receives (oldValue, newValue), returns merged result
// If remapping returns null: key is REMOVED from the map
```

#### compute — Full Control Over Value

```java
// compute(key, BiFunction<K, V, V> remapping)
// BiFunction receives (key, currentValue) — currentValue can be null

// Increment counter (handles null on first call):
map.compute(word, (k, v) -> Objects.isNull(v) ? 1 : v + 1);
// Same as merge(word, 1, Integer::sum) but more flexible

// Remove element when counter reaches 0:
map.compute(word, (k, v) -> {
    if (Objects.isNull(v)) {
        return null; // already absent
    }
    if (v == 1) {
        return null; // returning null REMOVES the key!
    }
    return v - 1;   // decrement
});
```

#### Other Important Java 8+ Map Methods

```java
// getOrDefault — avoid null checks:
int count = map.getOrDefault(key, 0); // 0 if key absent

// putIfAbsent — only insert if key not present:
map.putIfAbsent("key", "defaultValue"); // returns existing or null

// replaceAll — update all values in place:
map.replaceAll((k, v) -> v * 2);            // double all values
map.replaceAll((k, v) -> v.toUpperCase());  // uppercase all values

// forEach — iterate with lambda:
map.forEach((k, v) -> System.out.println(k + " -> " + v));
```

| Method | Key Absent Behavior | Key Present Behavior | Common Use Case |
|---|---|---|---|
| getOrDefault(k, d) | Returns d | Returns existing value | Safe reads with fallback |
| putIfAbsent(k, v) | Inserts v, returns null | Does nothing, returns existing | Initialize once only |
| computeIfAbsent(k, fn) | Calls fn(k), inserts result | Returns existing value | Lazy initialization of complex values |
| merge(k, v, fn) | Inserts v | Calls fn(old, v), inserts result | Frequency counting, accumulation |
| compute(k, fn) | Calls fn(k, null) | Calls fn(k, old), removes if null | Complex conditional updates |
| replaceAll(fn) | N/A | Calls fn(k, v) for every entry | Batch value transformation |

---

## Set Implementations

A Set is a Collection that contains no duplicate elements. Each Set implementation is backed directly by its corresponding Map — a Set is essentially a Map where only the keys matter and a placeholder constant is used as the value.

```java
// HashSet internal implementation (simplified):
public class HashSet<E> {
    private HashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public boolean add(E e) {
        return Objects.isNull(map.put(e, PRESENT)); // true = new element
    }

    public boolean contains(Object o) {
        return map.containsKey(o); // O(1)
    }

    public boolean remove(Object o) {
        return map.remove(o) == PRESENT; // O(1)
    }
}
// LinkedHashSet -> backed by LinkedHashMap
// TreeSet       -> backed by TreeMap
```

### HashSet

HashSet is the general-purpose Set backed by a HashMap. It provides O(1) average for add, remove, and contains. Like HashMap, it makes no guarantees about iteration order.

| Characteristic | Value / Behavior |
|---|---|
| Backed by | HashMap |
| Order | None — unpredictable |
| Null elements | ONE null allowed (same as HashMap null key) |
| Thread safety | Not thread-safe |
| add/remove/contains | O(1) average |
| Duplicates prevented by | equals() + hashCode() contract |

### LinkedHashSet

LinkedHashSet extends HashSet and is backed by a LinkedHashMap. It preserves insertion order while maintaining O(1) performance. Use when you need both uniqueness and predictable order.

| Characteristic | Value / Behavior |
|---|---|
| Backed by | LinkedHashMap |
| Order | Insertion order preserved |
| Null elements | ONE null allowed |
| Thread safety | Not thread-safe |
| add/remove/contains | O(1) average |
| Iteration advantage | O(n) visits only entries, not empty buckets |

### TreeSet

TreeSet is backed by a TreeMap. It stores unique elements in sorted order and provides the NavigableSet interface for range queries. All operations are O(log n). Null elements are not permitted.

```java
TreeSet<Integer> set = new TreeSet<>();
set.add(5); set.add(2); set.add(8); set.add(1); set.add(9);
// Stored: 1, 2, 5, 8, 9 (always sorted!)

set.first();        // 1  — smallest
set.last();         // 9  — largest
set.floor(6);       // 5  — largest element <= 6
set.ceiling(6);     // 8  — smallest element >= 6
set.headSet(5);     // [1, 2]    — STRICTLY < 5
set.tailSet(5);     // [5, 8, 9] — >= 5
set.subSet(2, 8);   // [2, 5]    — 2 <= x < 8
```

| Characteristic | Value / Behavior |
|---|---|
| Backed by | TreeMap |
| Order | Sorted (natural ordering or Comparator) |
| Null elements | NOT allowed — NullPointerException |
| Thread safety | Not thread-safe |
| add/remove/contains | O(log n) |
| Range operations | first, last, floor, ceiling, headSet, tailSet, subSet |

### Set Operations (Set Algebra)

```java
Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
Set<Integer> setB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

// UNION — all elements from both sets (no duplicates):
Set<Integer> union = new HashSet<>(setA);
union.addAll(setB);
// Result: {1, 2, 3, 4, 5, 6, 7, 8}

// INTERSECTION — only elements present in BOTH sets:
Set<Integer> intersection = new HashSet<>(setA);
intersection.retainAll(setB);
// Result: {4, 5}

// DIFFERENCE — elements in setA but NOT in setB:
Set<Integer> difference = new HashSet<>(setA);
difference.removeAll(setB);
// Result: {1, 2, 3}

// SYMMETRIC DIFFERENCE — elements in one but NOT both:
Set<Integer> symDiff = new HashSet<>(union);
symDiff.removeAll(intersection);
// Result: {1, 2, 3, 6, 7, 8}

// SUBSET CHECK — is setA a subset of setB?
boolean isSubset = setB.containsAll(setA); // false

// IMPORTANT: Always copy first! These methods modify in-place!
// BAD:  setA.retainAll(setB);                      <- destroys original setA!
// GOOD: new HashSet<>(setA).retainAll(setB);        <- safe copy
```

| Operation | Method | Result | Complexity |
|---|---|---|---|
| Union (A ∪ B) | copy.addAll(setB) | All elements from both | O(n) where n = size of setB |
| Intersection (A ∩ B) | copy.retainAll(setB) | Only elements in both | O(n * m) in worst case |
| Difference (A - B) | copy.removeAll(setB) | In A but not in B | O(n) where n = size of setB |
| Subset (A ⊆ B) | setB.containsAll(setA) | true/false | O(n) where n = size of setA |

> **WARNING:** Always work on a COPY! `addAll`/`retainAll`/`removeAll` modify the set IN PLACE.

### Set Comparison Table

| Feature | HashSet | LinkedHashSet | TreeSet |
|---|---|---|---|
| Backed by | HashMap | LinkedHashMap | TreeMap |
| Order | None | Insertion order | Sorted |
| Null elements | 1 allowed | 1 allowed | NOT allowed |
| add/remove/contains | O(1) | O(1) | O(log n) |
| Iteration | O(capacity + n) | O(n) | O(n) |
| Range operations | No | No | Yes |
| Use when | Fast unique check, no order | Unique + insertion order | Unique + sorted + ranges |

---

## Collections Utility Class

`java.util.Collections` is a utility class with static methods for common collection operations. It is distinct from the `Collection` interface.

### Sorting and Ordering

```java
List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9));
Collections.sort(list);                              // O(n log n) ascending
Collections.sort(list, Collections.reverseOrder());  // descending
Collections.sort(list, (a, b) -> b - a);             // custom Comparator
Collections.reverse(list);    // O(n) — reverse order in-place
Collections.shuffle(list);    // O(n) — random order
Collections.rotate(list, 2);  // O(n) — rotate right by 2
```

### Searching and Comparing

```java
Collections.sort(list); // Must sort first!
int idx = Collections.binarySearch(list, 5); // O(log n)
Collections.min(list);                        // O(n) — smallest element
Collections.max(list);                        // O(n) — largest element
Collections.frequency(list, 1);              // O(n) — count occurrences of 1
Collections.disjoint(list1, list2);          // true if no common elements
```

### Thread-Safe Wrappers

```java
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
Set<String>  syncSet  = Collections.synchronizedSet(new HashSet<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Note: Iteration still needs external synchronization:
synchronized (syncList) {
    for (String s : syncList) {
        System.out.println(s); // Must synchronize manually!
    }
}
// Better alternative: ConcurrentHashMap, CopyOnWriteArrayList
```

### Unmodifiable Wrappers

```java
List<String> unmodList = Collections.unmodifiableList(list);
Set<String>  unmodSet  = Collections.unmodifiableSet(set);
Map<String, Integer> unmodMap = Collections.unmodifiableMap(map);
unmodList.add("x"); // UnsupportedOperationException!

// Java 9+ cleaner alternative:
List<String> immutable    = List.of("a", "b", "c");
Set<String>  immutableSet = Set.of("a", "b", "c");
Map<String, String> immutableMap = Map.of("key", "value");
```

### Other Useful Methods

| Method | Complexity | Description |
|---|---|---|
| Collections.nCopies(n, obj) | O(1) | Returns immutable List with n copies of obj |
| Collections.singleton(obj) | O(1) | Returns immutable Set with single element |
| Collections.singletonList(obj) | O(1) | Returns immutable List with single element |
| Collections.emptyList() | O(1) | Returns immutable empty List |
| Collections.swap(list, i, j) | O(1) | Swaps elements at indices i and j |
| Collections.fill(list, obj) | O(n) | Replaces all elements with obj |
| Collections.copy(dest, src) | O(n) | Copies src into dest (dest must be >= src size) |
| Collections.addAll(coll, ...) | O(k) | Adds all specified elements to collection |

---

## Streams and Collections (Java 8+)

A stream is a pipeline of operations on a sequence of elements — it does not modify the underlying collection. Streams are **lazy**: intermediate operations (`filter`, `map`, `sorted`) are not executed until a terminal operation (`collect`, `forEach`, `count`) is triggered.

### Creating Streams

```java
List<String> list = Arrays.asList("banana", "apple", "cherry");

// From Collection:
Stream<String> s = list.stream();          // sequential
Stream<String> p = list.parallelStream();  // parallel (uses ForkJoinPool)

// From Array:
Stream<String> fromArray = Arrays.stream(array);

// From values directly:
Stream<String> fromValues = Stream.of("a", "b", "c");

// CRITICAL: Streams are SINGLE-USE!
Stream<String> stream = list.stream();
stream.count();  // OK
stream.count();  // IllegalStateException! Stream already closed!
// Always create a fresh stream for each terminal operation.
```

> **WARNING:** `parallelStream()` is NOT always faster! Avoid for: small collections (thread overhead > gain), operations with side effects (thread-unsafe), operations needing encounter order. Benchmark before using.

### Key Intermediate Operations

```java
list.stream()
    .filter(s -> s.length() > 4)                       // keep elements matching predicate
    .map(String::toUpperCase)                          // transform each element
    .sorted()                                          // natural order
    .sorted(Comparator.reverseOrder())                 // custom order
    .distinct()                                        // remove duplicates (uses equals)
    .limit(3)                                          // keep first 3
    .skip(2)                                           // skip first 2
    .flatMap(s -> Arrays.stream(s.split(",")))         // flatten nested
    .collect(Collectors.toList());
```

### Collectors — Converting Stream Back to Collection

```java
// Basic collectors:
List<String>       result = stream.collect(Collectors.toList());
Set<String>        unique = stream.collect(Collectors.toSet());
LinkedList<String> linked = stream.collect(Collectors.toCollection(LinkedList::new));
String joined             = stream.collect(Collectors.joining(", ")); // 'a, b, c'
long count                = stream.collect(Collectors.counting());

// Java 16+ cleaner alternative to Collectors.toList():
List<String> immutableResult = stream.toList(); // Unmodifiable list!

// toMap — CAREFUL: throws on duplicate keys!
Map<String, Integer> lengths = stream.collect(
    Collectors.toMap(
        s -> s,        // key: the string itself
        String::length // value: its length
    )
);

// toMap with merge function for duplicate keys:
Map<String, Long> wordCount = stream.collect(
    Collectors.toMap(
        s -> s,     // key
        s -> 1L,    // initial value
        Long::sum   // merge: sum on duplicate key
    )
);
```

### groupingBy — The Most Important Collector

`groupingBy` partitions stream elements into a Map by a classifier function. It is the stream equivalent of a SQL `GROUP BY` clause.

```java
List<String> words = Arrays.asList("eat", "tea", "tan", "ate", "nat", "bat");

// GROUP BY word length:
Map<Integer, List<String>> byLength = words.stream()
    .collect(Collectors.groupingBy(String::length));
// {3=[eat, tea, tan, ate, nat, bat]}

// GROUP BY first letter, count each group:
Map<Character, Long> byFirstLetter = words.stream()
    .collect(Collectors.groupingBy(
        s -> s.charAt(0),      // classifier
        Collectors.counting()  // downstream collector
    ));

// GROUP anagrams (classic LeetCode problem):
Map<String, List<String>> anagrams = words.stream()
    .collect(Collectors.groupingBy(w -> {
        char[] c = w.toCharArray();
        Arrays.sort(c);
        return new String(c); // sorted chars = group key
    }));
// {aet=[eat, tea, ate], ant=[tan, nat], abt=[bat]}
```

### Terminal Operations

```java
// Reduction:
Optional<String> first  = stream.findFirst();
Optional<String> any    = stream.findAny();   // faster in parallel
Optional<String> minVal = stream.min(Comparator.naturalOrder());
Optional<String> maxVal = stream.max(Comparator.naturalOrder());
long count              = stream.count();
boolean anyMatch        = stream.anyMatch(s -> s.length() > 3);
boolean allMatch        = stream.allMatch(s -> s.length() > 0);
boolean noneMatch       = stream.noneMatch(String::isEmpty);

// forEach vs forEachOrdered:
stream.forEach(System.out::println);         // any order (parallel ok)
stream.forEachOrdered(System.out::println);  // insertion order guaranteed

// reduce — manual accumulation:
int sum = IntStream.of(1, 2, 3, 4, 5).reduce(0, Integer::sum); // 15
```

| Collector | Returns | Common Use Case |
|---|---|---|
| toList() | List<T> | Default — most used |
| toSet() | Set<T> | Deduplicate results |
| toMap(kFn, vFn) | Map<K,V> | Build lookup map from stream |
| groupingBy(fn) | Map<K, List<T>> | Group elements by category |
| groupingBy(fn, downstream) | Map<K, R> | Group and then aggregate |
| counting() | Long | Count elements per group |
| joining(delim) | String | Concatenate strings with separator |
| partitioningBy(pred) | Map<Boolean, List<T>> | Split into true/false groups |

---

## Thread-Safe Collections

When multiple threads access a collection concurrently, standard collections (ArrayList, HashMap, HashSet) will produce unpredictable results or throw `ConcurrentModificationException`. Java provides several thread-safe alternatives.

### The Problem with Synchronized Wrappers

`Collections.synchronizedList/Map/Set` wrap every method with a `synchronized` block, using the collection itself as the lock. Only one thread can access it at a time — reads and writes are all serialized.

```java
List<String> syncList = Collections.synchronizedList(new ArrayList<>());

// Single operations are thread-safe:
syncList.add("a");  // OK — synchronized
syncList.get(0);    // OK — synchronized

// BUT iteration is NOT safe — must synchronize manually!
synchronized (syncList) {           // must lock the list
    for (String s : syncList) {     // now safe
        System.out.println(s);
    }
}
// If you forget the synchronized block -> ConcurrentModificationException!
```

### ConcurrentHashMap

ConcurrentHashMap is the go-to thread-safe Map. Instead of locking the entire map, it only locks the specific bucket being modified. Reads are completely lock-free.

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// All operations are thread-safe WITHOUT external synchronization:
map.put("a", 1);
map.get("a");

// Atomic compound operations (critical for correctness):
map.putIfAbsent("a", 1);           // atomic check-then-put
map.computeIfAbsent("a", k -> 0);  // atomic init on first access
map.merge("a", 1, Integer::sum);   // atomic increment

// Iteration is safe — weakly consistent (may not see latest writes):
for (Map.Entry<String, Integer> e : map.entrySet()) { } // no CME!
```

| Aspect | synchronizedMap | ConcurrentHashMap |
|---|---|---|
| Locking (Java 7) | Entire map (one lock) | 16 segments (16 locks) |
| Locking (Java 8+) | Entire map (one lock) | Node-level CAS — effectively one lock per bucket |
| Read throughput | Low — every read blocks | High — reads are lock-free |
| Write throughput | Low — serialized | High — only one bucket locked per write |
| Null keys/values | Allowed (same as HashMap) | NOT allowed — NullPointerException |
| Iteration | Needs external sync block | Safe, weakly consistent |
| Use when | Legacy, simple cases | High-concurrency production code |

> **WARNING:** ConcurrentHashMap does NOT allow null keys or null values. HashMap allows one null key. Classic interview trap!

### CopyOnWriteArrayList

CopyOnWriteArrayList handles thread safety by creating a fresh copy of the entire backing array on every write. Reads require no locking at all. Perfect for read-heavy scenarios like event listeners.

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

// WRITES: create a new copy of the entire array each time
list.add("a"); // O(n) — copies all n existing elements to new array!

// READS: lock-free, read the current snapshot
list.get(0);   // O(1) — no lock needed

// ITERATION: always safe — iterates over the snapshot at iteration start
for (String s : list) { // No ConcurrentModificationException, ever!
    // Other threads can modify list here — iterator won't see changes
    // but won't throw either
}
```

| Aspect | ArrayList | CopyOnWriteArrayList |
|---|---|---|
| Read | O(1), not thread-safe | O(1), fully thread-safe, no lock |
| Write | O(1) amortized, not thread-safe | O(n) — copies entire array! |
| Iteration | Fail-fast (CME on modification) | Snapshot — never throws CME |
| Use when | Single-threaded, high write rate | Multi-threaded, read-heavy, rare writes |
| Example | General purpose | Event listeners, config lists |

### BlockingQueue — Producer-Consumer Pattern

BlockingQueue extends Queue with blocking operations: `put()` blocks when full, `take()` blocks when empty.

```java
BlockingQueue<String> queue = new ArrayBlockingQueue<>(10); // capacity 10

// Producer thread:
queue.put("task");    // blocks if queue is full (waits for space)
queue.offer("task");  // returns false if full (non-blocking)

// Consumer thread:
String task1 = queue.take();    // blocks if queue is empty (waits for item)
String task2 = queue.poll();    // returns null if empty (non-blocking)

// Classic producer-consumer setup:
BlockingQueue<Integer> buffer = new LinkedBlockingQueue<>(100);
Thread producer = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        buffer.put(i); // blocks when full
    }
});
Thread consumer = new Thread(() -> {
    while (true) {
        process(buffer.take()); // blocks when empty
    }
});
```

| Implementation | Backed By | Key Feature |
|---|---|---|
| ArrayBlockingQueue | Array (fixed capacity) | Bounded — blocks producer when full |
| LinkedBlockingQueue | LinkedList (optional bound) | Unbounded by default, high throughput |
| PriorityBlockingQueue | Heap | Blocking + priority ordering |
| SynchronousQueue | None (no storage) | Direct handoff — producer waits for consumer |

### ConcurrentLinkedQueue and ConcurrentLinkedDeque

Both are lock-free, unbounded, thread-safe collections using atomic CAS (compare-and-swap) operations instead of locks — threads never block waiting for each other.

**ConcurrentLinkedQueue** is a thread-safe FIFO queue. It's unbounded, never blocks (`offer` always succeeds, `poll` returns null when empty), doesn't allow null elements, and has weakly consistent iteration.

**ConcurrentLinkedDeque** is the same idea but double-ended.

**When to use vs BlockingQueue:**

| Use | Recommendation |
|---|---|
| Need backpressure (producer should wait when buffer is full) | BlockingQueue (ArrayBlockingQueue) |
| Need raw throughput with no blocking and unbounded size | ConcurrentLinkedQueue |
| Same, but with both-ends access | ConcurrentLinkedDeque |

**Interview takeaways:**
- Lock-free doesn't mean no cost. CAS operations can retry under contention, but they never block a thread.
- No backpressure. If producers outpace consumers, a ConcurrentLinkedQueue grows unbounded — can cause memory issues. Use BlockingQueue when you need a size limit.
- Null elements not allowed in any concurrent queue or deque — null is reserved as the "empty" signal for `poll` and `peek`.
- Iteration is weakly consistent — iterators won't throw CME but may miss or duplicate elements added during iteration.

---

## Generics and PECS

Generics allow collections to enforce type safety at compile time. The PECS principle (Producer Extends, Consumer Super) governs when to use upper-bounded vs lower-bounded wildcards.

### Why Generics Exist

```java
// Pre-Java 5 — dangerous:
List list = new ArrayList();
list.add("hello");
list.add(42);                    // compiles fine — mixed types allowed
String s = (String) list.get(1); // ClassCastException at RUNTIME!

// Java 5+ with generics — safe:
List<String> typedList = new ArrayList<>();
typedList.add("hello");
typedList.add(42);               // compile error — caught early!
String safe = typedList.get(0);  // no cast needed
```

### Wildcards

**Unbounded: `List<?>`** — "A List of some unknown type."

```java
void printAll(List<?> list) {
    for (Object o : list) {
        System.out.println(o); // can only read as Object
    }
    // list.add("anything"); // COMPILE ERROR — can't add, type is unknown
}
```

You can call this with `List<String>`, `List<Integer>`, anything. But you can only read from it (as Object), not write.

**Upper bounded: `List<? extends Number>`** — "A List of Number or some subtype."

```java
double sum(List<? extends Number> list) {
    return list.stream().mapToDouble(Number::doubleValue).sum(); // safe to read
    // list.add(1.5); // COMPILE ERROR — we don't know which subtype
}
```

Accepts `List<Integer>`, `List<Double>`, `List<Number>`. You can read elements as Number, but you cannot add — the compiler doesn't know whether the actual list is of Integer, Double, or something else.

**Lower bounded: `List<? super Integer>`** — "A List of Integer or some supertype."

```java
void addNumbers(List<? super Integer> list) {
    list.add(1);   // safe — Integer fits in any supertype list
    list.add(2);
    // Integer x = list.get(0); // COMPILE ERROR — can only read as Object
}
```

Accepts `List<Integer>`, `List<Number>`, `List<Object>`. You can safely add Integers, but you can only read elements as Object.

### PECS — Producer Extends, Consumer Super

Pick your wildcard based on what the collection does in your method:

- Collection **produces** values for you (you read from it) → `? extends T`
- Collection **consumes** values from you (you write to it) → `? super T`
- You do both → use a concrete type, no wildcard

**Why this works:**

- **Producer (extends) is read-only:** you don't know the exact subtype, so you can't safely add. But reading is safe — whatever you pull out IS-A T.
- **Consumer (super) is write-only (for T):** you don't know the exact supertype, so reading as T isn't safe. But writing is safe — a T fits in any list of supertypes.

**The canonical example — `Collections.copy`:**

```java
// Real-world example — Collections.copy signature:
// public static <T> void copy(List<? super T> dest, List<? extends T> src)
//   dest CONSUMES T values -> super
//   src  PRODUCES T values -> extends
```

| Wildcard | Name | Can Read As | Can Write | Use When |
|---|---|---|---|---|
| List<T> | Concrete type | T | Yes, T only | Reading AND writing (most common) |
| List<?> | Unbounded | Object only | No (only null) | Only need to iterate, don't care about type |
| List<? extends T> | Upper bounded (PRODUCER) | T | No | Reading from a list (collection produces values) |
| List<? super T> | Lower bounded (CONSUMER) | Object only | Yes, T and subtypes | Writing to a list (collection consumes values) |

> **Memory trick:** PECS = Producer Extends, Consumer Super. The one who GIVES you data = extends. The one who TAKES data from you = super.

---

## Quick Reference

### Master Complexity Table

| Structure | Access | Add | Remove | Search | Space |
|---|---|---|---|---|---|
| Array | O(1) | O(n) / O(1) end | O(n) | O(n) / O(log n)* | O(n) |
| ArrayList | O(1) | O(1) amortized end | O(n) | O(n) | O(n) |
| LinkedList | O(n) | O(1) ends | O(1) ends | O(n) | O(n) |
| ArrayDeque | N/A | O(1) both ends | O(1) both ends | O(n) | O(n) |
| PriorityQueue | O(1) peek | O(log n) | O(log n) | O(n) | O(n) |
| HashMap | O(1) | O(1) | O(1) | O(1) key / O(n) val | O(n) |
| LinkedHashMap | O(1) | O(1) | O(1) | O(1) key / O(n) val | O(n) |
| TreeMap | O(log n) | O(log n) | O(log n) | O(log n) | O(n) |
| HashSet | N/A | O(1) | O(1) | O(1) | O(n) |
| LinkedHashSet | N/A | O(1) | O(1) | O(1) | O(n) |
| TreeSet | N/A | O(log n) | O(log n) | O(log n) | O(n) |

*Array search: O(n) unsorted, O(log n) if sorted and using binary search.

### Decision Guide

| Scenario | Best Choice | Key Reason |
|---|---|---|
| Random access by index | ArrayList | O(1) get(index) |
| Frequent add/remove at beginning | LinkedList or ArrayDeque | O(1) addFirst/removeFirst |
| FIFO Queue | ArrayDeque | Cache-friendly, no node overhead |
| LIFO Stack | ArrayDeque | Better than Stack class (no sync, better design) |
| Operations at both ends (deque) | ArrayDeque | O(1) both ends via circular array |
| Always get min or max element | PriorityQueue | O(1) peek, O(log n) poll |
| Fast key-value lookup | HashMap | O(1) average get/put |
| Key-value + insertion order | LinkedHashMap | O(1) + predictable iteration |
| Key-value + sorted keys | TreeMap | O(log n) + navigation methods |
| Range queries on sorted data | TreeMap / TreeSet | headMap/tailMap/subMap/subSet |
| LRU Cache | LinkedHashMap (accessOrder=true) | Built-in access ordering |
| Unique elements, fast lookup | HashSet | O(1) contains/add |
| Unique elements + insertion order | LinkedHashSet | O(1) + predictable iteration |
| Unique elements + sorted | TreeSet | O(log n) + range operations |
| Remove duplicates from List | HashSet | Add all — duplicates dropped automatically |
| Thread-safe List | CopyOnWriteArrayList | Good for read-heavy scenarios |
| Thread-safe Map | ConcurrentHashMap | Better than synchronizedMap — node-level locking |
| Thread-safe sorted Map | ConcurrentSkipListMap | O(log n), fully concurrent, sorted |
| Thread-safe Set | CopyOnWriteArraySet or Collections.synchronizedSet | COW for read-heavy; synchronizedSet for write-heavy |

### Common Interview Traps

| Trap | Wrong Assumption | Correct Answer |
|---|---|---|
| HashMap.containsValue() | O(1) like containsKey() | O(n) — values NOT hashed! Must scan all buckets. |
| Override equals() without hashCode() | HashSet/HashMap work correctly | BROKEN! Equal objects land in different buckets — duplicates allowed. |
| Override hashCode() without equals() | HashSet prevents duplicates | BROKEN! Same bucket but reference equals() — all 'different' — duplicates stored. |
| TreeMap/TreeSet with null key | Works like HashMap (one null allowed) | NullPointerException! Cannot compare null for sorting. |
| ArrayList.add(0, e) in loop | O(n) total | O(n²)! Each add(0) shifts all elements. n calls × O(n) = O(n²). |
| LinkedList.get(i) in for loop | O(n) total | O(n²)! Each get traverses list. Use iterator or for-each! |
| Stack class for stack ops | Standard Java practice | Legacy & synchronized! Use ArrayDeque instead. |
| HashMap.put() return value | Returns new value | Returns OLD value (null if new key). Classic mistake! |
| headMap("X") includes X | Inclusive upper bound | STRICT — excludes X! Use headMap("X", true) for inclusive. |
| ArrayDeque accepts null | Same as LinkedList | NullPointerException! ArrayDeque prohibits null elements. |
| Arrays.asList() is mutable | Can add/remove elements | FIXED SIZE! add/remove throw UnsupportedOperationException. |
| arr.equals(arr2) compares content | Same as Arrays.equals() | Compares REFERENCES — always false for different arrays! |
| Map is a Collection | Implements Collection interface | Map has its own hierarchy — NOT a Collection! |
| Modifying subList modifies original | subList is a copy | subList is a VIEW — modifications propagate to original! |
| remove(1) on List<Integer> | Removes element with value 1 | Removes element at INDEX 1! Use remove(Integer.valueOf(1)) for value. |
| ConcurrentHashMap with null | Allows null like HashMap | NullPointerException! Neither null keys NOR null values allowed. |
| Stream used twice | Can reuse a stream | IllegalStateException! Streams are single-use. |
| Iterator.next() without hasNext() | Returns null when empty | Throws NoSuchElementException! Always call hasNext() first. |
