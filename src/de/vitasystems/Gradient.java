package de.vitasystems;

import java.util.HashMap;
import java.util.Map;


public class Gradient {
	public static Gradient newGradient() {
		return new Gradient();
	}
	
	private Map<Evaluatable, Double> gradients = new HashMap<>();
	
	private Gradient() { }
	
	public Var newVar(String name) {
		Var var = new Var(name);
		gradients.put(var, 0d);
		return var;
	}
	
	public Const newConst(Double value) {
		Const c = new Const(value);
		gradients.put(c, value);
		return c;
	}

	public void clear() {
		gradients.clear();
	}
	
	public void clearGradients() {
		gradients.entrySet().stream().map(e -> e.setValue(0d)).count();
	}
	
	public void pushGradientValue(Evaluatable var, Double value) {
		gradients.put(var, value);
	}
	
//	public Double gradientVal();
}
