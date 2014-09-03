package computc;

import org.newdawn.slick.SlickException;

public abstract class Enemy extends Entity
{
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
	
	public Enemy(int rx, int ry, int tx, int ty) throws SlickException 
	{
		super(rx, ry, tx, ty);
	}
}