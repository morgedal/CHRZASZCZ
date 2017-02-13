package pl.edu.agh.kis.chrząszcz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Maciej Rajs
 * Klasa implementujaca interejs parser dla języka obslugi robota programowego "CHRZASZCZ"
 */
public class ParserImpl implements Parser 
{
	private String code;
		
	private Map<String,Integer> vars;
	private List<String> instructions;
	
	/**
	 * @param givenCode kod programu do sparsowania
	 */
	public ParserImpl( String givenCode )
	{
		code = givenCode;
		code = code.replaceAll( "#[^#]*#" , "" );	//usuwa komentarze
	}
	
	@Override
	public Map<String,Integer> getVarsMap()
	{
		return vars;
	}
	
	@Override
	public List<String> getInstructionsList()
	{
		return instructions;
	}
	
	@Override
	public void parse()
	{
		vars = createVarsMap();
		instructions = createInstructionList();
	}
	
	private Map<String,Integer> createVarsMap()
	{
		String varType = "zmienna";		//Furtka, żeby później łatwiej było wprowadzić ew. typy zmiennych
		Map<String,Integer> vars = new HashMap<String,Integer>();
		Pattern pattern = Pattern.compile( varType + " [_a-zA-Ząćęłńóśźż][_a-zA-Z0-9ąćęłńóśźż]*;" );
		Matcher matcher = pattern.matcher( code );
		
		while( matcher.find() )
			vars.put( matcher.group().substring( varType.length() ).replace( ";" , "" ).trim() , 0 );
		
		code = code.replaceAll( pattern.toString() , "" );
		
		return vars;
	}
	
	private List<String> createInstructionList()
	{
		List<String> instructionList = new ArrayList<String>();
		BlockStatementsSimplifier simplifier = new BlockStatementsSimplifierImpl();
		
		Map<String,List<String>> procedures = createProceduresMap();
		addGotoProcedureLabels(procedures);
		
		for( String s : procedures.keySet() )
			instructionList.addAll( simplifier.simplifyAllBlockStatements( procedures.get( s ) ) );

		return instructionList;
	}
	
	private Map<String,List<String>> createProceduresMap()
	{
		Map<String,List<String>> procedures = new HashMap<String,List<String>>();
		Pattern pattern = Pattern.compile( "procedura [_a-zA-Ząćęłńóśźż][_a-zA-Z0-9ąćęłńóśźż]*\\s*\\{[^\\$]*\\}\\s*\\$" );
		Matcher matcher = pattern.matcher( code );
		
		while( matcher.find() )
			addProcedure( matcher.group() , procedures );
		
		return procedures;
	}
	
	private void addProcedure( String procedure , Map<String,List<String>> procedures )		
	{
		String [] pair;
		procedure = procedure.substring( 10 );								//usuwa słowo kluczowe procedura
		procedure = procedure.replaceAll( "[ \\t\\n\\r]*" , "" );			//usuwa białe znaki
		pair = procedure.split( Pattern.quote( "{" ) , 2 );					//rozdziela na nazwę procedury i jej treść
		pair[1] = pair[1].substring( 0 , pair[1].length()-2 );				//usuwa nawias kończący procedurę
		procedures.put( pair[0] , TaskSplitter.splitTasks( pair[1] ) );
	}
		
	private void addGotoProcedureLabels( Map<String,List<String>> procedures )
	{
		for( String procedureName : procedures.keySet() )
			procedures.get( procedureName ).add( 0 , "&" + procedureName + "&" );			
	}
}
