package fidelitycard;

public class BasicFidelityCard implements FidelityCard{
	
	
	@Override
	public String toString() {
		return "BasicFidelityCard";
	}

	@Override
    public double applyDiscount(double price) {
        return price; 
    }
}