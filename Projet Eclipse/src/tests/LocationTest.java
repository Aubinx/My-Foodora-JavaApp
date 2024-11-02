package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utils.Location;

class LocationTest {

    @Test
    void testGetX() {
        Location location = new Location(3.0, 4.0);
        assertEquals(3.0, location.getX());
    }

    @Test
    void testGetY() {
        Location location = new Location(3.0, 4.0);
        assertEquals(4.0, location.getY());
    }

    @Test
    void testDistanceTo() {
        Location location1 = new Location(0.0, 0.0);
        Location location2 = new Location(3.0, 4.0);
        assertEquals(5.0, location1.distanceTo(location2), 0.001);
    }
}
