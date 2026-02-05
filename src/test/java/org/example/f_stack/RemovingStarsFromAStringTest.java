package org.example.f_stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemovingStarsFromAStringTest {

    private RemovingStarsFromAString solution;

    @BeforeEach
    void setUp() {
        solution = new RemovingStarsFromAString();
    }

    @Test
    @DisplayName("Example 1: leet**cod*e -> lecoe")
    void example1_removesNearestLeftCharacters() {
        assertBoth("lecoe", "leet**cod*e");
    }

    @Test
    @DisplayName("Example 2: erase***** -> empty string")
    void example2_allRemoved_returnsEmpty() {
        assertBoth("", "erase*****");
    }

    @Test
    @DisplayName("No stars: returns original string")
    void noStars_returnsSame() {
        assertBoth("abc", "abc");
    }

    @Test
    @DisplayName("Consecutive removals clear all")
    void consecutiveRemovals_clearAll() {
        assertBoth("", "ab**");
    }

    @Test
    @DisplayName("Alternating stars remove each left char")
    void alternatingStars_removeEachLeftChar() {
        assertBoth("", "a*b*c*");
    }

    @Test
    @DisplayName("Mixed pattern leaves correct remainder")
    void mixedPattern_correctRemainder() {
        assertBoth("ab", "abc*de**f*");
    }

    @Test
    @DisplayName("Trailing stars remove previous chars")
    void trailingStars_removePreviousChars() {
        assertBoth("st", "star**");
    }

    private void assertBoth(String expected, String input) {
        assertEquals(expected, solution.removeStars(input));
        assertEquals(expected, solution.removeStarsUsingStringBuilder(input));
    }
}
