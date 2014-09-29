package computc.worlds.dungeons;

import java.util.LinkedList;

import computc.Game;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.TileSet;

public class DungeonSegment
{
	private TileSet tileset = Game.assets.getTileSet("./res/tilesets/null.tileset.xml");
	
	private LinkedList<Room> majorRooms = new LinkedList<Room>();
	private LinkedList<Room> minorRooms = new LinkedList<Room>();
	
	public void addMajorRoom(Room room)
	{
		this.majorRooms.add(room);
		room.setDungeonSegment(this);
	}
	
	public void addMinorRoom(Room room)
	{
		this.minorRooms.add(room);
		room.setDungeonSegment(this);
	}
	
	public Room getFirstMajorRoom()
	{
		return this.majorRooms.getFirst();
	}
	
	public Room getLastMajorRoom()
	{
		return this.majorRooms.getLast();
	}
	
	public Room getRandomMinorRoom()
	{
		return this.minorRooms.get(Game.random.nextInt(this.minorRooms.size()));
	}
	
	public LinkedList<Room> getAllMajorRooms()
	{
		return this.majorRooms;
	}
	
	public LinkedList<Room> getAllMinorRooms()
	{
		return this.minorRooms;
	}
	
	public LinkedList<Room> getAllRooms()
	{
		LinkedList<Room> rooms = new LinkedList<Room>();
		rooms.addAll(this.majorRooms);
		rooms.addAll(this.minorRooms);
		return rooms;
	}
	
	public TileSet getTileSet()
	{
		return this.tileset;
	}
	
	public void setTileSet(TileSet tileset)
	{
		this.tileset = tileset;
	}
}