package org.example.g_queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecentCounterTest {

    private RecentCounter counter;

    @BeforeEach
    void setUp() {
        counter = new RecentCounter();
    }

    @Test
    @DisplayName("Example 1: [1], [100], [3001], [3002] -> 1, 2, 3, 3")
    void example1_leetcodeExample() {
        assertEquals(1, counter.ping(1));
        assertEquals(2, counter.ping(100));
        assertEquals(3, counter.ping(3001));
        assertEquals(3, counter.ping(3002));
    }

    @Test
    @DisplayName("Single ping returns 1")
    void singlePing_returnsOne() {
        assertEquals(1, counter.ping(1));
    }

    @Test
    @DisplayName("Two pings within 3000ms range")
    void twoPingsWithinRange() {
        assertEquals(1, counter.ping(1));
        assertEquals(2, counter.ping(3000));
    }

    @Test
    @DisplayName("Second ping exactly at boundary evicts first")
    void pingAtExactBoundary_evictsFirst() {
        assertEquals(1, counter.ping(1));
        assertEquals(1, counter.ping(3002));
    }

    @Test
    @DisplayName("Multiple pings all within range")
    void multiplePingsAllWithinRange() {
        assertEquals(1, counter.ping(100));
        assertEquals(2, counter.ping(200));
        assertEquals(3, counter.ping(300));
        assertEquals(4, counter.ping(400));
        assertEquals(5, counter.ping(500));
    }

    @Test
    @DisplayName("Large gap evicts all previous pings")
    void largeGap_evictsAllPrevious() {
        assertEquals(1, counter.ping(1));
        assertEquals(2, counter.ping(100));
        assertEquals(3, counter.ping(200));
        assertEquals(1, counter.ping(10000));
    }

    @Test
    @DisplayName("Gradual eviction as time progresses")
    void gradualEviction() {
        assertEquals(1, counter.ping(1));
        assertEquals(2, counter.ping(1000));
        assertEquals(3, counter.ping(2000));
        assertEquals(4, counter.ping(3000));
        assertEquals(5, counter.ping(3001));
        assertEquals(5, counter.ping(4000));
    }

    @Test
    @DisplayName("Large timestamps work correctly")
    void largeTimestamps() {
        assertEquals(1, counter.ping(1000000000));
        assertEquals(2, counter.ping(1000000001));
        assertEquals(2, counter.ping(1000003001));
    }
}
