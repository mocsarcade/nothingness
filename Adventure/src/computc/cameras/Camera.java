package computc.cameras;

import org.newdawn.slick.Input;

import computc.Direction;
import computc.entities.Entity;
import computc.worlds.rooms.Room;

public abstract class Camera
{
	protected float x, y;
	protected float speed = 1f;
	
	public boolean earthquake;
	public boolean earthquakeLeft;
	public boolean earthquakeRight;
	public boolean earthquakeUp;
	public boolean earthquakeDown;
	
	public boolean peeker;
	public boolean peekerLeft;
	public boolean peekerRight;
	public boolean peekerUp;
	public boolean peekerDown;
	
	public int peekerCooldown;
	
	public int earthquakeCooldown;
	public int earthquakeIntensity;
	
	public int getX()
	{
		return (int)(this.x);
	}
	
	public int getY()
	{
		return (int)(this.y);
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public void increaseX(float amount)
	{
		this.x += amount;
	}
	
	public void decreaseX(float amount)
	{
		this.x -= amount;
	}
	
	public void increaseY(float amount)
	{
		this.y += amount;
	}
	
	public void decreaseY(float amount)
	{
		this.y -= amount;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	public void setEarthQuake(Direction direction)
	{
		if(direction == Direction.NORTH)
		{
			earthquakeUp = true;
		}
		else if(direction == Direction.SOUTH)
		{
			earthquakeDown = true;
		}
		
		else if(direction == Direction.EAST)
		{
			earthquakeRight = true;
		}
		else if(direction == Direction.WEST)
		{
			earthquakeLeft = true;
		}
		
		earthquakeCooldown = 50;
		earthquakeIntensity = 3;
		
		earthquake = true;
	}
	
	public abstract void update(Input input, int delta);
}