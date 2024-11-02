package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fidelitycard.PointFidelityCard;

class PointFidelityCardTest {

    private PointFidelityCard card;

    @BeforeEach
    void setUp() {
        card = new PointFidelityCard();
    }

    @Test
    void testAddPoints() {
        assertEquals(0, card.getPoints());
        card.addPoints(50.0);
        assertEquals(5, card.getPoints());
        card.addPoints(100.0);
        assertEquals(15, card.getPoints());
    }

    @Test
    void testApplyDiscount() {
        card.addPoints(1000.0);
        assertEquals(100, card.getPoints());
        double price = 100.0;
        double discountedPrice = card.applyDiscount(price);
        assertEquals(90.0, discountedPrice);
        assertEquals(0, card.getPoints());

        discountedPrice = card.applyDiscount(price);
        assertEquals(100.0, discountedPrice);
        assertEquals(0, card.getPoints());
    }
}
