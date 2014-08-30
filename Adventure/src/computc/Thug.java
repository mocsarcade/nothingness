package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Thug extends Enemy	
{
	public Thug(int rx, int ry, int tx, int ty) throws SlickException 
	{
		super(rx, ry, tx, ty);
		
		this.image = new Image("res/thug.png");
		
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		
		this.currentHealth = this.maximumHealth = 3;
		
		right = true; down = true;
	}
	
	public void update(int delta)
	{
		if(left)
		{
			dx -= acceleration;
			if(dx < -maximumVelocity)
				dx = -maximumVelocity;
			
			dx *= delta;
		}
		else if(right) 
		{
			dx += acceleration;
			if(dx > maximumVelocity)
				dx = maximumVelocity;
			
			dx *= delta;
		}
		
		if(up) 
		{
			dy -= acceleration;
			if(dy < -maximumVelocity)
				dy = -maximumVelocity;
			
			dy *= delta;
		}
		else if(down) 
		{
			dy += acceleration;
			if(dy > maximumVelocity)
				dy = maximumVelocity;
			
			dy *= delta;
		}
		
		x += dx;
		y += dy;
		
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
		
		super.update(delta);
	}
}