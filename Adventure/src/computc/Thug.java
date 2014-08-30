package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Thug extends Enemy	
{
	public Thug(World world, int tx, int ty) throws SlickException 
	{
		super(world, tx, ty);
		
		this.image = new Image("res/thug.png");
		
		this.health = this.maxHealth = 5;
		this.damage = 2;
		moveSpeed = 0.03f;
		maxSpeed = 0.03f;
		
		right = true;
		down = true;
	}
	
	public void update(int delta)
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
		if(blinking) 
		{
			long elapsed = (System.nanoTime() - blinkTimer) / 1000000;
			if(elapsed > 400) 
			{
				blinking = false;
			}
		}
	}
	
	private void getNextPosition()
	{
		if(left) 
		{
			dx -= moveSpeed;
			if(dx < -maxSpeed) 
			{
				dx = -maxSpeed;
			}
		}
		
		else if(right) 
			{
			dx += moveSpeed;
				if(dx > maxSpeed) 
				{
				dx = maxSpeed;
				}
			}
		
		if(up) 
		{
			dy -= moveSpeed;
			if(dy < -maxSpeed) 
			{
				dy = -maxSpeed;
			}
		}
		
		else if(down) 
			{
			dy += moveSpeed;
				if(dy > maxSpeed) 
				{
				dy = maxSpeed;
				}
			}
	}
	
	public void render (Graphics graphics) 
	{
		super.render(graphics);
	}
	
}