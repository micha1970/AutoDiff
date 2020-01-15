package de.vitasystems;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AutoDiffTest {

	@Test
	void add1() {
		Var x = new Var("x");
		Add addX = new Add(x, x);
		
		x.bind(2d);
		Assertions.assertEquals(4d, addX.eval());
		x.bind(1d);
		Assertions.assertEquals(2d, addX.eval());
		
		x.bind(1d);
		Assertions.assertEquals(2d, addX.forward(x));
		
		x.bind(4d);
		Assertions.assertEquals(2d, addX.forward(x));
	}
	
	@Test
	void add2() {
		Const c = new Const(10d);
		Var x = new Var("x");
		Add addX = new Add(c, x);
		
		x.bind(2d);
		Assertions.assertEquals(12d, addX.eval());
		
		x.bind(2d);
		Assertions.assertEquals(1d, addX.forward(x));
	}
	
	@Test
	void mul1() {
		Var x = new Var("x");
		Mul mulX = new Mul(x, x);
		
		x.bind(2d);
		Assertions.assertEquals(4d, mulX.eval());
		
		x.bind(4d);
		Assertions.assertEquals(16d, mulX.eval());
		
		x.bind(2d);
		Assertions.assertEquals(4d, mulX.forward(x));
		
		x.bind(3d);
		Assertions.assertEquals(6d, mulX.forward(x));
	}
	
	@Test
	void mul2() {
		Var x = new Var("x");
		Var y = new Var("y");
		Mul mulX = new Mul(x, y);
		
		x.bind(2d);
		y.bind(3d);
		Assertions.assertEquals(6d, mulX.eval());
		
//		x.bind(2d);
		Assertions.assertEquals(3d, mulX.forward(x));
	}
}
