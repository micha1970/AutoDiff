package de.vitasystems.func;

import de.vitasystems.Ctx;
import de.vitasystems.Evaluatable;
import de.vitasystems.Var;
import de.vitasystems.func.visitor.Visitor;

public class NExp implements Evaluatable {
	private final Evaluatable exp;
	
	public NExp(Evaluatable exp) {
		this.exp = exp;
	}

	@Override
	public Double eval(Ctx ctx) {
		if(!ctx.contains(this)) {
			Double theExp = Math.exp(exp.eval(ctx));
			ctx.put(this, theExp);
		}
		return ctx.valueOf(this).get();
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return exp.forward(var, ctx) * Math.exp(eval(ctx));
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		return exp.backward(partGradient * eval(ctx), ctx);
	}

	@Override
	public Evaluatable symbolic(Var var, Ctx ctx) {
		return new Mul(exp.symbolic(var, ctx), this);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitNExp(this);
	}
}