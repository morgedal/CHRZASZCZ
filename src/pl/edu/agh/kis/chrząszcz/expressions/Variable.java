package pl.edu.agh.kis.chrząszcz.expressions;

import java.util.Map;

/**
 * @author Maciej Rajs
 * Klasa reprezentujaca zmienne typu calkowitoliczbowego
 */
public class Variable implements Expression
{
	String name;
	int value;
	Map<String,Integer> vars;
	
	/**
	 * @param name nazwa zmiennej
	 * @param vars referencja do mapy ze zmiennymi w programie
	 */
	public Variable( String name , Map<String,Integer> vars )
	{
		this.vars = vars;
		this.name = name;
		if( vars.containsKey( name ) )
			value = vars.get( name );
	}

	@Override
	public int eval() 
	{
		return value;
	}
	
	@Override
	public void set( int value )
	{
		this.value = value;
		if( vars.containsKey( name ) )
			vars.replace( name ,  value );
	}

}
