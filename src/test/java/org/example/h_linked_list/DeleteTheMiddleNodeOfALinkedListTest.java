package org.example.h_linked_list;

import org.example.h_linked_list.DeleteTheMiddleNodeOfALinkedList.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeleteTheMiddleNodeOfALinkedListTest {

    private DeleteTheMiddleNodeOfALinkedList solution;

    @BeforeEach
    void setUp() {
        solution = new DeleteTheMiddleNodeOfALinkedList();
    }

    @Test
    @DisplayName("Example 1: [1,3,4,7,1,2,6] -> [1,3,4,1,2,6]")
    void example1_oddLength() {
        ListNode head = buildList(1, 3, 4, 7, 1, 2, 6);
        assertListEquals(List.of(1, 3, 4, 1, 2, 6), solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("Example 2: [1,2,3,4] -> [1,2,4]")
    void example2_evenLength() {
        ListNode head = buildList(1, 2, 3, 4);
        assertListEquals(List.of(1, 2, 4), solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("Example 3: [2,1] -> [2]")
    void example3_twoNodes() {
        ListNode head = buildList(2, 1);
        assertListEquals(List.of(2), solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("Single node -> empty list")
    void singleNode_returnsNull() {
        ListNode head = buildList(1);
        assertNull(solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("Three nodes -> removes middle")
    void threeNodes_removesMiddle() {
        ListNode head = buildList(1, 2, 3);
        assertListEquals(List.of(1, 3), solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("Five nodes -> removes index 2")
    void fiveNodes_removesMiddle() {
        ListNode head = buildList(10, 20, 30, 40, 50);
        assertListEquals(List.of(10, 20, 40, 50), solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("Six nodes -> removes index 3")
    void sixNodes_removesMiddle() {
        ListNode head = buildList(1, 2, 3, 4, 5, 6);
        assertListEquals(List.of(1, 2, 3, 5, 6), solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("All same values -> removes middle occurrence")
    void allSameValues() {
        ListNode head = buildList(5, 5, 5, 5, 5);
        assertListEquals(List.of(5, 5, 5, 5), solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("Two nodes with same value -> removes second")
    void twoNodesSameValue() {
        ListNode head = buildList(7, 7);
        assertListEquals(List.of(7), solution.deleteMiddle(head));
    }

    @Test
    @DisplayName("Large values")
    void largeValues() {
        ListNode head = buildList(100000, 99999, 100000);
        assertListEquals(List.of(100000, 100000), solution.deleteMiddle(head));
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
