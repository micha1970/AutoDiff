package mba.autodiff;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mba.autodiff.func.MatrixMul;

public class MatrixTest {
	private Ctx ctx;
	
	@BeforeEach
	public void setup() {
		ctx = Ctx.newCtx();
	}
	
	@Test
	public void matrixMul() {
		Matrix m1 = new Matrix("M1", 2, 3);
			m1.initVar(ctx);
		Matrix m2 = new Matrix("M2", 3, 2);
			m2.initVar(ctx);
			
			
		MatrixMul matrixMul = new MatrixMul(m1, m2);
		Matrix mul = matrixMul.mul(ctx);
		
		
		
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
