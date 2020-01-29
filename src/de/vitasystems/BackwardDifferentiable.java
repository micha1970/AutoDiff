package de.vitasystems;

public interface BackwardDifferentiable {
	public Ctx backward(Double partGradient, Ctx g);
}
