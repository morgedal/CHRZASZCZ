package pl.edu.agh.kis.chrząszcz;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import pl.edu.agh.kis.chrząszcz.expressions.MathExpressionSolver;
import pl.edu.agh.kis.chrząszcz.expressions.Solver;

/**
 * @author Maciej Rajs
 * Implementacja interfejsu Interpreter dla jezyka "CHRZASZCZ"
 */
public class InterpreterImpl implements Interpreter
{
	private List<String> instructions;
	private Map<String,Integer> vars;
	private Map<String,ListIterator<String>> labels = new HashMap<String,ListIterator<String>>();
	private Map<String,EmbeddedCommand> embeddedCommands;
	private Stack<ListIterator<String>> callOutsStack = new Stack<ListIterator<String>>();
	private ListIterator<String> actual;
	private Solver solver;
	
	@Override
	public void setInstructions( List<String> instructions )
	{
		this.instructions = instructions;
	}
	
	@Override
	public void setVars( Map<String,Integer> vars )
	{
		this.vars = vars;
	}
	
	@Override
	public Map<String,Integer> getVars()
	{
		return vars;
	}
	
	@Override
	public void setEmbeddedCommands( Map<String,EmbeddedCommand> embeddedCommands )
	{
		this.embeddedCommands = embeddedCommands;
	}
	
	@Override
	public void setStart( String start )
	{
		if( start.equals( "" ) )
		{
			actual = instructions.listIterator( instructions.indexOf( "&główna&" ) );
			actual.next();					//żeby iterator znalazł się przed pierwszą realną instrukcją
			callOutsStack.push( instructions.listIterator( actual.previousIndex() ) );
		}
		else if( start.equals( "save" ) )
		{
			actual = SaveLoader.getActual();
			callOutsStack = SaveLoader.getStack();
		}
	}
	

	@Override
	public void save( Coordinates beetlePos , int beetleFace )
	{
		SaveCreator.save( instructions , vars , callOutsStack , actual , beetlePos , beetleFace , Main.board.getItemsList() );
	}
	
	@Override
	public void run()
	{
		solver = new MathExpressionSolver( vars );
		findLabels();
		while( !callOutsStack.isEmpty() && actual.hasNext() )
		{
			String instruction = actual.next();
			System.out.println( instruction );
			recognizeCommand( instruction );
			try 
			{
				//if( instruction.equals("idź;") )
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void findLabels()
	{
		int index = 0;
		for( String s : instructions )
		{
			if( s.matches( "&[^&]*&" ) )
				labels.put( s.substring( 1 , s.length()-1 )+";" , instructions.listIterator( index ) );
			
			index++;
		}
	}
	
	private void recognizeCommand( String command )
	{
		if( command.matches( "&[^&]*&" ) );		//etykiety nie zmieniają działania programu
		else if( command.matches( "jeżeli\\([^\\)]*\\)[^;]*;" ) )		//instrukcja warunkkowa
		{
			String condition = command.split("[//(//)]")[1];		
			String instruction = command.replaceFirst( "jeżeli[^\\)]*\\)" , "" );
			
			if( conditionIsTrue( condition ) )
				recognizeCommand( instruction );
		}
		else if( command.matches( "jeżeli!\\([^\\)]*\\)[^;]*;" ) )		//instrukcja warunkowa z przeciwnym warunkiem
		{
			String condition = command.split("[//(//)]")[1];
			String instruction = command.replaceFirst( "jeżeli![^\\)]*\\)" , "" );
			
			if( !conditionIsTrue( condition ) )
				recognizeCommand( instruction );
		}
		else if( command.matches( "powrót;" ) )				//instrukcje powrotu z procedur
		{
			actual = callOutsStack.pop();
		}
		else if( command.matches( "[_a-zA-Z0-9ąćęłńóśźż]*;" ) )		//wywołania procedur
		{
			if( embeddedCommands.containsKey( command ) )
				embeddedCommands.get( command ).execute();
			else if( labels.containsKey( command ) )
			{
				if( !command.matches( "et_[0-9]+;" ) )
					callOutsStack.push( instructions.listIterator( actual.nextIndex() ) );
				actual = instructions.listIterator( labels.get( command ).nextIndex() );
			}
		}
		else if( command.matches( "[_a-zA-Z0-9ąćęłńóśźż]*[\\+\\-=><!\\*/][=!]?[_a-zA-Z0-9ąćęłńóśźż\\+\\-=><\\*/!]*;" ) )	//operacje mat
			solver.solve( command );
	}
	
	private boolean conditionIsTrue( String condition )
	{
		if( condition.matches( "[-]?[0-9]*" ) )		//jeśli jest liczbą
			if( condition.equals( "0" ) )
				return false;
			else
				return true;
		
		if( vars.containsKey( condition ) )			//jeśli jest taka zmienna
			if( vars.get( condition ) == 0 )
				return false;
			else
				return true;
		
		if( condition.matches( "[_a-zA-Z0-9ąćęłńóśźż]*[\\+\\-=><!\\*/][=!]?[_a-zA-Z0-9ąćęłńóśźż\\+\\-=><\\*/!]*" ) )	//jeśli warunkiem jest operacja matematyczna
			if( solver.solve( condition ) == 0 )
				return false;
			else
				return true;
		
		System.err.println( "Zwracam bezsensowne true dla warunku " + condition ); 
		return true;		// każdy inny napis zwraca prawdę, tylko 0 oznacza fałsz		
	}
	
}
