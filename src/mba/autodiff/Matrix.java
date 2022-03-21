package mba.autodiff;

import java.util.HashSet;
import java.util.Set;

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
	
	public Set<Var> getFlat() {
		Set<Var> flat = new HashSet<>();
		
		for(int r = 0; r < row; r++)
			for(int s = 0; s < col; s++) {
				Evaluatable<Double> e = this.getLookup()[r][s];
				if(!(e instanceof Var))
					throw new IllegalArgumentException();
				flat.add((Var) this.getLookup()[r][s]);
			}
		return flat;
	}
	
	
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
		Matrix multiplied = new Matrix("xyz", row, col);
		
		for(int r = 0; r < row; r++) {
			for(int s = 0; s < col; s++) {
				Double res = this.getLookup()[r][s].forward(var, ctx);
				multiplied.getLookup()[r][s] = new Const(res);
			}
		}
		
		return multiplied;
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
