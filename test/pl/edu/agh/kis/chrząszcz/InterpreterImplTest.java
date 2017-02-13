package pl.edu.agh.kis.chrząszcz;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;

public class InterpreterImplTest 
{
	static List<String> inst;
	static Map<String,Integer> vars;
	static Map<String,EmbeddedCommand> ec;
	static Interpreter interpreter = new InterpreterImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		inst = new ArrayList<String>();
		
		inst.add( "&suma&" );
		inst.add( "WypiszSum;" );
		inst.add( "c=a+b;" );
		inst.add( "powrót;" );
		
		inst.add( "&główna&" );
		inst.add( "jeżeli(b)suma;" );
		inst.add( "jeżeli(a)suma;" );
		inst.add( "rekurencja;" );
		
		inst.add( "powrót;" );
		
		inst.add( "&rekurencja&" );
		inst.add( "dekrementacja;" );
		inst.add( "WypiszRek;" );
		inst.add( "jeżeli(a)rekurencja;" );
		inst.add( "powrót;" );
		
		vars = new HashMap<String,Integer>();
		
		vars.put( "a" , 3 );
		vars.put( "b" , 0 ); 
		vars.put( "c" , 1 );
		
		ec = new HashMap<String,EmbeddedCommand>();
		
		ec.put( "WypiszSum;" , () -> System.out.println( "Jestem wewnatrz funkcji suma" ) );
		ec.put( "dekrementacja;" , () -> { interpreter.getVars().replace("a", interpreter.getVars().get("a")-1 ); } );
		ec.put( "WypiszRek;" , () -> { System.out.println( "Jestem wewnatrz wywolania rekurencyjnego" ); } );	
		
	}

	@Before
	public void setUp() throws Exception 
	{
		vars.replace( "a" , 3 );
		vars.replace( "b" , 0 ); 
		vars.replace( "c" , 1 );
	}

	@Test
	public void testSetVars() 
	{	
		interpreter.setVars( vars );
		
		Assert.assertNotNull( interpreter.getVars() );
		Assert.assertEquals( 3 , interpreter.getVars().keySet().size() );
		Assert.assertSame( vars , interpreter.getVars() );
		int c = interpreter.getVars().get( "c" );
		Assert.assertEquals( 1 , c );
		
	}

	@Test
	public void testRun() 
	{
		interpreter.setInstructions( inst );
		interpreter.setVars( vars );
		interpreter.setEmbeddedCommands( ec );
		interpreter.setStart("");
		interpreter.run();
		
		int c = vars.get( "c" );
		Assert.assertEquals( 3 , c );
		int a = vars.get( "a" );
		Assert.assertEquals( 0 , a );	
	}

}
