package de.vitasystems;

import java.util.Optional;

import de.vitasystems.func.visitor.Visitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(of = {"name"})
@RequiredArgsConstructor
public class Var implements Evaluatable {
	@Getter
	private final String name;
	private Optional<Double> val;
	
	@Override
	public Double eval(Ctx ctx) {
		Double theValue = val.orElseThrow(() -> new IllegalArgumentException());
		ctx.put(this, theValue);
		return theValue;
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return this.equals(var) ? 1d : 0d;
	}
	
	public void bind(Double val) {
		this.val = Optional.of(val);
	}
	
	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		ctx.putGrad(this, d -> partGradient + d);
		return ctx;
	}

	@Override
	public Evaluatable symbolic(Var var, Ctx ctx) {
		return ctx.newConst(this.equals(var) ? 1d : 0d);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitVar(this);
	}	


}









