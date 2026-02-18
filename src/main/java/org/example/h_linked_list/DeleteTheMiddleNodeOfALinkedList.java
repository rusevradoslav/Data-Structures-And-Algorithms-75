package org.example.h_linked_list;

/**
 * Deletes the middle node of a singly-linked list and returns the head of the modified list.
 *
 * <p>The middle node is at index {@code ⌊n/2⌋} (0-based) where {@code n} is the list length.
 * For a single-node list, deleting the middle returns {@code null}.
 *
 * <p>Approach (Two-Pass):
 * <ul>
 *   <li>First pass: traverse the entire list to count the number of nodes</li>
 *   <li>Compute the middle index as {@code count / 2}</li>
 *   <li>Second pass: walk to the node just before the middle and skip over it
 *       by rewiring {@code prev.next = prev.next.next}</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * head = [1, 3, 4, 7, 1, 2, 6]   (n=7, middle index = 3)
 *
 * Pass 1: count = 7, midIndex = 3
 * Pass 2: walk to index 2 (node with val 4)
 *          prev.next = prev.next.next  →  skip node 7
 * Result:  [1, 3, 4, 1, 2, 6]
 * </pre>
 *
 * <p>Key insight: we only need a reference to the node <em>before</em> the middle
 * to perform the deletion — no need for extra data structures.
 *
 * <p>Time Complexity: O(n) — two passes through the list.
 *
 * <p>Space Complexity: O(1) — only a few pointer variables.
 */
public class DeleteTheMiddleNodeOfALinkedList {

    public ListNode deleteMiddle(ListNode head) {
        if (head.next == null) {
            return null;
        }
        int counter = 1;
        ListNode node = head;
        while (node.next != null) {
            node = node.next;
            counter++;
        }

        int midIndex = counter / 2;
        ListNode prev = head;
        for (int i = 0; i < midIndex - 1; i++) {
            prev = prev.next;
        }
        prev.next = prev.next.next;

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
