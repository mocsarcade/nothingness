package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledRoom extends TiledMap
{
	
	
	protected int x;
	protected int y;
//	protected int cameraTweaker;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private Image image;
	
	public TiledRoom(String tmx) throws SlickException
	{
		super(tmx);
		this.image = new Image(tmx);
	}
	
	public int getPixelWidth()
	{
		return this.getWidth() * this.getTileWidth();
	}
	
	public int getPixelHeight()
	{
		return this.getHeight() * this.getTileHeight();
	}
	
	public void setPosition(double x, double y) 
	{
		this.x += (x - this.x);
		this.y += (y - this.y);
		
		fixBounds();
		
	}
	
	public void fixBounds() 
	{
		if(x < xmin) x = xmin;      
		if(y < ymin) y = ymin;
		if (x > xmax) x = xmax;
		if (y > ymax) y = ymax;
	}
	
	public void loadMap()
	{
		xmin = (Adventure.SCREEN_WIDTH * Adventure.TILE_SIZE)/2 - width;
		xmax = 0;
		ymin = (Adventure.SCREEN_HEIGHT * Adventure.TILE_SIZE)/2 - height;
		ymax = 0;
	}
	
	public void render()
	{
		image.draw(x, y);
	}
}