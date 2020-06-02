package mba.autodiff.func;

import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

/**
 * Represents the exponentiation by e.
 */
public class NExp implements Evaluatable<Double> {
	private final Evaluatable<Double> exp;
	
	public NExp(Evaluatable<Double> exp) {
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
	public Evaluatable<Double> symbolic(Var var, Ctx ctx) {
		return new Mul(exp.symbolic(var, ctx), this);
	}

	@Override
	public <T> T accept(Visitor<T> visitor, Evaluatable<?> parent) {
		return visitor.visitNExp(this, parent);
	}
}