package mba.autodiff.func.visitor;

import java.util.Optional;

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
	public String toSting(Evaluatable e) {
		return e.accept(this, e).orElse("");
	}
	
	private Boolean isNull(Evaluatable e) {
		if(!(e instanceof Const))
			return false;
		return ((Const) e).getVal() == 0d;
	}
	
	private Boolean isOne(Evaluatable e) {
		if(!(e instanceof Const))
			return false;
		return ((Const) e).getVal() == 1d;
	}
	
	public Optional<String> visitAdd(Add f, Evaluatable parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable l = f.getLeft();
		Evaluatable r = f.getRight();
		
		if(!IsNull.of().isNull(l, parent) && !IsNull.of().isNull(r, parent)) {
			builder.append(l.accept(this, f).get()).append(" + ").append(r.accept(this, f).get());
			if(parent instanceof Mul) {
				builder.insert(0, " (").append(") ");
			}
		}
		else if(!IsNull.of().isNull(l, f)) {
			builder.append(l.accept(this, f).get());
		} else if(!IsNull.of().isNull(r, f)) {
			builder.append(r.accept(this, f).get());
		}
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitCos(Cos f, Evaluatable parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable e = f.getE();
		builder.append("cos(").append(e.accept(this, parent).get()).append(")");
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitMul(Mul f, Evaluatable parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable l = f.getLeft();
		Evaluatable r = f.getRight();
		
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
	public Optional<String> visitNExp(NExp f, Evaluatable parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> visitPow(Pow f, Evaluatable parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> visitSin(Sin f, Evaluatable parent) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable e = f.getE();
		builder.append("sin(").append(e.accept(this, parent).get()).append(")");
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitSum(Sum f, Evaluatable parent) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<String> visitVar(Var f, Evaluatable parent) {
		return Optional.of(f.getName());
	}

	public Optional<String> visitConst(Const f, Evaluatable parent) {
		return Optional.of(f.getVal().toString());
	}
}