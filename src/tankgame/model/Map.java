package tankgame.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import tankgame.view.MapView;

/**
 * 地图类是碰撞箱的集合
 * 存储所有实体（碰撞箱）的引用
 * @author s
 *
 */
public class Map implements Serializable
{
	private static final long serialVersionUID = -5014475051550494163L;
	
	private Set<Tank> tanks = Collections.synchronizedSet(new HashSet<>());
	private Set<Wall> walls = Collections.synchronizedSet(new HashSet<>());
	private Set<Bullet> bullets = Collections.synchronizedSet(new HashSet<>());
	
	private Point birthPointA = null;
	
	public Map()
	{
	}
	
	public void clear()
	{
		this.tanks.clear();
		this.walls.clear();
		this.bullets.clear();
		this.birthPointA = null;
	}
	
	public void load(Map newMap)
	{
		this.clear();
		for(Wall w : newMap.walls)
		{
			this.add(w);
		}
		this.setBirthPointA(newMap.getBirthPointA());
	}
	
	public Substance find(Point p)
	{
		for(Tank t:tanks)
		{
			if(t.getCollisionBox().contains(p))
				return t;
		}
		for(Wall w:walls)
		{
			if(w.getCollisionBox().contains(p))
				return w;
		}
		
		return null;
	}
	
	public void add(Wall wall)
	{
		this.walls.add(wall);
	}
	
	public void add(Tank tank)
	{
		this.tanks.add(tank);
	}
	
	public void add(Bullet bullet)
	{
		this.bullets.add(bullet);
	}
	
	public void remove(Substance s)
	{
		tanks.remove(s);
		walls.remove(s);
		bullets.remove(s);
	}
	
	public Set<Wall> getWalls()
	{
		return Collections.unmodifiableSet(this.walls);
	}
	
	public Set<Tank> getTanks()
	{
		return Collections.unmodifiableSet(this.tanks);
	}
	
	public Set<Bullet> getBullets()
	{
		return Collections.unmodifiableSet(this.bullets);
	}
	
	public void setBirthPointA(Point p)
	{
		this.birthPointA = p;
	}
	
	public Point getBirthPointA()
	{
		return this.birthPointA;
	}
	
	/**
	 * 判断sub在路径path上移动时，是否会与map内的其他实体相撞
	 * @param sub
	 * @param path
	 * @return	若会撞，则真
	 */
	public synchronized boolean collide(Substance sub,Rectangle2D path)
	{
		assert sub != null;
		assert path != null;
		
		for(Tank t : tanks)
		{
			if(Objects.equals(t, sub))
				continue;
			if(Substance.collide(path, t.getCollisionBox()))
				return true;
		}
		for(Wall w : walls)
		{
			if(Objects.equals(w, sub))
				continue;
			if(Substance.collide(path, w.getCollisionBox()))
				return true;
		}
		for(Bullet b : bullets)
		{
			if(Objects.equals(b, sub))
				continue;
			if(Substance.collide(path, b.getCollisionBox()))
				return true;
		}
		return false;
	}
	
	public synchronized void _explod_shame_useless(Rectangle2D rang,int damage)
	{
		for(Tank t : tanks)
		{
			if(Substance.collide(rang, t.getCollisionBox()))
			{
				if(t.beHitToDeath(damage))
					tanks.remove(t);
			}
		}
		for(Wall w : walls)
		{
			if(Substance.collide(rang, w.getCollisionBox()))
			{
				if(w.beHitToDeath(damage))
					walls.remove(w);
			}
		}
		for(Bullet b : bullets)
		{
			if(Substance.collide(rang, b.getCollisionBox()))
			{
				if(b.beHitToDeath(damage))
					bullets.remove(b);
			}
		}
	}
	
	public synchronized void explode(Rectangle2D rang,int damage)
	{
		Iterator<Tank> it = tanks.iterator();
		while(it.hasNext())
		{
			Tank t = it.next();
			if(Substance.collide(rang, t.getCollisionBox()))
			{
				if(t.beHitToDeath(damage))
					it.remove();
			}
		}
		Iterator<Wall> iw = walls.iterator();
		while(iw.hasNext())
		{
			Wall w = iw.next();
			if(Substance.collide(rang, w.getCollisionBox()))
			{
				if(w.beHitToDeath(damage))
					iw.remove();
			}
		}
		Iterator<Bullet> ib = bullets.iterator();
		while(ib.hasNext())
		{
			Bullet b = ib.next();
			if(Substance.collide(rang, b.getCollisionBox()))
			{
				if(b.beHitToDeath(damage))
					ib.remove();
			}
		}
	}
	
	public boolean inTheMap(Point p)
	{
		return 		p.x >= 0 && p.x <= MapView.WIDTH
				&&	p.y >= 0 && p.y <= MapView.HEIGHT;
	}
}











