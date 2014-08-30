package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Thug extends Enemy	
{
	public Thug(World world, int tx, int ty) throws SlickException 
	{
		super(world, tx, ty);
		
		this.image = new Image("res/thug.png");
		
		this.currentHealth = this.maximumHealth = 3;
		this.damage = 2;
		acceleration = 0.03f;
		maxspeed = 0.03f;
		
		right = true;
		down = true;
	}
	
	public void update(Input input, int delta)
	{
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		
		// if hits wall, change direction
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
		if(justHit) 
		{
			long elapsed = (System.nanoTime() - justHitTimer) / 1000000;
			if(elapsed > 400) 
			{
				justHit = false;
			}
		}
	}
	
	private void getNextPosition()
	{
		if(left) 
		{
			dx -= acceleration;
			if(dx < -maxspeed) 
			{
				dx = -maxspeed;
			}
		}
		
		else if(right) 
			{
			dx += acceleration;
				if(dx > maxspeed) 
				{
				dx = maxspeed;
				}
			}
		
		if(up) 
		{
			dy -= acceleration;
			if(dy < -maxspeed) 
			{
				dy = -maxspeed;
			}
		}
		
		else if(down) 
			{
			dy += acceleration;
				if(dy > maxspeed) 
				{
				dy = maxspeed;
				}
			}
	}
}