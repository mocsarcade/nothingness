package computc;

import java.io.File;
import java.util.Random;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Room
{
	private int rx, ry;
	
	public final static int WIDTH = 11*64;
	public final static int HEIGHT = 9*64;
	
	public Room westernRoom;
	public Room easternRoom;
	public Room southernRoom;
	public Room northernRoom;
	
	private Tile[][] tiles;
	private LinkedList<Thug> thugs;
	
	public Room(int rx, int ry) throws SlickException
	{
		this.rx = rx;
		this.ry = ry;
		
		int width = Game.screen.getWidthInTiles();
		int height = Game.screen.getHeightInTiles();
		this.tiles = new Tile[width][height];
		
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
		
		this.thugs = new LinkedList<Thug>();
		this.thugs.add(new Thug(this, 1, 1));
	}
	
	public void update(int delta)
	{
		for(Thug thug : this.thugs)
		{
			thug.update(delta);
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
		
		for(Thug thug : this.thugs)
		{
			thug.render(graphics, camera);
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
		return this.tiles.length;
	}
	
	public int getHeightInTiles()
	{
		return this.tiles[0].length;
	}
	
	public int getWidthInPixels()
	{
		return this.getWidthInTiles() * this.getTile(0, 0).getWidth();
	}
	
	public int getHeightInPixels()
	{
		return this.getHeightInTiles() * this.getTile(0, 0).getHeight();
	}
	
	public Tile getTile(int tx, int ty)
	{
		return this.tiles[tx][ty];
	}
	
	public Tile getTile(float x, float y)
	{
		int tx = (int)(x) / this.getTile(0, 0).getWidth();
		int ty = (int)(y) / this.getTile(0, 0).getHeight();
		
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
	
	public static String getRandomLayout()
	{
		Random random = new Random();
		File[] list = new File("./res/rooms").listFiles();
		return "./res/rooms/" + list[random.nextInt(list.length)].getName();
	}
}