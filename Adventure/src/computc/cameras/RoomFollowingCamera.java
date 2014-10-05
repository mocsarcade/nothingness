package computc.cameras;

import org.newdawn.slick.Input;

import computc.Direction;
import computc.GameData;
import computc.entities.Entity;
import computc.worlds.rooms.Room;

public class RoomFollowingCamera extends Camera
{
	private GameData gamedata;
	
	private Direction peekingDirection = Direction.NONE;
	
	private int shakingDuration = 0;
	private Direction shakingDirection = Direction.NONE;
	
	public RoomFollowingCamera(GameData gamedata)
	{
		this.gamedata = gamedata;

		this.setToTargetX();
		this.setToTargetY();
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

		if(this.shakingDuration > 0)
		{
			this.shakingDuration -= 1;
			this.shakingDirection = Direction.getOppositeDirection(this.shakingDirection);
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
		return (this.getTarget().getRoomyX() * Room.WIDTH) + this.getOffsetX();
	}
	
	public int getTargetY()
	{
		return (this.getTarget().getRoomyY() * Room.HEIGHT) + this.getOffsetY();
	}
	
	public int getOffsetX()
	{
		if(this.peekingDirection == Direction.EAST)
		{
			return Room.WIDTH / 2;
		}
		else if(this.peekingDirection == Direction.WEST)
		{
			return Room.WIDTH / 2 * -1;
		}
		else if(this.shakingDirection == Direction.EAST)
		{
			return this.shakingDuration * -1;
		}
		else if(this.shakingDirection == Direction.WEST)
		{
			return this.shakingDuration ;
		}
		else
		{
			return 0;
		}
	}
	
	public int getOffsetY()
	{
		if(this.peekingDirection == Direction.SOUTH)
		{
			return Room.HEIGHT / 2;
		}
		else if(this.peekingDirection == Direction.NORTH)
		{
			return Room.HEIGHT / 2 * -1;
		}
		else if(this.shakingDirection == Direction.NORTH)
		{
			return this.shakingDuration * -1;
		}
		else if(this.shakingDirection == Direction.SOUTH)
		{
			return this.shakingDuration;
		}
		else
		{
			return 0;
		}
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	
	public void setPeeking(Direction direction)
	{
		this.peekingDirection = direction;
	}
	
	public void resetPeeking()
	{
		this.peekingDirection = Direction.NONE;
	}
	
	public void setShaking(Direction direction, int duration)
	{
		this.shakingDirection = direction;		
		this.shakingDuration = duration;
	}
}