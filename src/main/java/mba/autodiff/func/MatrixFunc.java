package mba.autodiff.func;

import java.util.function.Function;

import lombok.Getter;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Matrix;
import mba.autodiff.Precedence;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

//:TODO
public class MatrixFunc implements Evaluatable<Matrix> {
	@Getter
	private final Matrix matrix;
	@Getter
	private final Function<Evaluatable<Double>, Evaluatable<Double>> funcConstructor;
	
	public MatrixFunc(Matrix matrix, Function<Evaluatable<Double>, Evaluatable<Double>> funcConstructor) {
		this.matrix = matrix;
		this.funcConstructor = funcConstructor;
	}

	
	@Override
	public Matrix eval(Ctx ctx) {
		Matrix funcMatrix = new Matrix("xyz", matrix.getRow(), matrix.getCol());
			funcMatrix.initVar(ctx);
		
		for(int r = 0; r < matrix.getRow(); r++) {
			for(int s = 0; s < matrix.getCol(); s++) {
				Evaluatable<Double> entry = matrix.getLookup()[r][s];
				funcMatrix.setEvaluatable(r, s, funcConstructor.apply(entry));
			}
		}
		
		return funcMatrix;       
	}

	@Override
	public Matrix forward(Var var, Ctx ctx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ctx backward(Double partGradient, Ctx g) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Evaluatable<Double> symbolic(Var var, Ctx ctx) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T accept(Visitor<T> visitor, Precedence parent) {
		throw new UnsupportedOperationException();
	}
}



