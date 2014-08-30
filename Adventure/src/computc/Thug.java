package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Thug extends Enemy	
{
	public Thug(World world, int tx, int ty) throws SlickException 
	{
		super(world, tx, ty);
		
		this.image = new Image("res/thug.png");
		
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maxacceleration = 0.03f;
		
		this.currentHealth = this.maximumHealth = 3;
		
		right = true;
		down = true;
	}
	
	public void update(Input input, int delta)
	{
		this.getNextPosition();
		this.checkTileMapCollision();
		this.setPosition(xtemp, ytemp);
		
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
		
		super.update(input, delta);
	}
	
	private void getNextPosition()
	{
		if(left) 
		{
			dx -= acceleration;
			if(dx < -maxacceleration) 
			{
				dx = -maxacceleration;
			}
		}
		
		else if(right) 
			{
			dx += acceleration;
				if(dx > maxacceleration) 
				{
				dx = maxacceleration;
				}
			}
		
		if(up) 
		{
			dy -= acceleration;
			if(dy < -maxacceleration) 
			{
				dy = -maxacceleration;
			}
		}
		
		else if(down) 
			{
			dy += acceleration;
				if(dy > maxacceleration) 
				{
				dy = maxacceleration;
				}
			}
	}
}