package computc;

public class Camera
{
	private float x, y;
	private Entity target;
	
	public Camera(Entity target)
	{
		this.target = target;
	}
	
	public void update(int delta)
	{
		/*if(this.getX() < this.getTargetX())
		{
			this.increaseX(delta);
		}
		else if(this.getX() > this.getTargetX())
		{
			this.decreaseX(delta);
		}
		
		if(this.getY() < this.getTargetY())
		{
			this.increaseY(delta);
		}
		else if(this.getY() > this.getTargetY())
		{
			this.decreaseY(delta);
		}*/
	}
	
	public int getX()
	{
		return (int)(this.x);
	}
	
	public int getY()
	{
		return (int)(this.y);
	}
	
	public int getTargetX()
	{
		int width = Adventure.SCREEN_WIDTH;
		return (int)(Math.floor(this.target.x / width)) * width;
	}
	
	public int getTargetY()
	{
		int height = Adventure.SCREEN_HEIGHT;
		return (int)(Math.floor(this.target.y / height)) * height;
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
}