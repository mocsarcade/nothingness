package computc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
	protected float maxspeed;
	protected float acceleration;
	protected float deacceleration;
	
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
	
	protected int currentHealth;
	protected int maximumHealth;
	protected boolean isDead;
	protected int damage;
	
	protected boolean justHit;
	protected long justHitTimer;
	
	public Entity(World world, int tx, int ty)
	{
		this.world = world;
		
		this.x = (tx + 0.5f) * this.world.room.getTileWidth();
		this.y = (ty + 0.5f) * this.world.room.getTileWidth();
	}
	
	public abstract void update(Input input, int delta);
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = this.getX() - (this.getWidth() / 2) - camera.getX();
		int y = this.getY() - (this.getHeight() / 2) - camera.getY();
		
		this.image.draw(x, y);
	}
	
	public int getX()
	{
		return (int)(this.x);
	}
		
	public int getY() 
	{
		return (int)(this.y);
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
			   else {
				   ytemp += dy;
			   }
		   }
			   
			if(dy > 0) 
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
			
			if(dx < 0) {
				if(topLeft || bottomLeft) 
				{
					dx = 0;
				}
				else 
				{
					xtemp += dx;
				}
			}
				
			if(dx > 0) {
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
	
	public void calculateCorners(double x, double y) 
	{
		int leftColumn = (int)(x - getHitboxWidth()/ 2)/ Adventure.TILE_SIZE;
		int rightColumn = (int)(x + getHitboxWidth()/ 2 - 1)/ Adventure.TILE_SIZE;
		int topRow = (int)(y - getHitboxHeight()/ 2) / Adventure.TILE_SIZE;
		int bottomRow = (int)(y + getHitboxHeight()/ 2 - 1)/ Adventure.TILE_SIZE;
		
		if(leftColumn < 0 || bottomRow >= world.room.getHeight() || leftColumn < 0 || rightColumn >= world.room.getWidth()) 
		{
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}
		
		topLeft = world.room.getTile(topRow, leftColumn).isBlock;
		topRight = world.room.getTile(topRow, rightColumn).isBlock;
		bottomLeft = world.room.getTile(bottomRow, leftColumn).isBlock;
		bottomRight = world.room.getTile(bottomRow, rightColumn).isBlock;
	}
	
	public boolean isDead() 
	{
		return this.isDead;
	}
	
	public void takeDamage(int damage)
	{
		if(!this.isDead && !this.justHit)
		{
			this.currentHealth -= damage;
			
			if(currentHealth <= 0)
			{
				this.isDead = true;
			}
			else
			{
				this.justHit = true;
			}
		}
	}
	
	public int getDamage()
	{
		return this.damage;
	}
}