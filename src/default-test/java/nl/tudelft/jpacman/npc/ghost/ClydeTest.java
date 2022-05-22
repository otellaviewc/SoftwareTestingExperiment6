package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ClydeTest {
    private MapParser mapParser;

    @BeforeEach
    void setUp() {
        PacManSprites spriteStore = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(spriteStore);

        LevelFactory levelFactory = new LevelFactory(spriteStore, ghostFactory, mock(PointCalculator.class));
        BoardFactory boardFactory = new BoardFactory(spriteStore);
        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    @Test
    public void departLessThanEight() {
        departTest(
            List.of(
                "##############",
                "#.#....C.....P",
                "##############"
            ),
            "WEST"
        );
    }

    @Test
    public void departMoreThanEight(){
        departTest(
            List.of(
                ".C................",
                "#.....#......#..P.",
                "##################"
            ),
            "EAST"
        );
    }

    private void departTest(List<String> text, String direction) {
        Level level = mapParser.parseMap(text);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assert clyde != null;

        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.WEST);
        level.registerPlayer(player);
        Optional<Direction> opt = clyde.nextAiMove();

        assert opt.isPresent();
        assertThat(opt.get()).isEqualTo(Direction.valueOf(direction));
    }
}
