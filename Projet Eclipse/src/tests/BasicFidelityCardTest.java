package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fidelitycard.BasicFidelityCard;

class BasicFidelityCardTest {

    private BasicFidelityCard card;

    @BeforeEach
    void setUp() {
        card = new BasicFidelityCard();
    }

    @Test
    void testApplyDiscount() {
        double price = 100.0;
        double discountedPrice = card.applyDiscount(price);
        assertEquals(price, discountedPrice);
    }
}
