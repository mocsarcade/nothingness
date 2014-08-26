package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Hero extends Entity
{
	private float speed = 0.15f;
	
	public Hero(TiledWorld world, int tx, int ty) throws SlickException
	{
		super(world, tx, ty);
		
		this.image = new Image("res/hero.png");
	}
	
	public void update(Input input, int delta)
	{
		float step = speed * delta;
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			if(y - step > 0)
			{
				y -= step;
			}
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			if(y + step < world.getPixelHeight())
			y += step;
		}
		
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			if(x - step > 0)
			{
				x -= step;
			}
		}
		else if(input.isKeyDown(Input.KEY_RIGHT))
		{
			if(x + step < world.getPixelWidth())
			{
				x += step;
			}
		}
	}
	
	public int getWidth()
	{
		return this.image.getWidth();
	}
	
	public int getHeight()
	{
		return this.image.getHeight();
	}
}