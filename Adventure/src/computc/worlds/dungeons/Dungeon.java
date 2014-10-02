package computc.worlds.dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Graphics;

import computc.Game;
import computc.cameras.Camera;
import computc.entities.Coin;
import computc.entities.Enemy;
import computc.entities.Entity;
import computc.entities.Key;
import computc.entities.OldMan;
import computc.worlds.rooms.Room;
import computc.worlds.rooms.RoomLayout;
import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileSubSet;
import computc.worlds.tiles.TileSet;

public abstract class Dungeon
{
	protected HashMap<String, Room> rooms = new HashMap<String, Room>();
	public LinkedList<Key> keys = new LinkedList<Key>();
	public LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	protected Room firstRoom;
	public Room lastRoom;
	public OldMan oldman;
	
	ArrayList<TileSet> tilesets = new ArrayList<TileSet>();
	LinkedList<RoomLayout> randomRoomLayouts = new LinkedList<RoomLayout>();
	HashMap<String, RoomLayout> specialRoomLayouts = new HashMap<String, RoomLayout>();
	
	public Dungeon()
	{
		this.tilesets.add(Game.assets.getTileSet("./res/tilesets/rocky.tileset.xml"));
		this.tilesets.add(Game.assets.getTileSet("./res/tilesets/grassy.tileset.xml"));
		this.tilesets.add(Game.assets.getTileSet("./res/tilesets/icy.tileset.xml"));

		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/arena.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/blob.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/brackets.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/corners.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/crumbs.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/diagonals.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/dot.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/fivedots.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/fourdots.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/grid.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/loop.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/niches.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/oval.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/spiral.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/square.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/threedots.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/threelines.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/twodots.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/twolines.room.tmx"));
		this.specialRoomLayouts.put("first room", Game.assets.getRoomLayout("./res/rooms/empty.room.tmx"));
		this.specialRoomLayouts.put("last room", Game.assets.getRoomLayout("./res/rooms/clamp.room.tmx"));
	}
	
	public void initiate()
	{
		for(Room room : this.getAllRooms())
		{
			room.initiate();
		}
	}
	
	public void update(int delta)
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			e.update(delta);
				if(e.isDead())
				{
					enemies.remove(i);
					i--;
				}
		}
	}

	public void render(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.render(graphics, camera);
		}

		for(Enemy enemy: this.getAllEnemies())
		{
			enemy.render(graphics, camera);
		}
		
		for(Key key : this.keys)
		{
			key.render(graphics, camera);
		}
		
		if(this.oldman != null)
		{
			this.oldman.render(graphics, camera);
		}
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.renderOnMap(graphics, camera);
		}
	}
	
	public LinkedList<Enemy> getAllEnemies()
	{
		return this.enemies;
	}
	
	public void addRoom(Room room)
	{
		int rx = room.getRoomyX();
		int ry = room.getRoomyY();
		
		if(this.hasRoom(rx, ry))
		{
			throw new DungeonException();
		}
		else
		{
			this.rooms.put(rx + ":" + ry, room);
		}
	}
	
	public Room getRoom(int rx, int ry)
	{
		return this.rooms.get(rx + ":" + ry);
	}
	
	public boolean hasRoom(int rx, int ry)
	{
		return this.rooms.containsKey(rx + ":" + ry);
	}
	
	public LinkedList<Room> getAllRooms()
	{
		return new LinkedList<Room>(this.rooms.values());
	}
	
	public Room getFirstRoom()
	{
		return this.firstRoom;
	}
	
	public void setFirstRoom(Room firstRoom)
	{
		this.firstRoom = firstRoom;
	}
	
	public Tile getTile(float x, float y)
	{
		int rx = (int)(Math.floor(x / Room.WIDTH));
		int ry = (int)(Math.floor(y / Room.HEIGHT));
		
		int tx = (int)(Math.floor((x - (rx * Room.WIDTH)) / Tile.SIZE));
		int ty = (int)(Math.floor((y - (ry * Room.HEIGHT)) / Tile.SIZE));
		
		return this.getRoom(rx, ry).getTile(tx, ty);
	}
	
	public TileSet getTileSet(int index)
	{
		return this.tilesets.get(index);
	}
	
	public TileSet getRandomTileSet()
	{
		return this.tilesets.get(Game.random.nextInt(tilesets.size()));
	}
	
	public RoomLayout getRandomRoomLayout()
	{
		RoomLayout randomRoomLayout = this.randomRoomLayouts.pop();
		this.randomRoomLayouts.add(randomRoomLayout);
		return randomRoomLayout;
	}
	
	public RoomLayout getSpecialRoomLayout(String type)
	{
		return this.specialRoomLayouts.get(type);
	}
}