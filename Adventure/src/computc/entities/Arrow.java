package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.cameras.Camera;
import computc.Direction;
import computc.worlds.Dungeon;
import computc.worlds.Room;

public class Arrow extends Entity
{

	private boolean hit;
	private boolean remove;
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    
    Image arrows = new Image("res/arrowSpriteSheet.png");
	
	public Arrow(Dungeon dungeon, Room room, int tx, int ty, Direction direction) throws SlickException
	{
		super(dungeon, room, tx, ty);
		
		this.direction = direction;
		
		this.dungeon = dungeon;
		
		this.acceleration = 5f;
		
		if(direction == Direction.NORTH)
		{
			dy = -acceleration;
			this.image = arrows.getSubImage(1, 1, 30, 30);
		}
		else if(direction == Direction.SOUTH)
		{
			dy = acceleration;
			this.image = arrows.getSubImage(129, 1, 30, 30);
		}
		else if(direction == Direction.EAST)
		{
			dx = acceleration;
			this.image = arrows.getSubImage(97, 1, 30, 30);
		}
		else if(direction == Direction.WEST)
		{
			dx = -acceleration;
			this.image = arrows.getSubImage(161, 1, 30, 30);
		}
		
		
		
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
