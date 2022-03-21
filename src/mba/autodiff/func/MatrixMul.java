package mba.autodiff.func;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Matrix;
import mba.autodiff.Precedence;
import mba.autodiff.Var;
import mba.autodiff.func.visitor.Visitor;

public class MatrixMul implements Evaluatable<Matrix> {
	@Getter
	private final Matrix left;
	@Getter
	private final Matrix right;
	
	public MatrixMul(Matrix left, Matrix right) {
		this.left = left;
		this.right = right;
	}

	private static final String NAME_TEMPL = "%s_%s";
	
	@Override
	public Matrix eval(Ctx ctx) {
		Matrix multiplied = new Matrix(String.format(NAME_TEMPL, left.getName(), right.getName()), left.getRow(), right.getCol());
			multiplied.initVar(ctx);
		
		for(int r = 0; r < left.getRow(); r++) {
			for(int s = 0; s < right.getCol(); s++) {
				multiplied.setEvaluatable(
					r,
					s,
					multiply(ctx, r, s, left, right)
				);
			}
		}
		
		return multiplied;       
	}
	
	private Evaluatable<Double> multiply(Ctx ctx, int row, int col, Matrix m1, Matrix m2) {
		List<Evaluatable<Double>> sumands = new ArrayList<>();

		for(int s = 0; s < m2.getRow(); s++)
			sumands.add(new Mul(m1.getLookup()[row][s], m2.getLookup()[s][col]));
		
		return new Sum(sumands);
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



