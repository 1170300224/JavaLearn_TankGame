package tankgame.view;

import java.awt.Color;
import java.awt.Graphics2D;

import tankgame.model.Wall;

public class WallView extends SubstanceView 
{
	private Wall wall;
	
	public WallView(Wall wall)
	{
		this.wall = wall;
	}
	
	public void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.draw(wall.getCollisionBox());
		switch(wall.getKind())
		{
		case solid:
			g.setColor(Color.GRAY);
			break;
		case fragile:
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fill(wall.getCollisionBox());
	}
	
}
