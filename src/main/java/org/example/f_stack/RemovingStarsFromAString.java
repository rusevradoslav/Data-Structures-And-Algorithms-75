package org.example.f_stack;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Removes stars from a string by deleting each star and the closest non-star
 * character to its left.
 *
 * <p>Solution 1 (Stack / ArrayDeque):
 * <ul>
 *   <li>Use an {@code ArrayDeque} as a stack with a last-in-first-out (LIFO) policy</li>
 *   <li>Push letters; on '*', pop the most recent character</li>
 *   <li>Build the final string by removing from the tail to restore left-to-right order</li>
 * </ul>
 *
 * <p>Solution 2 (StringBuilder as a stack):
 * <ul>
 *   <li>Append letters to a {@code StringBuilder}</li>
 *   <li>On '*', delete the last character</li>
 *   <li>Return the builder as the result</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * s = "leet**cod*e"
 * stack progression:
 *   l e e t -> '*' pops t -> '*' pops e -> c o d -> '*' pops d -> e
 * result = "lecoe"
 * </pre>
 *
 * <p>Key insight: each star only affects the closest character to its left, so a stack
 * captures exactly which character should be removed.
 *
 * <p>Time Complexity:
 * O(n) for both solutions, where n is the length of the string.
 *
 * <p>Space Complexity:
 * O(n) for both solutions (stack or StringBuilder content).
 */
public class RemovingStarsFromAString {

    public String removeStars(String string) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char character : string.toCharArray()) {
            if (character == '*') {
                stack.pop();
            } else {
                stack.push(character);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        while (!stack.isEmpty()) {
            stringBuilder.append(stack.removeLast());
        }

        return stringBuilder.toString();
    }


    public String removeStarsUsingStringBuilder(String string) {
        StringBuilder ans = new StringBuilder();

        for (char character : string.toCharArray()) {
            if (character != '*') {
                ans.append(character);
            } else {
                ans.deleteCharAt(ans.length() - 1);
            }
        }

        return ans.toString();
    }
}
