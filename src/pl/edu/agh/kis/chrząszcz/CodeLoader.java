package pl.edu.agh.kis.chrząszcz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Maciej Rajs
 * Klasa sluzaca do pobrania kodu z pliku
 */
public class CodeLoader 
{	
	/**
	 * Funkcja pobierajaca kod z pliku
	 * @param path sciezka do pliku z kodem
	 * @return string zawierający pobrany kod
	 */
	public static String getCode( String path )
	{
		BufferedReader br = bufferedReaderInit( path );
		String line;
		StringBuilder code = new StringBuilder("");
		
		try
		{
			while( ( line = br.readLine() ) != null )
				code.append( line );
			
			br.close();
		}
		catch(IOException e)
		{
			System.err.println( "Nie udało się odczytać pliku z kodem" );
		}
		
		return code.toString();
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
}
