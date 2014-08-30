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
		if(dy < 0) 
		{
			if(this.canMoveNorth(x, ydest))
			{
				dy = 0;
			}	
			else
			{
				ytemp += dy;
			}
		}
		else if(dy > 0) 
		{
			if(this.canMoveSouth(x, ydest)) 
			{
				dy = 0;
			}
			else 
			{
				ytemp += dy;
			}
		}
		
		if(dx < 0)
		{
			if(this.canMoveWest(xdest, y)) 
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
			if(this.canMoveEast(xdest, y))
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
	
	public Tile getNortheasternTile(float x, float y)
	{
		int easternBound = this.getEasternBound(x);
		int northernBound = this.getNorthernBound(y);
		
		return this.world.room.getTile(northernBound, easternBound);
	}
	
	public Tile getNorthwesternTile(float x, float y)
	{
		int westernBound = this.getWesternBound(x);
		int southernBound = this.getSouthernBound(y);
		
		return this.world.room.getTile(southernBound, westernBound);
	}
	
	public Tile getSoutheasternTile(float x, float y)
	{
		int easternBound = this.getEasternBound(x);
		int southernBound = this.getSouthernBound(y);
		
		return this.world.room.getTile(southernBound, easternBound);
	}
	
	public Tile getSouthwesternTile(float x, float y)
	{
		int westernBound = this.getWesternBound(x);
		int northernBound = this.getNorthernBound(y);
		
		return this.world.room.getTile(northernBound, westernBound);
	}
	
	public boolean canMoveNorth(float x, float y)
	{
		Tile northeasternTile = this.getNortheasternTile(x, y);
		Tile northwesternTile = this.getNorthwesternTile(x, y);
		
		if(northeasternTile != null && northwesternTile != null)
		{
			return !northeasternTile.isBlock && !northwesternTile.isBlock;
		}
		else
		{
			return false;
		}
	}
	
	public boolean canMoveSouth(float x, float y)
	{
		Tile southeasternTile = this.getSoutheasternTile(x, y);
		Tile southwesternTile = this.getSouthwesternTile(x, y);
		
		if(southeasternTile != null && southwesternTile != null)
		{
			return !southeasternTile.isBlock && !southwesternTile.isBlock;
		}
		else
		{
			return false;
		}
	}
	
	public boolean canMoveEast(float x, float y)
	{
		Tile northeasternTile = this.getNortheasternTile(x, y);
		Tile southeasternTile = this.getSoutheasternTile(x, y);

		if(northeasternTile != null && southeasternTile != null)
		{
			return !northeasternTile.isBlock && !southeasternTile.isBlock;
		}
		else
		{
			return false;
		}
	}
	
	public boolean canMoveWest(float x, float y)
	{
		Tile northwesternTile = this.getNorthwesternTile(x, y);
		Tile southwesternTile = this.getSouthwesternTile(x, y);
		
		if(northwesternTile != null && southwesternTile != null)
		{
			return !northwesternTile.isBlock && !southwesternTile.isBlock;
		}
		else
		{
			return false;
		}
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