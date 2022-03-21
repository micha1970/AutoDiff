package mba.autodiff.func.visitor;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import mba.autodiff.Const;
import mba.autodiff.Evaluatable;
import mba.autodiff.Precedence;
import mba.autodiff.Var;
import mba.autodiff.func.Add;
import mba.autodiff.func.Cos;
import mba.autodiff.func.Mul;
import mba.autodiff.func.NExp;
import mba.autodiff.func.Pow;
import mba.autodiff.func.Sin;
import mba.autodiff.func.Sum;

public class PrettyPrint implements Visitor<Optional<String>> {
	public String toString(Evaluatable<?> e) {
		class Noop implements Precedence {
			 public int precedence() {
				 return Integer.MIN_VALUE;
			}
		};
		
		return e.accept(this, new Noop()).orElse("");
	}
	
	@Override
	public Optional<String> visitMul(Mul f, Precedence parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable<?> l = f.getLeft();
		Evaluatable<?> r = f.getRight();
		
		if(IsNull.isNull(l) & IsNull.isNull(r))
			return Optional.empty();
		else if(IsNull.isNull(l) & !IsNull.isNull(r))
			builder.append(r.accept(this, f).get());
		else if(!IsNull.isNull(l) & IsNull.isNull(r))
			builder.append(l.accept(this, f).get());
		else if(!IsNull.isNull(l) && !IsNull.isNull(r)) {
			if(IsOne.isOne(l) && IsOne.isOne(r)) {
				builder.append("1");
			} else if(IsOne.isOne(l)) {
				builder.append(r.accept(this, f).get());
			} else if(IsOne.isOne(r)) {
				builder.append(l.accept(this, f).get());
			} else {
				if(parent.precedence() >= f.precedence())
					builder.append("(").append(l.accept(this, f).get()).append("*").append(r.accept(this, f).get()).append(")");
				else
					builder.append(l.accept(this, f).get()).append("*").append(r.accept(this, f).get());
			}
		}
		
		return Optional.of(builder.toString());
	}
	
	public Optional<String> visitAdd(Add add, Precedence parent) {
		Evaluatable<?> l = add.getLeft();
		Evaluatable<?> r = add.getRight();
		
		StringBuilder builder = new StringBuilder();
		
		if(IsNull.isNull(l) & IsNull.isNull(r))
			return Optional.empty();
		else if(IsNull.isNull(l) & !IsNull.isNull(r))
			builder.append(r.accept(this, add).get());
		else if(!IsNull.isNull(l) & IsNull.isNull(r))
			builder.append(l.accept(this, add).get());
		else if(!IsNull.isNull(l) && !IsNull.isNull(r)) {
			if(parent.precedence() >= add.precedence())
				builder.append("(").append(l.accept(this, add).get()).append("+").append(r.accept(this, add).get()).append(")");
			else
				builder.append(l.accept(this, add).get()).append("+").append(r.accept(this, add).get());
		}
		
		return Optional.of(builder.toString());
	}

	@Override
	public Optional<String> visitCos(Cos f, Precedence parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable<?> e = f.getE();
		builder.append("cos(").append(e.accept(this, f).get()).append(")");
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitNExp(NExp f, Precedence parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> visitPow(Pow f, Precedence parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> visitSin(Sin f, Precedence parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable<?> e = f.getE();
		builder.append("sin(").append(e.accept(this, f).get()).append(")");
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitSum(Sum f, Precedence parent) {
		StringBuilder builder = new StringBuilder();

		Iterator<Evaluatable<Double>> iterator = Stream.of(f.getSummands()).iterator();

		for(; iterator.hasNext() ;) {
			Optional<String> v = iterator.next().accept(this, f);
			v.ifPresent(g -> builder.append(g));
			if(iterator.hasNext())
				builder.append("+");
		}
		
		if(builder.length() != 0 && parent instanceof Mul)
			builder.insert(0, "(").append(")");
		
		return Optional.of(builder.toString());
	}

	public Optional<String> visitVar(Var f, Precedence parent) {
		return Optional.of(f.getName());
	}

	public Optional<String> visitConst(Const f, Precedence parent) {
		return Optional.of(f.getVal().toString());
	}
}