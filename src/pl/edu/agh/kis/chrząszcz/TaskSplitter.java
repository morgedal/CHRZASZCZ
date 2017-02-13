package pl.edu.agh.kis.chrząszcz;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Maciej Rajs
 * klasa pomocnicza dla parsera rozdzielajaca poszczegolne instrukcje
 */
public class TaskSplitter 
{
	/**
	 * Rozdziela poszczegolne instrukcje na danym poziomie zagniezdzenia
	 * @param code kod procedury do rozdzielenia instrukcji
	 * @return lista poszczegolnych instrukcji
	 */
	public static List<String> splitTasks( String code )
	{
		List<String> splittedTasks = new LinkedList<String>();
		code = addDelimiters( code );
		for( String s : code.split( "%" ) )
			splittedTasks.add( s );
		
		return splittedTasks;
	}
	
	private static String addDelimiters( String procedureCode )
	{
		int bracketsOpened = 0;
		StringBuilder s = new StringBuilder( "" );
		for( char c : procedureCode.toCharArray() )
		{
			if( c == '{' )
				bracketsOpened++;
			if( c == '}' )
				bracketsOpened--;
			s.append(c);
			if( bracketsOpened == 0 && c == ';' )
				s.append('%');		
		}
		
		return new String( s );
	}
}
