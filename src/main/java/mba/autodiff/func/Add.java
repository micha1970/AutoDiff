package mba.autodiff.func;

import lombok.Getter;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Precedence;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

public class Add implements Evaluatable<Double> {
	@Getter
	private final Evaluatable<Double> left;
	@Getter
	private final Evaluatable<Double> right;
	
	public Add(Evaluatable<Double> left, Evaluatable<Double> right) {
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
	public Evaluatable<Double> symbolic(Var var, Ctx ctx) {
		return new Add(left.symbolic(var, ctx), right.symbolic(var, ctx));
	}

	@Override
	public <T> T accept(Visitor<T> visitor, Precedence parent) {
		return visitor.visitAdd(this, parent);
	}

	@Override
	public int precedence() {
		return 10;
	}
	
	
}
