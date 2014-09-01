package computc;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class RoomHashMap
{
	private HashMap<RoomHashMapKey, Room> map = new HashMap<RoomHashMapKey, Room>();
	
	public void add(Room room)
	{
		this.map.put(new RoomHashMapKey(room), room);
	}
	
	public Room get(int rx, int ry)
	{
		return this.map.get(new RoomHashMapKey(rx, ry));
	}
	
	public LinkedList<Room> getAll()
	{
		return new LinkedList<Room>(this.map.values());
	}
}