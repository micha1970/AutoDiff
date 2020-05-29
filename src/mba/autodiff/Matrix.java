package mba.autodiff;

import lombok.Getter;

public class Matrix {
	@Getter
	private final String name;
	@Getter
	private final int row;
	@Getter
	private final int col;
	
	@Getter
	protected Evaluatable[][] lookup ;
	
	public Matrix(String name, int row, int col) {
		this.name = name;
		this.row = row;
		this.col = col;
	}
	
	private static final String NAME_TEMPL = "%s_%d_%d";
	
	public void initVar(Ctx ctx) {
		lookup = new Evaluatable[row][col];
		for(int r = 0; r < row; r++) for(int c = 0; c < col; c++) {
			String varName = String.format(NAME_TEMPL, name, r, c);
			Var var = ctx.newVar(varName);
//static
var.bind(2.0);
			lookup[r][c] = var;
		}
	}
	
	public void setEvaluatable(int row, int col, Evaluatable eval) {
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
}
