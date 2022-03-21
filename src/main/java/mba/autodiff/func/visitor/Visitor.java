package mba.autodiff.func.visitor;

import mba.autodiff.Const;
import mba.autodiff.Precedence;
import mba.autodiff.Var;
import mba.autodiff.func.Add;
import mba.autodiff.func.Cos;
import mba.autodiff.func.Mul;
import mba.autodiff.func.NExp;
import mba.autodiff.func.Pow;
import mba.autodiff.func.Sin;
import mba.autodiff.func.Sum;

public interface Visitor<T> {
	public T visitAdd(Add f, Precedence paraent);
	public T visitCos(Cos f, Precedence paraent);
	public T visitMul(Mul f, Precedence paraent);
	public T visitNExp(NExp f, Precedence paraent);
	public T visitPow(Pow f, Precedence paraent);
	public T visitSin(Sin f, Precedence paraent);
	public T visitSum(Sum f, Precedence paraent);
	public T visitVar(Var f, Precedence paraent);
	public T visitConst(Const f, Precedence paraent);
}
