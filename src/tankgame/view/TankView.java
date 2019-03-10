package tankgame.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import tankgame.model.Tank;
import tankgame.model.ActiveSubstance.Direction;

public class TankView 
{
	private Tank tank;
	
	public TankView(Tank tank)
	{
		this.tank = tank;
	}
	
	public void draw(Graphics2D g)
	{
		Rectangle2D pos = tank.getCollisionBox();
		int x1 = (int)pos.getMinX();
		int y1 = (int)pos.getMinY();
		int x2 = (int)pos.getMaxX();
		int y2 = (int)pos.getMaxY();
		
		int[] xs = new int[3];
		int[] ys = new int[3];
		
		switch(tank.getDirection())
		{
		case none:
		case up:
			xs[0] = (x1+x2)/2;	xs[1] = x1;	xs[2] = x2;
			ys[0] = y1;	ys[1] = y2;	ys[2] = y2;
			break;
		case left:
			xs[0] = x1; xs[1] = x2; xs[2] = x2;
			ys[0] = (y1+y2)/2;	ys[1] = y1; ys[2] = y2;
			break;
		case right:
			xs[0] = x2; xs[1] = x1; xs[2] = x1;
			ys[0] = (y1+y2)/2;	ys[1] = y1; ys[2] = y2;
			break;
		case down:
			xs[0] = (x1+x2)/2; xs[1] = x1; xs[2] = x2;
			ys[0] = y2;	ys[1] = y1; ys[2] = y1;
			break;
		}
		
		Polygon triangle = new Polygon(xs,ys,3);
		
		g.setColor(Color.RED);
		g.fill(triangle);
	}
	
	
}



