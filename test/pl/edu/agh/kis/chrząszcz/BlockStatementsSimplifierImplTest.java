package pl.edu.agh.kis.chrząszcz;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BlockStatementsSimplifierImplTest 
{
	static List<String> toSimplify = new ArrayList<String>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		toSimplify.add( "jeżeli(a==b){a=2*a;wywołajProcedurę;powrót;};" );
		toSimplify.add( "powtórz(5){a=3*a;wywołajProcedurę;};" );
		toSimplify.add( "dopóki(a==b)dopóty{a=2*a;wywołajProcedurę;powrót;};" );
		toSimplify.add( "dopóki(a==b)dopóty{powtórz(2){jeżeli(a>2b){b=b+1;wywołajProcedurę;};};wywołajProcedurę;powrót;};" );
	}

	@Test
	public void testSimplifyAllBlockStatements() 
	{
		BlockStatementsSimplifier simplifier = new BlockStatementsSimplifierImpl();
		List<String> simplified = simplifier.simplifyAllBlockStatements( toSimplify );
		
		for( String s : simplified )
			System.out.println( s );
		
		Assert.assertNotNull( simplified );
		Assert.assertFalse( simplified.contains( "powtórz(5){a=2*a;wywołajProcedurę;};" ) );
		Assert.assertFalse( simplified.contains( "dopóki(a==b)dopóty{powtórz(2){jeżeli(a>2b){b=b+1;wywołajProcedurę;};};wywołajProcedurę;powrót;};" ) );
		Assert.assertFalse( simplified.contains( "powtórz(2){jeżeli(a>2b){b=b+1;wywołajProcedurę;};};" ) );
		Assert.assertFalse( simplified.contains( "jeżeli(a>2b){b=b+1;wywołajProcedurę;};" ) );
		Assert.assertTrue( simplified.contains( "jeżeli!(a==b)et_0;" ) );
		Assert.assertTrue( simplified.contains( "&et_0&" ) );
		Assert.assertTrue( simplified.contains( "b=b+1;" ) );
		Assert.assertTrue( simplified.contains( "powrót;" ) );
		
	}

}
