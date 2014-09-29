package computc.worlds.rooms;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import computc.Direction;
import computc.Game;
import computc.cameras.Camera;
import computc.worlds.Door;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.DungeonSegment;
import computc.worlds.tiles.DoorTile;
import computc.worlds.tiles.FloorTile;
import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileSet;
import computc.worlds.tiles.WallTile;

public class Room
{
	private int rx, ry;
	private Dungeon dungeon;
	
	private ArrayList<Door> doors = new ArrayList<Door>();
	private Tile[][] tiles = new Tile[Room.TILEY_WIDTH][Room.TILEY_HEIGHT];

	private DungeonSegment dungeonsegment = null;
	private TileSet tileset = Game.assets.getTileSet("./res/tilesets/rocky.tileset.xml");
	private RoomLayout roomlayout = Game.assets.getRoomLayout("./res/rooms/empty.room.tmx");
	
	public boolean hasVisited = false;
	private Direction majorDirection = Direction.NONE;
	
	public Room(Dungeon dungeon, int rx, int ry)
	{
		this.rx = rx;
		this.ry = ry;

		this.dungeon = dungeon;
		this.dungeon.addRoom(this);
	}
	
	public void initiate()
	{
		for(int tx = 0; tx < this.getTileyWidth(); tx++)
		{
			for(int ty = 0; ty < this.getTileyHeight(); ty++)
			{
				int gid = this.getRoomLayout().getTileGID(tx, ty);
				
				if(gid == WallTile.GID)
				{
					this.tiles[tx][ty] = new WallTile(this, tx, ty);
				}
				else if(gid == FloorTile.GID)
				{
					this.tiles[tx][ty] = new FloorTile(this, tx, ty);
				}
			}
		}
		
		for(Door door : this.doors)
		{
			if(door.getDirection() == Direction.NORTH)
			{
				int tx = Room.TILEY_WIDTH / 2;
				int ty = 0;
				
				this.tiles[tx][ty] = new FloorTile(this, tx, ty);
			}
			else if(door.getDirection() == Direction.SOUTH)
			{
				int tx = Room.TILEY_WIDTH / 2;
				int ty = Room.TILEY_HEIGHT - 1;
				
				this.tiles[tx][ty] = new FloorTile(this, tx, ty);
			}
			else if(door.getDirection() == Direction.EAST)
			{
				int tx = Room.TILEY_WIDTH - 1;
				int ty = Room.TILEY_HEIGHT / 2;
				
				this.tiles[tx][ty] = new FloorTile(this, tx, ty);
			}
			else if(door.getDirection() == Direction.WEST)
			{
				int tx = 0;
				int ty = Room.TILEY_HEIGHT / 2;
				
				this.tiles[tx][ty] = new FloorTile(this, tx, ty);
			}
		}
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		for(int tx = 0; tx < this.getTileyWidth(); tx++)
		{
			for(int ty = 0; ty < this.getTileyHeight(); ty++)
			{
				this.getTile(tx, ty).render(graphics, camera);
			}
		}
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		for(int tx = 0; tx < this.getTileyWidth(); tx++)
		{
			for(int ty = 0; ty < this.getTileyHeight(); ty++)
			{
				this.getTile(tx, ty).renderOnMap(graphics, camera);
			}
		}
	}
	
	public Dungeon getDungeon()
	{
		return this.dungeon;
	}
	
	public DungeonSegment getDungeonSegment()
	{
		return this.dungeonsegment;
	}
	
	public void setDungeonSegment(DungeonSegment dungeonsegment)
	{
		this.dungeonsegment = dungeonsegment;
	}
	
	public RoomLayout getRoomLayout()
	{
		return this.roomlayout;
	}
	
	public void setRoomLayout(RoomLayout roomlayout)
	{
		this.roomlayout = roomlayout;
	}
	
	public TileSet getTileSet()
	{
		return this.tileset;
	}
	
	public void setTileSet(TileSet tileset)
	{
		this.tileset = tileset;
	}

	public void setMajorDirection(Direction direction)
	{
		this.majorDirection = direction;
	}
	
	public Direction getMajorDirection()
	{
		return this.majorDirection;
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
		return this.getRoomyX() * this.getWidth();
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
		return this.getRoomyY() * this.getHeight();
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
		
		return this.getTile(tx, ty);
	}
	
	/*
	 * Configures a tile at the specified
	 * position in units of tiles and
	 * relative to the room.
	 * 
	 * @units_of		tiles
	 * @relative_to		room
	 */
	public void setTile(int tx, int ty, Tile tile)
	{
		this.tiles[tx][ty] = tile;
	}
	
	public ArrayList<Direction> getDirectionsForAnotherRoom()
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
	
	public Direction getRandomDirectionForAnotherRoom()
	{
		ArrayList<Direction> directions = this.getDirectionsForAnotherRoom();
		
		if(directions.size() > 0)
		{
			return directions.get(Game.random.nextInt(directions.size()));
		}
		else
		{
			return Direction.NONE;
		}
	}
	
	public Room getRoomToTheNorth()
	{
		int rx = this.getRoomyX();
		int ry = this.getRoomyY() - 1;
		
		return this.getDungeon().getRoom(rx, ry);
	}
	
	public boolean hasRoomToTheNorth()
	{
		int rx = this.getRoomyX();
		int ry = this.getRoomyY() - 1;
		
		return this.getDungeon().hasRoom(rx, ry);
	}
	
	public void connectWithRoomToTheNorth()
	{
		Room roomToTheNorth = this.getRoomToTheNorth();
		
		this.doors.add(new Door(Direction.NORTH, -1));
		roomToTheNorth.doors.add(new Door(Direction.SOUTH, -1));
	}
	
	public final static int TILEY_WIDTH = 11;
	public final static int TILEY_HEIGHT = 9;
	public final static int WIDTH = Room.TILEY_WIDTH * Tile.SIZE;
	public final static int HEIGHT = Room.TILEY_HEIGHT * Tile.SIZE;
}