package computc.entities;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.Direction;
import computc.worlds.Dungeon;
import computc.worlds.Room;
import computc.worlds.Tile;

public class ChainEnd extends Entity{
	
	private boolean hit;
	private boolean remove;
	private Entity entity;
	private int chainAssembleCooldown;
	
	
	private Chain chain;
	
	Image ironBall = new Image("res/ironBall.png");
	
	public ChainEnd(Dungeon dungeon, int tx, int ty, Direction direction, Chain chain, Entity entity) throws SlickException
	{
		super(dungeon, tx, ty);
		
		this.dungeon = dungeon;
		this.entity = entity;
		this.direction = direction;
		this.image = ironBall;
		
		this.chain = chain;
		this.chainAssembleCooldown = 200;
		
		
		this.x = this.entity.getX();
		this.y = this.entity.getY();
		
		if(direction == Direction.NORTH)
		{
			
		}
		else if(direction == Direction.SOUTH)
		{
			
		}
		else if(direction == Direction.EAST)
		{
			
		}
		else if(direction == Direction.WEST)
		{
			
		}
	}
	
		public void update(int delta)
		{
			if(this.entity.roomTransition)
			{
				this.x = this.entity.x;
				this.y = this.entity.y;
			}
			else
			{
				this.x = this.entity.getRoom().getX() + (chain.lastLinkBody.getPosition().x * 30) + this.getHalfWidth();
				this.y = this.entity.getRoom().getY() + (chain.lastLinkBody.getPosition().y * 30) + this.getHalfHeight();
			}
			
			if(chainAssembleCooldown == 0)
			{
			checkTileMapCollision();
			setPosition(xtemp, ytemp);
			}
			
			if(hit)
			{
				remove = true;
			}
			
			if(chainAssembleCooldown > 0)
			{
				chainAssembleCooldown -= delta;
			}
			
			if(chainAssembleCooldown < 0)
			{
				chainAssembleCooldown = 0;
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
