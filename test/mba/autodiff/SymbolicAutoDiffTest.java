package mba.autodiff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mba.autodiff.Const;
import mba.autodiff.Ctx;
import mba.autodiff.Var;
import mba.autodiff.func.Add;
import mba.autodiff.func.Cos;
import mba.autodiff.func.Mul;
import mba.autodiff.func.Sin;
import mba.autodiff.func.visitor.PrettyPrint;

public class SymbolicAutoDiffTest {
	private Ctx ctx;
	
	@BeforeEach
	public void setup() {
		ctx = Ctx.newCtx();
	}
	
	@Test
	public void add_1() {
		Var x = ctx.newVar("x");
		Add addX = new Add(x, x);
		
		x.bind(2d);
		Assertions.assertEquals(4d, addX.eval(ctx));
	}
	
	@Test
	public void add_2() {
		Var x = ctx.newVar("x");
		Add addX = new Add(x, x);
		
		x.bind(1d);
		Assertions.assertEquals(2d, addX.eval(ctx));
	}
	
	@Test
	public void diffForwardAdd_1() {
		Var x = ctx.newVar("x");
		Add addX = new Add(x, x);
		
		x.bind(1d);
		System.out.println(new PrettyPrint().toSting(addX.symbolic(x, ctx)));
	}
	
	@Test
	public void diffForwardAdd_2() {
		Var x = ctx.newVar("x");
		Add addX = new Add(x, x);
		
		x.bind(4d);
		System.out.println(new PrettyPrint().toSting(addX.symbolic(x, ctx)));
	}
	
	@Test
	public void evalConstAndVar() {
		Const c = ctx.newConst(10d);
		Var x = ctx.newVar("x");
		Add addX = new Add(c, x);
		
		x.bind(2d);
		Assertions.assertEquals(12d, addX.eval(ctx));
	}
	
	@Test
	public void forwardDiffConstAndVar() {
		Const c = ctx.newConst(10d);
		Var x = ctx.newVar("x");
		Add addX = new Add(c, x);
		
		x.bind(2d);
		System.out.println(new PrettyPrint().toSting(addX.symbolic(x, ctx)));
	}
	
	@Test
	public void evalMul_1() {
		Var x = new Var("x");
		Mul mulX = new Mul(x, x);
		
		x.bind(2d);
		Assertions.assertEquals(4d, mulX.eval(ctx));
	}
	
	@Test
	public void evalMul_2() {
		Var x = ctx.newVar("x");
		Mul mulX = new Mul(x, x);
		
		x.bind(4d);
		Assertions.assertEquals(16d, mulX.eval(ctx));
	}
	
	@Test
	public void forwadDiffMul_1() {
		Var x = ctx.newVar("x");
		Mul mulX = new Mul(x, x);
		
		x.bind(2d);
		System.out.println(new PrettyPrint().toSting(mulX.symbolic(x, ctx)));
	}
	
	@Test
	public void forwadDiffMul_2() {
		Var x = ctx.newVar("x");
		Mul mulX = new Mul(x, x);
		
		x.bind(3d);
		System.out.println(new PrettyPrint().toSting(mulX.symbolic(x, ctx)));
	}
	
	@Test
	public void mul2() {
		Var x = ctx.newVar("x");
		Var y = ctx.newVar("y");
		Mul mulX = new Mul(x, y);
		
		x.bind(2d);
		y.bind(3d);
		Assertions.assertEquals(6d, mulX.eval(ctx));
	}
	
	@Test
	public void trigonometric1() {
		Var x = ctx.newVar("x");
		Sin sin = new Sin(x);
		
		x.bind(2d);
		Assertions.assertEquals(Math.sin(2d), sin.eval(ctx));
	}
	
	@Test
	public void trigonometric2() {
		Var x = ctx.newVar("x");
		Var y = ctx.newVar("y");
		Mul m = new Mul(x, y);
		
		Sin sin = new Sin(m);
		
		x.bind(2d);
		y.bind(3d);
		
		System.out.println(new PrettyPrint().toSting(sin.symbolic(x, ctx)));
	}
	
	
	@Test
	public void trigonometric3() {
		Var x = ctx.newVar("x");
		Var y = ctx.newVar("y");
		
		Cos cos = new Cos(y);
		Mul m = new Mul(x, cos);
		Mul m1 = new Mul(x, m);
		
		Sin sin = new Sin(m1);
		
		x.bind(2d);
		y.bind(3d);
		
		System.out.println(new PrettyPrint().toSting(sin.symbolic(x, ctx)));
	}
	
	
	
	
}
