package mba.autodiff.func.visitor;

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

public class IsNull implements Visitor<Boolean> {
	public static IsNull of() {
		return new IsNull();
	}
	
	public Boolean isNull(Evaluatable e, Evaluatable parent) {
		return e.accept(this, parent);
	}

	@Override
	public Boolean visitAdd(Add f, Evaluatable parent) {
		return f.getLeft().accept(this, parent) && f.getRight().accept(this, parent);
	}

	@Override
	public Boolean visitCos(Cos f, Evaluatable parent) {
		return f.getE().accept(this, parent);
	}

	@Override
	public Boolean visitMul(Mul f, Evaluatable parent) {
		return f.getLeft().accept(this, parent) || f.getRight().accept(this, parent);
	}

	@Override
	public Boolean visitNExp(NExp f, Evaluatable parent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean visitPow(Pow f, Evaluatable parent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean visitSin(Sin f, Evaluatable parent) {
		return f.getE().accept(this, parent);
	}

	@Override
	public Boolean visitSum(Sum f, Evaluatable parent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean visitVar(Var f, Evaluatable parent) {
		return false;
	}

	@Override
	public Boolean visitConst(Const f, Evaluatable parent) {
		return f.getVal() == 0d;
	}  
}