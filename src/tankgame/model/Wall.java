package tankgame.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Wall extends Substance 
{
	public enum Kind {solid, fragile};
	
	public static final int Size = 30;
	
	private Kind kind; 
	
	public Wall(Kind kind,Point center)
	{
		super(new Rectangle2D.Double(center.x - Size/2,center.y - Size/2,Size,Size));
		this.kind = kind;
		switch(kind)
		{
		case solid:
			this.setHp(Integer.MAX_VALUE);
			break;
		case fragile:
			this.setHp(3);
		}
	}
	
	public Kind getKind()
	{
		return kind;
	}
}
