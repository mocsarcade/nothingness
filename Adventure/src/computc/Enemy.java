package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Enemy extends Entity
{
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    
    protected boolean attacking;
	
	public Enemy(World world, int tx, int ty) throws SlickException 
	{
		super(world, tx, ty);
	}
}