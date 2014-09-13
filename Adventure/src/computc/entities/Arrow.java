package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.Camera;
import computc.Direction;

public class Arrow
{
	private float x, y;
	
	private double speed = .7;
	
	private Direction direction;
	
	Animation projectile;
	
	public boolean loaded = false;
	public boolean arrowHit = false;
	
	Image arrows = new Image("res/arrowSpriteSheet.png");
	Image arrow = arrows.getSubImage(1, 1, 30, 30);
	
	public Arrow(float x, float y, Direction direction, boolean arrowHit) throws SlickException
	{
		
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.arrowHit = false;
		
	}
	
	public void update(int delta) 
	{

		if(direction == Direction.NORTH)
			y -= delta * speed;
		
		else if(direction == Direction.SOUTH)
			y += delta * speed;
		
		else if(direction == Direction.WEST)
			x -= delta * speed;
		
		else if(direction == Direction.EAST)
			x += delta * speed;
		
		if(Thug.hit == true)
			arrowHit = true;	
		
	}
	
	public void render(Graphics g, Camera camera) 
	{
		if(!arrowHit)
		arrow.draw((float)x - camera.getX(), (float)y - camera.getY());
	}
	
	public float getX() 
	{
		return x;
	}
	
	public float getY() 
	{
		return y;
	}

}
