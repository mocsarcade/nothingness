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
		int x = tx * 64 - camera.getX();
		int y = ty * 64 - camera.getY();
		
		this.image.draw(x, y);
	}
}