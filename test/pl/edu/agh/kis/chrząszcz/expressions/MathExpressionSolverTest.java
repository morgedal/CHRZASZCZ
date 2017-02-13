package pl.edu.agh.kis.chrząszcz.expressions;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;

public class MathExpressionSolverTest 
{
	static Map<String,Integer> variables = new HashMap<String,Integer>(); 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		variables.put( "a", 0 );
		variables.put( "b" , 0 );
		variables.put( "c" , 1 );
		variables.put( "d" , 5 );
		variables.put( "e" , 0 );
	}

	@Test
	public void testSolve() 
	{
		Solver solver = new MathExpressionSolver( variables );
		
		//dzialania na stalych
		Assert.assertEquals( solver.solve( "0+0" ) , 0 );
		Assert.assertEquals( solver.solve( "1+2" ) , 3 );
		Assert.assertEquals( solver.solve( "1-2" ) , -1 );
		Assert.assertEquals( solver.solve( "2*2" ) , 4 );
		Assert.assertEquals( solver.solve( "4/2" ) , 2 );
		Assert.assertEquals( solver.solve( "2+3+4" ) , 9 );
		Assert.assertEquals( solver.solve( "2+3*4" ) , 14 );
		Assert.assertEquals( solver.solve( "a+2*2" ) , 4 );
		Assert.assertEquals( solver.solve( "2>0" ) , 1 );
		Assert.assertEquals( solver.solve( "0>=0" ) , 1 );
		Assert.assertEquals( solver.solve( "0!=0" ) , 0 );
		Assert.assertEquals( solver.solve( "0==0" ) , 1 );
		
		//dzialania na zmiennych
		Assert.assertEquals( solver.solve( "a+b" ) , 0 );
		Assert.assertEquals( solver.solve( "c+d" ) , 6 );
		Assert.assertEquals( solver.solve( "a>=c" ) , 0 );
		Assert.assertEquals( solver.solve( "a==a" ) , 1 );
		Assert.assertEquals( solver.solve( "c<d" ) , 1 );
		Assert.assertEquals( solver.solve( "a==a==a" ) , 0 );
		Assert.assertEquals( solver.solve( "a==1==a" ) , 1 );
		Assert.assertEquals( solver.solve( "2>0>a" ) , 1 );
		Assert.assertEquals( solver.solve( "a>=a+a" ) , 1 );
		Assert.assertEquals( solver.solve( "a>a+a" ) , 0 );
		
		// operatory przypisania
		Assert.assertEquals( solver.solve( "a=b+c" ) , 1 );
		Assert.assertEquals( solver.solve( "c=2" ) , 1 );
		Assert.assertEquals( solver.solve( "d=0>1" ) , 1 );
		Assert.assertEquals( solver.solve( "e=!0+c" ) , 0 );
		Assert.assertEquals( solver.solve( "e=!e" ) , 1 );
		
		int a = variables.get("a");
		int b = variables.get("b");
		int c = variables.get("c");
		int d = variables.get("d");
		int e = variables.get("e");
		
		Assert.assertEquals( a , 1 );
		Assert.assertEquals( b , 0 );
		Assert.assertEquals( c , 2 );
		Assert.assertEquals( d , 0 );
		Assert.assertEquals( e , 1 );
		
	}

}
