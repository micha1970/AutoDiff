package mba.autodiff.func;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Getter;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

public class Sum implements Evaluatable<Double> {
	@Getter
	private final Evaluatable<Double>[] summands;
	
	public Sum(Evaluatable<Double> summand, Evaluatable<Double>...summands) {
		this.summands = new Evaluatable[summands.length + 1];
		this.summands[0] = summand;
		IntStream.range(1, summands.length + 1).forEach(i -> this.summands[i] = summands[i]);
	}
	
	public Sum(List<Evaluatable<Double>> summands) {
		this(summands.toArray(new Evaluatable[] {}));
	}
	
	private Sum(Evaluatable<Double>...summands) {
		if(summands.length == 0)
			throw new IllegalArgumentException();
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
	public Evaluatable<Double> symbolic(Var var, Ctx ctx) {
		Evaluatable<Double>[] evaluatables = Stream.of(summands)
			.map(s -> s.symbolic(var, ctx))
			.toArray(size -> new Evaluatable[size]);
		return new Sum(evaluatables);
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor, Evaluatable<?> parent) {
		return visitor.visitSum(this, parent);
	}

}
