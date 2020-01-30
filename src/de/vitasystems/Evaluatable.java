package de.vitasystems;

import de.vitasystems.func.visitor.Visitor;

public interface Evaluatable extends ForwardDifferentiable, BackwardDifferentiable, SymbolicDifferentiable {
	public Double eval(Ctx ctx);
	public <T> T accept(Visitor<T> visitor);
}
