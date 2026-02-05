package org.example.f_stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecodeStringTest {

    private DecodeString solution;

    @BeforeEach
    void setUp() {
        solution = new DecodeString();
    }

    @Test
    @DisplayName("Example 1: 3[a]2[bc] -> aaabcbc")
    void example1_flatRepeats() {
        assertBoth("aaabcbc", "3[a]2[bc]");
    }

    @Test
    @DisplayName("Example 2: 3[a2[c]] -> accaccacc")
    void example2_nestedRepeats() {
        assertBoth("accaccacc", "3[a2[c]]");
    }

    @Test
    @DisplayName("Example 3: 2[abc]3[cd]ef -> abcabccdcdcdef")
    void example3_multipleSegments() {
        assertBoth("abcabccdcdcdef", "2[abc]3[cd]ef");
    }

    @Test
    @DisplayName("Multi-digit count: 10[a] -> aaaaaaaaaa")
    void multiDigitCount() {
        assertBoth("aaaaaaaaaa", "10[a]");
    }

    @Test
    @DisplayName("No brackets: returns original string")
    void noBrackets_returnsSame() {
        assertBoth("leetcode", "leetcode");
    }

    @Test
    @DisplayName("Nested with trailing text")
    void nestedWithTrailingText() {
        assertBoth("abcbcxyz", "a2[bc]xyz");
    }

    private void assertBoth(String expected, String input) {
        assertEquals(expected, solution.decodeStringUsingStack(input));
        assertEquals(expected, solution.decodeStringUsingStringBuilder(input));
    }
}
