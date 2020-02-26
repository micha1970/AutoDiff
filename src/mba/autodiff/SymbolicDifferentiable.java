package mba.autodiff;

public interface SymbolicDifferentiable {
	public Evaluatable symbolic(Var var, Ctx ctx);
}
