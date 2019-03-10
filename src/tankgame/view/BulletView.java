package tankgame.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import tankgame.model.Bullet;

public class BulletView 
{
	private Bullet bullet;
	
	public BulletView(Bullet bullet)
	{
		this.bullet = bullet;
	}
	
	public void draw(Graphics2D g)
	{
		Ellipse2D ell = new Ellipse2D.Double();
		ell.setFrame(bullet.getCollisionBox());
		
		g.setColor(Color.GREEN);
		g.fill(ell);
		
	}
}
