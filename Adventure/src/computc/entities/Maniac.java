package computc.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.cameras.Camera;
import computc.worlds.Dungeon;
import computc.worlds.Room;

public class Maniac extends Enemy
{
	
	public static boolean hit = false;
	
	public Maniac(Dungeon dungeon, Room room, float x, float y) throws SlickException 
	{
		super(dungeon, room, x, y);
		
		this.dungeon = dungeon;
		
		this.image = new Image("res/Maniac.png");
		
		this.damage = 1;
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		
		this.health = this.maximumHealth = 5;
		
		double a = Math.random();
		
		if(a > 0.5)
			{
				right = true; 
			}
			else
			{
				down = true;
			}
		
		
	}
	
	public void update(int delta)
	{
		getNextPosition(delta);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// if hits wall change direction
		if(right && dx == 0)
		{
			right = false;
			left = true;
		}
		else if(left && dx == 0) 
		{
			right = true;
			left = false;
		}
		
		if(up && dy == 0)
		{
			up = false;
			down = true;
		}
		else if(down && dy == 0) 
		{
			up = true;
			down = false;
		}
		
		// check blinking
		if (blinkCooldown > 0)
		{
			blinkCooldown --;
		}
		
		if(blinkCooldown == 0)
		{
			blinking = false;
		}
		
		if(this.getRoom() == this.dungeon.gamedata.hero.getRoom())
			{
				if(this.dungeon.gamedata.hero.getX() > this.x - this.getHalfWidth() && this.dungeon.gamedata.hero.getX() < this.x + this.getHalfWidth())
				{
					System.out.println("this is happening");
				}
			}
	
	}
	
	private void getNextPosition(int delta) 
	{
		if(left)
		{
			dx -= acceleration;
			if(dx < -maximumVelocity)
			{
				dx = -maximumVelocity;
			}
			dx *= delta;
		}
		
		else if(right) 
		{
			dx += acceleration;
			if(dx > maximumVelocity)
			{
				dx = maximumVelocity;
			}
			
			dx *= delta;
		}
		
		if(up) 
		{
			dy -= acceleration;
			if(dy < -maximumVelocity)
			{
				dy = -maximumVelocity;
			}
			dy *= delta;
		}
		else if(down) 
		{
			dy += acceleration;
			if(dy > maximumVelocity)
			{
				dy = maximumVelocity;
			}
			dy *= delta;
		}	
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		if(blinking) 
		{
			if(blinkCooldown % 4 == 0) 
			{
				return;
			}
		}
		super.render(graphics, camera);
		
	}
}


