package de.vitasystems.func;

import de.vitasystems.Ctx;
import de.vitasystems.Evaluatable;
import de.vitasystems.Var;
import de.vitasystems.func.visitor.Visitor;
import lombok.Getter;

public class Add implements Evaluatable {
	@Getter
	private final Evaluatable left;
	@Getter
	private final Evaluatable right;
	
	public Add(Evaluatable left, Evaluatable right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Double eval(Ctx ctx) {
		if(!ctx.contains(this))
			ctx.put(this, left.eval(ctx) + right.eval(ctx));
		return ctx.valueOf(this).get();
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return left.forward(var, ctx) + right.forward(var, ctx);
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		left.backward(partGradient, ctx);
		right.backward(partGradient, ctx);
		return ctx;
	}

	@Override
	public Evaluatable symbolic(Var var, Ctx ctx) {
		return new Add(left.symbolic(var, ctx), right.symbolic(var, ctx));
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitAdd(this);
	}
}
