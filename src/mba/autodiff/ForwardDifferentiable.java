package mba.autodiff;

public interface ForwardDifferentiable<D> {
	public D forward(Var var, Ctx ctx);
}
