package computc.worlds.rooms;

import java.util.List;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import computc.Direction;
import computc.Game;
import computc.cameras.Camera;
import computc.entities.Coin;
import computc.entities.Key;
import computc.entities.OldMan;
import computc.entities.Thug;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileTemplate;

public class Room
{
	private int rx, ry;
	private Dungeon dungeon;
	
	public Room westernRoom;
	public Room easternRoom;
	public Room southernRoom;
	public Room northernRoom;
	
	public Direction critpathDirection;
	
	public boolean visited = false;
	
	private Tile[][] tiles = new Tile[Room.TILEY_WIDTH][Room.TILEY_HEIGHT];
	private RoomTemplate template;
	
	public Room(Dungeon dungeon, int rx, int ry)
	{
		this(dungeon, rx, ry, dungeon.getRandomRoomTemplate());
	}
	
	public Room(Dungeon dungeon, int rx, int ry, RoomTemplate template)
	{
		this.dungeon = dungeon;
		
		this.rx = rx;
		this.ry = ry;
		
		this.template = template;
		
		for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
		{
			for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
			{
				int gid = this.template.getTileID(tx, ty);
				this.tiles[tx][ty] = new Tile(this, tx, ty, gid);
			}
		}
		
		this.dungeon.addRoom(this);
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
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		for(int tx = 0; tx < this.getTileyWidth(); tx++)
		{
			for(int ty = 0; ty < this.getTileyHeight(); ty++)
			{
				this.tiles[tx][ty].renderOnMap(graphics, camera);
			}
		}
	}
	
	/*
	 * Returns the horizontal position
	 * of this room in units of pixels
	 * and relative to the dungeon.
	 * 
	 * @units_of		pixels
	 * @relative_to		dungeon
	 */
	public int getX()
	{
		return this.getRoomyX() * Room.WIDTH;
	}

	/*
	 * Returns the vertical position
	 * of this room in units of pixels
	 * and relative to the dungeon.
	 * 
	 * @units_of		pixels
	 * @relative_to		dungeon
	 */
	public int getY()
	{
		return this.getRoomyY() * Room.HEIGHT;
	}

	/*
	 * Returns the horizontal position
	 * of this room in units of rooms
	 * and relative to the dungeon.
	 * 
	 * @units_of		rooms
	 * @relative_to		dungeon
	 */
	public int getRoomyX()
	{
		return this.rx;
	}

	/*
	 * Returns the vertical position
	 * of this room in units of rooms
	 * and relative to the dungeon.
	 * 
	 * @units_of		rooms
	 * @relative_to		dungeon
	 */
	public int getRoomyY()
	{
		return this.ry;
	}

	/*
	 * Returns the horizontal dimension
	 * of this room in units of pixels.
	 * 
	 * @units_of		pixels
	 */
	public int getWidth()
	{
		return Room.WIDTH;
	}

	/*
	 * Returns the vertical dimension
	 * of this room in units of pixels.
	 * 
	 * @units_of		pixels
	 */
	public int getHeight()
	{
		return Room.HEIGHT;
	}

	/*
	 * Returns the horizontal dimension
	 * of this room in units of tiles.
	 * 
	 * @units_of		tiles
	 */
	public int getTileyWidth()
	{
		return Room.TILEY_WIDTH;
	}
	
	/*
	 * Returns the vertical dimension
	 * of this room in units of tiles.
	 * 
	 * @units_of		tiles
	 */
	public int getTileyHeight()
	{
		return Room.TILEY_HEIGHT;
	}
	
	/*
	 * Returns the tile at the specified
	 * position in units of tiles and
	 * relative to the room.
	 * 
	 * @units_of		tiles
	 * @relative_to		room
	 */
	public Tile getTile(int tx, int ty)
	{
		return this.tiles[tx][ty];
	}
	
	/*
	 * Returns the tile at the specified
	 * position in units of pixels and
	 * relative to the room.
	 * 
	 * @units_of		pixels
	 * @relative_to		room
	 */
	public Tile getTile(float x, float y)
	{
		int tx = (int)(Math.floor(x / Tile.SIZE));
		int ty = (int)(Math.floor(y / Tile.SIZE));
		
		return this.tiles[tx][ty];
	}
	
	/*
	 * Sets a tile at the specified
	 * position in units of tiles
	 * and relative to the room.
	 * 
	 * @units_of		tiles
	 * @relative_to		room
	 */
	public void setTile(int tx, int ty, Tile tile)
	{
		this.tiles[tx][ty] = tile;
	}
	
	/*
	 * Returns the room that is north
	 * of this room, or null if there
	 * is no such room.
	 */
	public Room getNorthernRoom()
	{
		return this.northernRoom;
	}

	/*
	 * Returns the room that is south
	 * of this room, or null if there
	 * is no such room.
	 */
	public Room getSouthernRoom()
	{
		return this.southernRoom;
	}

	/*
	 * Returns the room that is east
	 * of this room, or null if there
	 * is no such room.
	 */
	public Room getEasternRoom()
	{
		return this.easternRoom;
	}

	/*
	 * Returns the room that is west
	 * of this room, or null if there
	 * is no such room.
	 */
	public Room getWesternRoom()
	{
		return this.westernRoom;
	}

	/*
	 * Returns true if there is a room
	 * to the north of this room, or
	 * false if there is no such room.
	 */
	public boolean hasNorthernRoom()
	{
		return this.northernRoom != null;
	}

	/*
	 * Returns true if there is a room
	 * to the south of this room, or
	 * false if there is no such room.
	 */
	public boolean hasSouthernRoom()
	{
		return this.southernRoom != null;
	}

	/*
	 * Returns true if there is a room
	 * to the east of this room, or
	 * false if there is no such room.
	 */
	public boolean hasEasternRoom()
	{
		return this.easternRoom != null;
	}

	/*
	 * Returns true if there is a room
	 * to the west of this room, or
	 * false if there is no such room.
	 */
	public boolean hasWesternRoom()
	{
		return this.westernRoom != null;
	}

	/*
	 * Sets the room to the north of
	 * this room, and opens a door
	 * to reach that room. 
	 */
	private void setNorthernRoom(Room room)
	{
		this.northernRoom = room;
		
		int tx = Room.TILEY_WIDTH / 2, ty = 0;
		this.tiles[tx][ty] = new Tile(this, tx, ty, 2);
	}

	/*
	 * Sets the room to the south of
	 * this room, and opens a door
	 * to reach that room. 
	 */
	private void setSouthernRoom(Room room)
	{
		this.southernRoom = room;
		
		int tx = Room.TILEY_WIDTH / 2, ty = Room.TILEY_HEIGHT - 1;
		this.tiles[tx][ty] = new Tile(this, tx, ty, 2);
	}

	/*
	 * Sets the room to the east of
	 * this room, and opens a door
	 * to reach that room. 
	 */
	private void setEasternRoom(Room room)
	{
		this.easternRoom = room;
		
		int tx = Room.TILEY_WIDTH - 1, ty = Room.TILEY_HEIGHT / 2;
		this.tiles[tx][ty] = new Tile(this, tx, ty, 2);
	}

	/*
	 * Sets the room to the west of
	 * this room, and opens a door
	 * to reach that room. 
	 */
	private void setWesternRoom(Room room)
	{
		this.westernRoom = room;
		
		int tx = 0, ty = Room.TILEY_HEIGHT / 2;
		this.tiles[tx][ty] = new Tile(this, tx, ty, 2);
	}
	
	/*
	 * Executes the relevant subroutines
	 * to connect a room to the north.
	 */
	public void connectNorthernRoom(Room that)
	{
		this.setNorthernRoom(that);
		that.setSouthernRoom(this);
	}
	
	/*
	 * Executes the relevant subroutines
	 * to connect a room to the south.
	 */
	public void connectSouthernRoom(Room that)
	{
		this.setSouthernRoom(that);
		that.setNorthernRoom(this);
	}

	/*
	 * Executes the relevant subroutines
	 * to connect a room to the east.
	 */
	public void connectEasternRoom(Room that)
	{
		this.setEasternRoom(that);
		that.setWesternRoom(this);
	}

	/*
	 * Executes the relevant subroutines
	 * to connect a room to the west.
	 */
	public void connectWesternRoom(Room that)
	{
		this.setWesternRoom(that);
		that.setEasternRoom(this);
	}

	/*
	 * Executes the relevant subroutines
	 * to connect a room in any direction.
	 */
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
	
	/*
	 * Executes the relevant subroutines to
	 * instantiate a new room to the north.
	 */
	public Room instantiateNorthernRoom()
	{
		Room room = new Room(this.dungeon, this.rx, this.ry - 1);
		this.connectNorthernRoom(room);
		return room;
	}

	/*
	 * Executes the relevant subroutines to
	 * instantiate a new room to the south.
	 */
	public Room instantiateSouthernRoom()
	{
		Room room = new Room(this.dungeon, this.rx, this.ry + 1);
		this.connectSouthernRoom(room);
		return room;
	}

	/*
	 * Executes the relevant subroutines to
	 * instantiate a new room to the east.
	 */
	public Room instantiateEasternRoom()
	{
		Room room = new Room(this.dungeon, this.rx + 1, this.ry);
		this.connectEasternRoom(room);
		return room;
	}

	/*
	 * Executes the relevant subroutines to
	 * instantiate a new room to the west.
	 */
	public Room instantiateWesternRoom()
	{
		Room room = new Room(this.dungeon, this.rx - 1, this.ry);
		this.connectWesternRoom(room);
		return room;
	}

	/*
	 * Executes the relevant subroutines to
	 * instantiate a new room in any direction.
	 */
	public Room instantiateRoom(Direction direction) 
	{
		if(direction == Direction.NORTH)
		{
			return this.instantiateNorthernRoom();
		}
		else if(direction == Direction.SOUTH)
		{
			return this.instantiateSouthernRoom();
		}
		else if(direction == Direction.EAST)
		{
			return this.instantiateEasternRoom();
		}
		else if(direction == Direction.WEST)
		{
			return this.instantiateWesternRoom();
		}
		else
		{
			return null;
		}
	}
	
	public ArrayList<Direction> getPotentialDirections()
	{
		ArrayList<Direction> directions = new ArrayList<Direction>();
		
		if(!this.dungeon.hasRoom(this.rx, this.ry - 1))
		{
			directions.add(Direction.NORTH);
		}
		if(!this.dungeon.hasRoom(this.rx, this.ry + 1))
		{
			directions.add(Direction.SOUTH);
		}
		if(!this.dungeon.hasRoom(this.rx + 1, this.ry))
		{
			directions.add(Direction.EAST);
		}
		if(!this.dungeon.hasRoom(this.rx - 1, this.ry))
		{
			directions.add(Direction.WEST);
		}
		
		return directions;
	}
	
	public Direction getRandomPotentialDirection()
	{
		ArrayList<Direction> directions = this.getPotentialDirections();
		
		if(directions.size() > 0)
		{
			Collections.shuffle(directions);
			return directions.get(0);
		}
		else
		{
			return Direction.NONE;
		}
	}
	
	public void addNorthernArrowTile()
	{
		int tx = Room.TILEY_WIDTH / 2, ty = 0;
		//this.tiles[tx][ty+1] = new ArrowFloorTile(this, tx, ty+1, Direction.NORTH);
	}

	public void addSouthernArrowTile()
	{
		int tx = Room.TILEY_WIDTH / 2, ty = Room.TILEY_HEIGHT - 1;
		//this.tiles[tx][ty-1] = new ArrowFloorTile(this, tx, ty-1, Direction.SOUTH);
	}

	public void addEasternArrowTile()
	{
		int tx = Room.TILEY_WIDTH - 1, ty = Room.TILEY_HEIGHT / 2;
		//this.tiles[tx-1][ty] = new ArrowFloorTile(this, tx-1, ty, Direction.EAST);
	}
	
	public void addWesternArrowTile()
	{
		int tx = 0, ty = Room.TILEY_HEIGHT / 2;
		//this.tiles[tx+1][ty] = new ArrowFloorTile(this, tx+1, ty, Direction.WEST);
	}
	
	public void addArrowTile(Direction direction)
	{
		if(direction == Direction.NORTH)
		{
			this.addNorthernArrowTile();
		}
		else if(direction == Direction.SOUTH)
		{
			this.addSouthernArrowTile();
		}
		else if(direction == Direction.EAST)
		{
			this.addEasternArrowTile();
		}
		else if(direction == Direction.WEST)
		{
			this.addWesternArrowTile();
		}
	}
	
	public void addNorthernDoor()
	{
		int tx = Room.TILEY_WIDTH / 2, ty = 0;
		//this.tiles[tx][ty] = new DoorTile(this, tx, ty);
	}

	public void addSouthernDoor()
	{
		int tx = Room.TILEY_WIDTH / 2, ty = Room.TILEY_HEIGHT - 1;
		//this.tiles[tx][ty] = new DoorTile(this, tx, ty);
	}

	public void addEasternDoor()
	{
		int tx = Room.TILEY_WIDTH - 1, ty = Room.TILEY_HEIGHT / 2;
		//this.tiles[tx][ty] = new DoorTile(this, tx, ty);
	}
	
	public void addWesternDoor()
	{
		int tx = 0, ty = Room.TILEY_HEIGHT / 2;
		//this.tiles[tx][ty] = new DoorTile(this, tx, ty);
	}
	
	public void addDoor(Direction direction)
	{
		if(direction == Direction.NORTH)
		{
			this.addNorthernDoor();
		}
		else if(direction == Direction.SOUTH)
		{
			this.addSouthernDoor();
		}
		else if(direction == Direction.EAST)
		{
			this.addEasternDoor();
		}
		else if(direction == Direction.WEST)
		{
			this.addWesternDoor();
		}
	}

	public void addKey()
	{
		Point spawnpoint = this.template.getRandomChestSpawnpoint();
		this.dungeon.keys.add(new Key(this.dungeon, this, spawnpoint.x, spawnpoint.y));
	}
	
	public final static int TILEY_WIDTH = 11;
	public final static int TILEY_HEIGHT = 9;
	public final static int WIDTH = Room.TILEY_WIDTH * Tile.SIZE;
	public final static int HEIGHT = Room.TILEY_HEIGHT * Tile.SIZE;
	
	public static HashMap<String, TileTemplate> tileTemplates = new HashMap<String, TileTemplate>();
}