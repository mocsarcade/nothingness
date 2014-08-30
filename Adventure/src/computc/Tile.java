package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public class Tile
{
	public boolean isBlock;
	
	private Room room;
	private int tx;
	private int ty;
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
		return this.getTileyX() * Tile.SIZE;
	}
	
	public int getY()
	{
		return this.getTileyY() * Tile.SIZE;
	}
	
	public int getTileyX()
	{
		return this.tx;
	}
	
	public int getTileyY()
	{
		return this.ty;
	}
	
	public final static int SIZE = 64;
}