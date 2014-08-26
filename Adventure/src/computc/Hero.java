package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Hero
{
	private float x, y;
	private Image image;
	private float speed = 0.15f;
	
	public Hero(float x, float y) throws SlickException
	{
		this.x = x;
		this.y = y;
		
		this.image = new Image("res/hero.png");
	}
	
	public void update(Input input, int delta)
	{
		float step = speed * delta;
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			y -= step;
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			y += step;
		}
		
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			x -= step;
		}
		else if(input.isKeyDown(Input.KEY_RIGHT))
		{
			x += step;
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