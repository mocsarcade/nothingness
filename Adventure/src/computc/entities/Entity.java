
package computc.entities;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
import org.jbox2d.dynamics.World;

import computc.Direction;
import computc.cameras.Camera;
import computc.worlds.Dungeon;
import computc.worlds.Room;
import computc.worlds.Tile;

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
	protected Tile topLeft;
	protected Tile topRight;
	protected Tile bottomRight;
	protected Tile bottomLeft;
	
	// blinking collision indicator
	protected boolean blinking;
	protected int blinkCooldown;
	
	// rendering
	protected Image image;
	
	// status
	protected int damage = 1;
	protected int currentHealth;
	protected int maximumHealth;
	protected int justHit = 0;
	
	// box2d status
	protected World world;
	protected boolean newRoom = true;
	protected boolean roomTransition = true;
	
	protected boolean facingRight;
	protected boolean facingDown;
	
	protected LinkedList<Key> keys = new LinkedList<Key>();
	
	public Entity(Dungeon dungeon, float x, float y)
	{
		this.dungeon = dungeon;
		
		this.x = x;
		this.y = y;
	}
	
	public Entity(Dungeon dungeon, int tx, int ty)
	{
		this.dungeon = dungeon;
		
		int rx = (int)(Math.floor(tx / Room.WIDTH));
		int ry = (int)(Math.floor(ty / Room.HEIGHT));
		
		tx -= rx * Room.TILEY_WIDTH;
		ty -= ry * Room.TILEY_HEIGHT;
		
		this.x = (rx * Room.WIDTH) + ((tx + 0.5f) * Tile.SIZE);
		this.y = (ry * Room.HEIGHT) + ((ty + 0.5f) * Tile.SIZE);
	}
	
	public Entity(Dungeon dungeon, Room room, int tx, int ty)
	{
		this.dungeon = dungeon;
		
		this.x = (room.getRoomyX() * Room.WIDTH) + ((tx + 0.5f) * Tile.SIZE);
		this.y = (room.getRoomyY() * Room.HEIGHT) + ((ty + 0.5f) * Tile.SIZE);
	}
	
	public Entity(Dungeon dungeon, Room room, float x, float y)
	{
		this.dungeon = dungeon;
		
		this.x = room.getX() + x;
		this.y = room.getY() + y;
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
		int x = (int)this.getX() - this.getHalfWidth() - camera.getX();
		int y = (int)this.getY() - this.getHalfHeight() - camera.getY();
		
		this.image.draw(x, y);
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		int x = ((int)(this.getX() - this.getHalfWidth()) / 8) - camera.getX();
		int y = ((int)(this.getY() - this.getHalfHeight()) / 8) - camera.getY();

		int WIDTH = this.getWidth() / 8;
		int HEIGHT = this.getHeight() / 8;
		
		graphics.setColor(Color.red);
		graphics.fillRoundRect(x, y, WIDTH, HEIGHT, 4);
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
	
	public float getLocalX(Camera camera)
	{
		return this.getX() - this.getHalfWidth() - camera.getX();
	}
	
	public float getLocalY(Camera camera)
	{
		return this.getY() - this.getHalfHeight() - camera.getY();
	}
	
	public float getRoomPositionX()
	{
		return this.x - (Room.WIDTH * this.getRoomyX());
	}
	
	public float getRoomPositionY()
	{
		return this.y - (Room.HEIGHT * this.getRoomyY());
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
	
	public World getWorld()
	{
		return world;
	}
	
	public Room getRoom()
	{
		return dungeon.getRoom(this.getRoomyX(), this.getRoomyY());
	}
	
	public int getHalfWidth()
	{
		return this.getWidth() / 2;
	}
	
	public int getHalfHeight()
	{
		return this.getHeight() / 2;
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
		   
		   topLeft = dungeon.getTile(leftColumn, topRow);
		   topRight = dungeon.getTile(rightColumn, topRow);
		   bottomLeft = dungeon.getTile(leftColumn, bottomRow);
		   bottomRight = dungeon.getTile(rightColumn, bottomRow);
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
			   if(topLeft.isBlocked || topRight.isBlocked) 
			   {
				   dy = 0;
					if(topLeft.locked && this.keys.size() > 0)
					{
						Key key = this.keys.pop();
						this.dungeon.keys.remove(key);
						
						topLeft.unlock();
					}
					if(topRight.locked && this.keys.size() > 0)
					{
						Key key = this.keys.pop();
						this.dungeon.keys.remove(key);
						
						topRight.unlock();
					}
			   }
			   else {
				   ytemp += dy;
			   }
		   }
			   
			if(dy > 0) 
			{
				if(bottomLeft.isBlocked || bottomRight.isBlocked)
				{
					if(bottomLeft.locked && this.keys.size() > 0)
					{
						Key key = this.keys.pop();
						this.dungeon.keys.remove(key);
						
						bottomLeft.unlock();
					}
					if(bottomRight.locked && this.keys.size() > 0)
					{
						Key key = this.keys.pop();
						this.dungeon.keys.remove(key);
						
						bottomRight.unlock();
					}
					
					dy = 0;
				}
				else 
				{
					ytemp += dy;
				}
			}
			
			calculateCorners(xdest, y);
			
			if(dx < 0) {
				if(topLeft.isBlocked || bottomLeft.isBlocked) 
				{
					dx = 0;
					if(topLeft.locked && this.keys.size() > 0)
					{
						Key key = this.keys.pop();
						this.dungeon.keys.remove(key);
						
						topLeft.unlock();
					}
					if(bottomLeft.locked && this.keys.size() > 0)
					{
						Key key = this.keys.pop();
						this.dungeon.keys.remove(key);
						
						bottomLeft.unlock();
					}
				}
				else 
				{
					xtemp += dx;
				}
			}
				
			if(dx > 0) {
				if(topRight.isBlocked || bottomRight.isBlocked) 
				{
					dx = 0;
					if(topRight.locked && this.keys.size() > 0)
					{
						Key key = this.keys.pop();
						this.dungeon.keys.remove(key);
						
						topRight.unlock();
					}
					if(bottomRight.locked && this.keys.size() > 0)
					{
						Key key = this.keys.pop();
						this.dungeon.keys.remove(key);
						
						bottomRight.unlock();
					}
				}
				else 
				{
					xtemp += dx;
				}
			}
	}
	
	public boolean isOnScreen(Camera camera)
	 {
		if((this.getX() >= camera.getX()) &&
			(this.getY() >= camera.getY()) &&
			(this.getX() <= camera.getX() + Room.WIDTH) &&
			(this.getY() <= camera.getY() + Room.HEIGHT))
		{
			return true;
		}
		else return false;
	 	}

}