package computc;

import org.newdawn.slick.Input;

import computc.entities.Entity;
import computc.worlds.Room;

public class RoomFollowingCamera extends Camera
{
	protected Entity target;
	
	public RoomFollowingCamera(Entity target)
	{
		this.target = target;
		
		this.x = this.getTargetX();
		this.y = this.getTargetY();
	}
	
	public void update(Input input, int delta)
	{
		if(this.getX() < this.getTargetX())
		{
			this.increaseX(delta);
			
			if(this.getX() > this.getTargetX())
			{
				this.setToTargetX();
			}
		}
		else if(this.getX() > this.getTargetX())
		{
			this.decreaseX(delta);
			
			if(this.getX() < this.getTargetX())
			{
				this.setToTargetX();
			}
		}
		
		if(this.getY() < this.getTargetY())
		{
			this.increaseY(delta);
			
			if(this.getY() > this.getTargetY())
			{
				this.setToTargetY();
			}
		}
		else if(this.getY() > this.getTargetY())
		{
			this.decreaseY(delta);
			
			if(this.getY() < this.getTargetY())
			{
				this.setToTargetY();
			}
		}
	}
	
	public void setToTargetX()
	{
		this.x = this.getTargetX(); 
	}
	
	public void setToTargetY()
	{
		this.y = this.getTargetY();
	}
	
	public Entity getTarget()
	{
		return this.target;
	}
	
	public int getTargetX()
	{
		return this.getTarget().getRoomyX() * Room.WIDTH;
	}
	
	public int getTargetY()
	{
		return this.getTarget().getRoomyY() * Room.HEIGHT;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
}
