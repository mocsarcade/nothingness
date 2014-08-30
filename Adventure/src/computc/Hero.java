package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Hero extends Entity
{
	public static boolean nextArea;
	
	public Hero(World world, int tx, int ty) throws SlickException
	{
		super(world, tx, ty);
		
		this.image = new Image("res/hero.png");
		
		this.currentHealth = this.maximumHealth = 3;
		
		this.maxspeed = 0.2f;
		this.acceleration = 0.015f;
		this.deacceleration = 0.001f;
	}
	
	public void update(Input input, int delta)
	{
		getNextPosition(input, delta);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		if(justHit) 
		{
			long elapsed = (System.nanoTime() - justHitTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) 
			{
				return;
			}
		}
			
		super.render(graphics, camera);
	}
	
	private void getNextPosition(Input input, int delta) 
	{
		
		if(input.isKeyDown(Input.KEY_UP)) 
		{
			dy -= acceleration * delta;
			if(dy < -maxspeed)
			{
				dy = -maxspeed * delta;
			}
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			dy += acceleration * delta;
			if(dy > maxspeed)
			{
				dy = maxspeed * delta;
			}
		}
		
		else 
		{
			if (dy > 0) 
			{
				dy -= deacceleration * delta;
				if(dy < 0)
				{
					dy = 0;
				}
			}
			else if (dy < 0)
			{
				dy += deacceleration * delta;
				if(dy > 0) 
				{
					dy = 0;
				}
			}
		}

		 if(input.isKeyDown(Input.KEY_RIGHT))
		{
			dx += acceleration * delta;
			if(dx > maxspeed) 
			{
				dx = maxspeed * delta;
			}
		}
		 else if(input.isKeyDown(Input.KEY_LEFT)) 
		{
			dx -= acceleration * delta;
			if(dx < -maxspeed)
			{
				dx = -maxspeed * delta;
			}
		}
		else 
		{
			if (dx > 0) 
			{
				dx -= deacceleration * delta;
				if(dx < 0)
				{
					dx = 0;
				}
			}
			else if (dx < 0)
			{
				dx += deacceleration * delta;
				if(dx > 0) 
				{
					dx = 0;
				}
			}
			
		}
		
//		float step = this.acceleration * delta;
		
			if(input.isKeyDown(Input.KEY_UP))
				{
					this.direction = Direction.NORTH;
//					this.y -= step;
				}
			else if(input.isKeyDown(Input.KEY_DOWN))
				{
				this.direction = Direction.SOUTH;
//				this.y += step;
				}
		
			if(input.isKeyDown(Input.KEY_LEFT))
				{
				this.direction = Direction.WEST;
//				this.x -= step;
				}
			else if(input.isKeyDown(Input.KEY_RIGHT))
			{
				this.direction = Direction.EAST;
//				this.x += step;
			}
	}
}