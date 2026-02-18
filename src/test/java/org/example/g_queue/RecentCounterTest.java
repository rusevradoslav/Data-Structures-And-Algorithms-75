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

    @Test
    @DisplayName("Exact left boundary t-3000 is inclusive")
    void exactLeftBoundaryIsInclusive() {
        assertEquals(1, counter.ping(1000));
        assertEquals(2, counter.ping(4000));  // 4000 - 3000 = 1000, so 1000 survives
        assertEquals(2, counter.ping(4001));  // 4001 - 3000 = 1001, so 1000 is evicted
    }

    @Test
    @DisplayName("Many pings expire at once")
    void manyPingsExpireAtOnce() {
        assertEquals(1, counter.ping(1));
        assertEquals(2, counter.ping(2));
        assertEquals(3, counter.ping(3));
        assertEquals(4, counter.ping(4));
        assertEquals(5, counter.ping(5));
        assertEquals(1, counter.ping(5000)); // all 5 evicted, only 5000 remains
    }

    @Test
    @DisplayName("Window fills then partially shrinks")
    void windowFillsThenPartiallyShrinks() {
        assertEquals(1, counter.ping(100));
        assertEquals(2, counter.ping(200));
        assertEquals(3, counter.ping(300));
        assertEquals(4, counter.ping(400));
        assertEquals(4, counter.ping(3200)); // evicts 100, keeps 200, 300, 400
    }

    @Test
    @DisplayName("Rapid consecutive pings then gap")
    void rapidPingsThenGap() {
        assertEquals(1, counter.ping(1));
        assertEquals(2, counter.ping(2));
        assertEquals(3, counter.ping(3));
        assertEquals(4, counter.ping(3001)); // 3001 - 3000 = 1, keeps all
        assertEquals(4, counter.ping(3002)); // 3002 - 3000 = 2, evicts 1
        assertEquals(4, counter.ping(3003)); // 3003 - 3000 = 3, evicts 2
    }

    @Test
    @DisplayName("Evenly spaced pings maintain steady window size")
    void evenlySpacedPings() {
        assertEquals(1, counter.ping(1000));
        assertEquals(2, counter.ping(2000));
        assertEquals(3, counter.ping(3000));
        assertEquals(4, counter.ping(4000)); // evicts nothing, 1000 is at exact boundary
        assertEquals(4, counter.ping(5000)); // evicts 1000 (< 2000), keeps 2000
        assertEquals(4, counter.ping(6000)); // evicts 2000 (< 3000), keeps 3000
    }

    @Test
    @DisplayName("Fresh counter returns 1 for any first ping value")
    void freshCounterAnyStartValue() {
        assertEquals(1, counter.ping(50000));
    }
}