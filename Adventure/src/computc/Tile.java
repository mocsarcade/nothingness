package computc;

import org.newdawn.slick.Image;

public class Tile
{
	public boolean collider;
	private Image image;
	private int type;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(Image image, int type)
	{
		this.image = image;
		this.type = type;
	}
	
	public Image getImage() 
	{
		return image;
	}
	
	public int getType() 
	{
		return type;
	}
}