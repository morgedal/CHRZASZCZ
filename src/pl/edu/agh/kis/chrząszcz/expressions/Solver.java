package pl.edu.agh.kis.chrząszcz.expressions;

/**
 * @author Maciej Rajs
 * Interfejs słuzacy do rozwiazywania wyrazen matematycznych
 */
public interface Solver 
{
	/**
	 * Funkcja rozwiazująca zadane wyrazenie
	 * @param expression string z dzialaniem matematycznym
	 * @return wynik dzialania w postaci liczby calkowitej
	 */
	public int solve( String expression );
}
