package computc;

import java.io.File;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Room
{
	private int rx, ry;
	private Tile[][] tiles;
	private Dungeon dungeon;
	
	public Room westernRoom;
	public Room easternRoom;
	public Room southernRoom;
	public Room northernRoom;
	
	public Room(Dungeon dungeon, int rx, int ry) throws SlickException
	{
		this.dungeon = dungeon;
		
		this.rx = rx;
		this.ry = ry;
		
		this.tiles = new Tile[Room.TILEY_WIDTH][Room.TILEY_HEIGHT];
		TiledMap tmx = new TiledMap(Room.getRandomLayout());
		
		for(int tx = 0; tx < this.getTileyWidth(); tx++)
		{
			for(int ty = 0; ty < this.getTileyHeight(); ty++)
			{
				int tid = tmx.getTileId(tx, ty, 0);
				
				this.tiles[tx][ty] = new Tile(this, tx, ty, tmx.getTileImage(tx, ty, 0));
				this.tiles[tx][ty].isBlocked = tid == 1;
			}
		}
		
		this.dungeon.rooms.add(this.rx, this.ry, this);
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		for(int tx = 0; tx < this.getTileyWidth(); tx++)
		{
			for(int ty = 0; ty < this.getTileyHeight(); ty++)
			{
				this.tiles[tx][ty].render(graphics, camera);
			}
		}
	}
	
	public int getX()
	{
		return this.getRoomyX() * Room.WIDTH;
	}
	
	public int getY()
	{
		return this.getRoomyY() * Room.HEIGHT;
	}
	
	public int getRoomyX()
	{
		return this.rx;
	}
	
	public int getRoomyY()
	{
		return this.ry;
	}
	
	public int getTileyWidth()
	{
		return Room.TILEY_WIDTH;
	}
	
	public int getTileyHeight()
	{
		return Room.TILEY_HEIGHT;
	}
	
	public int getWidth()
	{
		return this.getTileyWidth() * Tile.SIZE;
	}
	
	public int getHeight()
	{
		return this.getTileyHeight() * Tile.SIZE;
	}
	
	public Tile getTile(int tx, int ty)
	{
		return this.tiles[tx][ty];
	}
	
	public Tile getTile(float x, float y)
	{
		int tx = (int)(x) / Tile.SIZE;
		int ty = (int)(y) / Tile.SIZE;
		
		return this.tiles[tx][ty];
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
	
	public boolean hasNorthernRoom()
	{
		return this.northernRoom != null;
	}
	
	public boolean hasSouthernRoom()
	{
		return this.southernRoom != null;
	}
	
	public boolean hasEasternRoom()
	{
		return this.easternRoom != null;
	}
	
	public boolean hasWesternRoom()
	{
		return this.westernRoom != null;
	}
	
	public void setNorthernRoom(Room room)
	{
		this.northernRoom = room;
		this.tiles[11/2][0] = new Tile(this, 11/2, 0, this.tiles[11/2][1].getImage());
	}
	
	public void setSouthernRoom(Room room)
	{
		this.southernRoom = room;
		this.tiles[11/2][9-1] = new Tile(this, 11/2, 9-1, this.tiles[11/2][1].getImage());
	}
	
	public void setEasternRoom(Room room)
	{
		this.easternRoom = room;
		this.tiles[11-1][9/2] = new Tile(this, 11-1, 9/2, this.tiles[11/2][1].getImage());
	}
	
	public void setWesternRoom(Room room)
	{
		this.westernRoom = room;
		this.tiles[0][9/2] = new Tile(this, 0, 9/2, this.tiles[11/2][1].getImage());
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
	
	public final static int TILEY_WIDTH = 11;
	public final static int TILEY_HEIGHT = 9;
	public final static int WIDTH = Room.TILEY_WIDTH * Tile.SIZE;
	public final static int HEIGHT = Room.TILEY_HEIGHT * Tile.SIZE;
	
	public static String getRandomLayout()
	{
		Random random = new Random();
		File[] list = new File("./res/rooms").listFiles();
		return "./res/rooms/" + list[random.nextInt(list.length)].getName();
	}
}