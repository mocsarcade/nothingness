package computc;

import java.io.File;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Room
{
	protected Tile[][] tiles;
	
	public Room() throws SlickException
	{
		this.tiles = new Tile[11][9];
		
		TiledMap tmx = new TiledMap(Room.getRandomLayout());
		
		for(int tx = 0; tx < tmx.getWidth(); tx++)
		{
			for(int ty = 0; ty < tmx.getHeight(); ty++)
			{
				int tid = tmx.getTileId(tx, ty, 0);
				
				this.tiles[tx][ty] = new Tile(this, tx, ty, tmx.getTileImage(tx, ty, 0));
				this.tiles[tx][ty].isBlock = tmx.getTileProperty(tid, "block", "false").equals("false");
				this.tiles[tx][ty].isDoor = tmx.getTileProperty(tid, "door", "false").equals("false");
			}
		}
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		for(int tx = 0; tx < this.getWidthInTiles(); tx++)
		{
			for(int ty = 0; ty < this.getHeightInTiles(); ty++)
			{
				this.tiles[tx][ty].render(graphics, camera);
			}
		}
	}
	
	public int getWidthInTiles()
	{
		return 11;
	}
	
	public int getHeightInTiles()
	{
		return 9;
	}
	
	public int getWidthInPixels()
	{
		return 11 * 64;
	}
	
	public int getHeightInPixels()
	{
		return 9 * 64;
	}
	
	public int getWidthOfTile()
	{
		return 64;
	}
	
	public int getHeightOfTile()
	{
		return 64;
	}
	
	public Tile getTile(int tx, int ty)
	{
		return this.tiles[tx][ty];
	}
	
	public Tile getTile(float x, float y)
	{
		int tx = (int)(x) / 64;
		int ty = (int)(y) / 64;
		
		return this.tiles[tx][ty];
	}
	
	public static String getRandomLayout()
	{
		Random random = new Random();
		File[] list = new File("./res/rooms").listFiles();
		return "./res/rooms/" + list[random.nextInt(list.length)].getName();
	}
}