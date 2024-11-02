package fidelitycard;

import java.util.Random;

public class LotteryFidelityCard implements FidelityCard {
    private Random random;

    public LotteryFidelityCard() {
        this.random = new Random();
    }

    @Override
	public String toString() {
		return "LotteryFidelityCard";
	}

	@Override
    public double applyDiscount(double price) {
        if (random.nextDouble() < 0.1) { // 10% chance to get the meal for free
            return 0;
        }
        return price;
    }
}
