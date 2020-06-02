package mba.autodiff;

import lombok.Getter;
import mba.autodiff.func.visitor.Visitor;

public class Matrix implements Evaluatable<Matrix> {
	@Getter
	private final String name;
	@Getter
	private final int row;
	@Getter
	private final int col;
	
	@Getter
	protected Evaluatable<Double>[][] lookup ;
	
	public Matrix(String name, int row, int col) {
		this.name = name;
		this.row = row;
		this.col = col;
	}
	
	public void initVar(Ctx ctx) {
		lookup = new Evaluatable[row][col];
	}
	
	public void setEvaluatable(int row, int col, Evaluatable<Double> eval) {
		lookup[row][col] = eval;
	}
	
	public Matrix eval(Ctx ctx) {
		Matrix multiplied = new Matrix("xyz", row, col);
		
		for(int r = 0; r < row; r++) {
			for(int s = 0; s < col; s++) {
				Double res = this.getLookup()[r][s].eval(ctx);
				multiplied.getLookup()[r][s] = new Const(res);
			}
		}
		
		return multiplied;       
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
	public <T> T accept(Visitor<T> visitor, Evaluatable<?> parent) {
		throw new UnsupportedOperationException();
	}
}
