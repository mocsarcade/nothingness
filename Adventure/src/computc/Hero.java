package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Hero extends Entity
{
	private Dungeon dungeon;
	
	public Hero(Dungeon dungeon, Room room, int tx, int ty) throws SlickException
	{
		super(room, tx, ty);
		
		this.dungeon = dungeon;
		
		this.acceleration = 0.015f;
		this.deacceleration = 0.0015f;
		this.maximumVelocity = 0.2f;
		
		this.currentHealth = this.maximumHealth = 3;
		
		this.image = new Image("res/hero.png");
	}
	
	public void update(Input input, int delta)
	{
		if(input.isKeyDown(Input.KEY_Z))
		{
			maximumVelocity = 0.4f;
		}
		else
		{
			maximumVelocity = 0.2f;
		}
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			dy -= acceleration;
			if(dy < -maximumVelocity)
				dy = -maximumVelocity;
			
			dy *= delta;
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			dy += acceleration;
			if(dy > maximumVelocity)
				dy = maximumVelocity;
			
			dy *= delta;
		}
		else //if neither KEY_UP nor KEY_DOWN
		{
			if(dy > 0)
			{
				dy -= deacceleration;
				if(dy < 0)
					dy = 0;
				
				dy *= delta;
			}
			else if(dy < 0)
			{
				dy += deacceleration;
				if(dy > 0)
					dy = 0;
				
				dy *= delta;
			}
		}
		
		if(input.isKeyDown(Input.KEY_RIGHT))
		{
			dx += acceleration;
			if(dx > maximumVelocity)
				dx = maximumVelocity;
			
			dx *= delta;
		}
		else if(input.isKeyDown(Input.KEY_LEFT)) 
		{
			dx -= acceleration;
			if(dx < -maximumVelocity)
				dx = -maximumVelocity;
			
			dx *= delta;
		}
		else  //if neither KEY_RIGHT nor KEY_LEFT
		{
			if (dx > 0) 
			{
				dx -= deacceleration;
				if(dx < 0)
					dx = 0;
				
				dx *= delta;
			}
			else if (dx < 0)
			{
				dx += deacceleration;
				if(dx > 0)
					dx = 0;
				
				dx *= delta;
			}
		}
		
		x += dx;
		y += dy;
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			this.direction = Direction.NORTH;
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			this.direction = Direction.SOUTH;
		}
		
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			this.direction = Direction.WEST;
		}
		else if(input.isKeyDown(Input.KEY_RIGHT))
		{
			this.direction = Direction.EAST;
		}
		
		if(this.y < 0)
		{
			if(this.room.hasNorthernRoom())
			{
				this.room = this.room.getNorthernRoom();
				this.y = this.room.getHeightInPixels();
			}
			else
			{
				this.y = 0;
			}
		}
		
		if(this.y > this.room.getHeightInPixels())
		{
			if(this.room.hasSouthernRoom())
			{
				this.room = this.room.getSouthernRoom();
				this.y = 0;
			}
			else
			{
				this.y = this.room.getHeightInPixels();
			}
		}
		
		if(this.x < 0)
		{
			if(this.room.hasWesternRoom())
			{
				this.room = this.room.getWesternRoom();
				this.x = this.room.getWidthInPixels();
			}
			else
			{
				this.x = 0;
			}
		}
		
		if(this.x > this.room.getWidthInPixels())
		{
			if(this.room.hasEasternRoom())
			{
				this.room = this.room.getEasternRoom();
				this.x = 0;
			}
			else
			{
				this.x = this.room.getWidthInPixels();
			}
		}
		
		super.update(delta);
	}
}