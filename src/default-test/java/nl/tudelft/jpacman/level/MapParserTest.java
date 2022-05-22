package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class MapParserTest {
    private MapParser mapParser;
    private final LevelFactory levelFactory = mock(LevelFactory.class);
    private final BoardFactory boardFactory = mock(BoardFactory.class);

    @BeforeEach
    void setUp() {
        mapParser = new MapParser(levelFactory, boardFactory);
    }

    @Test
    public void nullFile() {
        assertThatThrownBy(() -> mapParser.parseMap((String) null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void notExistFile() {
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));

        when(levelFactory.createGhost()).thenReturn(mock(Ghost.class));
        when(levelFactory.createPellet()).thenReturn(mock(Pellet.class));
        String file = "/NOT_EXIST.txt";
        assertThatThrownBy(() -> mapParser.parseMap(file)).isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Could not get resource for: " + file);
    }

    @Test
    public void existFile() throws IOException {
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));

        when(levelFactory.createGhost()).thenReturn(mock(Ghost.class));
        when(levelFactory.createPellet()).thenReturn(mock(Pellet.class));
        mapParser.parseMap("/correct_map.txt");
        verify(boardFactory, times(4)).createGround();
        verify(boardFactory, times(2)).createWall();
        verify(levelFactory, times(1)).createGhost();
    }

    @Test
    public void unrecognizedMap() {
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));

        when(levelFactory.createGhost()).thenReturn(mock(Ghost.class));
        when(levelFactory.createPellet()).thenReturn(mock(Pellet.class));
        String file = "/wrong_map.txt";
        assertThatThrownBy(() -> mapParser.parseMap(file)).isInstanceOf(PacmanConfigurationException.class)
            .hasMessage("Invalid character at 0,0: A");
    }
}
