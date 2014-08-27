package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Hero extends Entity
{
	
	public static boolean nextArea;
	private boolean dead = false;
	
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
		
		moveSpeed = .15f;
		hitboxWidth = getWidth();
		hitboxHeight = getHeight();
		health = 5;
	}
	
	public void update(Input input, int delta)
	{
		float step = this.moveSpeed * delta;
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			this.direction = Direction.NORTH;
			
			if(!world.isBlocked(this.x, this.y - getHeight()/2))
				this.y -= step;
				
				if(world.isAccessible(this.x, this.y))
				{
				 nextArea = true;
				}
				else nextArea = false;
		
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			this.direction = Direction.SOUTH;
			
			if(!world.isBlocked(this.x, this.y + getHeight()/2))
				this.y += step;
			
				if(world.isAccessible(this.x, this.y))
				{
				 nextArea = true;
				}
				else nextArea = false;
		}
		
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			this.direction = Direction.WEST;
			
			if(!world.isBlocked(this.x - getWidth()/2, this.y))
				this.x -= step;
				
			if(world.isAccessible(this.x, this.y))
			{
				nextArea = true;
			}
				else nextArea = false;
		}
		
		else if(input.isKeyDown(Input.KEY_RIGHT))
		{
			this.direction = Direction.EAST;
			
			if(!world.isBlocked(this.x  + getWidth()/2, this.y))
				this.x += step;
				
			if(world.isAccessible(this.x, this.y))
			{
			 nextArea = true;
			}
			else nextArea = false;
		}
	}
	
	public void render(Graphics graphics) 
	{
		if (blinking) 
		{
			long elapsed = (System.nanoTime() - blinkTimer)/ 1000000;
			if(elapsed / 100 % 2 == 0) 
			{
				return;
			}
		}
		
		setMapPosition();
		super.render(graphics);
	}
	
	private void hit(int damage) 
	{
		if(blinking) 
			return;
		health -= damage;
		
		if(health < 0)
			health = 0;
		
		if(health == 0) 
			dead = true;
		
		blinking = true;
		blinkTimer = (int) System.nanoTime();
	}
	
	public int getX() 
	{
		return (int) this.x;
	}
	
	public int getY() 
	{
		return (int) this.y;
	}
	
	
	private int health;
	private int maxHealth;
}