package computc;

public class Camera
{
	private int x, y;
	private Entity target;
	
	public Camera(Entity target)
	{
		this.target = target;
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
			this.increaseY(delta);
		}
		else if(this.getY() > this.getTargetY())
		{
			this.decreaseY(delta);
		}
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
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
}