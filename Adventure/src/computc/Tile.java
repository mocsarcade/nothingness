package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public class Tile
{
	public boolean isBlock;
	public boolean isDoor;
	
	private Room room;
	private int tx, ty;
	private Image image;
	
	public Tile(Room room, int tx, int ty, Image image)
	{
		this.room = room;
		this.tx = tx;
		this.ty = ty;
		this.image = image;
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = tx * this.getWidth() - camera.getX();
		int y = ty * this.getHeight() - camera.getY();
		
		this.image.draw(x, y);
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