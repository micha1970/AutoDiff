package mba.autodiff;

public interface SymbolicDifferentiable {
	public Evaluatable<Double> symbolic(Var var, Ctx ctx);
}
