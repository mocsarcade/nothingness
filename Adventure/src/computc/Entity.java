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
	// world
	protected World world;
	
	// position
	protected float x;
	protected float y;
	
	// movement
	protected Vector2f step;
	protected Direction direction;
	protected float dx;
	protected float dy;
	protected float acceleration;
	protected float deacceleration;
	protected float maximumVelocity;
	
	// rendering
	protected Image image;
	
	protected float xtemp;
	protected float ytemp;
	protected float xdest;
	protected float ydest;
	
	// corner collision
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;

	protected int damage = 1;
	protected int currentHealth;
	protected int maximumHealth;
	
	protected int justHit;
	
	public Entity(World world, int tx, int ty)
	{
		this.world = world;
		
		this.x = (tx + 0.5f) * this.world.room.getTileWidth();
		this.y = (ty + 0.5f) * this.world.room.getTileWidth();
	}
	
	public void update(Input input, int delta)
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
	
	public void setPosition(float x, float y) 
	{
		this.x = x;
		this.y = y;
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
	
	public int getHitboxWidth()
	{
		return this.getWidth();
	}
		
	public int getHitboxHeight() 
	{
		return this.getHeight();
	}
	
	public void checkTileMapCollision() 
	{
		xdest = x + dx;
		ydest = y + dy;
		   
		xtemp = x;
		ytemp = y;
		
		calculateCorners(x, ydest);
		
		if(dy < 0) 
		{
			if(topLeft || topRight)
			{
				dy = 0;
			}	
			else
			{
				ytemp	 += dy;
			}
		}
		else if(dy > 0) 
		{
			if(bottomLeft || bottomRight) 
			{
				dy = 0;
			}
			else 
			{
				ytemp += dy;
			}
		}
		
		calculateCorners(xdest, y);
			
		if(dx < 0)
		{
			if(topLeft || bottomLeft) 
			{
				dx = 0;
			}
			else 
			{
				xtemp += dx;
			}
		}
		else if(dx > 0)
		{
			if(topRight || bottomRight)
			{
				dx = 0;
			}
			else 
			{
				xtemp += dx;
			}
		}
	}
	
	public int getNorthernBound(float y)
	{
		return ((int)(y) - (this.getHitboxHeight() / 2)) / Adventure.TILE_SIZE;
	}
	
	public int getSouthernBound(float y)
	{
		return ((int)(y) + (this.getHitboxHeight() / 2) - 1) / Adventure.TILE_SIZE;
	}
	
	public int getEasternBound(float x)
	{
		return ((int)(x) + (this.getHitboxWidth() / 2) - 1) / Adventure.TILE_SIZE;
	}
	
	public int getWesternBound(float x)
	{
		return ((int)(x) - (this.getHitboxWidth() / 2)) / Adventure.TILE_SIZE;
	}
	
	public void calculateCorners(float x, float y)
	{
		int northernBound = this.getNorthernBound(y);
		int southernBound = this.getSouthernBound(y);
		int easternBound = this.getEasternBound(x);
		int westernBound = this.getWesternBound(x);
		
		if(westernBound < 0 || southernBound >= world.room.getHeight() || westernBound < 0 || easternBound >= world.room.getWidth()) 
		{
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}
		
		topLeft = world.room.getTile(northernBound, westernBound).isBlock;
		topRight = world.room.getTile(northernBound, easternBound).isBlock;
		bottomLeft = world.room.getTile(southernBound, westernBound).isBlock;
		bottomRight = world.room.getTile(southernBound, easternBound).isBlock;
	}
	
	public boolean isDead() 
	{
		return this.currentHealth < 0;
	}
	
	public boolean wasJustHit()
	{
		return this.justHit > 0;
	}
	
	public void takeDamage(int damage)
	{
		if(!this.isDead() && !this.wasJustHit())
		{
			this.currentHealth -= damage;
			this.justHit = 100;
		}
	}
	
	public int getDamage()
	{
		return this.damage;
	}
}