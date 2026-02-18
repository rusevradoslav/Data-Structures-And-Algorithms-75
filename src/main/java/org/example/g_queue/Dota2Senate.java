package org.example.g_queue;

import java.util.ArrayDeque;

/**
 * Predicts which party will win the Dota2 senate voting procedure.
 *
 * <p>Senators from two parties (Radiant {@code 'R'} and Dire {@code 'D'}) vote in rounds,
 * left to right. Each senator can ban one opponent or announce victory if only their party remains.
 * Every senator plays optimally — banning the nearest upcoming opponent.
 *
 * <p>Approach (Two Queues with Indices):
 * <ul>
 *   <li>Store each senator's index in a party-specific queue</li>
 *   <li>Compare the fronts of both queues — the lower index acts first and bans the other</li>
 *   <li>The winner re-enters their queue with {@code index + n} to represent the next round</li>
 *   <li>Repeat until one queue is empty</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * senate = "RDD"
 * radiantQueue: [0], direQueue: [1, 2]
 *
 * R(0) vs D(1) → 0 &lt; 1, R wins → radiantQueue: [0+3=3], direQueue: [2]
 * R(3) vs D(2) → 3 &gt; 2, D wins → radiantQueue: [], direQueue: [2+3=5]
 * radiantQueue empty → "Dire"
 * </pre>
 *
 * <p>Key insight: the {@code + n} offset preserves round ordering without actually
 * iterating through the string again. A senator with index 0 in round 2 becomes index {@code n},
 * which is correctly compared against opponents still queued from round 1.
 *
 * <p>Time Complexity: O(n) — each senator is enqueued and dequeued at most twice (once initially,
 * once as a winner). Each comparison is O(1).
 *
 * <p>Space Complexity: O(n) — both queues together hold all n senators.
 */
public class Dota2Senate {

    public String predictPartyVictory(String senate) {
        ArrayDeque<Integer> radiantQueue = new ArrayDeque<>();
        ArrayDeque<Integer> direQueue = new ArrayDeque<>();

        char[] senateCharacters = senate.toCharArray();
        int numberOfSenates = senateCharacters.length;
        for (int i = 0; i < senateCharacters.length; i++) {
            if (senateCharacters[i] == 'R') {
                radiantQueue.offer(i);
            } else {
                direQueue.offer(i);
            }
        }

        while (!radiantQueue.isEmpty() && !direQueue.isEmpty()) {
            Integer radiant = radiantQueue.poll();
            Integer dire = direQueue.poll();
            if (radiant < dire) {
                radiantQueue.offer(radiant + numberOfSenates);
            } else {
                direQueue.offer(dire + numberOfSenates);
            }
        }

        return radiantQueue.isEmpty() ? "Dire" : "Radiant";
    }
}
