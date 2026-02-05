package org.example.f_stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AsteroidCollisionTest {

    private AsteroidCollision solution;

    @BeforeEach
    void setUp() {
        solution = new AsteroidCollision();
    }

    @Test
    @DisplayName("Example 1: [5,10,-5] -> [5,10]")
    void example1_singleCollision() {
        assertArrayEquals(new int[]{5, 10}, solution.asteroidCollision(new int[]{5, 10, -5}));
    }

    @Test
    @DisplayName("Example 2: [8,-8] -> []")
    void example2_equalSizeBothExplode() {
        assertArrayEquals(new int[]{}, solution.asteroidCollision(new int[]{8, -8}));
    }

    @Test
    @DisplayName("Example 3: [10,2,-5] -> [10]")
    void example3_chainCollision() {
        assertArrayEquals(new int[]{10}, solution.asteroidCollision(new int[]{10, 2, -5}));
    }

    @Test
    @DisplayName("Example 4: [3,5,-6,2,-1,4] -> [-6,2,4]")
    void example4_multipleCollisions() {
        assertArrayEquals(new int[]{-6, 2, 4}, solution.asteroidCollision(new int[]{3, 5, -6, 2, -1, 4}));
    }

    @Test
    @DisplayName("No collision: same direction")
    void sameDirection_noCollision() {
        assertArrayEquals(new int[]{1, 2, 3}, solution.asteroidCollision(new int[]{1, 2, 3}));
        assertArrayEquals(new int[]{-3, -2, -1}, solution.asteroidCollision(new int[]{-3, -2, -1}));
    }

    @Test
    @DisplayName("All left after collisions")
    void allLeftAfterCollisions() {
        assertArrayEquals(new int[]{-5, -4, -3}, solution.asteroidCollision(new int[]{-5, -4, -3}));
    }

    @Test
    @DisplayName("All right after collisions")
    void allRightAfterCollisions() {
        assertArrayEquals(new int[]{4, 5, 6}, solution.asteroidCollision(new int[]{4, 5, 6}));
    }

    @Test
    @DisplayName("Right then larger left survives")
    void rightThenLargerLeftSurvives() {
        assertArrayEquals(new int[]{-8}, solution.asteroidCollision(new int[]{5, 6, -8}));
    }

    @Test
    @DisplayName("Left then right never collide")
    void leftThenRight_neverCollide() {
        assertArrayEquals(new int[]{-2, 1}, solution.asteroidCollision(new int[]{-2, 1}));
    }

    @Test
    @DisplayName("No collisions")
    void multipleCollisions_lastSurvivor() {
        assertArrayEquals(new int[]{-2, -1, 1, 2}, solution.asteroidCollision(new int[]{-2, -1, 1, 2}));
    }

    @Test
    void lastTest() {
        assertArrayEquals(new int[]{-2, -2, -2}, solution.asteroidCollision(new int[]{-2, -2, 1, -2}));
    }
}
