package mba.autodiff.func;

import lombok.Getter;
import mba.autodiff.Const;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

public class Cos implements Evaluatable {
	@Getter
	private final Evaluatable e; 
	
	public Cos(Evaluatable e) {
		this.e = e;
	}

	@Override
	public Double eval(Ctx ctx) {
		if(!ctx.contains(this))
			ctx.put(this, Math.cos(e.eval(ctx)));
		return ctx.valueOf(this).get();
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return e.forward(var, ctx) * -1 * Math.sin(e.eval(ctx));
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		e.backward(Math.cos(e.eval(ctx)) * partGradient, ctx);
		return ctx;
	}

	@Override
	public Evaluatable symbolic(Var var, Ctx ctx) {
		Const minusOne = ctx.newConst(-1d);
		return new Mul(new Mul(minusOne, e.symbolic(var, ctx)), new Sin(e));
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor, Evaluatable parent) {
		return visitor.visitCos(this, parent);
	}
	
}
