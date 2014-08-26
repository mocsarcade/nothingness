package computc;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledWorld extends TiledMap
{
	private Tile[][] tiles;
	
	public TiledWorld(String tmx) throws SlickException
	{
		super(tmx);
		
		tiles = new Tile[this.getWidth()][this.getHeight()];

		for(int x = 0; x < this.getWidth(); x++)
		{
			for(int y = 0; y < this.getHeight(); y++)
			{
				int tid = this.getTileId(x, y, 0);
				
				tiles[x][y] = new Tile();
				tiles[x][y].collider = this.getTileProperty(tid, "collider", "false").equals("false");
			}
		}
	}
	
	public int getPixelWidth()
	{
		return this.getWidth() * this.getTileWidth();
	}
	
	public int getPixelHeight()
	{
		return this.getHeight() * this.getTileHeight();
	}
	
	public void update()
	{
		//code goes here.
	}
	
	public void render()
	{
		this.render(0, 0);
	}
}