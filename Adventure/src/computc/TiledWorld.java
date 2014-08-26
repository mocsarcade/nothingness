package computc;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledWorld extends TiledMap
{
	private Hero hero;
	private Tile[][] tiles;
	
	public TiledWorld(String tmx) throws SlickException
	{
		super(tmx);
		
		tiles = new Tile[this.getWidth()][this.getHeight()];

		for(int tx = 0; tx < this.getWidth(); tx++)
		{
			for(int ty = 0; ty < this.getHeight(); ty++)
			{
				int tid = this.getTileId(tx, ty, 0);
				
				tiles[tx][ty] = new Tile();
				tiles[tx][ty].collider = this.getTileProperty(tid, "collider", "false").equals("false");
			}
		}
		
		hero = new Hero(this, 5, 0);
	}
	
	public int getPixelWidth()
	{
		return this.getWidth() * this.getTileWidth();
	}
	
	public int getPixelHeight()
	{
		return this.getHeight() * this.getTileHeight();
	}
	
	public void update(Input input, int delta)
	{
		hero.update(input, delta);
	}
	
	public void render()
	{
		this.render(0, 0);
		hero.render();
	}
}