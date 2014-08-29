package computc;

import java.io.File;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Room
{
	private Tile[][] tiles;
	
	private final int WIDTH_OF_TILE = 64;
	private final int HEIGHT_OF_TILE = 64;
	private final int WIDTH_IN_TILES = 11;
	private final int HEIGHT_IN_TILES = 9;
	
	public Room() throws SlickException
	{
		TiledMap tmx = new TiledMap(Room.getRandomLayout());		
		this.tiles = new Tile[WIDTH_IN_TILES][HEIGHT_IN_TILES];
		
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
		for(int tx = 0; tx < WIDTH_IN_TILES; tx++)
		{
			for(int ty = 0; ty < HEIGHT_IN_TILES; ty++)
			{
				this.tiles[tx][ty].render(graphics, camera);
			}
		}
	}
	
	public int getWidthInTiles()
	{
		return WIDTH_IN_TILES;
	}
	
	public int getHeightInTiles()
	{
		return HEIGHT_IN_TILES;
	}
	
	public int getWidthOfTile()
	{
		return WIDTH_OF_TILE;
	}
	
	public int getHeightOfTile()
	{
		return HEIGHT_OF_TILE;
	}
	
	public int getWidthInPixels()
	{
		return WIDTH_IN_TILES * WIDTH_OF_TILE;
	}
	
	public int getHeightInPixels()
	{
		return HEIGHT_IN_TILES * HEIGHT_OF_TILE;
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