package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public class Tile
{
	private Room room;
	private int tx;
	private int ty;
	
	public boolean isBlocked;
	
	public Tile(Room room, int tx, int ty)
	{
		this.room = room;
		this.tx = tx;
		this.ty = ty;
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = this.getX() + this.room.getX() - camera.getX();
		int y = this.getY() + this.room.getY() - camera.getY();
		
		if(this.isBlocked)
		{
			Tile.WALL_IMAGE.draw(x, y);
		}
		else
		{
			Tile.FLOOR_IMAGE.draw(x, y);
		}
	}
	
	public int getX()
	{
		return this.getTileyX() * this.getWidth();
	}
	
	public int getY()
	{
		return this.getTileyY() * this.getHeight();
	}
	
	public int getTileyX()
	{
		return this.tx;
	}
	
	public int getTileyY()
	{
		return this.ty;
	}
	
	public int getWidth()
	{
		return Tile.SIZE;
	}
	
	public int getHeight()
	{
		return Tile.SIZE;
	}
	
	public final static int SIZE = 64;
	
	public static Image WALL_IMAGE;
	public static Image FLOOR_IMAGE;
}