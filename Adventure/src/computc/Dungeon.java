package computc;

import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room firstRoom, secondRoom, thirdRoom;
	public HashMap<Integer, Room> rooms = new HashMap<Integer, Room>();
	
	public Dungeon() throws SlickException
	{
		this.firstRoom = new Room(0, 0);
		this.secondRoom = new Room(0, 1);
		this.thirdRoom = new Room(0, 2);
		
		this.firstRoom.connectSouthernRoom(this.secondRoom);
		this.secondRoom.connectSouthernRoom(this.thirdRoom);

		this.addRoom(this.firstRoom);
		this.addRoom(this.secondRoom);
		this.addRoom(this.thirdRoom);
		
		for(Entry<Integer, Room> entry : this.rooms.entrySet())
		{
			System.out.println(entry.getValue());
		}
	}
	
	public void update(int delta)
	{
		//code goes here.
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		this.firstRoom.render(graphics, camera);
		this.secondRoom.render(graphics, camera);
		this.thirdRoom.render(graphics, camera);
	}
	
	public void addRoom(Room room)
	{
		int ry = room.getRoomY();
		this.rooms.put(ry, room);
	}
	
	public Room getRoom(int rx, int ry)
	{
		return this.rooms.get(ry);
	}
}