package pl.edu.agh.kis.chrząszcz;

/**
 * @author Maciej Rajs
 * Glowna klasa zarzadzajaca calym systemem symulacji robota programowego i jej wizualizacji
 */
public class Main 
{
	/** flagi wykonania przygotowujacych czynności przez watek wizualizacji  */
	volatile static boolean simulationStarted = false , boardCreated = false;
	
	/** sciezka do kodu */
	static String pathToCode;
	
	/** obiekt robota programowego */
	static Beetle beetle;
	
	/** plansza do wyswietlania symulacji */
	static Board board;
	
	/**
	 * Glowna funkcja main 
	 * @param args Tablica stringow zawierająca 1. ścieżkę do kodu
	 */
	public static void main( String [] args )
	{
		pathToCode = args[0];	//może być albo z rozszerzeniem .chrz - program albo .schrz - save z poprzedniego wykonania
		String startType = recognizeSuffix( pathToCode );
		
		initComponents();
		
		while( !boardCreated );
		if( startType.equals( "save" ) )
			loadSimulationObjectsFromSave();
			
		while( !simulationStarted );
		beetle.start( startType );
	}
	
	private static String recognizeSuffix( String path )
	{
		if( pathToCode.endsWith( ".chrz" ) )
			return "";
		else if( pathToCode.endsWith( ".schrz" ) )
			return "save";
		else
		{
			System.err.println( "To nie jest właściwy format pliku z kodem!" );
			System.exit(-1);	
			return "-1";	//bez sesnsu, ale Java nie pozwala na brak instrukcji powrotu
		}
	}
	
	private static void initComponents()
	{
		beetle = new SimpleBeetle( pathToCode , 0 , 0 );
		beetle.initInterpreter();
		board = new BoardImpl( beetle.getCoordinates() );
		new Thread( board ).start();
	}
	
	private static void loadSimulationObjectsFromSave()
	{
		for( Coordinates c : SaveLoader.getBarriers() )
			board.addItem( c );
		for( Coordinates c : SaveLoader.getWalls() )
			board.addWall( c );
		board.resetFieldsColor();
	}
	
	/** Powoduje zapisanie do pliku *.schrz w katalogu w którym znajdował się kod *.chrz aktualnego stanu programu */
	static void save()
	{
		beetle.save();
	}
}
