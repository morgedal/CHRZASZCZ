package pl.edu.agh.kis.chrząszcz;

/**
 * @author Maciej Rajs
 * Interfejs funkcyjny zawierający wykonywalna funkcje, służy do tworzenia i wykonywania wbudowanych komend chrzaszcza
 */
@FunctionalInterface
public interface EmbeddedCommand 
{
	/**
	 * Wykonuje zadana akcje
	 */
	public void execute();
}
