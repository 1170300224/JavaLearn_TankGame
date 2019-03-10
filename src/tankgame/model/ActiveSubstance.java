package tankgame.model;

import java.awt.geom.Rectangle2D;

public abstract class ActiveSubstance extends Substance 
{

	public enum Direction {up,left,down,right};
	
	private int speed = 0;
	private Direction direction = Direction.up;
	private Map map;
	
	public ActiveSubstance(Rectangle2D collisionBox,Map map) {
		super(collisionBox);
		this.map = map;
		// TODO Auto-generated constructor stub
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getSpeed()
	{
		return this.speed;
	}
	
	public void setDirection(Direction dir)
	{
		this.direction = dir;
	}
	
	public Direction getDirection()
	{
		return this.direction;
	}
	
	/**
	 * 待碰撞检测的移动，若实际移动距离比最大移动距离小，返回false
	 */
	public boolean move()
	{
		int vspeed = speed;
		while(!move(vspeed))
		{
			vspeed /= 2;
		}
		return vspeed == speed;
	}
	
	/**
	 * 移动direction和vspeed所指示的位移。在可能发生碰撞时，不移动
	 * @param vspeed 虚速度，在技术上与碰撞判定的实现有关
	 * @return 若移动成功，则真；否则（路径上有其他实体），假
	 */
	private boolean move(int vspeed)
	{
		if(speed == 0)
			return true;
		
		Rectangle2D oldPos = this.getCollisionBox();
		
		double x1 = oldPos.getMinX();
		double y1 = oldPos.getMinY();
		double x2 = oldPos.getMaxX();
		double y2 = oldPos.getMaxY();
		
		Rectangle2D newPos = new Rectangle2D.Double();
		Rectangle2D path = new Rectangle2D.Double();
		
		switch(direction)
		{
		case up:	//-y
			newPos.setFrameFromDiagonal(x1, y1-vspeed, x2, y2-vspeed);
			break;
		case left:	//-x
			newPos.setFrameFromDiagonal(x1-vspeed, y1, x2-vspeed, y2);
			break;
		case down:	//+y
			newPos.setFrameFromDiagonal(x1, y1+vspeed, x2, y2+vspeed);
			break;
		case right:	//+x
			newPos.setFrameFromDiagonal(x1+vspeed, y1, x2+vspeed, y2);
			break;
		}
		Rectangle2D.union(oldPos, newPos, path);
		
		if(map.collide(this, path))
			return false;

		this.setCollisionBox(newPos);
		return true;
	}
	
	protected Map getMap()
	{
		return this.map;
	}
	
}
