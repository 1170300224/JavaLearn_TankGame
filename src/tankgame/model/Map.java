package tankgame.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 地图类是碰撞箱的集合
 * 存储所有实体（碰撞箱）的引用
 * @author s
 *
 */
public class Map 
{
	private Set<Tank> tanks = new HashSet<>();
	private Set<Wall> walls = new HashSet<>();
	
	private Point birthPointA = null;
	
	public Map()
	{
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
	
	public void remove(Substance s)
	{
		tanks.remove(s);
		walls.remove(s);
	}
	
	public Set<Wall> getWalls()
	{
		return Collections.unmodifiableSet(this.walls);
	}
	
	public Set<Tank> getTanks()
	{
		return Collections.unmodifiableSet(this.tanks);
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
	public boolean collide(Substance sub,Rectangle2D path)
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
		return false;
	}
}

