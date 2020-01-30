package de.vitasystems.func.visitor;

import java.util.Optional;

import de.vitasystems.Const;
import de.vitasystems.Evaluatable;
import de.vitasystems.Var;
import de.vitasystems.func.Add;
import de.vitasystems.func.Cos;
import de.vitasystems.func.Mul;
import de.vitasystems.func.NExp;
import de.vitasystems.func.Pow;
import de.vitasystems.func.Sin;
import de.vitasystems.func.Sum;

public class PrettyPrint implements Visitor<Optional<String>> {
	public String toSting(Evaluatable e) {
		return e.accept(this).orElse("");
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
	
	public Optional<String> visitAdd(Add f) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable l = f.getLeft();
		Evaluatable r = f.getRight();
		
		if(!IsNull.of().isNull(l) && !IsNull.of().isNull(r))
			builder.append("{").append(l.accept(this).get()).append(" + ").append(r.accept(this).get()).append("}");
		else if(!IsNull.of().isNull(l)) {
			builder.append(l.accept(this).get());
		} else if(!IsNull.of().isNull(r)) {
			builder.append(r.accept(this).get());
		}
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitCos(Cos f) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable e = f.getE();
		builder.append("cos(").append(e.accept(this).get()).append(")");
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitMul(Mul f) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable l = f.getLeft();
		Evaluatable r = f.getRight();
		
		if(!isNull(l) && !isNull(r)) {
			if(isOne(l) && isOne(r)) {
				builder.append("1");
			} else if(isOne(l)) {
				builder.append(r.accept(this).get());
			} else if(isOne(r)) {
				builder.append(l.accept(this).get());
			} else
				builder.append(l.accept(this).get()).append(" * ").append(r.accept(this).get());
		}
		else if(isNull(l))
			builder.append(r.accept(this).get());
		else if(isNull(r))
			builder.append(l.accept(this).get());
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitNExp(NExp f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> visitPow(Pow f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> visitSin(Sin f) {
		StringBuilder builder = new StringBuilder();
		
		Evaluatable e = f.getE();
		builder.append("sin(").append(e.accept(this).get()).append(")");
		
		return builder.length() == 0 ? Optional.empty() : Optional.ofNullable(builder.toString());
	}

	@Override
	public Optional<String> visitSum(Sum f) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<String> visitVar(Var f) {
		return Optional.of(f.getName());
	}

	public Optional<String> visitConst(Const f) {
		return Optional.of(f.getVal().toString());
	}
}