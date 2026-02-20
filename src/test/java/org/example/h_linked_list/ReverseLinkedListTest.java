package org.example.h_linked_list;

import org.example.h_linked_list.ReverseLinkedList.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReverseLinkedListTest {

    private ReverseLinkedList solution;

    @BeforeEach
    void setUp() {
        solution = new ReverseLinkedList();
    }

    @Test
    @DisplayName("Example 1: [1,2,3,4,5] -> [5,4,3,2,1]")
    void example1_fiveNodes() {
        assertListEquals(List.of(5, 4, 3, 2, 1), solution.reverseList(buildList(1, 2, 3, 4, 5)));
        assertListEquals(List.of(5, 4, 3, 2, 1), solution.reverseListOptimised(buildList(1, 2, 3, 4, 5)));
    }

    @Test
    @DisplayName("Example 2: [1,2] -> [2,1]")
    void example2_twoNodes() {
        assertListEquals(List.of(2, 1), solution.reverseList(buildList(1, 2)));
        assertListEquals(List.of(2, 1), solution.reverseListOptimised(buildList(1, 2)));
    }

    @Test
    @DisplayName("Example 3: empty list -> null")
    void example3_emptyList() {
        assertNull(solution.reverseList(null));
        assertNull(solution.reverseListOptimised(null));
    }

    @Test
    @DisplayName("Single node -> same node")
    void singleNode() {
        assertListEquals(List.of(1), solution.reverseList(buildList(1)));
        assertListEquals(List.of(1), solution.reverseListOptimised(buildList(1)));
    }

    @Test
    @DisplayName("Three nodes -> [3,2,1]")
    void threeNodes() {
        assertListEquals(List.of(3, 2, 1), solution.reverseList(buildList(1, 2, 3)));
        assertListEquals(List.of(3, 2, 1), solution.reverseListOptimised(buildList(1, 2, 3)));
    }

    @Test
    @DisplayName("Four nodes -> [4,3,2,1]")
    void fourNodes() {
        assertListEquals(List.of(4, 3, 2, 1), solution.reverseList(buildList(1, 2, 3, 4)));
        assertListEquals(List.of(4, 3, 2, 1), solution.reverseListOptimised(buildList(1, 2, 3, 4)));
    }

    @Test
    @DisplayName("Negative values -> [-3,-2,-1]")
    void negativeValues() {
        assertListEquals(List.of(-3, -2, -1), solution.reverseList(buildList(-1, -2, -3)));
        assertListEquals(List.of(-3, -2, -1), solution.reverseListOptimised(buildList(-1, -2, -3)));
    }

    @Test
    @DisplayName("All same values -> [1,1,1]")
    void duplicateValues() {
        assertListEquals(List.of(1, 1, 1), solution.reverseList(buildList(1, 1, 1)));
        assertListEquals(List.of(1, 1, 1), solution.reverseListOptimised(buildList(1, 1, 1)));
    }

    @Test
    @DisplayName("Two same nodes -> [5,5]")
    void twoSameNodes() {
        assertListEquals(List.of(5, 5), solution.reverseList(buildList(5, 5)));
        assertListEquals(List.of(5, 5), solution.reverseListOptimised(buildList(5, 5)));
    }

    @Test
    @DisplayName("Boundary values -> [-5000,5000]")
    void boundaryValues() {
        assertListEquals(List.of(-5000, 5000), solution.reverseList(buildList(5000, -5000)));
        assertListEquals(List.of(-5000, 5000), solution.reverseListOptimised(buildList(5000, -5000)));
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
