package pl.edu.agh.kis.chrząszcz;

import java.util.List;
import java.util.Map;

/**
 * @author Maciej Rajs
 * Interfejs parsera wczytanego kodu do postaci czytelnej dla interpretera
 */
public interface Parser 
{
	/**
	 *  Parsuje kod, trzeba wykonac parsowanie przed przekazaniem wyników do interpretera
	 */
	public void parse();
	
	/**
	 * Przekazuje sparsowana mape zmiennych i ich wartosci
	 * @return mapa zmiennych ( nazwa , wartosc )
	 */
	public Map<String,Integer> getVarsMap();
	
	/**
	 * Przekazuje sprarsowana liste instrukcji
	 * @return lista instrukcji dla interpretera
	 */
	public List<String> getInstructionsList();
}
