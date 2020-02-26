package mba.autodiff;

public interface ForwardDifferentiable {
	public Double forward(Var var, Ctx ctx);
}
