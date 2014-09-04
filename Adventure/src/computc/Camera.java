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
		}
		else if(this.getX() > this.getTargetX())
		{
			this.decreaseX(delta);
		}
		
		if(this.getY() < this.getTargetY())
		{
			this.increaseY( delta);
		}
		else if(this.getY() > this.getTargetY())
		{
			this.decreaseY(delta);
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
	
	public void increaseX(int delta)
	{
		this.x += delta;
	}
	
	public void decreaseX(int delta)
	{
		this.x -= delta;
	}
	
	public void increaseY(int delta)
	{
		this.y += delta;
	}
	
	public void decreaseY(int delta)
	{
		this.y -= delta;
	}
	
	public Entity getTarget()
	{
		return this.target;
	}
	
	public int getTargetY()
	{
		int height = this.target.getRoomyY() * Room.HEIGHT;
		return (int)(Math.floor(this.target.y / height)) * height;
	}
	
	public int getTargetX()
	{
		int width = this.target.getRoomyX() * Room.WIDTH;
		return (int)(Math.floor(this.target.x / width)) * width;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
}