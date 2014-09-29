package computc.worlds.dungeons;

import java.util.LinkedList;

import computc.Game;
import computc.worlds.rooms.Room;

public class DungeonSegment
{
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
		return this.majorRooms;
	}
}