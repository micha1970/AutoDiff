package mba.autodiff;

public interface BackwardDifferentiable {
	public Ctx backward(Double partGradient, Ctx g);
}
