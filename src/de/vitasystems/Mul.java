package de.vitasystems;


public class Mul implements Evaluatable {
	private final Evaluatable left; 
	private final Evaluatable right;
	
	public Mul(Evaluatable left, Evaluatable right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Double eval() {
		return left.eval() * right.eval();
	}

	@Override
	public Double forward(Var var) {
		Double res1 = right.eval() * left.forward(var);
		Double res2 = left.eval() * right.forward(var);

		return res1 + res2;
	}

	@Override
	public Double backward(Gradient g) {
		// TODO Auto-generated method stub
		return null;
	}
}
