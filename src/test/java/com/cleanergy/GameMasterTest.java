package com.cleanergy;

import com.cleanergy.model.GameMaster;
import com.cleanergy.model.State;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameMasterTest {

    // Gamemaster methods test
    @Test
    public void testAddRound() {
        GameMaster gm = new GameMaster();
        gm.addRound();
        gm.addRound();
        assertEquals(2, gm.getRound());

    }

    @Test
    public void testSetPlayers1() {
        GameMaster gm = new GameMaster();
        gm.setPlayers(2);
        assertEquals(2, gm.getPlayerCount());
        assertEquals(2, gm.getPlayers().size());

        gm = new GameMaster();
        gm.setPlayers(4);
        assertEquals(4, gm.getPlayerCount());
        assertEquals(4, gm.getPlayers().size());
    }

    @Test
    public void testPlayerRoll() {
        GameMaster gm = new GameMaster();
        gm.setPlayers(2);

        gm.playerRoll(0, 2);
        gm.playerRoll(1, 6);

        assertEquals(2, gm.getPlayers().get(0).getField());
        assertEquals(6, gm.getPlayers().get(1).getField());
    }

    @Test
    public void testCheckPlayerPosition1() {
        GameMaster gm = new GameMaster();
        gm.setPlayers(1);

        gm.playerRoll(0, 4);
        State state = gm.checkPlayerPosition(0);

        assertEquals(State.DICE, state);
    }

    @Test
    public void testCheckPlayerPosition2() {
        GameMaster gm = new GameMaster();
        gm.setPlayers(1);
        State state;

        gm.playerRoll(0, 4);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.DICE, state);

        gm.playerRoll(0, 4);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.QUIZ, state);
    }

    @Test
    public void testCheckPlayerPosition3() {
        GameMaster gm = new GameMaster();
        gm.setPlayers(1);
        State state;

        gm.playerRoll(0, 4);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.DICE, state);

        gm.playerRoll(0, 4);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.QUIZ, state);

        gm.playerRoll(0, 1);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.DICE, state);
    }

    @Test
    public void testCheckPlayerPosition4() {
        GameMaster gm = new GameMaster();
        gm.setPlayers(1);
        State state;

        gm.playerRoll(0, 7);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.QUIZ, state);

        gm.playerRoll(0, 6);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.QUIZ, state);

        gm.playerRoll(0, 6);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.QUIZ, state);

        gm.playerRoll(0, 6);
        state = gm.checkPlayerPosition(0);
        assertEquals(State.FINALQUIZ, state);
    }

    @Test
    public void testSelectMinigame1() {
        GameMaster gm = new GameMaster();
        State state;

        state = gm.selectMiniGame();
        assertEquals(State.FACTCHECK, state);

        gm.addRound();
        gm.addRound();
        state = gm.selectMiniGame();
        assertEquals(State.FACTCHECK, state);

        gm.addRound();
        gm.addRound();
        state = gm.selectMiniGame();
        assertEquals(State.FACTCHECK, state);
    }

    @Test
    public void testSelectMinigame2() {
        GameMaster gm = new GameMaster();
        State state;

        gm.addRound();
        state = gm.selectMiniGame();
        assertEquals(State.SONNENMINIGAME, state);

        gm.addRound();
        gm.addRound();
        gm.addRound();
        gm.addRound();
        state = gm.selectMiniGame();
        assertEquals(State.SONNENMINIGAME, state);
    }

    @Test
    public void testSelectMinigame3() {
        GameMaster gm = new GameMaster();
        State state;

        gm.addRound();
        gm.addRound();
        gm.addRound();

        state = gm.selectMiniGame();
        assertEquals(State.QUESTION, state);
    }

    @Test
    public void testSetPlayerSkip() {
        GameMaster gm = new GameMaster();
        gm.setPlayers(2);
        
        gm.setPlayerIndex(0);
        gm.setPlayerSkip(true);
        gm.setPlayerIndex(1);
        gm.setPlayerSkip(false);
        
        assertTrue(gm.getPlayers().get(0).isSkip());
        assertFalse(gm.getPlayers().get(1).isSkip());
    }
    
      @Test
      public void testcheckPlayerSkip()
      {
      GameMaster gm = new GameMaster();
      gm.setPlayers(1);
      
      gm.setPlayerIndex(0);
      gm.setPlayerSkip(true);
      
      assertTrue(gm.checkPlayerSkip(0));
      assertFalse(gm.checkPlayerSkip(0));
      }
     

}