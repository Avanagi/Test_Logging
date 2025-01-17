import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {
    private static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    private static final String NAME_CANNOT_BE_BLANK = "Name cannot be blank.";
    private static final String SPEED_CANNOT_BE_NEGATIVE = "Speed cannot be negative.";
    private static final String DISTANCE_CANNOT_BE_NEGATIVE = "Distance cannot be negative.";

    @Mock
    private Horse horse;

    @Test
    public void constructorNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 1);
        });

        try {
            new Horse(null, 1);
        } catch (Exception exception) {
            assertEquals(NAME_CANNOT_BE_NULL, exception.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    public void constructorNameBlank(String name) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, 1);
        });

        try {
            new Horse(name, 1);
        } catch (Exception exception) {
            assertEquals(NAME_CANNOT_BE_BLANK, exception.getMessage());
        }
    }

    @Test
    public void constructorSpeedNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("null", -1);
        });

        try {
            new Horse("null", -1);
        } catch (Exception exception) {
            assertEquals(SPEED_CANNOT_BE_NEGATIVE, exception.getMessage());
        }
    }

    @Test
    public void constructorDistanceNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("null", 1, -1);
        });

        try {
            new Horse("null", 1, -1);
        } catch (Exception exception) {
            assertEquals(DISTANCE_CANNOT_BE_NEGATIVE, exception.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Horse", "Glock"})
    public void getName(String name) {
        horse = new Horse(name, 1);
        assertEquals(name, horse.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, 500})
    public void getSpeed(int speed) {
        horse = new Horse("Horse", speed);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void getDistanceWithTwoParams() {
        horse = new Horse("Horse", 1);
        assertEquals(0, horse.getDistance());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, 500})
    public void getDistanceWithAllParams(int distance) {
        horse = new Horse("Horse", 1, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void moveVerify() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse.getRandomDouble(0.2, 0.9);
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "10, 100, 0.5, 105.0",
            "20, 200, 0.8, 216.0",
            "5, 50, 0.3, 51.5"
    })
    public void moveAccuracy(double speed, double distance, double randomValue, double expectedDistance) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            horse = new Horse("Horse", speed, distance);
            horse.move();
            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}
