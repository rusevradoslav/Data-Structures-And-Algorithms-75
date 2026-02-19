package org.example.h_linked_list;

import org.example.h_linked_list.OddEvenLinkedList.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OddEvenLinkedListTest {

    private OddEvenLinkedList solution;

    @BeforeEach
    void setUp() {
        solution = new OddEvenLinkedList();
    }

    @Test
    @DisplayName("Example 1: [1,2,3,4,5] -> [1,3,5,2,4]")
    void example1_oddLength() {
        ListNode head = buildList(1, 2, 3, 4, 5);
        assertListEquals(List.of(1, 3, 5, 2, 4), solution.oddEvenList(head));
    }

    @Test
    @DisplayName("Example 2: [2,1,3,5,6,4,7] -> [2,3,6,7,1,5,4]")
    void example2_evenLength() {
        ListNode head = buildList(2, 1, 3, 5, 6, 4, 7);
        assertListEquals(List.of(2, 3, 6, 7, 1, 5, 4), solution.oddEvenList(head));
    }

    @Test
    @DisplayName("Empty list -> null")
    void emptyList_returnsNull() {
        assertNull(solution.oddEvenList(null));
    }

    @Test
    @DisplayName("Two nodes -> same order")
    void twoNodes() {
        ListNode head = buildList(1, 2);
        assertListEquals(List.of(1, 2), solution.oddEvenList(head));
    }

    @Test
    @DisplayName("Three nodes -> [1,3,2]")
    void threeNodes() {
        ListNode head = buildList(1, 2, 3);
        assertListEquals(List.of(1, 3, 2), solution.oddEvenList(head));
    }

    @Test
    @DisplayName("Four nodes -> [1,3,2,4]")
    void fourNodes() {
        ListNode head = buildList(1, 2, 3, 4);
        assertListEquals(List.of(1, 3, 2, 4), solution.oddEvenList(head));
    }

    @Test
    @DisplayName("All same values -> order preserved")
    void allSameValues() {
        ListNode head = buildList(5, 5, 5, 5, 5);
        assertListEquals(List.of(5, 5, 5, 5, 5), solution.oddEvenList(head));
    }

    @Test
    @DisplayName("Negative values")
    void negativeValues() {
        ListNode head = buildList(-1, -2, -3, -4);
        assertListEquals(List.of(-1, -3, -2, -4), solution.oddEvenList(head));
    }

    @Test
    @DisplayName("Mixed positive and negative values")
    void mixedValues() {
        ListNode head = buildList(1, -2, 3, -4, 5);
        assertListEquals(List.of(1, 3, 5, -2, -4), solution.oddEvenList(head));
    }

    @Test
    @DisplayName("Test with one node")
    void oneNode() {
        ListNode head = buildList(1);
        assertListEquals(List.of(1), solution.oddEvenList(head));
    }

    private ListNode buildList(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }
        return dummy.next;
    }

    private void assertListEquals(List<Integer> expected, ListNode head) {
        List<Integer> actual = new ArrayList<>();
        while (head != null) {
            actual.add(head.val);
            head = head.next;
        }
        assertEquals(expected, actual);
    }
}
