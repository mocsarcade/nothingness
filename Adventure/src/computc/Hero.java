package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Hero extends Entity
{
	private float x, y;
	private Image image;
	private float speed = 0.15f;
	
	private TiledWorld world;
	
	public Hero(TiledWorld world, int tx, int ty) throws SlickException
	{
		super(world, tx, ty);
		this.world = world;
				
		this.x = (tx + 0.5f) * this.world.getTileWidth();
		this.y = (ty + 0.5f) * this.world.getTileWidth();
		
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
	
	public void render()
	{
		int x = (int)(this.x) - (this.getWidth() / 2);
		int y = (int)(this.y) - (this.getHeight() / 2);
		
		image.draw(x, y);
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