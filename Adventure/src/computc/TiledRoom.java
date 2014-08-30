package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledRoom extends TiledMap
{
	protected Tile[][] tiles;
	
	public TiledRoom() throws SlickException
	{
		
		super("res/world.tmx");
		try 
		{
			this.tiles = new Tile[this.getHeight()][this.getWidth()];
			System.out.println("the width apparently is: " + this.getWidth() + " and the Height apparently is: " + this.getHeight());
			for(int ty = 0; ty < this.getWidth(); ty++)
			{
				for(int tx = 0; tx < tiles[ty].length; tx++)
				{
					int tid = this.getTileId(tx, ty, 0);
				
					this.tiles[ty][tx] = new Tile();
				
					if(this.getTileProperty(tid, "block", "false").equals("true"))
						this.tiles[ty][tx].isBlock = true; 
					this.tiles[ty][tx].tileX = tx;
					this.tiles[ty][tx].tileY = ty;
					
					if(this.getTileProperty(tid, "door", "false").equals("true"))
						this.tiles[ty][tx].isDoor = true;
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = camera.getX() * -1;
		int y = camera.getY() * -1;
		
		this.render(x, y);
	}
	
	public int getPixelWidth()
	{
		return this.getWidth() * this.getTileWidth();
	}
	
	public int getPixelHeight()
	{
		return this.getHeight() * this.getTileHeight();
	}
	
	public Tile getTile(int ty, int tx)
	{
		if(tx >= 0 && tx < this.tiles.length
		&& ty >= 0 && ty < this.tiles.length)
		{
			return this.tiles[tx][ty];
		}
		else
		{
			return null;
		}
	}
	
	public Tile getTile(float y, float x)
	{
		int tx = (int)(x) / this.getTileWidth();
		int ty = (int)(y) / this.getTileHeight();
		
		return this.tiles[tx][ty];
	}
}