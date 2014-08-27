package computc;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledRoom extends TiledMap
{
	
	
	protected int x;
	protected int y;
	protected double cameraTweaker;
	
	protected int fullMapWidth;
	protected int fullMapHeight;
	
	protected boolean[][] doors;
	protected boolean[][] blocked;
	
	
	public TiledRoom(String tmx) throws SlickException
	{
		super(tmx);
	}
	
	public int getPixelWidth()
	{
		return this.getWidth() * this.getTileWidth();
	}
	
	public int getPixelHeight()
	{
		return this.getHeight() * this.getTileHeight();
	}
	
	public void setCameraTweaker(double d) 
	{
		cameraTweaker = d;
	}
	
	public int getX() 
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean isAccessible(float x, float y)
	{
	int tileX = (int)x / getTileWidth();
	int tileY = (int)y / getTileHeight();
	return doors[tileX][tileY];
	}
	
	public boolean isBlocked(float x, float y)
	{
	int tileX = (int)x / getTileWidth();
	int tileY = (int)y / getTileHeight();
	return blocked[tileX][tileY];
	}
	
	public void loadRoom() 
	{
		blocked = new boolean [getWidth()][getHeight()];
		doors = new boolean[getWidth()][getHeight()];
		
		
		for (int xAxis=0;xAxis<getWidth(); xAxis++)
		{
			for (int yAxis=0;yAxis<getHeight(); yAxis++)
			{
				int tileID = getTileId(xAxis, yAxis, 0);
				String doorValue = getTileProperty(tileID, "door", "false");
				if ("true".equals(doorValue))
					{
					doors[xAxis][yAxis] = true;
					}
				
				String blockedValue = getTileProperty(tileID, "stone", "false");
				if ("true".equals(blockedValue))
					{
					blocked[xAxis][yAxis] = true;
					}
			}
		}
	}
	
}