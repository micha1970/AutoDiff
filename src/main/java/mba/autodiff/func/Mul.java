package mba.autodiff.func;

import lombok.Getter;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Precedence;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

public class Mul implements Evaluatable<Double> {
	@Getter
	private final Evaluatable<Double> left;
	@Getter
	private final Evaluatable<Double> right;
	
	public Mul(Evaluatable<Double> left, Evaluatable<Double> right) {
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
	public Evaluatable<Double> symbolic(Var var, Ctx ctx) {
		Evaluatable<Double> res1 = new Mul(right, left.symbolic(var, ctx));
		Evaluatable<Double> res2 = new Mul(left, right.symbolic(var, ctx));
		return new Add(res1, res2);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor, Precedence parent) {
		return visitor.visitMul(this, parent);
	}
	
	@Override
	public int precedence() {
		return 10;
	}
}
