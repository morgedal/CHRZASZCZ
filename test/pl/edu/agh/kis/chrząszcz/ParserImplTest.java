package pl.edu.agh.kis.chrząszcz;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest 
{
	static Parser parser;
	static String code;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		StringBuilder c = new StringBuilder();
		c.append( " procedura główna{ zmienna a; #komentarz#zmienna b; zmienna c; a=6; dopóki(a>0)dopóty{ a=a-1; idź; }; suma; kwadrat; powrót; }$ " );
		c.append( "procedura suma{ c=a+b;# zmienna d; inny komentarz lalalalaallallala# powrót;}$" );
		c.append( "procedura kwadrat{ powtórz(4){ idź; idź; skręćWPrawo; powrót;}; }$" );
		code = c.toString();
	}

	@Before
	public void setUp() throws Exception 
	{
		parser = new ParserImpl( code );
		parser.parse();
	}

	@Test
	public void testGetVarsMap() 
	{
		Map<String,Integer> vars = parser.getVarsMap();
		
		Assert.assertNotNull( vars );
		Assert.assertEquals( 3 , vars.size() );
		Assert.assertTrue( vars.containsKey( "a" ) );
		Assert.assertTrue( vars.containsKey( "b" ) );
		Assert.assertTrue( vars.containsKey( "c" ) );
		Assert.assertFalse( vars.containsKey( "d" ) );

		int value = vars.get( "a" );
		Assert.assertEquals( 0 , value );
		value = vars.get( "b" );
		Assert.assertEquals( 0 , value );
		value = vars.get( "c" );
		Assert.assertEquals( 0 , value );
		
	}

	@Test
	public void testGetInstructionsList() 
	{
		List<String> instructions = parser.getInstructionsList();
		
		for( String s : instructions )
			System.out.println( s );
		
		Assert.assertNotNull( instructions );
		Assert.assertFalse( instructions.contains( "procedura główna" ) );
		Assert.assertFalse( instructions.contains( "$" ) );
		Assert.assertTrue( instructions.contains( "&główna&" ) );
		Assert.assertTrue( instructions.contains( "&suma&" ) );
		Assert.assertTrue( instructions.contains( "powrót;" ) );
		Assert.assertFalse( instructions.contains( "powrót;}" ) );
		Assert.assertFalse( instructions.contains( "zmienna a;" ) );
		Assert.assertFalse( instructions.contains( "zmienna d;" ) );
		Assert.assertTrue( instructions.contains( "kwadrat;" ) );
		Assert.assertFalse( instructions.contains( "powtórz(4){ idź; idź; skręćWPrawo; powrót;};" ) );
		Assert.assertFalse( instructions.contains( "dopóki(a>0)dopóty{ a=a-1; idź; };" ) );
		
	}


}
