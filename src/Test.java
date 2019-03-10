import java.awt.EventQueue;

import tankgame.control.Controller;


public class Test {

	public static void main(String[] args)
	{
		EventQueue.invokeLater(()->{
			Controller c = new Controller();
			c.start();
		});
	}
}
