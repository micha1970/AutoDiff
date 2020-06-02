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

public interface Visitor<T> {
	public T visitAdd(Add f, Evaluatable<?> paraent);
	public T visitCos(Cos f, Evaluatable<?> paraent);
	public T visitMul(Mul f, Evaluatable<?> paraent);
	public T visitNExp(NExp f, Evaluatable<?> paraent);
	public T visitPow(Pow f, Evaluatable<?> paraent);
	public T visitSin(Sin f, Evaluatable<?> paraent);
	public T visitSum(Sum f, Evaluatable<?> paraent);
	public T visitVar(Var f, Evaluatable<?> paraent);
	public T visitConst(Const f, Evaluatable<?> paraent);
}
