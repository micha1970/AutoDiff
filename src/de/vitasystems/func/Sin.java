package de.vitasystems.func;

import de.vitasystems.Ctx;
import de.vitasystems.Evaluatable;
import de.vitasystems.Var;
import de.vitasystems.func.visitor.Visitor;
import lombok.Getter;

public class Sin implements Evaluatable {
	@Getter
	private final Evaluatable e; 
	
	public Sin(Evaluatable e) {
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
	public Evaluatable symbolic(Var var, Ctx ctx) {
		return new Mul(e.symbolic(var, ctx), new Cos(e));
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSin(this);
	}
}
