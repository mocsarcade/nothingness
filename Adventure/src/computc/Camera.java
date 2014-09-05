package computc;

public class Camera
{
	private float x, y;
	private Entity target;
	private float speed = 1f;
	
	public Camera(Entity target)
	{
		this.target = target;
		
		this.x = this.getTargetX();
		this.y = this.getTargetY();
	}
	
	public void update(int delta)
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
	
	public int getX()
	{
		return (int)(this.x);
	}
	
	public int getY()
	{
		return (int)(this.y);
	}
	
	public void increaseX(int amount)
	{
		this.x += amount;
	}
	
	public void decreaseX(int amount)
	{
		this.x -= amount;
	}
	
	public void increaseY(int amount)
	{
		this.y += amount;
	}
	
	public void decreaseY(int amount)
	{
		this.y -= amount;
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