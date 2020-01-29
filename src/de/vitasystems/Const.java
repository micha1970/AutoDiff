package de.vitasystems;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Const implements Evaluatable {
	private final Double val; 
	
	@Override
	public Double eval(Ctx ctx) {
		ctx.put(this, val);
		return val;
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return 0d;
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		ctx.putGrad(this, d -> 0d);
		return ctx;
	}

	@Override
	public Evaluatable symbolic(Var var, Ctx ctx) {
		return ctx.newConst(0d);
	}

	@Override
	public String toString() {
		String x = val.toString();
		return val.toString();
	}
}
