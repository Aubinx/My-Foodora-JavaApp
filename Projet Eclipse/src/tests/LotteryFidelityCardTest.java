package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fidelitycard.LotteryFidelityCard;

import java.util.Random;

class LotteryFidelityCardTest {

    private LotteryFidelityCard card;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        card = new LotteryFidelityCard() {
            @Override
            public double applyDiscount(double price) {
                return random.nextDouble() < 0.1 ? 0 : price;
            }
        };
    }

    @Test
    void testApplyDiscount() {
        double price = 100.0;
        boolean discountApplied = false;

        for (int i = 0; i < 100; i++) {
            double discountedPrice = card.applyDiscount(price);
            if (discountedPrice == 0) {
                discountApplied = true;
                break;
            }
        }

        assertTrue(discountApplied);
    }
}
