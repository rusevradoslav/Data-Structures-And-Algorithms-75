package org.example.f_stack;

import java.util.ArrayDeque;
import java.util.Deque;


/**
 * Simulates collisions between moving asteroids and returns the final state.
 *
 * <p>Each asteroid is an integer: positive moves right, negative moves left.
 * When a right-moving asteroid meets a left-moving one, the smaller explodes.
 * If they are equal size, both explode. Asteroids moving in the same direction
 * never collide.
 *
 * <p>The algorithm uses a stack approach in stages:
 * <ul>
 *   <li>Stage 1 — Initialize: Keep a stack of surviving asteroids in left-to-right order.</li>
 *   <li>Stage 2 — Detect: A collision is only possible when the stack top is moving right
 *       (positive) and the incoming asteroid is moving left (negative).</li>
 *   <li>Stage 3 — Resolve: While a collision is possible, pop smaller right-moving asteroids.
 *       If sizes are equal, both explode and the incoming asteroid stops.</li>
 *   <li>Stage 4 — Commit: If the incoming asteroid survives all collisions, push it onto the stack.</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * asteroids = [10, 2, -5]
 * stack: 10, 2 -> -5 collides with 2 (2 explodes) -> -5 collides with 10 (5 explodes)
 * result = [10]
 * </pre>
 *
 * <p>Key insight: collisions only happen at the boundary between a right-moving asteroid
 * already in the stack and a left-moving incoming asteroid, so a stack is enough to
 * capture all interactions.
 *
 * <p>Time Complexity:
 * O(n), where n is the number of asteroids. Each asteroid is pushed and popped at most once.
 *
 * <p>Space Complexity:
 * O(n) for the stack in the worst case (no collisions).
 */
public class AsteroidCollision {
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (Integer incomingAsteroid : asteroids) {
            if (stack.isEmpty()) {
                stack.add(incomingAsteroid);
                continue;
            }

            if (stack.peekLast() > 0 && incomingAsteroid < 0) {
                while (!stack.isEmpty() && stack.peekLast() > 0 && stack.peekLast() < Math.abs(incomingAsteroid)) {
                    stack.pollLast();
                }

                if (!stack.isEmpty() && stack.peekLast() > 0 && Math.abs(incomingAsteroid) == Math.abs(stack.peekLast())) {
                    stack.pollLast();
                    continue;
                }

                if (!stack.isEmpty() && stack.peekLast() > 0) {
                    continue;
                }
            }
            stack.add(incomingAsteroid);
        }

        return stack.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
