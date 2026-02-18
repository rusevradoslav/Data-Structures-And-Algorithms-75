package org.example.g_queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Dota2SenateTest {

    private Dota2Senate solution;

    @BeforeEach
    void setUp() {
        solution = new Dota2Senate();
    }

    @Test
    @DisplayName("Example 1: RD -> Radiant")
    void example1_radiantBansDire() {
        assertEquals("Radiant", solution.predictPartyVictory("RD"));
    }

    @Test
    @DisplayName("Example 2: RDD -> Dire")
    void example2_direWinsWithMajority() {
        assertEquals("Dire", solution.predictPartyVictory("RDD"));
    }

    @Test
    @DisplayName("Single R senator -> Radiant")
    void singleRadiant() {
        assertEquals("Radiant", solution.predictPartyVictory("R"));
    }

    @Test
    @DisplayName("Single D senator -> Dire")
    void singleDire() {
        assertEquals("Dire", solution.predictPartyVictory("D"));
    }

    @Test
    @DisplayName("DR -> Dire (Dire goes first)")
    void direGoesFirst() {
        assertEquals("Dire", solution.predictPartyVictory("DR"));
    }

    @Test
    @DisplayName("All Radiant -> Radiant")
    void allRadiant() {
        assertEquals("Radiant", solution.predictPartyVictory("RRRR"));
    }

    @Test
    @DisplayName("All Dire -> Dire")
    void allDire() {
        assertEquals("Dire", solution.predictPartyVictory("DDDD"));
    }

    @Test
    @DisplayName("RRDDD -> Dire (Dire has majority)")
    void direMajority() {
        assertEquals("Radiant", solution.predictPartyVictory("RRDDD"));
    }

    @Test
    @DisplayName("RRRDD -> Radiant (Radiant has majority)")
    void radiantMajority() {
        assertEquals("Radiant", solution.predictPartyVictory("RRRDD"));
    }

    @Test
    @DisplayName("DRRRD -> Radiant (circular banning)")
    void circularBanning() {
        assertEquals("Radiant", solution.predictPartyVictory("DRRRD"));
    }

    @Test
    @DisplayName("DRRDRDRDRDDRDRDR -> Radiant")
    void longerMixedSequence() {
        assertEquals("Radiant", solution.predictPartyVictory("DRRDRDRDRDDRDRDR"));
    }

    @Test
    @DisplayName("RDDRD -> Dire (wrap-around matters)")
    void wrapAroundMatters() {
        assertEquals("Dire", solution.predictPartyVictory("RDDRD"));
    }

    @Test
    @DisplayName("DDRRR -> Dire (wrap-around matters)")
    void sequenceMatters() {
        assertEquals("Dire", solution.predictPartyVictory("DDRRR"));
    }
}
