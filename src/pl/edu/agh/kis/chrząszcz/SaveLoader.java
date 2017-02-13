package pl.edu.agh.kis.chrząszcz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

/**
 * @author Maciej Rajs
 * Klasa do obslugi pobrania danych z save'a
 */
public class SaveLoader 
{
	private static final int INSTRUCTIONS = 0;
	private static final int VARS = 1;
	private static final int STACK = 2;
	private static final int ACTUAL = 3;
	private static final int POSITION = 4;
	private static final int FACE = 5;
	private static final int BARRIERS = 6;
	private static final int WALLS = 7;
	
	private static List<String> instructions = new ArrayList<String>();
	private static Map<String,Integer> vars = new HashMap<String,Integer>();
	private static Stack<ListIterator<String>> stack = new Stack<ListIterator<String>>();
	private static ListIterator<String> actual;
	private static Coordinates beetlePos;
	private static List<Coordinates> barriers = new ArrayList<Coordinates>() ;
	private static int face;
	private static List<Coordinates> walls = new ArrayList<Coordinates>();
	
	/**
	 * Pobiera dane z pliku zapisu
	 * @param path sciezka do pliku *.schrz w ktorym znajduja sie zapisane dane
	 */
	public static void load( String path )
	{
		BufferedReader br = bufferedReaderInit( path );
		String line;
		int section = 0;
		
		try
		{
			while( ( line = br.readLine() ) != null )
				if( line.equals("$") )
					section++;
				else
				{
					if( section == INSTRUCTIONS )
						instructions.add( line );
					else if( section == VARS )
						vars.put( line.split(":")[0] , Integer.valueOf( line.split(":")[1] ) );
					else if( section == STACK )
						stack.push( instructions.listIterator( Integer.valueOf( line ) ) );
					else if( section == ACTUAL )
						actual = instructions.listIterator( Integer.valueOf( line ) );
					else if( section == POSITION )
						beetlePos = new Coordinates( Integer.valueOf( line.split(":")[0] ) , Integer.valueOf( line.split(":")[1] ) );
					else if( section == FACE )
						face = Integer.valueOf( line );
					else if( section == BARRIERS )
						barriers.add( new Coordinates( Integer.valueOf( line.split(":")[0] ) , Integer.valueOf( line.split(":")[1] ) ) );
					else if( section == WALLS )
						walls.add( new Coordinates( Integer.valueOf( line.split(":")[0] ) , Integer.valueOf( line.split(":")[1] ) ) );
				}
			
			br.close();
		}
		catch(IOException e)
		{
			System.err.println( "Nie udało się odczytać pliku z kodem" );
		}
	}
	
	private static BufferedReader bufferedReaderInit( String path )
	{
		try
		{
			return new BufferedReader( new InputStreamReader( new FileInputStream( new File( path ) ) , "UTF-8" ) );
		}
		catch(Exception e)
		{
			System.err.println( "Nie udało się otworzyć pliku do odczytu" );
			return null;
		}
	}

	/**
	 * @return Zwraca pobrane z zapisu instrukcje
	 */
	public static List<String> getInstructions() 
	{
		return instructions;
	}

	/**
	 * @return Zwraca pobrane z zapisu zmienne
	 */
	public static Map<String, Integer> getVars() 
	{
		return vars;
	}

	/**
	 * @return Zwraca pobrany z zapisu stan stosu wywolan procedur
	 */
	public static Stack<ListIterator<String>> getStack() 
	{
		return stack;
	}

	/**
	 * @return Zwraca pobrany z zapisu itetator do instrukcji od której program sie wznowi
	 */
	public static ListIterator<String> getActual() 
	{
		return actual;
	}

	/**
	 * @return Zwraca pobrane z zapisu koordynaty chrzaszcza
	 */
	public static Coordinates getBeetlePos() 
	{
		return beetlePos;
	}

	/**
	 * @return Zwraca pobrana z zapisu liste koordynatow "przedmiotow"
	 */
	public static List<Coordinates> getBarriers() 
	{
		return barriers;
	}
	
	/**
	 * @return Zwraca pobrany z zapisu kierunek w którym chrzaszcz jest zwrocony
	 */
	public static int getBeetleFace()
	{
		return face;
	}
	
	/**
	 * @return Zwraca pobrana z zapisu liste koordynatow przeszkod
	 */
	public static List<Coordinates> getWalls()
	{
		return walls;
	}
}
