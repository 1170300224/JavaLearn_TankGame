package tankgame.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Wall extends Substance 
{
	public enum Kind {solid, fragile};
	
	public static final int Size = 30;
	
	private Kind kind; 
	private int Hp;
	
	public Wall(Kind kind,Point center)
	{
		super(new Rectangle2D.Double(center.x - Size/2,center.y - Size/2,Size,Size));
		this.kind = kind;
		switch(kind)
		{
		case solid:
			Hp = Integer.MAX_VALUE;
			break;
		case fragile:
			Hp = 3;
		}
	}
	
	public Kind getKind()
	{
		return kind;
	}
}
