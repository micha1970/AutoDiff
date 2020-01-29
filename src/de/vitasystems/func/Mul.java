package de.vitasystems.func;

import de.vitasystems.Ctx;
import de.vitasystems.Evaluatable;
import de.vitasystems.Var;

public class Mul implements Evaluatable {
	private final Evaluatable left; 
	private final Evaluatable right;
	
	public Mul(Evaluatable left, Evaluatable right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Double eval(Ctx ctx) {
		if(!ctx.contains(this))
			ctx.put(this, left.eval(ctx) * right.eval(ctx));
		return ctx.valueOf(this).get();
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		Double res1 = right.eval(ctx) * left.forward(var, ctx);
		Double res2 = left.eval(ctx) * right.forward(var, ctx);
		return res1 + res2;
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		Double grd1 = partGradient * right.eval(ctx);
		left.backward(grd1, ctx);
		
		Double grd2 = partGradient * left.eval(ctx);
		right.backward(grd2, ctx);
		return ctx;
	}

	@Override
	public Evaluatable symbolic(Var var, Ctx ctx) {
		Evaluatable res1 = new Mul(right, left.symbolic(var, ctx));
		Evaluatable res2 = new Mul(left, right.symbolic(var, ctx));
		return new Add(res1, res2);
	}

	@Override
	public String toString() {
		return left.toString() + " * " + right.toString();
	}
}
