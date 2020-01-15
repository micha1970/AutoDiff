package de.vitasystems;


public class Add implements Evaluatable {
	private final Evaluatable left; 
	private final Evaluatable right;
	
	public Add(Evaluatable left, Evaluatable right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Double eval() {
		return left.eval() + right.eval();
	}

	@Override
	public Double forward(Var var) {
		return left.forward(var) + right.forward(var);
	}

	@Override
	public Double backward(Gradient g) {
		// TODO Auto-generated method stub
		return null;
	}
}
