package tankgame.model;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public abstract class Substance implements Serializable
{

	private static final long serialVersionUID = -4942897799029884870L;
	private Rectangle2D collisionBox;
	private int hp = Integer.MAX_VALUE;
	
	public Substance(Rectangle2D collisionBox) {
		this.collisionBox = collisionBox;
	}

	protected void setCollisionBox(Rectangle2D collisionBox)
	{
		this.collisionBox = collisionBox;
	}
	
	public Rectangle2D getCollisionBox()
	{
		return collisionBox;
	}
	
	public void setHp(int hp)
	{
		this.hp = hp;
	}
	
	/**
	 * 将目标扣除相应血量，若目标死亡，则返回真;
	 * @param damage
	 * @return
	 */
	public synchronized boolean beHitToDeath(int damage)
	{
		this.hp -= damage;
		return hp <= 0;
	}
	
	public boolean alive()
	{
		return hp>0;
	}
	
	/**
	 * 判断两实体是否有相交的部分
	 * @param a
	 * @param b
	 * @return	若相交，则真
	 */
	public static boolean collide(Substance a,Substance b)
	{
		return collide(a.collisionBox,b.collisionBox);
	}
	
	/**
	 * 判断两矩形是否有相交的部分
	 * @param a
	 * @param b
	 * @return	若相交，则真
	 */
	public static boolean collide(Rectangle2D a,Rectangle2D b)
	{
		double maxXa = a.getMaxX();
		double minXa = a.getMinX();
		double maxYa = a.getMaxY();
		double minYa = a.getMinY();
		double maxXb = b.getMaxX();
		double minXb = b.getMinX();
		double maxYb = b.getMaxY();
		double minYb = b.getMinY();
		
		if(		b.contains(maxXa,maxYa) || b.contains(maxXa,minYa)
			||	b.contains(minXa,maxYa) || b.contains(minXa,minYa))
		return true;
		
		if(		a.contains(maxXb,maxYb) || a.contains(maxXb,minYb)
			||	a.contains(minXb,maxYb) || a.contains(minXb,minYb))
		return true;
		
		return false;
	}
	
}
