package de.vitasystems;

import java.util.Optional;

public class Var implements Evaluatable {
	private final String name;
	private Optional<Double> val;
	
	public Var(String name) {
		this.name = name;
	}

	@Override
	public Double eval() {
		return val.orElseThrow(() -> new IllegalArgumentException());
	}

	@Override
	public Double forward(Var var) {
		return this.equals(var) ? 1d : 0d;
	}
	
	public void bind(Double val) {
		this.val = Optional.of(val);
	}
	
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Var))
			return false;
		return ((Var) obj).name.equals(this.name);
	}
	
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public Double backward(Gradient g) {
		// TODO Auto-generated method stub
		return null;
	}
}
