package computc;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class RoomGrid
{
	private HashMap<RoomGridKey, Room> map = new HashMap<RoomGridKey, Room>();
	
	public void add(int x, int y, Room room)
	{
		if(this.has(x, y))
		{
			throw new RoomGridException();
		}
		else
		{
			this.map.put(new RoomGridKey(x, y), room);
		}
	}
	
	public Room get(int x, int y)
	{
		return this.map.get(new RoomGridKey(x, y));
	}
	
	public boolean has(int x, int y)
	{
		return this.map.containsKey(new RoomGridKey(x, y));
	}
	
	public LinkedList<Room> getAll()
	{
		return new LinkedList<Room>(this.map.values());
	}
}