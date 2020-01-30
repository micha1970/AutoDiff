package de.vitasystems.func;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import de.vitasystems.Ctx;
import de.vitasystems.Evaluatable;
import de.vitasystems.Var;
import de.vitasystems.func.visitor.Visitor;

public class Sum implements Evaluatable {
	private final Evaluatable[] summands;
	
	public Sum(Evaluatable summand, Evaluatable...summands) {
		this.summands = new Evaluatable[summands.length + 1];
		this.summands[0] = summand;
		IntStream.range(1, summands.length + 1).forEach(i -> this.summands[i] = summands[i]);
	}
	
	private Sum(Evaluatable...summands) {
		this.summands = summands;
	}

	@Override
	public Double eval(Ctx ctx) {
		if(!ctx.contains(this)) {
			Double theSum = Stream.of(summands).map(s -> s.eval(ctx)).reduce(0d, (r, s) -> r + s);
			ctx.put(this, theSum);
		}
		return ctx.valueOf(this).get();
	}

	@Override
	public Double forward(Var var, Ctx ctx) {
		return Stream.of(summands)
			.map(s -> s.forward(var, ctx))
			.reduce(0d, (r, d) -> r + d);
	}

	@Override
	public Ctx backward(Double partGradient, Ctx ctx) {
		Stream.of(summands).forEach(s -> s.backward(partGradient, ctx));
		return ctx;
	}

	@Override
	public Evaluatable symbolic(Var var, Ctx ctx) {
		Evaluatable[] evaluatables = Stream.of(summands)
			.map(s -> s.symbolic(var, ctx))
			.toArray(size -> new Evaluatable[size]);
		return new Sum(evaluatables);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSum(this);
	}

}
