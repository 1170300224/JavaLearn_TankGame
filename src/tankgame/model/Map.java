package tankgame.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * ��ͼ������ײ��ļ���
 * �洢����ʵ�壨��ײ�䣩������
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
	 * �ж�sub��·��path���ƶ�ʱ���Ƿ����map�ڵ�����ʵ����ײ
	 * @param sub
	 * @param path
	 * @return	����ײ������
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

