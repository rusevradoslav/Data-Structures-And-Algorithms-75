package org.example.h_linked_list;

import java.util.ArrayDeque;

/**
 * Reverses a singly-linked list and returns the new head.
 *
 * <p>Two solutions are provided:
 *
 * <p><b>Solution 1 — Deque ({@link #reverseList}):</b>
 * <ul>
 *   <li>First pass: push all values onto a deque (front insertion reverses order)</li>
 *   <li>Second pass: overwrite each node's value by polling from the deque</li>
 * </ul>
 *
 * <p><b>Solution 2 — Iterative pointer reversal ({@link #reverseListOptimised}):</b>
 * <ul>
 *   <li>Maintain three pointers: {@code prev}, {@code curr}, {@code next}</li>
 *   <li>At each step: save next, flip {@code curr.next} to point backwards, advance both pointers</li>
 *   <li>When {@code curr} reaches null, {@code prev} is the new head</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * head = [1, 2, 3, 4, 5]
 *
 * Step 1: prev=null, curr=1  →  1.next=null     null←1   2→3→4→5
 * Step 2: prev=1,    curr=2  →  2.next=1         null←1←2   3→4→5
 * Step 3: prev=2,    curr=3  →  3.next=2         null←1←2←3   4→5
 * Step 4: prev=3,    curr=4  →  4.next=3         null←1←2←3←4   5
 * Step 5: prev=4,    curr=5  →  5.next=4         null←1←2←3←4←5
 *
 * curr=null, return prev=5  →  [5, 4, 3, 2, 1]
 * </pre>
 *
 * <p>Key insight: you need to save {@code curr.next} before overwriting it,
 * otherwise you lose the reference to the rest of the list.
 *
 * <p>Time Complexity: O(n) for both solutions — each node visited once (or twice for the deque approach).
 *
 * <p>Space Complexity: O(n) for the deque solution, O(1) for the optimised pointer reversal.
 */
public class ReverseLinkedList {
    public ListNode reverseListOptimised(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;

        }

        return prev;
    }

    public ListNode reverseList(ListNode head) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        ListNode curr = head;
        while (curr != null) {
            deque.offerFirst(curr.val);
            curr = curr.next;
        }

        curr = head;
        while (curr != null) {
            curr.val = deque.poll();
            curr = curr.next;
        }

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
