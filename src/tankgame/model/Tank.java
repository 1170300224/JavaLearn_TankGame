package tankgame.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

import tankgame.control.Controller;

public class Tank extends ActiveSubstance implements Runnable
{
	public enum Kind{player,ai};
	
	public static final int SIZE = 30;
	
	private Kind kind;
	private int regularSpeed;
	
	public Tank(Point centre,Kind kind,Map map)
	{
		this(new Rectangle2D.Double(centre.x-SIZE/2, centre.y-SIZE/2, SIZE, SIZE),kind,map);
	}
	
	private Tank(Rectangle2D collisionBox,Kind kind,Map map) {
		super(collisionBox,map);
		this.kind = kind;
		switch(kind)
		{
		case player:
			this.setHp(3);
			this.regularSpeed = 4;
			this.setSpeed(regularSpeed);
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
	
	public void shot()
	{		
		int x = (int)this.getCollisionBox().getCenterX();
		int y = (int)this.getCollisionBox().getCenterY();
		
		int dis = 5;	//射出的子弹到坦克前端的距离
		int append = dis + SIZE/2;
		
		switch(this.getDirection())
		{
		case up:
			y -= append;
			break;
		case left:
			x -= append;
			break;
		case down:
			y += append;
			break;
		case right:
			x += append;
			break;
		}
		
		Point centre = new Point(x,y);
		
		Bullet bullet = new Bullet(centre,this.getMap(),this.getDirection());
		
		this.getMap().add(bullet);
		Thread bulletThread = new Thread(bullet);
		bulletThread.start();
	}

	@Override
	public void run() 
	{
		while(this.alive())
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
