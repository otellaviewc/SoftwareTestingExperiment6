package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PlayerCollisionsTest {
    PlayerCollisions playerCollisions;
    Player player;

    @BeforeEach
    void setUp() {
        PointCalculator pointCalculator = mock(PointCalculator.class);
        playerCollisions = new PlayerCollisions(pointCalculator);
        player = mock(Player.class);
    }

    @Test
    void nothingHappened() {
        Ghost ghost = mock(Ghost.class);
        Pellet pellet = mock(Pellet.class);
        playerCollisions.collide(ghost, ghost);
        playerCollisions.collide(ghost, pellet);
        playerCollisions.collide(pellet, ghost);
        doCallRealMethod().when(player).setAlive(anyBoolean());
        doCallRealMethod().when(player).setKiller(any(Unit.class));
        doNothing().when(pellet).leaveSquare();
        verify(player, times(0)).setAlive(false);
        verify(player, times(0)).setKiller(ghost);
        verify(pellet, times(0)).leaveSquare();
    }

    @Test
    void playerVersusGhost() {
        Ghost ghost = mock(Ghost.class);
        playerCollisions.collide(player, ghost);
        doCallRealMethod().when(player).setAlive(anyBoolean());
        doCallRealMethod().when(player).setKiller(any(Unit.class));
        verify(player, times(1)).setAlive(false);
        verify(player, times(1)).setKiller(ghost);
    }

    @Test
    void playerVersusPellet() {
        Pellet pellet = mock(Pellet.class);
        playerCollisions.collide(player, pellet);
        doNothing().when(pellet).leaveSquare();
        verify(pellet, times(1)).leaveSquare();
    }

    @Test
    void ghostVersusPlayer() {
        Ghost ghost = mock(Ghost.class);
        playerCollisions.collide(ghost, player);
        doCallRealMethod().when(player).setAlive(anyBoolean());
        doCallRealMethod().when(player).setKiller(any(Unit.class));
        verify(player, times(1)).setAlive(false);
        verify(player, times(1)).setKiller(ghost);
    }

    @Test
    void pelletVersusPlayer() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);
        playerCollisions.collide(pellet, player);
        doNothing().when(pellet).leaveSquare();
        verify(pellet, times(1)).leaveSquare();
    }
}
