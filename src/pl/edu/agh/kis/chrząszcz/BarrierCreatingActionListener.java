package pl.edu.agh.kis.chrząszcz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Maciej Rajs
 * Klasa ActionListenera dla przyciskow do zarzadzania przeszkodami na planszy 
 */
public class BarrierCreatingActionListener implements ActionListener
{
	private int i,j;
	private int MouseClicks = 0;
	
	/**
	 * @param j Pierwsza wspolrzedna pola ktorym bedziemy zarzadzac
	 * @param i Druga wspolrzedna pola którym bedziemy zarzadzac
	 */
	public BarrierCreatingActionListener( int j , int i  )
	{
		this.i = i;
		this.j = j;
	}
	
	/* 
	 * Funkcja wywolywana po kliknieciu na przycisk, słuzaca do tworzenia/usuwania obiektow na planszy
	 */
	@Override
	public void actionPerformed( ActionEvent e )
	{
		if( MouseClicks == 0 )
			Main.board.addItem( new Coordinates( j , i ) );
		else if( MouseClicks == 1 )
		{
			Coordinates c = new Coordinates( j , i );
			Main.board.removeItem( c );
			Main.board.addWall( c );
		}
		else
			Main.board.removeWall( new Coordinates( j , i ) );
		
		MouseClicks++;
		if( MouseClicks > 2 )
			MouseClicks = 0;
	}

}
