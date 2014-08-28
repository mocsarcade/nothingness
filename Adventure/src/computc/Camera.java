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
		this.x = this.getTargetX();
		this.y = this.getTargetY();
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
}