package computc;

public class Camera
{
	public Entity target;
	
	public Camera(Entity target)
	{
		this.target = target;
	}
	
	public int getX()
	{
		int rx = (int)(Math.floor(this.target.x / Adventure.SCREEN_WIDTH));
		return rx * Adventure.SCREEN_WIDTH;
	}
	
	public int getY()
	{
		int ry = (int)(Math.floor(this.target.y / Adventure.SCREEN_HEIGHT));
		return ry * Adventure.SCREEN_HEIGHT;
	}
}