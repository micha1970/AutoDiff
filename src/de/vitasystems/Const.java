package de.vitasystems;

public class Const implements Evaluatable {
	private final Double val; 
	
	public Const(Double val) {
		this.val = val;
	}

	@Override
	public Double eval() {
		return this.val;
	}

	@Override
	public Double forward(Var var) {
		return 0d;
	}

	@Override
	public Double backward(Gradient g) {
		// TODO Auto-generated method stub
		return null;
	}
}
