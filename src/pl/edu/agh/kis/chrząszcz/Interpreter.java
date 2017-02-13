package pl.edu.agh.kis.chrząszcz;

import java.util.List;
import java.util.Map;

/**
 * @author Maciej Rajs
 * Interfejs interpretera poleceń języka
 */
public interface Interpreter extends Runnable
{	
	/**
	 * Ustawia liste instrukcji dla interpretera
	 * @param instructions lista z spreparowanymi instrukcjami
	 */
	public void setInstructions( List<String> instructions );
	
	/**
	 * Ustawia interpreterowi dostep do zmiennych wsytepujących w programie
	 * @param vars mapa z nazwami zmiennych i przypisanymi do nich wartosciami
	 */
	public void setVars( Map<String,Integer> vars );
	
	/**
	 * Pobiera referencje do zmiennych wsytepujących w programie
	 * @return referencja do mapy zmiennych
	 */
	public Map<String,Integer> getVars();
	
	/**
	 * Ustawia wbudowane komendy
	 * @param embeddedCommands mapa par nazwaProcedury , komenda
	 */
	public void setEmbeddedCommands( Map<String,EmbeddedCommand> embeddedCommands );
	
	/**
	 * Ustawia punkt startowy
	 * @param start Pusty string oznacza normalne zaczecie programu, "save" rozpoczecie z save'a
	 */
	public void setStart( String start );
	
	/* 
	 * Rozpoczyna interpretacje programu
	 */
	public void run();
	
	/**
	 * Zapsiuje stan programu
	 * @param beetlePos aktualne koordynaty chrzaszcza
	 * @param beetleFace kierunek w który skierowany jest chrzaszcz
	 */
	public void save( Coordinates beetlePos , int beetleFace );
}
