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
	
	
	private Chain chain;
	
	Image ironBall = new Image("res/ironBall.png");
	
	public ChainEnd(Dungeon dungeon, int tx, int ty, Direction direction, Chain chain, Entity entity) throws SlickException
	{
		super(dungeon, tx, ty);
		
		this.direction = direction;
		this.dungeon = dungeon;
		
		this.image = ironBall;
		this.entity = entity;
		this.chain = chain;
		
		
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
	
		public void update()
		{
			System.out.println(" the ball's x & y are: " + this.x + " , " + this.y);
			System.out.println("the hero's x & y are: " + this.entity.x + " , " + this.entity.y);
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
			

			checkTileMapCollision();
			setPosition(xtemp, ytemp);
		
//			if(dx == 0 && !hit && (this.direction == Direction.EAST || this.direction == Direction.WEST))
//			{
//				setHit();
//			}
//			if(dy == 0 && !hit && (this.direction == Direction.NORTH || this.direction == Direction.SOUTH))
//			{
//				setHit();
//			}
			
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
