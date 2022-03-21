package mba.autodiff.func;

import mba.autodiff.Const;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Precedence;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

public class Pow implements Evaluatable<Double> {
	private final Evaluatable<Double> base;
	private final Double exp;
	
	public Pow(Evaluatable<Double> base, Double exp) {
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
	public Evaluatable<Double> symbolic(Var var, Ctx ctx) {
		return new Mul(new Const(exp - 1), base.symbolic(var, ctx));
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor, Precedence parent) {
		return visitor.visitPow(this, parent);
	}
	
	@Override
	public int precedence() {
		return 100;
	}

}