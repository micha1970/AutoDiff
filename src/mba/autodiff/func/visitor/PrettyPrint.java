package mba.autodiff.func.visitor;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import mba.autodiff.Const;
import mba.autodiff.Evaluatable;
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
		return e.accept(this, e).orElse("");
	}
	
	private Boolean isNull(Evaluatable<?> e) {
		if(!(e instanceof Const))
			return false;
		return ((Const) e).getVal() == 0d;
	}
	
	private Boolean isOne(Evaluatable<?> e) {
		if(!(e instanceof Const))
			return false;
		return ((Const) e).getVal() == 1d;
	}
	
	public Optional<String> visitAdd(Add add, Evaluatable<?> parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable<?> l = add.getLeft();
		Evaluatable<?> r = add.getRight();
		
		if(!IsNull.of().isNull(l, parent) && !IsNull.of().isNull(r, parent)) {
			builder.append(l.accept(this, add).get()).append(" + ").append(r.accept(this, add).get());
			if(parent instanceof Mul) {
				builder.insert(0, " (").append(") ");
			}
		}
		else if(!IsNull.of().isNull(l, add)) {
			builder.append(l.accept(this, add).get());
		} else if(!IsNull.of().isNull(r, add)) {
			builder.append(r.accept(this, add).get());
		}
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitCos(Cos f, Evaluatable<?> parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable<?> e = f.getE();
		builder.append("cos(").append(e.accept(this, parent).get()).append(")");
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitMul(Mul f, Evaluatable<?> parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable<?> l = f.getLeft();
		Evaluatable<?> r = f.getRight();
		
		if(!isNull(l) && !isNull(r)) {
			if(isOne(l) && isOne(r)) {
				builder.append("1");
			} else if(isOne(l)) {
				builder.append(r.accept(this, f).get());
			} else if(isOne(r)) {
				builder.append(l.accept(this, f).get());
			} else
				builder.append(l.accept(this, f).get()).append(" * ").append(r.accept(this, f).get());
		}
		else if(isNull(l))
			builder.append(r.accept(this, f).get());
		else if(isNull(r))
			builder.append(l.accept(this, f).get());
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitNExp(NExp f, Evaluatable<?> parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> visitPow(Pow f, Evaluatable<?> parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> visitSin(Sin f, Evaluatable<?> parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable<?> e = f.getE();
		builder.append("sin(").append(e.accept(this, parent).get()).append(")");
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitSum(Sum f, Evaluatable<?> parent) {
		StringBuilder builder = new StringBuilder();

		Iterator<Evaluatable<Double>> iterator = Stream.of(f.getSummands()).iterator();

		for(; iterator.hasNext() ;) {
			builder.append(iterator.next().accept(this, parent).get());
			if(iterator.hasNext())
				builder.append(" + ");
		}
		
		if(builder.length() != 0 && parent instanceof Mul)
			builder.insert(0, " (").append(") ");
		
		return Optional.of(builder.toString());
	}

	public Optional<String> visitVar(Var f, Evaluatable<?> parent) {
		return Optional.of(f.getName());
	}

	public Optional<String> visitConst(Const f, Evaluatable parent) {
		return Optional.of(f.getVal().toString());
	}
}