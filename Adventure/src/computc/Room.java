package computc;

import java.io.File;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Room
{
	private Tile[][] tiles;
	
	private int rx, ry;
	
	private final int WIDTH_OF_TILE = 64;
	private final int HEIGHT_OF_TILE = 64;
	private final int WIDTH_IN_TILES = 11;
	private final int HEIGHT_IN_TILES = 9;
	
	public Room westernRoom;
	public Room easternRoom;
	public Room southernRoom;
	public Room northernRoom;
	
	public Room(int rx, int ry) throws SlickException
	{
		this.rx = rx;
		this.ry = ry;
		
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
	
	public int getX()
	{
		return this.getRoomX() * this.getWidthInPixels();
	}
	
	public int getY()
	{
		return this.getRoomY() * this.getHeightInPixels();
	}
	
	public int getRoomX()
	{
		return this.rx;
	}
	
	public int getRoomY()
	{
		return this.ry;
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
		int tx = (int)(x) / WIDTH_OF_TILE;
		int ty = (int)(y) / HEIGHT_OF_TILE;
		
		return this.tiles[tx][ty];
	}
	
	public void connectRoom(Direction direction, Room room)
	{
		if(direction == Direction.NORTH)
		{
			this.connectNorthernRoom(room);
		}
		else if(direction == Direction.SOUTH)
		{
			this.connectSouthernRoom(room);
		}
		else if(direction == Direction.EAST)
		{
			this.connectEasternRoom(room);
		}
		else if(direction == Direction.WEST)
		{
			this.connectWesternRoom(room);
		}
	}
	
	public void connectNorthernRoom(Room that)
	{
		this.setNorthernRoom(that);
		that.setSouthernRoom(this);
	}
	
	public void connectSouthernRoom(Room that)
	{
		this.setSouthernRoom(that);
		that.setNorthernRoom(this);
	}
	
	public void connectEasternRoom(Room that)
	{
		this.setEasternRoom(that);
		that.setWesternRoom(this);
	}
	
	public void connectWesternRoom(Room that)
	{
		this.setWesternRoom(that);
		that.setEasternRoom(this);
	}
	
	public void setNorthernRoom(Room room)
	{
		this.northernRoom = room;
		this.tiles[11/2][0] = new Tile(this, 11/2, 0, this.tiles[11/2][1].image);
	}
	
	public void setSouthernRoom(Room room)
	{
		this.southernRoom = room;
		this.tiles[11/2][9-1] = new Tile(this, 11/2, 9-1, this.tiles[11/2][1].image);
	}
	
	public void setEasternRoom(Room room)
	{
		this.easternRoom = room;
		this.tiles[11-1][9/2] = new Tile(this, 11-1, 9/2, this.tiles[11/2][1].image);
	}
	
	public void setWesternRoom(Room room)
	{
		this.westernRoom = room;
		this.tiles[0][9/2] = new Tile(this, 0, 9/2, this.tiles[11/2][1].image);
	}
	
	public Room getNorthernRoom()
	{
		return this.northernRoom;
	}
	
	public Room getSouthernRoom()
	{
		return this.southernRoom;
	}
	
	public Room getEasternRoom()
	{
		return this.easternRoom;
	}
	
	public Room getWesternRoom()
	{
		return this.westernRoom;
	}
	
	public static String getRandomLayout()
	{
		Random random = new Random();
		File[] list = new File("./res/rooms").listFiles();
		return "./res/rooms/" + list[random.nextInt(list.length)].getName();
	}
}