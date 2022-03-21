package mba.autodiff;

import java.util.Random;

public class MatrixInitialization {
	
	private static final String NAME_TEMPL = "%s_%d_%d";
	
	public static void init(Ctx ctx, Matrix matrix) {
		matrix.initVar(ctx);
		Evaluatable<Double>[][] lookup = matrix.getLookup();
		
		Random rnd = new Random();
		
		for(int r = 0; r < matrix.getRow(); r++)
			for(int c = 0; c < matrix.getCol(); c++) {
				Var var = ctx.newVar(String.format(NAME_TEMPL, matrix.getName(), r, c));
					var.bind(rnd(rnd));
				matrix.setEvaluatable(r, c, var);
		}
	}
	
	private static Double rnd(Random rnd) {
		return rnd.nextDouble();
	}
}