package de.vitasystems;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Ctx {
	public static Ctx newCtx() {
		return new Ctx();
	}
	
	private Map<Evaluatable, Optional<Double>> values = new HashMap<>();
	private Map<Evaluatable, Double> gradients = new HashMap<>();
	
	public Var newVar(String name) {
		Var var = new Var(name);
		values.put(var, Optional.empty());
		gradients.put(var, 0d);
		return var;
	}
	
	public Const newConst(Double value) {
		Const c = new Const(value);
		values.put(c, Optional.of(value));
		gradients.put(c, 0d);
		return c;
	}

	public void put(Evaluatable e, Double result) {
		values.put(e, Optional.of(result));
	}
	
	public void putGrad(Evaluatable e, Function<Double, Double> comp) {
		Double currentGrad = gradients.get(e);
		Double newGrad = comp.apply(currentGrad);
		gradients.put(e, newGrad);
	}
	
	public boolean contains(Evaluatable e) {
		return values.containsKey(e);
	}
	
	public Optional<Double> valueOf(Evaluatable e) {
		return values.getOrDefault(e, Optional.empty());
	}
	
	public Double valueOfGrad(Evaluatable e) {
		return gradients.get(e);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}