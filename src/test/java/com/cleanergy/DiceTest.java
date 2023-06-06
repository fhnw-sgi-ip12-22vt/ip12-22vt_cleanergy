package com.cleanergy;



import com.cleanergy.model.Dice;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DiceTest {

    @Test
    public void testMaxDice() {
        Dice dice = new Dice();
        ArrayList<Integer> rolls = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            rolls.add(dice.rollDice());
        }
        boolean result = true;
        for (int roll : rolls) {
            if (roll > 6) {
                result = false;
            }
        }
        assertTrue(result);
    }

    @Test
    public void testMinDice() {
        Dice dice = new Dice();
        ArrayList<Integer> rolls = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            rolls.add(dice.rollDice());
        }
        boolean result = true;
        for (int roll : rolls) {
            if (roll < 1) {
                result = false;
            }
        }
        assertTrue(result);
    }
}