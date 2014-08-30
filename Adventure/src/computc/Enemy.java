package computc;

import org.newdawn.slick.SlickException;

public abstract class Enemy extends Entity
{
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
	
	public Enemy(Room room, int tx, int ty) throws SlickException 
	{
		super(room, tx, ty);
	}
}