package computc;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledRoom extends TiledMap
{
	protected Tile[][] tiles;
	
	public TiledRoom(String tmx) throws SlickException
	{
		super(tmx);
		
		this.tiles = new Tile[this.getWidth()][this.getHeight()];
		
		for(int tx = 0; tx < this.getWidth(); tx++)
		{
			for(int ty = 0; ty < this.getHeight(); ty++)
			{
				int tid = this.getTileId(tx, ty, 0);
				
				this.tiles[tx][ty] = new Tile();
				this.tiles[tx][ty].isBlock = this.getTileProperty(tid, "block", "false").equals("false");
				this.tiles[tx][ty].isDoor = this.getTileProperty(tid, "door", "false").equals("false");
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
	
	public Tile getTile(int tx, int ty)
	{
		return this.tiles[tx][ty];
	}
	
	public Tile getTile(float x, float y)
	{
		int tx = (int)(x) / this.getTileWidth();
		int ty = (int)(y) / this.getTileHeight();
		
		return this.tiles[tx][ty];
	}
}