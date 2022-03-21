package mba.autodiff;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mba.autodiff.func.visitor.Visitor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Const implements Evaluatable<Double> {
	@Getter
	private final Double val; 
	
	@Override
	public Double eval(Ctx ctx) {
		ctx.put(this, val);
		return val;
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return 0d;
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		ctx.putGrad(this, d -> 0d);
		return ctx;
	}

	@Override
	public Evaluatable<Double> symbolic(Var var, Ctx ctx) {
		return ctx.newConst(0d);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor, Precedence parent) {
		return visitor.visitConst(this, parent);
	}

}
