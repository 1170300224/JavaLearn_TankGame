package tankgame.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class GameMessageView extends JComponent
{
	public static final int WIDTH = 280;
	public static final int HEIGHT = 300;
	
	public GameMessageView()
	{
		this.setSize(WIDTH, HEIGHT);
	}
	
	@Override
	public void paintComponent(Graphics go)
	{
		Graphics2D g = (Graphics2D) go;
		
		showFrame(g);
	}
	
	private void showFrame(Graphics2D g)
	{
		g.setColor(Color.BLUE);
		g.draw(new Rectangle2D.Double(0,1,this.getSize().getWidth()-1,this.getSize().getHeight()-2));
	}
}
