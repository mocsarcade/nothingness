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
	
	public Arrow(Dungeon dungeon, int rx, int ry, int tx, int ty, Direction direction) throws SlickException
	{
		super(dungeon, rx, ry, tx, ty);
		
		this.direction = direction;
		
		this.dungeon = dungeon;
		
		this.acceleration = 5f;
		
		if(direction == Direction.NORTH)
		{
			dy = -acceleration;
		}
		else if(direction == Direction.SOUTH)
		{
			dy = acceleration;
		}
		else if(direction == Direction.EAST)
		{
			dx = acceleration;
		}
		else if(direction == Direction.WEST)
		{
			dx = -acceleration;
		}
		
		
		this.image = arrows.getSubImage(1, 1, 30, 30);
	}
	
	public void update()
	{
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit && (this.direction == Direction.EAST || this.direction == Direction.WEST))
		{
			setHit();
		}
		
		if(dy == 0 && !hit && (this.direction == Direction.NORTH || this.direction == Direction.SOUTH))
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
	
	public Image getImage()
	{
		return this.image;
	}

}
