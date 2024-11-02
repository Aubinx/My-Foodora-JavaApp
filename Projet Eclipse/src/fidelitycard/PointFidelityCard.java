package fidelitycard;

public class PointFidelityCard implements FidelityCard {
    private int points;

    public PointFidelityCard() {
        this.points = 0;
    }

    public void addPoints(double amountSpent) {
        this.points += (int) (amountSpent / 10);
    }

    @Override
	public String toString() {
		return "PointFidelityCard";
	}

	@Override
    public double applyDiscount(double price) {
        if (points >= 100) {
            points -= 100;
            return price * 0.90; // 10% discount
        }
        return price;
    }

	public Integer getPoints() {
		return points;
		
	}
}
