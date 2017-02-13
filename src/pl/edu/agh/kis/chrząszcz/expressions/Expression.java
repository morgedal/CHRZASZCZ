package pl.edu.agh.kis.chrząszcz.expressions;

/**
 * @author Maciej Rajs
 * Interfejs reprezentujacy wyrazenie matematyczne
 */
public interface Expression 
{
	/**
	 * @return Zwraca wartosc wyrazenia
	 */
	public int eval();
	
	/**
	 * W wyniku wyrazenia ustawia wartosc w okreslonym w implementacji punkcie
	 * @param value wartosc do ustawienia
	 */
	public void set( int value );
}
