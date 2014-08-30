package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public class Tile
{
	public boolean isBlock;
	public boolean isDoor;
	
	private Room room;
	private int tx, ty;
	public Image image;
	
	public Tile(Room room, int tx, int ty, Image image)
	{
		this.room = room;
		this.tx = tx;
		this.ty = ty;
		this.image = image;
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = this.getX() + this.room.getX() - camera.getX();
		int y = this.getY() + this.room.getY() - camera.getY();
		
		this.image.draw(x, y);
	}
	
	public int getX()
	{
		return this.getTileX() * this.getWidth();
	}
	
	public int getY()
	{
		return this.getTileY() * this.getHeight();
	}
	
	public int getTileX()
	{
		return this.tx;
	}
	
	public int getTileY()
	{
		return this.ty;
	}
	
	public int getWidth()
	{
		return this.image.getWidth();
	}
	
	public int getHeight()
	{
		return this.image.getHeight();
	}
}