package de.vitasystems;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.vitasystems.func.Add;
import de.vitasystems.func.Mul;

public class BackwardAutoDiffTest {
	private Ctx ctx;
	
	@BeforeEach
	public void setup() {
		ctx = Ctx.newCtx();
	}
	
	@Test
	public void diffBackwardAdd_1() {
		Var x = ctx.newVar("x");
		Add addX = new Add(x, x);
		
		x.bind(1d);
		addX.eval(ctx);
		addX.backward(1d, ctx);
		Assertions.assertEquals(2d, ctx.valueOfGrad(x));
	}
	
	@Test
	public void diffBackwardAdd_2() {
		Var x = ctx.newVar("x");
		Add addX = new Add(x, x);
		
		x.bind(4d);
		addX.eval(ctx);
		addX.backward(1d, ctx);
		Assertions.assertEquals(2d, ctx.valueOfGrad(x));
	}
	
	@Test
	public void backwardDiffConstAndVar() {
		Const c = ctx.newConst(10d);
		Var x = ctx.newVar("x");
		Add addX = new Add(c, x);
		
		x.bind(2d);
		addX.eval(ctx);
		addX.backward(1d, ctx);
		Assertions.assertEquals(1d, ctx.valueOfGrad(x));
	}
	
	@Test
	public void backwardDiffMul_1() {
		Var x = ctx.newVar("x");
		Mul mulX = new Mul(x, x);
		
		x.bind(2d);
		mulX.eval(ctx);
		mulX.backward(1d, ctx);
		Assertions.assertEquals(4d, ctx.valueOfGrad(x));
	}
	
	@Disabled
	@Test
	public void backwardDiffMul_2() {
		Var x = ctx.newVar("x");
		Mul mulX = new Mul(x, x);
		
		x.bind(3d);
		mulX.eval(ctx);
		mulX.backward(1d, ctx);
		Assertions.assertEquals(6d, mulX.backward(1d, ctx));
	}
	
	@Test
	public void mul2() {
		Var x = ctx.newVar("x");
		Var y = ctx.newVar("y");
		Mul mulX = new Mul(x, y);
		
		x.bind(2d);
		y.bind(3d);
		mulX.eval(ctx);
		mulX.backward(1d, ctx);
		Assertions.assertEquals(3d, ctx.valueOfGrad(x));
		Assertions.assertEquals(2d, ctx.valueOfGrad(y));
	}}
