package pl.edu.agh.kis.chrząszcz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Maciej Rajs
 * 
 * Prosta implementacja robota programowego zawierajaca najprostsze wbudowane komendy takie jak: 
 * idz, skrecWPrawo, skrecWLewo, połozCegłe, zabierzCegłe, sprawdzOdległosc oraz wbudowana zmienna odleglosc prezentującą
 * aktualna odleglosc chrzaszcza od przeszkody uaktualniana komenda sprawdzOdleglosc;
 */
public class SimpleBeetle implements Beetle
{
	private String code;
	private Parser parser;
	private Map<String,Integer> vars;
	private List<String> instructions;
	private Interpreter interpreter = new InterpreterImpl();
	
	private Coordinates pos;
	private int face;
	
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	
	/**
	 * @param path sciezka do pliku z kodem/pliku zapisu
	 * @param x pierwsza wspolrzedna startowej pozycji robota
	 * @param y druga wspolrzedna startowej pozycji robota
	 */
	public SimpleBeetle( String path , int x , int y )
	{
		pos = new Coordinates( x , y );
		face = NORTH;
		
		if( path.endsWith( ".chrz" ) )
			initFromCode( path );
		else if( path.endsWith( ".schrz" ) )
			initFromSave( path );
	}
	
	private void initFromCode( String path )
	{
		code = CodeLoader.getCode( path );
		parser = new ParserImpl( code );
		parser.parse();
		vars = parser.getVarsMap();
		vars.put( "odległość" , 0 );		//dodaje zmienną, w którą odpowienia komenda wpisuje odl. do najbliższej przeszkody
		instructions = parser.getInstructionsList();
	}
	
	private void initFromSave( String path )
	{
		SaveLoader.load( path );
		vars = SaveLoader.getVars();
		instructions = SaveLoader.getInstructions();
		pos = SaveLoader.getBeetlePos();
		face = SaveLoader.getBeetleFace();
	}
	
	@Override
	public void initInterpreter()
	{
		interpreter.setInstructions( instructions );
		interpreter.setVars( vars );
		interpreter.setEmbeddedCommands( loadEmbeddedCommands() );
	}
	
	@Override
	public void start( String start )
	{
		interpreter.setStart( start );
		interpreter.run();
	}
	
	@Override
	public void save()
	{
		interpreter.save( pos , face );
	}
	
	@Override
	public Coordinates getCoordinates()
	{
		return pos;
	}
	
	private Map<String,EmbeddedCommand> loadEmbeddedCommands()
	{
		Map<String,EmbeddedCommand> map = new HashMap<String,EmbeddedCommand>();
		
		map.put( "idź;" , ()->
		{
			int oldX = pos.x;
			int oldY = pos.y;
			
			if( face == NORTH )
				pos.x--;
			else if( face == EAST )
				pos.y++;
			else if( face == SOUTH )
				pos.x++;
			else if( face == WEST )
				pos.y--;
			
			if( Main.board.getWallList().contains( new Coordinates( pos.y , pos.x ) ) )
			{
				System.out.println( "BŁĄD: NIE MOŻNA WEJŚĆ W ŚCIANĘ!" );
				
				pos.x = oldX;
				pos.y = oldY;
			}
		});
		
		map.put( "skręćWPrawo;" , ()->
		{
			face++;
			if( face == 4 )
				face = 0;
		});
		
		map.put( "skręćWLewo;" , ()->
		{
			face--;
			if( face == -1 )
				face = 3;
		});
		
		map.put( "połóżCegłę;" , ()->
		{
			Main.board.addItem( new Coordinates( pos.y , pos.x ) );
		});
		
		map.put( "zabierzCegłę;" , ()->
		{
			Main.board.removeItem( new Coordinates( pos.y , pos.x ) );
		});
		
		map.put( "wypisz;" , ()->
		{
			if( vars.get("out") != null )
				System.out.println( "CHRZĄSZCZ: Wartość zmiennej out = " + vars.get("out") );
			else
				System.out.println( "Zmienna out nie istnieje" );
		});
		
		map.put( "sprawdźOdległość;" , ()->
		{
			int row = pos.x;
			int col = pos.y;
			List<Coordinates> tmp = new ArrayList<Coordinates>();
			if( face == NORTH )
			{
				for( Coordinates c : Main.board.getItemsList() )
					if( c.x == col && c.y <= row )
						tmp.add( c );
				for( Coordinates c : Main.board.getWallList() )
					if( c.x == col && c.y <= row )
						tmp.add( c );
				for( int i = 0 ; row >= 0 ; row-- , i++ )
					for( Coordinates c : tmp )
						if( c.equals( new Coordinates( col , row ) ) )
						{
							vars.replace( "odległość" , i );
							return;
						}
				vars.replace( "odległość" , pos.x );
				return;
			}
			else if( face == EAST )
			{
				for( Coordinates c : Main.board.getItemsList() )
					if( c.y == row && c.x >= col )
						tmp.add( c );
				for( Coordinates c : Main.board.getWallList() )
					if( c.y == row && c.x >= col )
						tmp.add( c );
				for( int i = 0 ; col <= BoardImpl.BOARD_SIZE ; col++ , i++ )
					for( Coordinates c : tmp )
						if( c.equals( new Coordinates( col , row ) ) )
						{
							vars.replace( "odległość" , i );
							return;
						}
				vars.replace( "odległość" , BoardImpl.BOARD_SIZE - pos.y );
				return;
			}
			else if( face == SOUTH )
			{
				for( Coordinates c : Main.board.getItemsList() )
					if( c.x == col && c.y >= row )
						tmp.add( c );
				for( Coordinates c : Main.board.getWallList() )
					if( c.x == col && c.y >= row )
						tmp.add( c );
				for( int i = 0 ; row <= BoardImpl.BOARD_SIZE ; row++ , i++ )
					for( Coordinates c : tmp )
						if( c.equals( new Coordinates( col , row ) ) )
						{
							vars.replace( "odległość" , i );
							return;
						}
				vars.replace( "odległość" , BoardImpl.BOARD_SIZE - pos.x );
				return;
			}
			else if( face == WEST )
			{
				for( Coordinates c : Main.board.getItemsList() )
					if( c.y == row && c.x <= col )
						tmp.add( c );
				for( Coordinates c : Main.board.getWallList() )
					if( c.y == row && c.x <= col )
						tmp.add( c );
				for( int i = 0 ; col >= 0 ; col-- , i++ )
					for( Coordinates c : tmp )
						if( c.equals( new Coordinates( col , row ) ) )
						{
							vars.replace( "odległość" , i );
							return;
						}
				vars.replace( "odległość" , pos.y );
				return;
			}
			
			
		});
		
		return map;
	}
	
}
