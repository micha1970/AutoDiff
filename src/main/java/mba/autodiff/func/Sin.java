package mba.autodiff.func;

import lombok.Getter;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Precedence;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

public class Sin implements Evaluatable<Double> {
	@Getter
	private final Evaluatable<Double> e; 
	
	public Sin(Evaluatable<Double> e) {
		this.e = e;
	}

	@Override
	public Double eval(Ctx ctx) {
		if(!ctx.contains(this))
			ctx.put(this, Math.sin(e.eval(ctx)));
		return ctx.valueOf(this).get();
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return e.forward(var, ctx) * Math.cos(e.eval(ctx));
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		e.backward(Math.cos(e.eval(ctx)) * partGradient, ctx);
		return ctx;
	}

	@Override
	public Evaluatable<Double> symbolic(Var var, Ctx ctx) {
		return new Mul(e.symbolic(var, ctx), new Cos(e));
	}

	@Override
	public <T> T accept(Visitor<T> visitor, Precedence parent) {
		return visitor.visitSin(this, parent);
	}
	
	@Override
	public int precedence() {
		return 5;
	}
}
