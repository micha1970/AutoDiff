package mba.autodiff;

import mba.autodiff.func.visitor.Visitor;

public interface Evaluatable<D> extends ForwardDifferentiable<D>, BackwardDifferentiable, SymbolicDifferentiable, Precedence {
	
	
	public D eval(Ctx ctx);
	public <T> T accept(Visitor<T> visitor, Precedence parent);
}
