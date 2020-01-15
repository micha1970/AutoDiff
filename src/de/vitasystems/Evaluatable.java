package de.vitasystems;

public interface Evaluatable extends ForwardDifferentiable, BackwardDifferentiable {
	public Double eval();
}
