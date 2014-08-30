package computc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity
{
	// position
	protected float x;
	protected float y;
	
	// movement
	protected Vector2f step;
	protected Direction direction;
	protected float dx;
	protected float dy;
	protected float xtemp;
	protected float ytemp;
	protected float xdest;
	protected float ydest;
	protected float acceleration;
	protected float deacceleration;
	protected float maximumVelocity;
	
	// rendering
	protected Image image;
	
	// status
	protected int damage = 1;
	protected int currentHealth;
	protected int maximumHealth;
	protected int justHit = 0;
	
	public Entity(int rx, int ry, int tx, int ty)
	{
		this.x = (rx * Room.WIDTH) + ((tx + 0.5f) * Tile.SIZE);
		this.y = (ry * Room.HEIGHT) + ((ty + 0.5f) * Tile.SIZE);
	}
	
	public void update(int delta)
	{
		if(justHit > 0)
		{
			justHit -= delta;
			
			if(justHit < 0)
			{
				justHit = 0;
			}
		}
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = (int)(this.getX()) - (this.getWidth() / 2) - camera.getX();
		int y = (int)(this.getY()) - (this.getHeight() / 2) - camera.getY();
		
		this.image.draw(x, y);
	}
	
	public float getX()
	{
		return this.x;
	}
		
	public float getY()
	{
		return this.y;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public int getRoomX()
	{
		return (int)(Math.floor(this.getX() / Room.WIDTH) * Room.WIDTH);
	}
	
	public int getRoomY()
	{
		return (int)(Math.floor(this.getY() / Room.HEIGHT) * Room.HEIGHT);
	}
	
	public int getWidth()
	{
		return this.image.getWidth();
	}
	
	public int getHeight()
	{
		return this.image.getHeight();
	}
	
	public Direction getDirection()
	{
		return this.direction;
	}
	
	public void setDirection(Direction direction) 
	{
		this.direction = direction;
	}
	
	public boolean isDead() 
	{
		return this.currentHealth < 0;
	}
	
	public boolean wasJustHit()
	{
		return this.justHit > 0;
	}
	
	public void gotHit(int damage)
	{
		if(!this.isDead() && !this.wasJustHit())
		{
			this.currentHealth -= damage;
			this.justHit = 100;
		}
	}
}