package computc;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledWorld extends TiledMap
{
	public TiledWorld(String tmx) throws SlickException
	{
		super(tmx);
	}
	
	public int getPixelWidth()
	{
		return getWidth() * getTileWidth();
	}
	
	public int getPixelHeight()
	{
		return getHeight() * getTileHeight();
	}
}