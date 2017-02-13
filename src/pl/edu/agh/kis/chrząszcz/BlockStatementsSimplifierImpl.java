package pl.edu.agh.kis.chrząszcz;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maciej Rajs
 * Implementacja interfejsu BlockStatementsSimplifier
 */
public class BlockStatementsSimplifierImpl implements BlockStatementsSimplifier
{
	private int etCounter = 0;	///zmienna licząca etykiety, przydaje się, aby nazwy się nie powtórzyły
	
	@Override
	public List<String> simplifyAllBlockStatements( List<String> procedure )
	{
		List<String> beforeSimplifying = procedure;
		List<String> afterSimplifying = simplifyBlockStatements( beforeSimplifying );
	
		while( !beforeSimplifying.equals( afterSimplifying ) )
		{
			beforeSimplifying = afterSimplifying;
			afterSimplifying = simplifyBlockStatements( beforeSimplifying );			
		}
		
		return afterSimplifying;
	}
	
	private List<String> simplifyBlockStatements( List<String> procedure )
	{
		List<String> simplifiedCode = new ArrayList<String>();
		
		for( String statement : procedure )
		{
			if( statement.endsWith( "};" ) )
			{
				statement = statement.replaceFirst( "\\(" , "%" );
				statement = statement.replaceFirst( "\\)" , "%" );
				String [] tab = statement.split( "%" );
				simplifiedCode.addAll( matchInstruction( tab ) );
			}
			else
				simplifiedCode.add( statement );
		}
		
		return simplifiedCode;
	}
	
	private List<String> matchInstruction( String [] tab )
	{
		if( tab[0].equals("jeżeli") )
			return simplifyIfStatement( tab );
		
		if( tab[0].equals("dopóki") )
			return simplifyConditionLoop( tab );
		
		if( tab[0].equals("powtórz") )
			return simplifyIterationLoop( tab );
		
		return null;								//TODO: DODAĆ OBSŁUGĘ BŁĘDÓW
	}
	
	private List<String> simplifyIfStatement( String [] tab )
	{
		List<String> instructions = new ArrayList<String>();
		
		instructions.add( "jeżeli!(" + tab[1] + ")et_" + etCounter + ";" );					// w tab[1] znajduje sie warunek
		instructions.addAll( splitTasks( tab[2].substring( 1 , tab[2].length()-2 ) ) );		//w tab[3] są instrukcje
		instructions.add( "&et_" + etCounter + "&" );
		etCounter++;
		
		return instructions;
	}
	
	private List<String> splitTasks( String code )
	{
		return TaskSplitter.splitTasks( code );
	}
	
	private List<String> simplifyConditionLoop( String [] tab )
	{
		List<String> instructions = new ArrayList<String>();
		
		instructions.add( "jeżeli!(" + tab[1] + ")et_" + etCounter + ";" );
		instructions.add( "&et_" + (etCounter+1) + "&" );
		instructions.addAll( splitTasks( tab[2].substring( 7 , tab[2].length()-2 ) ) );		//pomija 7 znaków: dopóty{
		instructions.add( "jeżeli(" + tab[1] + ")et_" + (etCounter+1) + ";" );
		instructions.add( "&et_" + etCounter + "&" );
		etCounter += 2;		// +2 bo dodajemy dwie etykiety
		
		return instructions;
	}
	
	private List<String> simplifyIterationLoop( String [] tab )	//TODO: Dorobić możliwość wstawiania operacji na zmiennych jako warunku
	{
		List<String> instructions = new ArrayList<String>();
		
		for( int i = 0 ; i < Integer.valueOf( tab[1] ) ; i++ )
			instructions.addAll( splitTasks( tab[2].substring( 1 , tab[2].length()-2 ) ) );
		
		return instructions;
	}
}
