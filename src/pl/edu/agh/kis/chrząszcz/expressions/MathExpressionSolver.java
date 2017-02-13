package pl.edu.agh.kis.chrząszcz.expressions;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Maciej Rajs
 * Implementacja interfesju solver do przeprowadzania prostych operacji matematycznych
 */
public class MathExpressionSolver implements Solver
{
	private Map<String,Integer> vars;
	
	/**
	 * @param vars refrencja do zmiennych wykorzystywanych w programie
	 */
	public MathExpressionSolver( Map<String,Integer> vars )
	{
		this.vars = vars;
	}
	
	/**
	 * @return Zwraca referencje do zmiennych wykorzystywanych w programie
	 */
	public Map<String,Integer> getVars()
	{
		return vars;
	}
	
	/* 
	 * Funcja rozwiazujaca zadane dzialanie
	 * @see pl.edu.agh.kis.chrząszcz.expressions.Solver#solve(java.lang.String)
	 */
	@Override
	public int solve( String expression )			//TODO: Poprawić algorytm dla dłuższych działań
	{
		if( expression.endsWith(";") )
			expression = expression.replace( ";" , "" );
		
		String [] variables = expression.split( "[\\+=\\-><\\*/][=]?" );
		String [] operators = expression.split( "[_a-zA-Z0-9ąćęłńóśźż]+" );		
		operators = Arrays.copyOfRange( operators , 1 , operators.length );		//pozbywa się pustego stringa z początku operators
		
		return solveMathTree( variables , operators );
	}
	
	private int solveMathTree( String [] variables , String [] operators )
	{
		String [] var=variables,op=operators;
		
		if( variables.length > 2 )
		{
			var = Arrays.copyOfRange( variables , 0 , variables.length-1 );
			op = Arrays.copyOfRange( operators , 0 , operators.length-1 );
			var[ var.length-1 ] = "" + solveMathTree( Arrays.copyOfRange( variables , 1 , variables.length ) ,
													  Arrays.copyOfRange( operators , 1 , operators.length )  );
		}
		
		Expression left,right;
			
		if( var[0].matches( "[-]?[0-9]+" ) )
			left = new Constant( Integer.valueOf( var[0] ) );
		else
			left = new Variable( var[0] , vars );
		
		if( var[1].matches( "[-]?[0-9]+" ) )
			right = new Constant( Integer.valueOf( var[1] ) );
		else
			right = new Variable( var[1] , vars );
		
		Expression e = new Operator( op[0] , left , right );
		
		return e.eval();
	}
}
