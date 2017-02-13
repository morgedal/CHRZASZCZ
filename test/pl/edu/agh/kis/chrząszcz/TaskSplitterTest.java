package pl.edu.agh.kis.chrząszcz;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class TaskSplitterTest 
{

	@Test
	public void testSplitTasks() 
	{
		String code = "dopóki(warunek)dopóty{jeżeli(drugiWarunek){idź;idź;};};";
		code += "powtórz(6){idź;};";
		code += "jeżeli(w1){jeżeli(w2){jeżeli(w3){idź};idź;};idź;};";
		List<String> splittedTasks = TaskSplitter.splitTasks( code );
		
		Assert.assertEquals( 3 , splittedTasks.size() );
		Assert.assertEquals( "dopóki(warunek)dopóty{jeżeli(drugiWarunek){idź;idź;};};" , splittedTasks.get( 0 ) );
		Assert.assertEquals( "powtórz(6){idź;};" , splittedTasks.get( 1 ) );
		Assert.assertEquals( "jeżeli(w1){jeżeli(w2){jeżeli(w3){idź};idź;};idź;};" , splittedTasks.get( 2 ) );
		
		code = "idź;idź;jeżeli(warunek){idź;idź;};";
		splittedTasks = TaskSplitter.splitTasks( code );
		
		Assert.assertEquals( 3 , splittedTasks.size() );
		Assert.assertEquals( "idź;" , splittedTasks.get( 0 ) );
		Assert.assertEquals( "jeżeli(warunek){idź;idź;};" , splittedTasks.get( 2 ) );
	}

}
