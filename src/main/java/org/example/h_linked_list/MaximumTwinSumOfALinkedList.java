package org.example.h_linked_list;

import java.util.ArrayDeque;

/**
 * Finds the maximum twin sum of a linked list with even length.
 *
 * <p>The twin of node {@code i} is node {@code n - 1 - i} (0-indexed).
 * The twin sum is the sum of a node and its twin. We return the maximum
 * twin sum across all pairs.
 *
 * <p><b>Approach — Deque:</b>
 * <ul>
 *   <li>First pass: push all node values into a deque (preserves order)</li>
 *   <li>Second pass: simultaneously remove from both ends, computing the
 *       twin sum at each step and tracking the maximum</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * head = [5, 4, 2, 1]
 *
 * Pairs:  (5, 1) → 6
 *         (4, 2) → 6
 *
 * Maximum twin sum = 6
 * </pre>
 *
 * <p><b>Optimised approach — Slow/Fast + Reverse:</b>
 * <ol>
 *   <li>Use slow/fast pointers to find the middle of the list</li>
 *   <li>Reverse the second half in place</li>
 *   <li>Walk both halves simultaneously, tracking the maximum sum</li>
 * </ol>
 * This reduces space from O(n) to O(1).
 *
 * <p>Time Complexity: O(n) — each node visited once (deque) or twice (optimised).
 *
 * <p>Space Complexity: O(n) for the deque approach, O(1) for the optimised approach.
 */
public class MaximumTwinSumOfALinkedList {

    public int pairSum(ListNode head) {
        ArrayDeque<Integer> linkedList = new ArrayDeque<>();
        ListNode temp = head;
        while (temp != null) {
            linkedList.addLast(temp.val);
            temp = temp.next;
        }

        int midIndex = linkedList.size() / 2;

        int sum = 0;
        for (int i = 0; i < midIndex; i++) {
            Integer fValue = linkedList.removeFirst();
            Integer sValue = linkedList.removeLast();
            sum = Math.max(sum, fValue + sValue);
        }
        return sum;
    }

    public int pairSumOptimised(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode prev = null;
        ListNode curr = slow.next;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        int sum = 0;
        ListNode fPointer = head;
        ListNode sPointer = prev;
        while (fPointer != null && sPointer != null) {
            sum = Math.max(sum, fPointer.val + sPointer.val);
            fPointer = fPointer.next;
            sPointer = sPointer.next;
        }

        return sum;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
