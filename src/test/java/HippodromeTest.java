import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class HippodromeTest {
    private static final String HORSES_CANNOT_BE_NULL = "Horses cannot be null.";
    private static final String HORSES_CANNOT_BE_EMPTY = "Horses cannot be empty.";

    private static final ArrayList<Horse> horses = new ArrayList<>();
    private static final ArrayList<Horse> mockedHorses = new ArrayList<>();

    @Mock
    private Hippodrome hippodrome;

    @BeforeAll
    static void init() {
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse_" + i, i, i * 3));
            mockedHorses.add(spy(new Horse("Horse_" + i, i, i * 3)));
        }
    }

    @Test
    public void constructorListNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });

        try {
            new Hippodrome(null);
        } catch (Exception exception) {
            assertEquals(HORSES_CANNOT_BE_NULL, exception.getMessage());
        }
    }

    @Test
    public void constructorListEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(new ArrayList<>());
        });

        try {
            new Hippodrome(new ArrayList<>());
        } catch (Exception exception) {
            assertEquals(HORSES_CANNOT_BE_EMPTY, exception.getMessage());
        }
    }

    @Test
    public void getHorses() {
        hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void move() {
        hippodrome = new Hippodrome(mockedHorses);
        hippodrome.move();
        for (Horse mockHorse : mockedHorses) {
            verify(mockHorse).move();
        }
    }

    @Test
    public void getWinner() {
        hippodrome = new Hippodrome(horses);
        assertEquals(horses.get(horses.size() - 1), hippodrome.getWinner());
    }

}
