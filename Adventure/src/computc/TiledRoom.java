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
		
		this.tiles = new Tile[this.getWidth()][this.getHeight()];
		System.out.println("Does it even get here?");
		for(int tx = 0; tx < this.getWidth(); tx++)
		{
			for(int ty = 0; ty < this.getHeight(); ty++)
			{
				int tid = this.getTileId(tx, ty, 0);
				
				this.tiles[tx][ty] = new Tile();
				
				if(this.getTileProperty(tid, "block", "false").equals("true"))
				this.tiles[tx][ty].isBlock = true; 
				System.out.println("isBlock is : " + this.tiles[tx][ty].isBlock);
				
				if(this.getTileProperty(tid, "door", "false").equals("true"))
				this.tiles[tx][ty].isDoor = true;
			}
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