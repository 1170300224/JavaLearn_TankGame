package tankgame.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import tankgame.control.Controller;

public class Bullet extends ActiveSubstance implements Runnable,Serializable
{

	private static final long serialVersionUID = 3668213229460938551L;
	public static final int SIZE = 6;
	public static final int EXPLODE_SIZE = 8;
	
	private int damage = 1;
	
	public Bullet(Point centre,Map map,Direction dir)
	{
		super(new Rectangle2D.Double(centre.x-SIZE/2, centre.y-SIZE/2, SIZE, SIZE),map);
		this.setDirection(dir);
		this.setSpeed(5);
		this.setHp(1);
	}
	
	private Bullet(Rectangle2D collisionBox, Map map) {
		super(collisionBox, map);
		// TODO Auto-generated constructor stub
	}
	
	private void explode()
	{
		Rectangle2D rang = new Rectangle2D.Double(this.getCollisionBox().getCenterX()-EXPLODE_SIZE/2, this.getCollisionBox().getCenterY()-EXPLODE_SIZE/2, EXPLODE_SIZE, EXPLODE_SIZE);
		this.getMap().explode(rang, damage);
		this.beHitToDeath(damage);
		this.getMap().remove(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(this.alive())
		{
			try {
				Thread.sleep(Controller.DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(!move())	//·¢ÉúÁËÅö×²
			{
				this.explode();
				break;
			}
		}
	}

	
	
	
	
	
}
