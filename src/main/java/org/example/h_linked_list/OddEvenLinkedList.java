package org.example.h_linked_list;

import java.util.Objects;

/**
 * Groups all odd-indexed nodes together followed by all even-indexed nodes in a singly-linked list.
 *
 * <p>The first node is considered odd (index 1), the second even (index 2), and so on.
 * The relative order within each group must be preserved.
 *
 * <p>Approach (Two Pointers — Odd/Even Chains):
 * <ul>
 *   <li>Maintain two pointers: {@code odd} walks odd-indexed nodes, {@code even} walks even-indexed nodes</li>
 *   <li>Save {@code evenHead} to reconnect the even chain at the end</li>
 *   <li>In each iteration, rewire {@code odd.next} to skip the even node, and {@code even.next} to skip the odd node</li>
 *   <li>After the loop, connect the two chains: {@code odd.next = evenHead}</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * head = [1, 2, 3, 4, 5]
 *
 * odd=1, even=2, evenHead=2
 *
 * Iteration 1: odd: 1→3    even: 2→4    Odd chain: 1→3    Even chain: 2→4
 * Iteration 2: odd: 3→5    even: 4→null  Odd chain: 1→3→5  Even chain: 2→4
 *
 * Connect: 5→evenHead(2)
 * Result: [1, 3, 5, 2, 4]
 * </pre>
 *
 * <p>Key insight: saving {@code evenHead} before the loop is essential — without it,
 * we lose the reference to the start of the even chain after rewiring pointers.
 *
 * <p>Time Complexity: O(n) — single pass through the list.
 *
 * <p>Space Complexity: O(1) — only three pointer variables, no extra data structures.
 */
public class OddEvenLinkedList {
    public ListNode oddEvenList(ListNode head) {

        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while (Objects.nonNull(even) && Objects.nonNull(even.next)) {
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;

        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }
}
