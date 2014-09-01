package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public class Tile
{
	private Room room;
	private int tx;
	private int ty;
	private Image image;
	
	public boolean isBlocked;
	
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
	
	public Image getImage()
	{
		return this.image;
	}
	
	public final static int SIZE = 64;
}