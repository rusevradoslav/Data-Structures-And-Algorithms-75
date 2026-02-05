package org.example.g_queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Counts the number of recent requests within a {@code 3000} millisecond time window.
 *
 * <p>Each call to {@link #ping(int)} records a new request at time {@code t} and returns the
 * number of requests that occurred in the inclusive range {@code [t - 3000, t]}.
 *
 * <p>Approach:
 * <ul>
 *   <li>Maintain a FIFO queue of timestamps</li>
 *   <li>On each ping, add the new timestamp, then evict all timestamps older than {@code t - 3000}</li>
 *   <li>The queue size is the answer</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * ping(1)    -> [1]             -> 1
 * ping(100)  -> [1, 100]        -> 2
 * ping(3001) -> [1, 100, 3001]  -> 3
 * ping(3002) -> [100, 3001, 3002] -> 3
 * </pre>
 *
 * <p>Key insight: since {@code t} is strictly increasing, the queue is always sorted,
 * so expired timestamps are always at the front and can be removed with a simple while loop.
 *
 * <p>Time Complexity: O(1) amortized per ping — each timestamp is enqueued and dequeued at most once.
 *
 * <p>Space Complexity: O(n) — where n is the number of pings in the current 3000ms window.
 */
class RecentCounter {

    private final Deque<Integer> queue;

    public RecentCounter() {
        queue = new ArrayDeque<>();
    }

    public int ping(int t) {
        queue.offer(t);
        while (!queue.isEmpty() && queue.peek() < t - 3000) {
            queue.poll();
        }
        return queue.size();
    }
}