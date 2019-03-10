package tankgame.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

import tankgame.control.Controller;

public class Tank extends ActiveSubstance implements Runnable
{
	public enum Kind{player,ai};
	
	public static final int SIZE = 30;
	
	private Kind kind;
	private int hp;
	private int regularSpeed;
	
	public Tank(Point location,Kind kind,Map map)
	{
		this(new Rectangle2D.Double(location.x-SIZE/2, location.y-SIZE/2, SIZE, SIZE),kind,map);
	}
	
	private Tank(Rectangle2D collisionBox,Kind kind,Map map) {
		super(collisionBox,map);
		this.kind = kind;
		switch(kind)
		{
		case player:
			hp = 3;
			this.setSpeed(4);
			this.regularSpeed = 4;
			break;
		}
	}
	
	@Override
	public void setDirection(Direction dir)
	{
		super.setDirection(dir);
		this.setSpeed(this.regularSpeed);	//每设置一次方向就将速度置回正常
	}
	
	@Override
	public boolean move()
	{
		boolean r = super.move();
		this.setSpeed(0);	//每移动一次就将速度置到零
		return r;
	}

	@Override
	public void run() 
	{
		while(true)
		{
			try {
				Thread.sleep(Controller.DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			move();
			//System.out.println("tank move");	//
		}
	}

}
