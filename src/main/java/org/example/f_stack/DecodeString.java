package org.example.f_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Decodes an encoded string with counts and bracketed substrings.
 *
 * <p>Encoding format: {@code k[encoded_string]} where the inside substring is repeated {@code k} times.
 * Nested encodings are allowed.
 *
 * <p>Solution 1 (Two stacks):
 * <ul>
 *   <li>Use a stack for repeat counts and a stack for partial strings</li>
 *   <li>On '[', push the current count and current string, then reset both</li>
 *   <li>On ']', pop and repeat the current string, append to the previous string</li>
 * </ul>
 *
 * <p>Solution 2 (Iterative StringBuilder replacement):
 * <ul>
 *   <li>Find the innermost bracket pair</li>
 *   <li>Read the number directly before '['</li>
 *   <li>Replace {@code k[substring]} with {@code substring.repeat(k)}</li>
 *   <li>Repeat until no brackets remain</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * s = "3[a2[c]]"
 * decode: "a2[c]" -> "acc" -> "accaccacc"
 * </pre>
 *
 * <p>Key insight: decoding always completes from the innermost bracket outward, so a stack
 * (or repeated inner replacement) naturally models the process.
 *
 * <p>Time Complexity:
 * O(n) for the stack solution, where n is the length of the string.
 * The replacement solution can be higher depending on repeat sizes and string rebuilding.
 *
 * <p>Space Complexity:
 * O(n) for both solutions due to stored substrings.
 */
public class DecodeString {

    public String decodeStringUsingStack(String s) {
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> stringStack = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();

        int count = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                count = count * 10 + (c - '0');
            } else if (c == '[') {
                countStack.push(count);
                stringStack.push(current);
                count = 0;
                current = new StringBuilder();
            } else if (c == ']') {
                int repeatCount = countStack.pop();
                StringBuilder previous = stringStack.pop();
                previous.append(current.toString().repeat(repeatCount));
                current = previous;
            } else {
                current.append(c);
            }
        }

        return current.toString();
    }

    public String decodeStringUsingStringBuilder(String string) {
        long counters = string.chars().filter(c -> c == '[').count();
        StringBuilder result = new StringBuilder(string);

        while (counters > 0) {
            int openingBracketIndex = result.lastIndexOf("[");
            int closingBracketIndex = result.indexOf("]", openingBracketIndex);
            int counterStartIndex = findCounterStartIndex(result, openingBracketIndex);
            int counterNumber = Integer.parseInt(result.substring(counterStartIndex, openingBracketIndex));
            String charactersToRepeat = result.substring(openingBracketIndex + 1, closingBracketIndex);
            result.replace(counterStartIndex, closingBracketIndex + 1, charactersToRepeat.repeat(counterNumber));
            counters--;
        }

        return result.toString();
    }

    private int findCounterStartIndex(StringBuilder result, int openingBracketIndex) {
        int index = openingBracketIndex - 1;
        while (index >= 0 && Character.isDigit(result.charAt(index))) {
            index--;
        }
        return index + 1;
    }
}
