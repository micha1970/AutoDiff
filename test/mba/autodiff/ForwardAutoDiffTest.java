package mba.autodiff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mba.autodiff.Const;
import mba.autodiff.Ctx;
import mba.autodiff.Var;
import mba.autodiff.func.Add;
import mba.autodiff.func.Mul;

public class ForwardAutoDiffTest {
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
		Assertions.assertEquals(2d, addX.forward(x, ctx));
	}
	
	@Test
	public void diffForwardAdd_2() {
		Var x = ctx.newVar("x");
		Add addX = new Add(x, x);
		
		x.bind(4d);
		Assertions.assertEquals(2d, addX.forward(x, ctx));
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
		Assertions.assertEquals(1d, addX.forward(x, ctx));
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
		Assertions.assertEquals(4d, mulX.forward(x, ctx));
	}
	
	@Test
	public void forwadDiffMul_2() {
		Var x = ctx.newVar("x");
		Mul mulX = new Mul(x, x);
		
		x.bind(3d);
		Assertions.assertEquals(6d, mulX.forward(x, ctx));
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
}
