package pl.edu.agh.kis.chrząszcz;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author Maciej Rajs
 * Klasa implementująca interfejs Board
 */
public class BoardImpl implements Board
{
	/** Zmienna przechowujaca wymiary planszy */
	static final int BOARD_SIZE = 25;
	
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final Color ITEM_COLOR = Color.BLUE;
	private static final Color WALL_COLOR = Color.RED;

	private Coordinates beetlePos;
	private static List<Coordinates> items = new ArrayList<Coordinates>();
	private static List<Coordinates> walls = new ArrayList<Coordinates>();
	
	/**
	 * @param beetlePos koordynaty robota
	 */
	public BoardImpl( Coordinates beetlePos )
	{
		this.beetlePos = beetlePos;
	}
	
	@Override
	public void addItem( Coordinates c )
	{
		items.add( c );
		BoardGUI.getSquares()[c.x][c.y].setBackground( ITEM_COLOR );
		BoardPanel.oldColor = ITEM_COLOR;
	}
	
	@Override
	public void removeItem( Coordinates c )
	{
		items.remove( c );
		BoardGUI.getSquares()[c.x][c.y].setBackground( BACKGROUND_COLOR );
		BoardPanel.oldColor = BACKGROUND_COLOR;
	}
	
	@Override
	public List<Coordinates> getItemsList()
	{
		return items;
	}
	
	@Override
	public void addWall( Coordinates c )
	{
		walls.add( c );
		BoardGUI.getSquares()[c.x][c.y].setBackground( WALL_COLOR );
		BoardPanel.oldColor = WALL_COLOR;
	}
	
	@Override
	public void removeWall( Coordinates c )
	{
		walls.remove( c );
		BoardGUI.getSquares()[c.x][c.y].setBackground( BACKGROUND_COLOR );
		BoardPanel.oldColor = BACKGROUND_COLOR;
	}
	
	@Override
	public List<Coordinates> getWallList()
	{
		return walls;
	}
	
	@Override
	public void resetFieldsColor() 
	{
		BoardPanel.oldColor = BACKGROUND_COLOR;
	}
	
	@Override
	public void run()
	{
		Runnable r = new Runnable() 
        {
            @Override
            public void run() 
            {
                BoardGUI gui = new BoardGUI( beetlePos );

                JFrame f = new JFrame( "Plansza do pokazywania ruchu chrząszcza" );
                f.add( gui.getGui() );
                f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                f.pack();
                f.setMinimumSize( f.getSize() );
                f.setVisible( true );
            }
        };

        SwingUtilities.invokeLater(r);
	}
}
