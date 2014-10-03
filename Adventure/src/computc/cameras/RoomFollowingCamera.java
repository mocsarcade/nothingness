package computc.cameras;

import org.newdawn.slick.Input;

import computc.Direction;
import computc.GameData;
import computc.entities.Entity;
import computc.worlds.rooms.Room;

public class RoomFollowingCamera extends Camera
{
	protected GameData gamedata;
	
	public RoomFollowingCamera(GameData gamedata)
	{
		this.gamedata = gamedata;
		
		this.x = this.getTargetX();
		this.y = this.getTargetY();
	}
	
	public void update(Input input, int delta)
	{
		
		if(this.getX() < this.getTargetX())
		{
			if(earthquake)
			{
				this.increaseX(delta + 10);
			}
			
			this.increaseX(delta);
			
			if(!earthquake)
			{
				if(this.getX() > this.getTargetX())
				{
					this.setToTargetX();
				}
			}
		}
		else if(this.getX() > this.getTargetX())
		{
			this.decreaseX(delta);
			
			if(!earthquake)
			{
				if(this.getX() < this.getTargetX())
				{
					this.setToTargetX();
				}
			}
		}
		
		if(this.getY() < this.getTargetY())
		{
			if(earthquake)
			{
				this.increaseY(delta + 10);
			}
			
			this.increaseY(delta);
			
			if(!earthquake)
			{			
				if(this.getY() > this.getTargetY())
				{
					this.setToTargetY();
				}
			}
		}
		else if(this.getY() > this.getTargetY())
		{
			this.decreaseY(delta);
			
			if(!earthquake)
			{
				if(this.getY() < this.getTargetY())
				{
					this.setToTargetY();
				}
			}
		}
		
		if(earthquake)
		{
			earthquakeCooldown--;
		}
		
		if(earthquakeIntensity > 0)
		{
			earthquakeIntensity--;
		}
		
		if(earthquakeCooldown <= 0)
		{
			earthquakeCooldown = 0;
			earthquake = false; earthquakeLeft = false; earthquakeRight = false; earthquakeUp = false; earthquakeDown = false;
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
		return this.gamedata.hero;
	}
	
	public int getTargetX()
	{
		if(earthquakeLeft && !(earthquakeIntensity == 0))
		{
		return this.getTarget().getRoomyX() - 1 * Room.WIDTH;
		}
		else if (earthquakeRight && !(earthquakeIntensity == 0))
		{
		return (this.getTarget().getRoomyX() + 1) * Room.WIDTH;
		}
		else if(peekerLeft)
		{
			return (this.getTarget().getRoomyX() * Room.WIDTH) - Room.WIDTH/2;
		}
		else if(peekerRight)
		{
			return (this.getTarget().getRoomyX() * Room.WIDTH) + Room.WIDTH/2;
		}
		else
		{
		return this.getTarget().getRoomyX() * Room.WIDTH;
		}
	}
	
	public int getTargetY()
	{
		if(earthquakeUp && !(earthquakeIntensity == 0))
		{
			return this.getTarget().getRoomyY() - 1 * Room.HEIGHT;
		}
		else if(earthquakeDown && !(earthquakeIntensity == 0))
		{
			return (this.getTarget().getRoomyY() + 1) * Room.HEIGHT;
		}
		else if(peekerUp)
		{
			return (this.getTarget().getRoomyY() * Room.HEIGHT) - Room.HEIGHT/2;
		}
		else if(peekerDown)
		{
			return (this.getTarget().getRoomyY() * Room.HEIGHT) + Room.HEIGHT/2;
		}
		else
		{
		return this.getTarget().getRoomyY() * Room.HEIGHT;
		}
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	
	public void setPeeking(Direction direction)
	{
		if(direction == Direction.NORTH)
		{
			peekerUp = true;
		}
		if(direction == Direction.SOUTH)
		{
			peekerDown = true;
		}
		if(direction == Direction.EAST)
		{
			peekerRight = true;
		}
		if(direction == Direction.WEST)
		{
			peekerLeft = true;
		}
		
		peekerCooldown = 100;
		
	}
	
	public void turnOffPeeking()
	{
		peekerLeft = false;
		peekerRight = false;
		peekerUp = false;
		peekerDown = false;
	}
	
	public boolean getEarthquake()
	{
		return earthquake;
	}
}
