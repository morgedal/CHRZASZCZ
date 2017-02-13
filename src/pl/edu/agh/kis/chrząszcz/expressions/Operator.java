package pl.edu.agh.kis.chrząszcz.expressions;

/**
 * @author Maciej Rajs
 * Klasa reprezentująca operatory matematyczne i działania ktore przeprowadzaja
 */
public class Operator implements Expression
{
	String symbol;
	Expression right,left;
	
	/**
	 * @param symbol symbol operatora, definiuje dzialanie
	 * @param left wyrazenie po lewej stronie operatora
	 * @param right wyrazenie po prawej stronie operatora
	 */
	public Operator( String symbol , Expression left , Expression right )
	{
		this.symbol = symbol;
		this.right = right;
		this.left = left;
	}
		
	@Override
	public int eval() 
	{
		if( symbol.equals( "=" ) )	
		{
			left.set( right.eval() );
			return 1;
		}
		
		if( symbol.equals( "+" ) )	
			return left.eval() + right.eval();
		
		if( symbol.equals( "-" ) )	
			return left.eval() - right.eval();
				
		if( symbol.equals( "*" ) )	
			return left.eval() * right.eval();
					
		if( symbol.equals( "/" ) ) 
			return left.eval() / right.eval();
		
		if( symbol.equals( "==" ) )	
			if( left.eval() == right.eval() )
				return 1;
			else 
				return 0;
		
		if( symbol.equals( "!=" ) )	
			if( left.eval() == right.eval() )
				return 0;
			else 
				return 1;
				
		if( symbol.equals( ">" ) ) 
			if( left.eval() > right.eval() )
				return 1;
			else 
				return 0;
					
		if( symbol.equals( "<" ) ) 
			if( left.eval() < right.eval() )
				return 1;
			else 
				return 0;
		
		if( symbol.equals( ">=" ) ) 
			if( left.eval() >= right.eval() )
				return 1;
			else 
				return 0;
					
		if( symbol.equals( "<=" ) ) 
			if( left.eval() <= right.eval() )
				return 1;
			else 
				return 0;
		
		if( symbol.equals( "=!" ) )
		{
			if( right.eval() == 0 )
			{
				left.set(1);
				return 1;
			}
			else
			{
				left.set(0);
				return 0;
			}
		}
		
		return 0;
	}
	
	@Override
	public void set( int value )
	{
		// w tym przypadku nic nie robi
	}

}
