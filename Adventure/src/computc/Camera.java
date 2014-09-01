package computc;

public class Camera
{
	private float x, y;
	private Entity target;
	private float speed = 1f;
	
	public Camera(Entity target)
	{
		this.target = target;
		this.x = target.getRoomyX() * Room.WIDTH;
		this.y = target.getRoomyY() * Room.HEIGHT;
	}
	
	public void update(int delta)
	{
		if(this.getX() < this.getTargetX())
		{
			this.increaseX(this.getSpeed() * delta);
		}
		else if(this.getX() > this.getTargetX())
		{
			this.decreaseX(this.getSpeed() * delta);
		}
		
		if(this.getY() < this.getTargetY())
		{
			this.increaseY(this.getSpeed() * delta);
		}
		else if(this.getY() > this.getTargetY())
		{
			this.decreaseY(this.getSpeed() * delta);
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