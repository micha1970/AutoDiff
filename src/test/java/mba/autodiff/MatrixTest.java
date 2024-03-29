package mba.autodiff;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mba.autodiff.func.MatrixFunc;
import mba.autodiff.func.MatrixMul;
import mba.autodiff.func.Sin;
import mba.autodiff.func.visitor.PrettyPrint;

public class MatrixTest {
	private Ctx ctx;
	
	@BeforeEach
	public void setup() {
		ctx = Ctx.newCtx();
	}
	
	@Test
	public void matrixMul() {
		Matrix m1 = new Matrix("M1", 2, 3);
		MatrixInitialization.init(ctx, m1);
		
		Matrix m2 = new Matrix("M2", 3, 2);
		MatrixInitialization.init(ctx, m2);
			
		MatrixMul matrixMul = new MatrixMul(m1, m2);
		Matrix mul = matrixMul.eval(ctx);
		
		PrettyPrint pp = new PrettyPrint();
		 
		for(int r = 0; r < mul.getCol(); r++)
			for(int c = 0; c < mul.getCol(); c++) {
				System.out.print(pp.toString(mul.getLookup()[r][c]) + "   ~   ");
				System.out.println("M[" + r + "][" + c + "] = " + mul.getLookup()[r][c].eval(ctx));
			}
		
		
		System.out.println("");
	}
	
	@Test
	public void funvMatrix() {
		Matrix m1 = new Matrix("M1", 2, 3);
		MatrixInitialization.init(ctx, m1);
		Matrix m2 = new Matrix("M2", 3, 2);
		MatrixInitialization.init(ctx, m2);
			
		MatrixMul matrixMul = new MatrixMul(m1, m2);
		Matrix mul = matrixMul.eval(ctx);
		
		MatrixFunc matricFunc = new MatrixFunc(mul, e -> new Sin(e));
		mul = matricFunc.eval(ctx);
		final Matrix fmul = mul;
		PrettyPrint pp = new PrettyPrint();
		
		
		Stream<Var> stream = m1.getFlat().stream();
		
		@SuppressWarnings("unused")
		long count = stream
			.filter(x -> true)
			.peek((Var v) -> {
				System.out.println();
				for(int r = 0; r < fmul.getCol(); r++) {
					for(int c = 0; c < fmul.getCol(); c++) {
//						Double forward = fmul.getLookup()[r][c].forward(v, ctx);
//						System.out.print(pp.toString(fmul.getLookup()[r][c]) + "   ~   ");
						Evaluatable<Double> symbolic = fmul.getLookup()[r][c].symbolic(v, ctx);
						System.out.println("(d/d"+ v.getName() +  ")M[" + r + "][" + c + "] = " + pp.toString(symbolic));
					}
				}
			})
			.count();
		
//		for(int r = 0; r < mul.getCol(); r++)
//			for(int c = 0; c < mul.getCol(); c++) {
//				System.out.print(pp.toString(mul.getLookup()[r][c]) + "   ~   ");
//				System.out.println("M[" + r + "][" + c + "] = " + mul.getLookup()[r][c].eval(ctx));
//			}
		
		System.out.println("");
	}
	
//	@Test
//	public void matrixVectorMul() {
//		Matrix m1 = new Matrix("M1", 2, 3);
//			m1.initVar(ctx);
//		Vector m2 = new Vector("V1", 3);
//			m2.initVar(ctx);
//		Vector m3 = m1.mul(ctx, m2);
//		
//		System.out.println("");
//		
//		
//	}
}
