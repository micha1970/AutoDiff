package de.vitasystems.func.visitor;

import de.vitasystems.Const;
import de.vitasystems.Var;
import de.vitasystems.func.Add;
import de.vitasystems.func.Cos;
import de.vitasystems.func.Mul;
import de.vitasystems.func.NExp;
import de.vitasystems.func.Pow;
import de.vitasystems.func.Sin;
import de.vitasystems.func.Sum;

public interface Visitor<T> {
	public T visitAdd(Add f);
	public T visitCos(Cos f);
	public T visitMul(Mul f);
	public T visitNExp(NExp f);
	public T visitPow(Pow f);
	public T visitSin(Sin f);
	public T visitSum(Sum f);
	public T visitVar(Var f);
	public T visitConst(Const f);
}
