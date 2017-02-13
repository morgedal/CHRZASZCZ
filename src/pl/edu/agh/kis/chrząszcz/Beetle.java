package pl.edu.agh.kis.chrząszcz;


/**
 * @author Maciej Rajs
 * Interfejs robota programowego, ktory wykonuje powierzony mu kod
 */
public interface Beetle 
{
	/**
	 * Funkcja inicjalizująca interpreter, przekazuje do niego potrzebne skladniki takie jak: instrukcje, zmienne, wbudowane komendy
	 */
	public void initInterpreter();
	
	/**
	 * Powoduje wystartowanie pracy robota
	 * @param start Jako parametr przyjmuje pusty string, gdy program ma zaczac się normalnie, w przypakdu, gdy program
	 * 				zaczyna się z save'a podajemy "save"
	 */
	public void start( String start );
	
	/**
	 * Powoduje zapisanie do pliku *.schrz w katalogu w ktorym znajdował się kod *.chrz aktualnego stanu programu
	 */
	public void save();
	
	/**
	 * @return Zwraca obiekt Coordinates reprezentujacy aktualna pozycje chrzaszcza 
	 */
	public Coordinates getCoordinates();
}
