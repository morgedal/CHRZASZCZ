package pl.edu.agh.kis.chrząszcz.expressions;

/**
 * @author Maciej Rajs
 * Klasa reprezentujaca stała
 */
public class Constant implements Expression
{
	int value;
	
	/**
	 * @param value wartosc stalej
	 */
	public Constant( int value )
	{
		this.value = value;
	}

	@Override
	public int eval() 
	{
		return value;
	}
	
	@Override
	public void set( int value )
	{
		//puste, nie da sie przypisac nic do stalej
	}
}
