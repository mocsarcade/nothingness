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
		moveSpeed = .02f;
	}
	
	public void update(int delta)
	{
		if(left) 
		{
			this.x -= moveSpeed;
			if(this.x < -maxSpeed) 
			{
				this.x = -maxSpeed;
			}
		}
		else if(right) 
		{
			this.x += moveSpeed;
			if(this.x > maxSpeed) 
			{
				this.x = maxSpeed;
			}
		
		}
		else if(up) 
		{
			this.x += moveSpeed;
			if(this.x > maxSpeed) 
			{
				this.x = maxSpeed;
			}
		}
		else if(down) 
		{
			this.x += moveSpeed;
			if(this.x > maxSpeed) 
			{
				this.x = maxSpeed;
			}
		}
	}
	
}