package computc.worlds.rooms;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import computc.Direction;
import computc.Game;
import computc.cameras.Camera;
import computc.entities.Key;
import computc.entities.Loafer;
import computc.entities.Maniac;
import computc.entities.Thug;
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
	private TileSet tileset = Game.assets.getTileSet("./res/tilesets/null.tileset.xml");
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
			float dtx = door.getTileyX() - this.getTileyX();
			float dty = door.getTileyY() - this.getTileyY();
			int tx = (int)(Math.floor(Math.abs(dtx)));
			int ty = (int)(Math.floor(Math.abs(dty)));
			
			this.tiles[tx][ty] = new DoorTile(this, tx, ty, door.critdir);
		}
		
		for(Point point : this.roomlayout.thugs)
		{
			this.dungeon.enemies.add(new Thug(this.dungeon, this, point.x, point.y));
		}
		
		for(Point point : this.roomlayout.maniacs)
		{
			this.dungeon.enemies.add(new Maniac(this.dungeon, this, point.x, point.y));
		}
		
		for(Point point : this.roomlayout.loafers)
		{
			this.dungeon.enemies.add(new Loafer(this.dungeon, this, point.x, point.y));
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
		
		blueness += 10;
		if(blueness > 255) {blueness = 50;}
	}
	
	public int blueness = 50;
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		for(int tx = 0; tx < this.getTileyWidth(); tx++)
		{
			for(int ty = 0; ty < this.getTileyHeight(); ty++)
			{
				//if(this.hasVisited)
				{
					this.getTile(tx, ty).renderOnMap(graphics, camera);
				}
			}
		}
		
		if(this == this.dungeon.lastRoom)
		{
			blueness += 10;
			if(blueness > 255) {blueness = 50;}
			
			final int UNIT = Tile.SIZE / 2;
			
			int x = ((this.getX() + (this.getWidth() / 2)) / 8) - camera.getX() - (UNIT / 2);
			int y = ((this.getY() + (this.getHeight() / 2)) / 8) - camera.getY() - (UNIT / 2);
			
			graphics.setColor(new Color(0, 0, blueness));
			graphics.fillRoundRect(x, y, UNIT, UNIT, 5);
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
	 * of this room in units of tiles
	 * and relative to the dungeon.
	 * 
	 * @units_of		tiles
	 * @relative_to		dungeon
	 */
	public int getTileyX()
	{
		return this.getRoomyX() * this.getTileyWidth();
	}

	/*
	 * Returns the vertical position
	 * of this room in units of tiles
	 * and relative to the dungeon.
	 * 
	 * @units_of		tiles
	 * @relative_to		dungeon
	 */
	public int getTileyY()
	{
		return this.getRoomyY() * this.getTileyHeight();
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
	
	public int getRandomTileyX()
	{
		return Game.random.nextInt(this.getTileyWidth() - 4) + 2;
	}
	
	public int getRandomTileyY()
	{
		return Game.random.nextInt(this.getTileyHeight() - 4) + 2;
	}
	
	public Room getRoomToTheNorth()
	{
		int rx = this.getRoomyX();
		int ry = this.getRoomyY() - 1;
		
		return this.getDungeon().getRoom(rx, ry);
	}
	
	public Room getRoomToTheSouth()
	{
		int rx = this.getRoomyX();
		int ry = this.getRoomyY() + 1;
		
		return this.getDungeon().getRoom(rx, ry);
	}
	
	public Room getRoomToTheEast()
	{
		int rx = this.getRoomyX() + 1;
		int ry = this.getRoomyY();
		
		return this.getDungeon().getRoom(rx, ry);
	}
	
	public Room getRoomToTheWest()
	{
		int rx = this.getRoomyX() - 1;
		int ry = this.getRoomyY();
		
		return this.getDungeon().getRoom(rx, ry);
	}
	
	public Room makeRoomToTheNorth()
	{
		int rx = this.getRoomyX();
		int ry = this.getRoomyY() - 1;
		
		return new Room(this.getDungeon(), rx, ry);
	}
	
	public Room makeRoomToTheSouth()
	{
		int rx = this.getRoomyX();
		int ry = this.getRoomyY() + 1;
		
		return new Room(this.getDungeon(), rx, ry);
	}
	
	public Room makeRoomToTheEast()
	{
		int rx = this.getRoomyX() + 1;
		int ry = this.getRoomyY();
		
		return new Room(this.getDungeon(), rx, ry);
	}
	
	public Room makeRoomToTheWest()
	{
		int rx = this.getRoomyX() - 1;
		int ry = this.getRoomyY();
		
		return new Room(this.getDungeon(), rx, ry);
	}
	
	public Room makeRoom(Direction direction)
	{
		if(direction == Direction.NORTH)
		{
			return this.makeRoomToTheNorth();
		}
		else if(direction == Direction.SOUTH)
		{
			return this.makeRoomToTheSouth();
		}
		else if(direction == Direction.EAST)
		{
			return this.makeRoomToTheEast();
		}
		else if(direction == Direction.WEST)
		{
			return this.makeRoomToTheWest();
		}
		else
		{
			return null;
		}
		
	}
	
	public Door makeDoorToTheNorth(boolean critpath)
	{
		if(critpath)
		{
			return new Door(this, this.getRoomToTheNorth(), Direction.NORTH);
		}
		else
		{
			return new Door(this, this.getRoomToTheNorth(), Direction.NONE);
		}
	}
	
	public Door makeDoorToTheSouth(boolean critpath)
	{
		if(critpath)
		{
			return new Door(this, this.getRoomToTheSouth(), Direction.SOUTH);
		}
		else
		{
			return new Door(this, this.getRoomToTheSouth(), Direction.NONE);
		}
	}
	
	public Door makeDoorToTheEast(boolean critpath)
	{
		if(critpath)
		{
			return new Door(this, this.getRoomToTheEast(), Direction.EAST);
		}
		else
		{
			return new Door(this, this.getRoomToTheEast(), Direction.NONE);
		}
	}
	
	public Door makeDoorToTheWest(boolean critpath)
	{
		if(critpath)
		{
			return new Door(this, this.getRoomToTheWest(), Direction.WEST);
		}
		else
		{
			return new Door(this, this.getRoomToTheWest(), Direction.NONE);
		}
	}
	
	public Door makeDoor(Direction direction, boolean critpath)
	{
		if(direction == Direction.NORTH)
		{
			return this.makeDoorToTheNorth(critpath);
		}
		else if(direction == Direction.SOUTH)
		{
			return this.makeDoorToTheSouth(critpath);
		}
		else if(direction == Direction.EAST)
		{
			return this.makeDoorToTheEast(critpath);
		}
		else if(direction == Direction.WEST)
		{
			return this.makeDoorToTheWest(critpath);
		}
		else
		{
			return null;
		}
	}
	
	public void addDoor(Door door)
	{
		this.doors.add(door);
	}
	
	public final static int TILEY_WIDTH = 11;
	public final static int TILEY_HEIGHT = 9;
	public final static int WIDTH = Room.TILEY_WIDTH * Tile.SIZE;
	public final static int HEIGHT = Room.TILEY_HEIGHT * Tile.SIZE;

	public void addKey()
	{
		int tx = this.roomlayout.getKeyX();
		int ty = this.roomlayout.getKeyY();
		
		this.dungeon.keys.add(new Key(dungeon, this, tx, ty));
	}
}