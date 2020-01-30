package de.vitasystems.func;

import de.vitasystems.Const;
import de.vitasystems.Ctx;
import de.vitasystems.Evaluatable;
import de.vitasystems.Var;
import de.vitasystems.func.visitor.Visitor;

public class Pow implements Evaluatable {
	private final Evaluatable base;
	private final Double exp;
	
	public Pow(Evaluatable base, Double exp) {
		this.base = base;
		this.exp = exp;
	}

	@Override
	public Double eval(Ctx ctx) {
		if(!ctx.contains(this)) {
			Double thePow = Math.pow(base.eval(ctx), exp);
			ctx.put(this, thePow);
		}
		return ctx.valueOf(this).get();
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return (exp - 1) * base.forward(var, ctx);
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		return base.backward(partGradient * (exp - 1), ctx);
	}

	@Override
	public Evaluatable symbolic(Var var, Ctx ctx) {
		return new Mul(new Const(exp - 1), base.symbolic(var, ctx));
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitPow(this);
	}	

}