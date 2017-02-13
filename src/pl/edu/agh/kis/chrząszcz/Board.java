package pl.edu.agh.kis.chrząszcz;

import java.util.List;

/**
 * @author Maciej Rajs
 * Interfejs do obslugi graficznej wersji planszy na której mozna obserwowac przeszkody i dzialanie programu
 */
public interface Board extends Runnable
{
	/** Dodaje "przedmiot" na planszę
	 * @param c koordynaty przeszkody
	 */
	public void addItem( Coordinates c );
	
	/** Usuwa "przedmiot" z planszy
	 * @param c koordynaty przedmiotu
	 */
	public void removeItem( Coordinates c );
	
	/**
	 * @return Zwraca listę z koordynatami "przedmiotow"
	 */
	public List<Coordinates> getItemsList();
	
	/**
	 * Resetuje początkowy kolor pol na odpowiadający pustemu polu
	 */
	public void resetFieldsColor();
	
	/* 
	 * Tworzy GUI planszy
	 */
	public void run();
	
	/**
	 * Dodaje niemozliwa do przejscia przeszkode do planszy
	 * @param c koordynaty przeszkody
	 */
	public void addWall( Coordinates c );
	
	/**
	 * Usuwa niemozliwa do przejscia przeszkode
	 * @param c koordynaty przeszkody
	 */
	public void removeWall( Coordinates c );
	
	/**
	 * @return Zwraca liste z koordynatami niemozliwych do przejscia przeszkod
	 */
	public List<Coordinates> getWallList();
}
