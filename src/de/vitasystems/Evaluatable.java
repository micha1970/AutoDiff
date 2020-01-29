package de.vitasystems;

public interface Evaluatable extends ForwardDifferentiable, BackwardDifferentiable, SymbolicDifferentiable {
	public Double eval(Ctx ctx);
	public String toString();
}
