package org.example.h_linked_list;

import org.example.h_linked_list.MaximumTwinSumOfALinkedList.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximumTwinSumOfALinkedListTest {

    private MaximumTwinSumOfALinkedList solution;

    @BeforeEach
    void setUp() {
        solution = new MaximumTwinSumOfALinkedList();
    }

    @Test
    @DisplayName("Example 1: [5,4,2,1] -> 6")
    void example1() {
        assertEquals(6, solution.pairSum(buildList(5, 4, 2, 1)));
        assertEquals(6, solution.pairSumOptimised(buildList(5, 4, 2, 1)));
    }

    @Test
    @DisplayName("Example 2: [4,2,2,3] -> 7")
    void example2() {
        assertEquals(7, solution.pairSum(buildList(4, 2, 2, 3)));
        assertEquals(7, solution.pairSumOptimised(buildList(4, 2, 2, 3)));
    }

    @Test
    @DisplayName("Example 3: [1,100000] -> 100001")
    void example3_boundaryValues() {
        assertEquals(100001, solution.pairSum(buildList(1, 100000)));
        assertEquals(100001, solution.pairSumOptimised(buildList(1, 100000)));
    }

    @Test
    @DisplayName("Two nodes: [1,2] -> 3")
    void twoNodes() {
        assertEquals(3, solution.pairSum(buildList(1, 2)));
        assertEquals(3, solution.pairSumOptimised(buildList(1, 2)));
    }

    @Test
    @DisplayName("Four equal nodes: [5,5,5,5] -> 10")
    void fourEqualNodes() {
        assertEquals(10, solution.pairSum(buildList(5, 5, 5, 5)));
        assertEquals(10, solution.pairSumOptimised(buildList(5, 5, 5, 5)));
    }

    @Test
    @DisplayName("Max twin pair at edges: [9,1,1,2] -> 11")
    void maxAtEdges() {
        assertEquals(11, solution.pairSum(buildList(9, 1, 1, 2)));
        assertEquals(11, solution.pairSumOptimised(buildList(9, 1, 1, 2)));
    }

    @Test
    @DisplayName("Max twin pair at middle: [1,9,8,2] -> 17")
    void maxAtMiddle() {
        assertEquals(17, solution.pairSum(buildList(1, 9, 8, 2)));
        assertEquals(17, solution.pairSumOptimised(buildList(1, 9, 8, 2)));
    }

    @Test
    @DisplayName("Six nodes: [1,2,3,4,5,6] -> 7")
    void sixNodes() {
        assertEquals(7, solution.pairSum(buildList(1, 2, 3, 4, 5, 6)));
        assertEquals(7, solution.pairSumOptimised(buildList(1, 2, 3, 4, 5, 6)));
    }

    @Test
    @DisplayName("Asymmetric: [1,1,1,100] -> 101")
    void asymmetric() {
        assertEquals(101, solution.pairSum(buildList(1, 1, 1, 100)));
        assertEquals(101, solution.pairSumOptimised(buildList(1, 1, 1, 100)));
    }

    @Test
    @DisplayName("Large values: [100000,1,1,100000] -> 200000")
    void largeValues() {
        assertEquals(200000, solution.pairSum(buildList(100000, 1, 1, 100000)));
        assertEquals(200000, solution.pairSumOptimised(buildList(100000, 1, 1, 100000)));
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
}
