package pl.edu.agh.kis.chrząszcz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Maciej Rajs
 * Klasa obslugujaca wyswietlanie aktualnego stanu planszy
 */
public class BoardPanel extends JPanel implements ActionListener
{
	/** Kolor znajdujący sie "pod" wizualna reprezentacja chrząszcza */
	static Color oldColor = Color.WHITE;
	
	private Timer timer;
	private JButton[][] squares;
	private static Coordinates oldPos = new Coordinates(0,0);
	private Coordinates beetlePos;
	
	/**
	 * @param squares referencja do pol planszy
	 * @param beetlePos referencja do koordynatów chrzaszcza
	 */
	public BoardPanel( JButton[][] squares , Coordinates beetlePos )
	{
		super( new GridLayout( 0, BoardImpl.BOARD_SIZE + 1 ) );
		
		timer = new Timer( 1000, this );
		timer.setInitialDelay( 500 );
		timer.start();
		
		this.squares = squares;
		this.beetlePos = beetlePos;
	}
	
    /* 
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public final Dimension getPreferredSize() 
    {
        Dimension d = super.getPreferredSize();
        Dimension prefSize = null;
        Component c = getParent();
        if ( c == null ) 
            prefSize = new Dimension( (int)d.getWidth(),(int)d.getHeight() );
        else if ( c.getWidth() > d.getWidth() && c.getHeight() > d.getHeight() ) 
            prefSize = c.getSize();
        else
            prefSize = d;
        
        int w = (int) prefSize.getWidth();
        int h = (int) prefSize.getHeight();
        int s = ( w > h ? h : w );
        return new Dimension( s, s );
    }

	/* 
	 * Funkcja aktualizujaca stan planszy w przerwaniach Timera
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		squares[oldPos.y][oldPos.x].setBackground( oldColor );
		
		oldColor = squares[beetlePos.y][beetlePos.x].getBackground();
		squares[beetlePos.y][beetlePos.x].setBackground( Color.GREEN );		
		
		oldPos.x = beetlePos.x;
		oldPos.y = beetlePos.y;
		
		validate();
		repaint();
	}
}
