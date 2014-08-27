package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Hero extends Entity
{
	public Hero(World world, int tx, int ty) throws SlickException
	{
		super(world, tx, ty);
		try 
		{
		this.image = new Image("res/hero.png");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void update(Input input, int delta)
	{
		float step = this.speed * delta;
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			if(this.y - step > 0)
			{
				this.y -= step;
			}
//			else moveRoom();
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			if(this.y + step < 64*9) //this.world.getPixelHeight())
			{
				this.y += step;
			}
//			else moveRoom();
		}
		
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			if(this.x - step > 0)
			{
				this.x -= step;
			}
//			else moveRoom();
		}
		else if(input.isKeyDown(Input.KEY_RIGHT))
		{
			if(this.x + step < 64*11)
			{
				this.x += step;
			}
//			else moveRoom();
		}
	}
	private float speed = 0.15f;
}