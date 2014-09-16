package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.Camera;
import computc.Direction;
import computc.worlds.Dungeon;

public class Arrow extends Entity
{

	private boolean hit;
	private boolean remove;
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    
    Image arrows = new Image("res/arrowSpriteSheet.png");
	
	public Arrow(Dungeon dungeon, int rx, int ry, int tx, int ty, boolean down, boolean right) throws SlickException
	{
		super(dungeon, rx, ry, tx, ty);
		
		this.dungeon = dungeon;
		
		facingRight = right; facingDown = down;
		
		this.acceleration = 2f;
		
		if(right)
		{
			dx = acceleration;
		}
		else if(left)
		{
			dx = - acceleration;
		}
		
		if(down)
		{
			dy = acceleration;
		}
		else if(up)
		{
			dy = -acceleration;
		}
		
		this.image = arrows.getSubImage(1, 1, 30, 30);
	}
	
	public void update()
	{
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit)
		{
			setHit();
		}
		
		if(hit)
		{
			remove = true;
		}
	}
	
	public void setHit() 
	{
		if(hit)
			return;
		
		hit = true;
		dx = 0; dy = 0;
	}
	
	public boolean shouldRemove()
	{
		return remove;
	}
	
	public float getX() 
	{
		return x;
	}
	
	public float getY() 
	{
		return y;
	}

}
