package mba.autodiff.func.visitor;

import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IsNull implements Visitor<Boolean> {
	public static boolean isNull(Evaluatable<?> e) {
		return new IsNull().isNull(e, null);
	}
	
	public Boolean isNull(Evaluatable<?> e, Evaluatable<?> parent) {
		return e.accept(this, parent);
	}

	@Override
	public Boolean visitAdd(Add f, Precedence parent) {
		return f.getLeft().accept(this, parent) && f.getRight().accept(this, parent);
	}

	@Override
	public Boolean visitCos(Cos f, Precedence parent) {
		return f.getE().accept(this, parent);
	}

	@Override
	public Boolean visitMul(Mul f, Precedence parent) {
		return f.getLeft().accept(this, parent) || f.getRight().accept(this, parent);
	}

	@Override
	public Boolean visitNExp(NExp f, Precedence parent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean visitPow(Pow f, Precedence parent) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean visitSin(Sin f, Precedence parent) {
		return f.getE().accept(this, parent);
	}

	@Override
	public Boolean visitSum(Sum f, Precedence parent) {
		return Stream.of(f.getSummands())
			.map(e -> IsNull.isNull(e))
			.reduce(true, (l1, l2) -> l1 && l2);
	}

	@Override
	public Boolean visitVar(Var f, Precedence parent) {
		return false;
	}

	@Override
	public Boolean visitConst(Const f, Precedence parent) {
		return f.getVal() == 0d;
	}  
}