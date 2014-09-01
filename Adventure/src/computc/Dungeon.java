package computc;

import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room firstRoom, secondRoom, thirdRoom, fourthRoom, fifthRoom;
	public HashMap<RoomKey, Room> rooms = new HashMap<RoomKey, Room>();
	
	public Dungeon() throws SlickException
	{
		this.firstRoom = new Room(0, 0);
		this.secondRoom = new Room(0, 1);
		this.thirdRoom = new Room(0, 2);
		this.fourthRoom = new Room(1, 1);
		this.fifthRoom = new Room(-1, 1);
		
		this.secondRoom.connectNorthernRoom(this.firstRoom);
		this.secondRoom.connectSouthernRoom(this.thirdRoom);
		this.secondRoom.connectEasternRoom(this.fourthRoom);
		this.secondRoom.connectWesternRoom(this.fifthRoom);

		this.addRoom(this.firstRoom);
		this.addRoom(this.secondRoom);
		this.addRoom(this.thirdRoom);
		this.addRoom(this.fourthRoom);
		this.addRoom(this.fifthRoom);
	}
	
	public void update(int delta)
	{
		//code goes here.
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		for(Entry<RoomKey, Room> entry : this.rooms.entrySet())
		{
			entry.getValue().render(graphics, camera);
		}
	}
	
	public void addRoom(Room room)
	{
		int rx = room.getRoomX();
		int ry = room.getRoomY();
		
		this.rooms.put(new RoomKey(rx, ry), room);
	}
	
	public Room getRoom(int rx, int ry)
	{
		return this.rooms.get(new RoomKey(rx, ry));
	}
}