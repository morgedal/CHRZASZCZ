package pl.edu.agh.kis.chrząszcz;

import java.util.List;

/**
 * @author Maciej Rajs
 * Interfejs z funkcja upraszczajaca instrukcje blokowe do prostych instrukcji i skokow
 */
public interface BlockStatementsSimplifier 
{
	/**
	 * @param procedure lista z instrukcjami procedury do uproszczenia
	 * @return Zwraca liste uproszczonych instrukcji 
	 */
	public List<String> simplifyAllBlockStatements( List<String> procedure );
}
