package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Thug extends Enemy	
{
//	public Thug(Dungeon dungeon, int rx, int ry, int tx, int ty) throws SlickException 
//	{
//		super(dungeon, rx, ry, tx, ty);
//		
//		this.image = new Image("res/thug.png");
//		
//		this.damage = 2;
//		this.acceleration = 0.03f;
//		this.deacceleration = 0.001f;
//		this.maximumVelocity = 0.03f;
//		
//		this.currentHealth = this.maximumHealth = 5;
//		
//		right = true; down = true;
//	}
	
	public Thug(Dungeon dungeon, int tx, int ty) throws SlickException 
	{
		super(dungeon, (int)(Math.floor(tx / Room.WIDTH)), (int)(Math.floor(ty / Room.HEIGHT)), tx - ((int)(Math.floor(tx / Room.WIDTH)) * Room.TILEY_WIDTH), ty - ((int)(Math.floor(ty / Room.HEIGHT)) * Room.TILEY_HEIGHT));
		
		this.dungeon = dungeon;
		
		this.image = new Image("res/thug.png");
		
		this.damage = 2;
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		
		this.currentHealth = this.maximumHealth = 5;
		
		right = true; down = true;
	}
	
	public void update(int delta)
	{
		getNextPosition(delta);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// if hits wall change direction
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
		
		// check blinking
		if(blinking)
		{
			long elapsed = (System.nanoTime() - blinkCooldown) / 1000000;
			if(elapsed > 400)
		{
				blinking = false;
		}
	}
	}
	
	private void getNextPosition(int delta) 
	{
		if(left)
		{
			dx -= acceleration;
			if(dx < -maximumVelocity)
			{
				dx = -maximumVelocity;
			}
			dx *= delta;
		}
		
		else if(right) 
		{
			dx += acceleration;
			if(dx > maximumVelocity)
			{
				dx = maximumVelocity;
			}
			
			dx *= delta;
		}
		
		if(up) 
		{
			dy -= acceleration;
			if(dy < -maximumVelocity)
			{
				dy = -maximumVelocity;
			}
			dy *= delta;
		}
		else if(down) 
		{
			dy += acceleration;
			if(dy > maximumVelocity)
			{
				dy = maximumVelocity;
			}
			dy *= delta;
		}	
	}

}