package pl.edu.agh.kis.chrząszcz;

/**
 * @author Maciej Rajs
 * Struktura przechowujaca koordynaty obiektu na planszy
 */
public class Coordinates 
{
	int x;
	int y;
	
	/**
	 * @param x pierwsza wspolrzedna
	 * @param y druga wspolrzedna
	 */
	public Coordinates( int x , int y )
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Coordinates other = ( Coordinates ) obj;
		if ( x != other.x )
			return false;
		if ( y != other.y )
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
}
