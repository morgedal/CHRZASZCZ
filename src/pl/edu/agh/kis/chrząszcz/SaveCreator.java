package pl.edu.agh.kis.chrząszcz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

/**
 * @author Maciej Rajs
 * Klasa sluzaca do zapisywania aktualnego stanu programu do pliku
 */
public class SaveCreator 
{
	private static BufferedWriter bw;
	
	/**
	 * Powoduje zapisanie do pliku *.schrz w katalogu w którym znajdowal się kod *.chrz aktualnego stanu programu
	 * 
	 * @param instructions lista instrukcji interpretera
	 * @param vars mapa ze zmiennymi i ich aktualnymi wartościami
	 * @param stack stos wywyołań procedur
	 * @param actual iterator do aktualnie wykonywanej instrukcji
	 * @param beetlePos pozycja chrzaszcza
	 * @param face kierunek w który chrzaszcz jest skierowany
	 * @param items lista z koordynatami przeszkod
	 */
	public static void save( List<String> instructions , Map<String,Integer> vars , Stack<ListIterator<String>> stack ,
							 ListIterator<String> actual , Coordinates beetlePos , int face , List<Coordinates> items )
	{
		bw = bufferedWriterInit( Main.pathToCode.replaceFirst( "\\.chrz" , ".schrz" ) );
		
		saveInstructions( instructions );
		writeLine("$");
		
		saveVars( vars );
		writeLine("$");
		
		saveCalloutsStack( stack );
		writeLine("$");
		
		saveActualIter( actual );
		writeLine("$");
		
		saveBeetlePos( beetlePos );
		writeLine("$");
		
		saveBeetleFace( face );
		writeLine("$");
		
		saveItems( items );
		writeLine("$");
		
		saveWalls( Main.board.getWallList() );
		
		try 
		{
			bw.close();
		} 
		catch (IOException e) 
		{
			System.err.println( "Błąd przy zamknięciu pliku zapisu stanu programu!" );
		}
	}
	
	private static BufferedWriter bufferedWriterInit( String path )
    {
    	try 
    	{
			File file = new File( path );
			if( !file.exists() )
				file.createNewFile();
			OutputStream fos = new FileOutputStream ( file );
			Writer fw = new OutputStreamWriter( fos , "UTF-8" );
			return new BufferedWriter( fw );
		} 
    	catch (Exception e) 
    	{
			System.err.println( "Błąd podczas zapisu stanu programu!" );
			return null;
		}
    }
	
	private static void writeLine( String line )
	{
		try 
    	{
			bw.write( line );
			bw.newLine();
		} 
    	catch ( IOException e )
    	{
			e.printStackTrace();
    	}
	}
	
	private static void saveInstructions( List<String> instructions )
	{
		for( String s : instructions )
			writeLine( s );
	}
	
	private static void saveVars( Map<String,Integer> vars )
	{
		for( String s : vars.keySet() )
			writeLine( s + ":" + vars.get( s ) );
	}
	
	private static void saveCalloutsStack( Stack<ListIterator<String>> stack )
	{
		for( ListIterator<String> iterator : stack )
			writeLine( "" + iterator.nextIndex() );
	}
	
	private static void saveActualIter( ListIterator<String> actual )
	{
		writeLine( "" + actual.nextIndex() );
	}
	
	private static void saveBeetlePos( Coordinates beetlePos )
	{
		writeLine( beetlePos.x + ":" + beetlePos.y );
	}
	
	private static void saveBeetleFace( int face )
	{
		writeLine( face + "" );
	}
	
	private static void saveItems( List<Coordinates> items )
	{
		for( Coordinates c : items )
			writeLine( c.x + ":" + c.y );
	}
	
	private static void saveWalls( List<Coordinates> walls )
	{
		for( Coordinates c : walls )
			writeLine( c.x + ":" + c.y );
	}
}
