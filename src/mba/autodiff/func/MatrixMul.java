package mba.autodiff.func;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import mba.autodiff.Ctx;
import mba.autodiff.Evaluatable;
import mba.autodiff.Matrix;

public class MatrixMul {
	@Getter
	private final Matrix left;
	@Getter
	private final Matrix right;
	
	public MatrixMul(Matrix left, Matrix right) {
		this.left = left;
		this.right = right;
	}

	@SuppressWarnings("unused")
	public Matrix mul(Ctx ctx) {
		Matrix multiplied = new Matrix("xyz", left.getRow(), right.getCol());
			multiplied.initVar(ctx);
		
		for(int r = 0; r < left.getRow(); r++) {
			for(int s = 0; s < right.getCol(); s++) {
				Evaluatable evaluatable = multiply(ctx, r, s, left, right);
				Evaluatable evaluatable2 = multiplied.getLookup()[r][s];
				multiplied.setEvaluatable(r, s, evaluatable);
				Evaluatable[][] lookup = multiplied.getLookup();
//				lookup[r][s] = evaluatable;
//						= evaluatable;
				
			}
		}
		
		return multiplied;       
	}
	
	private Evaluatable multiply(Ctx ctx, int row, int col, Matrix m1, Matrix m2) {
		List<Evaluatable> sumands = new ArrayList<>();
		
		for(int s = 0; s < m2.getRow(); s++) {
			Evaluatable left = m1.getLookup()[row][s];
			Evaluatable right = m2.getLookup()[s][col];
			sumands.add(new Mul(left, right));
		}
		
		return new Sum(sumands);
	}
}



