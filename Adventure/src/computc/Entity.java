package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;

public abstract class Entity
{
	protected Dungeon dungeon;
	
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
	
	//collision
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomRight;
	protected boolean bottomLeft;
	
	// blinking collision indicator
	protected boolean blinking;
	protected int blinkTimer;
	
	// rendering
	protected Image image;
	
	// status
	protected int damage = 1;
	protected int currentHealth;
	protected int maximumHealth;
	protected int justHit = 0;
	
	public Entity(Dungeon dungeon, int rx, int ry, int tx, int ty)
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
	
	public boolean intersects(Entity that)
	{
		Rectangle r1 = this.getHitbox();
		Rectangle r2 = that.getHitbox();
		
		return r1.intersects(r2);
	}
	
	public Rectangle getHitbox()
	{
		int x = (int) this.getX();
		int y = (int) this.getY();
		
		int width = this.getHitboxWidth();
		int height = this.getHitboxHeight();
		
		return new Rectangle(x - (width / 2), y - (width / 2), width, height);
	}
	
	public int getHitboxWidth() 
	{
		return this.getWidth();
	}
		
	public int getHitboxHeight() 
	{
		return this.getHeight();
	}
	
	public float getX()
	{
		return this.x;
	}
		
	public float getY()
	{
		return this.y;
	}
	
	public void setPosition(float x, float y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public int getTileyX()
	{
		return (int)(Math.floor(this.x / Tile.SIZE));
	}
	
	public int getTileyY()
	{
		return (int)(Math.floor(this.y / Tile.SIZE));
	}
	
	public int getRoomyX()
	{
		return (int)(Math.floor(this.x / Room.WIDTH));
	}
	
	public int getRoomyY()
	{
		return (int)(Math.floor(this.y / Room.HEIGHT));
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
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
	
	public void calculateCorners(float x, float y) 
	{
		   int leftColumn = (int)(x - getHitboxWidth()/ 2);
		   int rightColumn = (int)(x + getHitboxWidth()/ 2 - 1);
		   int topRow = (int)(y - getHitboxHeight()/ 2);
		   int bottomRow = (int)(y + getHitboxHeight()/ 2 - 1);
		   
		   topLeft = dungeon.getTile(leftColumn, topRow).isBlocked;
		   topRight = dungeon.getTile(rightColumn, topRow).isBlocked;
		   bottomLeft = dungeon.getTile(leftColumn, bottomRow).isBlocked;
		   bottomRight = dungeon.getTile(rightColumn, bottomRow).isBlocked;
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
}