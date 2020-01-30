package de.vitasystems.func.visitor;

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

public class IsNull implements Visitor<Boolean> {
	public static IsNull of() {
		return new IsNull();
	}
	
	public Boolean isNull(Evaluatable e) {
		return e.accept(this);
	}

	@Override
	public Boolean visitAdd(Add f) {
		return f.getLeft().accept(this) && f.getRight().accept(this);
	}

	@Override
	public Boolean visitCos(Cos f) {
		return f.getE().accept(this);
	}

	@Override
	public Boolean visitMul(Mul f) {
		return f.getLeft().accept(this) || f.getRight().accept(this);
	}

	@Override
	public Boolean visitNExp(NExp f) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean visitPow(Pow f) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean visitSin(Sin f) {
		return f.getE().accept(this);
	}

	@Override
	public Boolean visitSum(Sum f) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean visitVar(Var f) {
		return false;
	}

	@Override
	public Boolean visitConst(Const f) {
		return f.getVal() == 0d;
	}  
}