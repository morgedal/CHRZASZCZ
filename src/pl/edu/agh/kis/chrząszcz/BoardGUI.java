package pl.edu.agh.kis.chrząszcz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 * @author Maciej Rajs
 * Klasa obslugująca GUI planszy
 */
public class BoardGUI 
{
	private final JPanel gui = new JPanel( new BorderLayout( 3, 3 ) );
    private static JButton[][] squares = new JButton[BoardImpl.BOARD_SIZE][BoardImpl.BOARD_SIZE];
    private static JPanel chessboard;
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    /**
     * Tworzy GUI wraz z odpowiednimi akcjami przyciskow
     * @param beetlePos Referencja do koordynatów chrzaszcza
     */
    public BoardGUI( Coordinates beetlePos ) 
    {
    	chessboard = new BoardPanel( squares , beetlePos );
    	
    	JToolBar tools = new JToolBar();
        tools.setFloatable( false );
        gui.add( tools , BorderLayout.PAGE_START );
        tools.add( addNewGameAction() );
        tools.add( addSaveAction() );
    	
        JPanel board = new JPanel( new GridBagLayout() );
        board.add( chessboard );
        gui.add( board );

        addChessboard();
        addLabels();
        Main.boardCreated = true;
    }
    
    private Action addNewGameAction()
    {
    	return new AbstractAction( "Rozpocznij symulację" ) 
        {

            @Override
            public void actionPerformed( ActionEvent e ) 
            {
                for( JButton [] btab : squares )
                	for( JButton b : btab )
                		b.setEnabled( false );
                
                Main.simulationStarted = true;
                BoardPanel.oldColor = Color.WHITE;
            }
        };
    }
    
    private Action addSaveAction()
    {
    	return new AbstractAction( "Zapisz stan programu" )
    	{
    		@Override
    		public void actionPerformed( ActionEvent e )
    		{
    			Main.save();
    		}
    	};
    }
    
    private void addChessboard()
    {
    	for ( int i = 0; i < squares.length; i++ )
            for ( int j = 0; j < squares[i].length; j++ ) 
            {
                final JButton b = new JButton();
                b.setEnabled( true );
                
                b.addActionListener( new BarrierCreatingActionListener( j , i ) );
                b.setBackground( Color.WHITE );
          
                squares[j][i] = b;
            }
    }
    
    private void addLabels()
    {
    	chessboard.add( new JLabel( "" ) );		//brak etykiety na przecięciu
        
    	for ( int i = 0; i < BoardImpl.BOARD_SIZE; i++ )			// górny rząd
            chessboard.add( new JLabel( LETTERS.substring( i, i + 1 ), SwingConstants.CENTER ) );
        
        for ( int i = 0; i < BoardImpl.BOARD_SIZE; i++ )			// kolumna
            for ( int j = 0; j < BoardImpl.BOARD_SIZE; j++ ) 
            {
                if( j == 0 )
                	chessboard.add( new JLabel( "" + ( BoardImpl.BOARD_SIZE+1 - ( i + 1 ) ), SwingConstants.CENTER ) );
                chessboard.add( squares[j][i] );
            }
    }
    
    /**
     * @return Zwraca JComponent z GUI planszy
     */
    public JComponent getGui() 
    {
        return gui;
    }
    
    /**
     * @return Zwraca referencje do tablicy pol planszy
     */
    public static JButton[][] getSquares()
    {
    	return squares;
    }
}
